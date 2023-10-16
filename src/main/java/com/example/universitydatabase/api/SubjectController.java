package com.example.universitydatabase.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.example.universitydatabase.abl.SubjectAbl;
import com.example.universitydatabase.annotation.Command;
import com.example.universitydatabase.annotation.CommandController;
import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoIn;
import com.example.universitydatabase.api.dto.subject.SubjectCreateDtoOut;
import java.util.Objects;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;

@CommandController
public class SubjectController {

  private static final Logger LOGGER = LogManager.getLogger(SubjectController.class);

  private final SubjectAbl subjectAbl;

  public SubjectController(SubjectAbl subjectAbl) {
    this.subjectAbl = Objects.requireNonNull(subjectAbl);
  }

  @Command(path = "/subject/create", method = POST)
  public SubjectCreateDtoOut create(@RequestBody SubjectCreateDtoIn dtoIn) {
    LOGGER.info("subject/create command started with dtoIn: {}", dtoIn);
    final SubjectCreateDtoOut dtoOut = subjectAbl.create(dtoIn);
    LOGGER.info("subject/create command finished");
    return dtoOut;
  }
}
