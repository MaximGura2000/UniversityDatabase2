package com.example.universitydatabase.abl;

import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoOut;
import com.example.universitydatabase.entity.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SubjectAbl {

  private static final Logger LOGGER = LogManager.getLogger(SubjectAbl.class);

  public SubjectCreateDtoOut create(SubjectCreateDtoIn dtoIn) {
    LOGGER.info("Start subject create");
    SubjectCreateDtoOut dtoOut = new SubjectCreateDtoOut();

    dtoOut.setSubject(new Subject());
    dtoOut.getSubject().setSubjectName(dtoIn.getSubjectName());
    LOGGER.info("Subject create finished");
    return dtoOut;
  }
}
