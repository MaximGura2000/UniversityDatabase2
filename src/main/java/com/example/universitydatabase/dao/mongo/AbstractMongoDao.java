package com.example.universitydatabase.dao.mongo;

import com.example.universitydatabase.entity.AbstractMongoEntity;
import com.example.universitydatabase.enums.DatastoreConstants;
import com.example.universitydatabase.exception.DatascoreRuntimeException;
import com.example.universitydatabase.exception.DatascoreRuntimeException.Error;
import com.mongodb.DuplicateKeyException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.BasicBSONEncoder;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Configuration
public abstract class AbstractMongoDao<T extends AbstractMongoEntity> {

  private static final Logger LOGGER = LogManager.getLogger(AbstractMongoDao.class);

  protected static final String ATTR_ID = "_id";

  //Class which is handled by this implementation. Each dao has to define it's entityClass class.
  private Class<T> entityClass;

  private final MongoTemplate mongoTemplate;
  private MongoCollection<Document> collection;

  public AbstractMongoDao(Class<T> collectionClass, MongoTemplate mongoTemplate) {
    this.mongoTemplate = Objects.requireNonNull(mongoTemplate);

    this.setEntityClass(collectionClass);

    if (!mongoTemplate.collectionExists(collectionClass)) {
      this.mongoTemplate.createCollection(collectionClass);
      createSchema();
    }

    this.collection = mongoTemplate.getCollection(collectionClass.getSimpleName());
  }

  public Class<T> getEntityClass() {
    return entityClass;
  }

  protected void setEntityClass(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  protected MongoOperations getMongoDbTemplate() {
    return this.mongoTemplate;
  }

  public T create(T entity) {
    Assert.notNull(entity, "Entity cannot be null.");
    return insertOne(entity);
  }

  abstract void createSchema();

  protected void createIndex(IndexDefinition index) {
    mongoTemplate.indexOps(getEntityClass()).ensureIndex(index);
  }

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
      throw new DatascoreRuntimeException(Error.NOT_UNIQUE_ID, e);
    } catch (DataAccessException e) {
      throw new DatascoreRuntimeException(Error.DATASTORE_UNEXPECTED_ERROR, e);
    }
    LOGGER.info("Inserting one object to {} collection finished.", this.collection.getNamespace().getCollectionName());
    return entity;
  }

  private void checkObjectSize(T entity) {
    Document document = (Document) getMongoDbTemplate().getConverter().convertToMongoType(entity);
    BasicBSONEncoder encoder = new BasicBSONEncoder();
    byte[] bson = encoder.encode(new BasicBSONObject(document));
    int bsonSize = bson.length;

    if (entity.getId() == null) {
      bsonSize += 17;
    }
    if (bsonSize > DatastoreConstants.DEFAULT_MAXIMUM_SOI) {
      throw new DatascoreRuntimeException(Error.SOI_EXCEEDED);
    }
  }

  private void checkObjectNumber(MongoCollection<Document> collection, int addCount) {
    long collectionSize = collection.countDocuments();
    if (collectionSize + addCount > DatastoreConstants.DEFAULT_MAXIMUM_NOI) {
      throw new DatascoreRuntimeException(Error.NOI_EXCEEDED);
    }
  }

  private void setSystemAttributesForCreate(T entity) {
    entity.setCeTime(Instant.now());
    entity.setMeTime(Instant.now());
    entity.setModifyNumber(0);
  }

  private void setSystemAttributesForUpdate(T entity) {
    entity.setMeTime(Instant.now());
    entity.setModifyNumber(entity.getModifyNumber() + 1);
  }
}
