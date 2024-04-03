package com.example.universitydatabase.api.dto.subject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DtoIn body for subject/updateShortName command
 */
public class SubjectUpdateShortNameDtoIn {

  @NotNull
  @Size(min = 24, max = 24)
  private String id;
  @NotNull
  @Size(min = 3, max = 3)
  private String shortName;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }
}
