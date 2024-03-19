package com.example.universitydatabase.abl;

import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoOut;
import com.example.universitydatabase.api.dto.subject.SubjectGetDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectGetDtoOut;
import com.example.universitydatabase.api.dto.subject.SubjectListDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectListDtoOut;
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
      for (ConstraintViolation<SubjectCreateDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + ", received: " + result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_CREATE, params);
    }

    Subject subject = new Subject();
    subject.setSubjectName(dtoIn.getSubjectName());
    subject.setSubjectCredits(dtoIn.getSubjectCredits());
    subject.setShortName(dtoIn.getShortName());

    Subject storedSubject = this.subjectMongoDao.getByShortName(dtoIn.getShortName());
    if (storedSubject != null) {
      throw new SubjectRuntimeException(Error.ALREADY_EXIST_CREATE.getCode(), Error.ALREADY_EXIST_CREATE.getMessage(), dtoIn.getShortName());
    }

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

  public SubjectGetDtoOut get(SubjectGetDtoIn dtoIn) {
    LOGGER.info("Start subject get");
    SubjectGetDtoOut dtoOut = new SubjectGetDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectGetDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectGetDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + ", received: " + result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_CREATE, params);
    }
    if (dtoIn.getId() == null && dtoIn.getShortName() == null) {
      throw new SubjectRuntimeException(Error.MISSING_ID_AND_SHORT_NAME_GET.getCode(), Error.MISSING_ID_AND_SHORT_NAME_GET.getMessage());
    }
    // get from mongo
    try {
      Subject subject = null;
      if (dtoIn.getId() != null) {
        subject = this.subjectMongoDao.get(dtoIn.getId());
        if (subject == null) {
          throw new SubjectRuntimeException(Error.MISSING_SUBJECT_FROM_ID_GET.getCode(), Error.MISSING_SUBJECT_FROM_ID_GET.getMessage(), dtoIn.getId());
        }
      } else {
        subject = this.subjectMongoDao.getByShortName(dtoIn.getShortName());
        if (subject == null) {
          throw new SubjectRuntimeException(Error.MISSING_SUBJECT_FROM_SHORT_NAME_GET.getCode(), Error.MISSING_SUBJECT_FROM_SHORT_NAME_GET.getMessage(), dtoIn.getShortName());
        }
      }
      dtoOut.setSubject(subject);
    } catch (DatastoreRuntimeException | MongoException exception) {
      LOGGER.info("Unexpected error while getting subject from MongoDb");
      throw new SubjectRuntimeException(Error.MONGO_ERROR_GET, exception);
    }

    return dtoOut;
  }

  public SubjectListDtoOut list(SubjectListDtoIn dtoIn) {
    LOGGER.info("Start subject get");
    SubjectListDtoOut dtoOut = new SubjectListDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectListDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectListDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + ", received: " + result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_CREATE, params);
    }
    if (dtoIn.getMinCredit() != null && dtoIn.getMaxCredit() != null && dtoIn.getMinCredit() > dtoIn.getMaxCredit()) {
      throw new SubjectRuntimeException(Error.INVALID_MIN_AND_MAX_CREDIT_VALUES.getCode(), Error.INVALID_MIN_AND_MAX_CREDIT_VALUES.getMessage(), dtoIn.getMinCredit(), dtoIn.getMaxCredit());
    }
    // get list of subject
    dtoOut.setSubjectList(this.subjectMongoDao.listByCriteria(dtoIn));

    return dtoOut;
  }
}
