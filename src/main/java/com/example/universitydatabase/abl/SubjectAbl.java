package com.example.universitydatabase.abl;

import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoOut;
import com.example.universitydatabase.api.dto.subject.SubjectDeleteDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectDeleteDtoOut;
import com.example.universitydatabase.api.dto.subject.SubjectGetDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectGetDtoOut;
import com.example.universitydatabase.api.dto.subject.SubjectListDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectListDtoOut;
import com.example.universitydatabase.api.dto.subject.SubjectUpdateDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectUpdateDtoOut;
import com.example.universitydatabase.api.dto.subject.SubjectUpdateShortNameDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectUpdateShortNameDtoOut;
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
  public static final String RECEIVED = ", received: ";

  private final Validator validator;
  private final SubjectMongoDao subjectMongoDao;

  public SubjectAbl(Validator validator, SubjectMongoDao subjectMongoDao) {
    this.validator = Objects.requireNonNull(validator, "Missing Validator.");
    this.subjectMongoDao = Objects.requireNonNull(subjectMongoDao, "Missing SubjectMongoDao.");
  }

  /**
   * Create subject object based on subjectName, shortName and subjectCredits
   * @param dtoIn {@link SubjectCreateDtoIn}
   * @return {@link SubjectCreateDtoOut} with created subject
   */
  public SubjectCreateDtoOut create(SubjectCreateDtoIn dtoIn) {
    LOGGER.info("Start subject create");
    SubjectCreateDtoOut dtoOut = new SubjectCreateDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectCreateDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectCreateDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + RECEIVED + result.getInvalidValue());
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

  /**
   * Get subject object from MongoDB
   * @param dtoIn {@link SubjectGetDtoIn}
   * @return {@link SubjectGetDtoOut} with created subject
   */
  public SubjectGetDtoOut get(SubjectGetDtoIn dtoIn) {
    LOGGER.info("Start subject get");
    SubjectGetDtoOut dtoOut = new SubjectGetDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectGetDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectGetDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + RECEIVED + result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_GET, params);
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

  /**
   * List subject objects form MongoDB based on subject credits
   * @param dtoIn {@link SubjectListDtoIn}
   * @return {@link SubjectListDtoOut} with list of subject
   */
  public SubjectListDtoOut list(SubjectListDtoIn dtoIn) {
    LOGGER.info("Start subject get");
    SubjectListDtoOut dtoOut = new SubjectListDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectListDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectListDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + RECEIVED + result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_LIST, params);
    }
    if (dtoIn.getMinCredit() != null && dtoIn.getMaxCredit() != null && dtoIn.getMinCredit() > dtoIn.getMaxCredit()) {
      throw new SubjectRuntimeException(Error.INVALID_MIN_AND_MAX_CREDIT_VALUES.getCode(), Error.INVALID_MIN_AND_MAX_CREDIT_VALUES.getMessage(), dtoIn.getMinCredit(), dtoIn.getMaxCredit());
    }
    // get list of subject
    try {
      dtoOut.setSubjectList(this.subjectMongoDao.listByCriteria(dtoIn));
    } catch (DatastoreRuntimeException | MongoException exception) {
      LOGGER.info("Error while list subjects from MongoDb");
      throw new SubjectRuntimeException(Error.MONGO_ERROR_LIST, exception);
    }

    return dtoOut;
  }

  /**
   * Update subject object form MongoDB
   * @param dtoIn {@link SubjectUpdateDtoIn}
   * @return {@link SubjectUpdateDtoOut} with modified of subject
   */
  public SubjectUpdateDtoOut update(SubjectUpdateDtoIn dtoIn) {
    SubjectUpdateDtoOut dtoOut = new SubjectUpdateDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectUpdateDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectUpdateDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + RECEIVED + result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_UPDATE, params);
    }
    if (dtoIn.getId() == null && dtoIn.getShortName() == null) {
      throw new SubjectRuntimeException(Error.MISSING_ID_AND_SHORT_NAME_UPDATE.getCode(), Error.MISSING_ID_AND_SHORT_NAME_UPDATE.getMessage());
    }

    // 2 get subject from mongo
    Subject subject = null;
    if (dtoIn.getId() != null) {
      subject = subjectMongoDao.get(dtoIn.getId());
      if (subject == null) {
        throw new SubjectRuntimeException(Error.MISSING_SUBJECT_FROM_ID_UPDATE.getCode(), Error.MISSING_SUBJECT_FROM_ID_UPDATE.getMessage(), dtoIn.getId());
      }
    } else {
      subject = subjectMongoDao.getByShortName(dtoIn.getShortName());
      if (subject == null) {
        throw new SubjectRuntimeException(Error.MISSING_SUBJECT_FROM_SHORT_NAME_UPDATE.getCode(), Error.MISSING_SUBJECT_FROM_SHORT_NAME_UPDATE.getMessage(), dtoIn.getShortName());
      }
    }

    // 3 modify subject
    if (dtoIn.getSubjectName() != null) {
      subject.setSubjectName(dtoIn.getSubjectName());
    }
    if (dtoIn.getSubjectCredits() != null) {
      subject.setSubjectCredits(dtoIn.getSubjectCredits());
    }

    // 4 store subject
    try {
      subject = this.subjectMongoDao.update(subject);
    } catch (DatastoreRuntimeException | MongoException exception) {
      LOGGER.info("Error while modifying subject at MongoDb");
      throw new SubjectRuntimeException(Error.MONGO_ERROR_UPDATE, exception);
    }

    dtoOut.setSubject(subject);

    return dtoOut;
  }

  /**
   * Delete subject object form MongoDB
   * @param dtoIn {@link SubjectUpdateShortNameDtoIn}
   * @return {@link SubjectUpdateShortNameDtoOut} with modified of subject
   */
  public SubjectDeleteDtoOut delete(SubjectDeleteDtoIn dtoIn) {
    SubjectDeleteDtoOut dtoOut = new SubjectDeleteDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectDeleteDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectDeleteDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + RECEIVED + result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_DELETE, params);
    }
    if (dtoIn.getId() == null && dtoIn.getShortName() == null) {
      throw new SubjectRuntimeException(Error.MISSING_ID_AND_SHORT_NAME_DELETE.getCode(), Error.MISSING_ID_AND_SHORT_NAME_DELETE.getMessage());
    }

    // 2 get subject from mongo
    Subject subject = null;
    if (dtoIn.getId() != null) {
      subject = subjectMongoDao.get(dtoIn.getId());
      if (subject == null) {
        throw new SubjectRuntimeException(Error.MISSING_SUBJECT_FROM_ID_UPDATE.getCode(), Error.MISSING_SUBJECT_FROM_ID_UPDATE.getMessage(), dtoIn.getId());
      }
    } else {
      subject = subjectMongoDao.getByShortName(dtoIn.getShortName());
      if (subject == null) {
        throw new SubjectRuntimeException(Error.MISSING_SUBJECT_FROM_SHORT_NAME_UPDATE.getCode(), Error.MISSING_SUBJECT_FROM_SHORT_NAME_UPDATE.getMessage(), dtoIn.getShortName());
      }
    }

    // 3 delete subject
    try {
      this.subjectMongoDao.delete(subject);
      dtoOut.setSubject(subject);
    } catch (DatastoreRuntimeException | MongoException exception) {
      LOGGER.info("Error while modifying subject at MongoDb");
      throw new SubjectRuntimeException(Error.MONGO_ERROR_UPDATE, exception);
    }

    return dtoOut;
  }

  /**
   * Update subject's shortName at MongoDB
   * @param dtoIn {@link SubjectUpdateShortNameDtoIn}
   * @return {@link SubjectUpdateShortNameDtoOut} with modified of subject
   */
  public SubjectUpdateShortNameDtoOut updateShortName(SubjectUpdateShortNameDtoIn dtoIn) {
    SubjectUpdateShortNameDtoOut dtoOut = new SubjectUpdateShortNameDtoOut();

    // 1 validate dtoIn
    Set<ConstraintViolation<SubjectUpdateShortNameDtoIn>> validationResult = this.validator.validate(dtoIn);
    if (!validationResult.isEmpty()) {
      HashMap<String, Object> params = new HashMap<>();
      for (ConstraintViolation<SubjectUpdateShortNameDtoIn> result : validationResult) {
        params.put(result.getPropertyPath().toString(), result.getMessage() + RECEIVED + result.getInvalidValue());
      }
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_UPDATE_SHORT_NAME, params);
    }

    // 2 get subject from mongo
    Subject subject = this.subjectMongoDao.get(dtoIn.getId());

    if (subject == null) {
      throw new SubjectRuntimeException(Error.MISSING_SUBJECT_FROM_ID_UPDATE_SHORT_NAME.getCode(), Error.MISSING_SUBJECT_FROM_ID_UPDATE_SHORT_NAME.getMessage(), dtoIn.getId());
    }

    // 3 update subject
    subject.setShortName(dtoIn.getShortName());

    // 4 store subject to mongo
    try {
      this.subjectMongoDao.update(subject);
      dtoOut.setSubject(subject);
    } catch (DatastoreRuntimeException | MongoException exception) {
      LOGGER.info("Error while modifying subject at MongoDb");
      throw new SubjectRuntimeException(Error.MONGO_ERROR_UPDATE_SHORT_NAME, exception);
    }

    return dtoOut;
  }
}
