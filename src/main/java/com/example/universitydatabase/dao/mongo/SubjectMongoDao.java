package com.example.universitydatabase.dao.mongo;

import com.example.universitydatabase.entity.Subject;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
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
}
