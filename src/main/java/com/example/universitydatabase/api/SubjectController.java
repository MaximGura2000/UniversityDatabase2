package com.example.universitydatabase.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.example.universitydatabase.abl.SubjectAbl;
import com.example.universitydatabase.annotation.Command;
import com.example.universitydatabase.annotation.CommandController;
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
import com.example.universitydatabase.entity.Subject;
import java.util.List;
import java.util.Objects;
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

  /**
   * Command subject/create controller
   * @param dtoIn of create command
   * @return created {@link Subject}
   */
  @Command(path = "/subject/create", method = POST)
  public SubjectCreateDtoOut create(@RequestBody SubjectCreateDtoIn dtoIn) {
    LOGGER.info("subject/create command started with dtoIn: {}", dtoIn);
    final SubjectCreateDtoOut dtoOut = subjectAbl.create(dtoIn);
    LOGGER.info("subject/create command finished");
    return dtoOut;
  }

  /**
   * Command subject/get controller
   * @param dtoIn of get command
   * @return {@link Subject}
   */
  @Command(path = "/subject/get", method = GET)
  public SubjectGetDtoOut get(@RequestBody SubjectGetDtoIn dtoIn) {
    LOGGER.info("subject/get command started with dtoIn: {}", dtoIn);
    final SubjectGetDtoOut dtoOut = subjectAbl.get(dtoIn);
    LOGGER.info("subject/get command finished");
    return dtoOut;
  }

  /**
   * Command subject/list controller
   * @param dtoIn of list command
   * @return {@link List<Subject>}
   */
  @Command(path = "/subject/list", method = GET)
  public SubjectListDtoOut get(@RequestBody SubjectListDtoIn dtoIn) {
    LOGGER.info("subject/list command started with dtoIn: {}", dtoIn);
    final SubjectListDtoOut dtoOut = subjectAbl.list(dtoIn);
    LOGGER.info("subject/get command finished");
    return dtoOut;
  }

  /**
   * Command subject/update controller
   * @param dtoIn of update command
   * @return updated {@link Subject}
   */
  @Command(path = "/subject/update", method = POST)
  public SubjectUpdateDtoOut update(@RequestBody SubjectUpdateDtoIn dtoIn) {
    LOGGER.info("subject/update command started with dtoIn: {}", dtoIn);
    final SubjectUpdateDtoOut dtoOut = subjectAbl.update(dtoIn);
    LOGGER.info("subject/update command finished");
    return dtoOut;
  }

  /**
   * Command subject/update controller
   * @param dtoIn of update command
   * @return updated {@link Subject}
   */
  @Command(path = "/subject/updateShortName", method = POST)
  public SubjectUpdateShortNameDtoOut update(@RequestBody SubjectUpdateShortNameDtoIn dtoIn) {
    LOGGER.info("subject/updateShortName command started with dtoIn: {}", dtoIn);
    final SubjectUpdateShortNameDtoOut dtoOut = subjectAbl.updateShortName(dtoIn);
    LOGGER.info("subject/updateShortName command finished");
    return dtoOut;
  }

  /**
   * Command subject/delete controller
   * @param dtoIn of delete command
   * @return deleted {@link Subject}
   */
  @Command(path = "/subject/delete", method = POST)
  public SubjectDeleteDtoOut create(@RequestBody SubjectDeleteDtoIn dtoIn) {
    LOGGER.info("subject/delete command started with dtoIn: {}", dtoIn);
    final SubjectDeleteDtoOut dtoOut = subjectAbl.delete(dtoIn);
    LOGGER.info("subject/delete command finished");
    return dtoOut;
  }
}
