package com.example.universitydatabase.abl;

import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoOut;
import com.example.universitydatabase.dao.mongo.SubjectMongoDao;
import com.example.universitydatabase.entity.Subject;
import com.example.universitydatabase.exception.DatastoreRuntimeException;
import com.example.universitydatabase.exception.SubjectRuntimeException;
import com.example.universitydatabase.exception.SubjectRuntimeException.Error;
import com.mongodb.MongoException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SubjectAbl {

  private static final Logger LOGGER = LogManager.getLogger(SubjectAbl.class);

  private final Validator validator;
  private final SubjectMongoDao subjectMongoDao;

  public SubjectAbl(Validator validator, SubjectMongoDao subjectMongoDao) {
    this.validator = Objects.requireNonNull(validator, "Missing Validator.");
    this.subjectMongoDao = Objects.requireNonNull(subjectMongoDao, "Missing SubjectMongoDao.");
  }

  public SubjectCreateDtoOut create(SubjectCreateDtoIn dtoIn) {
    LOGGER.info("Start subject create");
    SubjectCreateDtoOut dtoOut = new SubjectCreateDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectCreateDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectCreateDtoIn> result: validationResult) {
        params.put(result.getMessage(), result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_CREATE, params);
    }

    Subject subject = new Subject();
    subject.setSubjectName(dtoIn.getSubjectName());
    subject.setSubjectCredits(dtoIn.getSubjectCredits());
    subject.setShortName(dtoIn.getShortName());

    //TODO add unikness of shortName

    try {
      this.subjectMongoDao.create(subject);
      dtoOut.setSubject(subject);
    } catch (DatastoreRuntimeException | MongoException exception) {
      LOGGER.info("Error while adding subject to MongoDb");
      throw new SubjectRuntimeException(Error.MONGO_ERROR_CREATE, exception);
    }


    LOGGER.info("Subject create finished");
    return dtoOut;
  }
}
