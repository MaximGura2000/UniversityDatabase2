package com.example.universitydatabase.dao.mongo;

import com.example.universitydatabase.api.dto.subject.SubjectListDtoIn;
import com.example.universitydatabase.entity.Subject;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class SubjectMongoDao extends AbstractMongoDao<Subject> {

  private static final String SUBJECT_NAME = "subjectName";
  private static final String SUBJECT_SHORT_NAME = "shortName";
  private static final String SUBJECT_CREDITS = "subjectCredits";

  public SubjectMongoDao(MongoTemplate mongoTemplate) {
    super(Subject.class, mongoTemplate);
  }

  @Override
  public void createSchema() {
    createIndex(new Index().on(SUBJECT_SHORT_NAME, Direction.ASC).unique());
    createIndex(new Index().on(SUBJECT_NAME, Direction.ASC).on(SUBJECT_CREDITS, Direction.ASC).unique());
  }

  /**
   * get subject from MongoDB based on its shortName
   * @param shortName of subject
   * @return {@link Subject}
   */
  public Subject getByShortName(String shortName) {
    Query query = new Query().addCriteria(Criteria.where(SUBJECT_SHORT_NAME).is(shortName));

    return super.findOne(query);
  }

  /**
   * list Subject from MongoDB based on some criteria
   * @param dtoIn with list criteria
   * @return list of {@link Subject}
   */
  public List<Subject> listByCriteria(SubjectListDtoIn dtoIn) {
    Query query = new Query();
    Criteria criteria = new Criteria();

    if (dtoIn.getMinCredit() != null && dtoIn.getMaxCredit() != null) {
      criteria.and(SUBJECT_CREDITS).gte(dtoIn.getMinCredit()).lte(dtoIn.getMaxCredit());
    } else if (dtoIn.getMinCredit() != null) {
      criteria.and(SUBJECT_CREDITS).gte(dtoIn.getMinCredit());
    } else if (dtoIn.getMaxCredit() != null) {
      criteria.and(SUBJECT_CREDITS).lte(dtoIn.getMaxCredit());
    }
    query.addCriteria(criteria);
    if (dtoIn.isSortByCredits()) {
      query.with(Sort.by(Direction.ASC, SUBJECT_CREDITS));
    }
    if (dtoIn.isSortByName()) {
      query.with(Sort.by(Direction.ASC, SUBJECT_NAME));
    }
    if (dtoIn.isSortByShortName()) {
      query.with(Sort.by(Direction.ASC, SUBJECT_SHORT_NAME));
    }

    return super.find(query, dtoIn.getListPage());
  }
}
