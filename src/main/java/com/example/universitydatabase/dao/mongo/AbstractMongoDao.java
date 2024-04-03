package com.example.universitydatabase.dao.mongo;

import com.example.universitydatabase.dao.ListPage;
import com.example.universitydatabase.entity.AbstractMongoEntity;
import com.example.universitydatabase.enums.DatastoreConstants;
import com.example.universitydatabase.exception.DatastoreRuntimeException;
import com.example.universitydatabase.exception.DatastoreRuntimeException.Error;
import com.mongodb.DuplicateKeyException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BasicBSONEncoder;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

/**
 * AbstractMongoDao with default CRUD mongo commands
 */
@Configuration
public abstract class AbstractMongoDao<T extends AbstractMongoEntity> {

  private static final Logger LOGGER = LogManager.getLogger(AbstractMongoDao.class);

  protected static final String ATTR_ID = "_id";

  //Class which is handled by this implementation. Each dao has to define it's entityClass class.
  private Class<T> entityClass;

  private final MongoTemplate mongoTemplate;
  private MongoCollection<Document> collection;

  /**
   * Creates a new AbstractMongoDao
   * @param collectionClass class
   * @param mongoTemplate defined by subApp configuration
   */
  protected AbstractMongoDao(Class<T> collectionClass, MongoTemplate mongoTemplate) {
    this.mongoTemplate = Objects.requireNonNull(mongoTemplate);

    this.setEntityClass(collectionClass);

    if (!mongoTemplate.collectionExists(collectionClass)) {
      this.mongoTemplate.createCollection(collectionClass);
      createSchema();
    }

    this.collection = mongoTemplate.getCollection(collectionClass.getSimpleName());
  }

  /**
   * get class for certain MongoDao class entity
   * @return class
   */
  public Class<T> getEntityClass() {
    return entityClass;
  }

