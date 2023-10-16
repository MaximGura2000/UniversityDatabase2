package com.example.universitydatabase.abl;

import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoOut;
import com.example.universitydatabase.entity.Subject;
import com.example.universitydatabase.exception.SubjectRuntimeException;
import com.example.universitydatabase.exception.SubjectRuntimeException.Error;
import java.util.HashMap;
import javax.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SubjectAbl {

  private static final Logger LOGGER = LogManager.getLogger(SubjectAbl.class);

  private final Validator validator;

  public SubjectAbl(Validator validator) {
    this.validator = validator;
  }

  public SubjectCreateDtoOut create(SubjectCreateDtoIn dtoIn) {
    LOGGER.info("Start subject create");
    SubjectCreateDtoOut dtoOut = new SubjectCreateDtoOut();

    // 1 validate dtoIn
    if (!this.validator.validate(dtoIn).isEmpty()) {
      throw new SubjectRuntimeException(Error.INVALID_DTO_IN_CREATE, new HashMap<>());
    }

    dtoOut.setSubject(new Subject());
    dtoOut.getSubject().setSubjectName(dtoIn.getSubjectName());
    dtoOut.getSubject().setSubjectCredits(dtoIn.getSubjectCredits());

    LOGGER.info("Subject create finished");
    return dtoOut;
  }
}