  /**
   * set class for certain MongoDao entity
   */
  protected void setEntityClass(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  /**
   * @return {@link MongoTemplate}
   */
  protected MongoOperations getMongoDbTemplate() {
    return this.mongoTemplate;
  }

  /**
   * default create command for store object to MongoDb
   * @param entity object to be stored
   * @return stored object
   */
  public T create(T entity) {
    Assert.notNull(entity, "Entity cannot be null.");
    return insertOne(entity);
  }

  /**
   * default get command for receiving object from MongoDb based on id
   * @param id of object
   * @return object from MongoDB
   */
  public T get(String id) {
    Assert.notNull(id, "Id cannot be null");
    return findOne(idQuery(id));
  }

  /**
   * method for receiving object from MongoDb based on query
   * @param query with certain search criteria
   * @return object from MongoDB
   */
  protected T findOne(Query query) {
    Assert.notNull(query, "Query must not be null.");
    return mongoTemplate.findOne(query, getEntityClass());
  }

  /**
   * method for receiving objects from MongoDb based on query
   * @param query with certain search criteria
   * @return list of objects from MongoDB
   */
  protected List<T> find(Query query, final ListPage page) {
    Assert.notNull(query, "Query must not be null.");

    ListPage listPage = page != null ? page : new ListPage();

    if (listPage.getPageIndex() == null) {
      listPage.setPageIndex(ListPage.DEFAULT_PAGE_INDEX);
    }
    if (listPage.getPageSize() == null) {
      listPage.setPageSize(ListPage.DEFAULT_PAGE_SIZE);
    }

    Pageable pageable = PageRequest.of(listPage.getPageIndex(), listPage.getPageSize());
    return mongoTemplate.find(query.with(pageable), getEntityClass());
  }

  /**
   * default update command to modify object at MongoDb
   * @param entity of object
   * @return object from MongoDB
   */
  public T update(T entity) {
    return findOneAndUpdate(getIdEntityQuery(entity), entity);
  }

  /**
   * default delete command
   * @param entity of object
   */
  public void delete(T entity) throws DatastoreRuntimeException {
    deleteOne(getIdEntityQuery(entity));
  }

  /**
   * default update command to modify single object at MongoDb
   * @param query of searching object
   * @param entity with modified data
   * @return modified entity from mongoDB
   */
  protected T findOneAndUpdate(Query query, T entity) {
    prepareUpdate(entity);
    T updateResult = null;
    try {
      query.limit(1);
      updateResult = mongoTemplate.findAndReplace(query, entity, FindAndReplaceOptions.options().returnNew());
    } catch (Exception e) {
      undoUpdate(entity);
      throw e;
    }

    return updateResult;
  }

  /**
   * default update command to modify several objects at MongoDb
   * @param query of searching object
   * @param update data to modify objects
   */
  protected void updateMulti(Query query, Update update) {
    getMongoDbTemplate().updateMulti(query, update, getEntityClass());
  }

  /**
   * revert modify counter at entity
   * @param entity to modify
   */
  protected void undoUpdate(T entity) {
    int currentModifyNumber = entity.getModifyNumber();
    entity.setModifyNumber(currentModifyNumber - 1);
  }

  /**
   * update modify counter at entity
   * @param entity to modify
   */
  protected void prepareUpdate(T entity) {
    setSystemAttributesForUpdate(entity);
    checkObjectSize(entity);
  }

  /**
   * default delete command to remove one object at MongoDb
   * @param query of searching object
   */
  protected void deleteOne(Query query) {
    query.limit(1);
    DeleteResult result = mongoTemplate.remove(query, getEntityClass());
    LOGGER.info("Deleted count: {}", result);
  }

  /**
   * creates query based on object id
   * @param id of MongoDB object
   * @return query with id
   */
  private Query idQuery(String id) {
    return new Query().addCriteria(Criteria.where(ATTR_ID).is(id));
  }

  /**
   * create schema with index at MongoDB
   */
  abstract void createSchema();

  /**
   * create indexes at MongoDB
   * @param index to be added
   */
  protected void createIndex(IndexDefinition index) {
    mongoTemplate.indexOps(getEntityClass()).ensureIndex(index);
  }

  /**
   * Helper method to create query for finding entity by its unique id of the given entity.
   *
   * @param entity entity
   * @return query with  appropriate conditions
   */
  protected Query getIdEntityQuery(T entity) {
    Assert.notNull(entity, "entity cannot be null.");

    return new Query().addCriteria(Criteria.where(ATTR_ID).is(entity.getId()));
  }

  /**
   * default insert command for one object
   * @param entity to create
   * @return entity
   */
  private T insertOne(T entity) {
    LOGGER.info("Inserting one object to {} collection started.", this.collection.getNamespace().getCollectionName());
    // Check maximum size of the object to be stored
    checkObjectSize(entity);
    // Check the collection size, so that the current object won't exceed the noi.
    checkObjectNumber(this.collection, 1);
    setSystemAttributesForCreate(entity);
    try {
      mongoTemplate.insert(entity, mongoTemplate.getCollectionName(entity.getClass()));
    } catch (DuplicateKeyException e) {
      throw new DatastoreRuntimeException(Error.NOT_UNIQUE_ID, e, new HashMap<>());
    } catch (DataAccessException e) {
      throw new DatastoreRuntimeException(Error.DATASTORE_UNEXPECTED_ERROR, e, new HashMap<>());
    }
    LOGGER.info("Inserting one object to {} collection finished.", this.collection.getNamespace().getCollectionName());
    return entity;
  }

  /**
   * check if object size is not too big
   * @param entity to be stored
   */
  private void checkObjectSize(T entity) {
    Document document = (Document) getMongoDbTemplate().getConverter().convertToMongoType(entity);
    BasicBSONEncoder encoder = new BasicBSONEncoder();
    byte[] bson = encoder.encode(new BasicBSONObject(document));
    int bsonSize = bson.length;

    if (entity.getId() == null) {
      bsonSize += 17;
    }
    if (bsonSize > DatastoreConstants.DEFAULT_MAXIMUM_SOI) {
      throw new DatastoreRuntimeException(Error.SOI_EXCEEDED, new HashMap<>());
    }
  }

  /**
   * check if capacity of objects is not full
   * @param collection to be checked
   * @param addCount number of new objects to be stored
   */
  private void checkObjectNumber(MongoCollection<Document> collection, int addCount) {
    long collectionSize = collection.countDocuments();
    if (collectionSize + addCount > DatastoreConstants.DEFAULT_MAXIMUM_NOI) {
      throw new DatastoreRuntimeException(Error.NOI_EXCEEDED, new HashMap<>());
    }
  }

  /**
   * set created time, modified time and modified number to entity
   * @param entity to be created
   */
  private void setSystemAttributesForCreate(T entity) {
    entity.setCreateEntityTime(Instant.now());
    entity.setModifyEntityTime(Instant.now());
    entity.setModifyNumber(0);
  }

  /**
   * update modified time and modified number for updated object
   * @param entity to be modified
   */
  private void setSystemAttributesForUpdate(T entity) {
    entity.setModifyEntityTime(Instant.now());
    entity.setModifyNumber(entity.getModifyNumber() + 1);
  }
}
