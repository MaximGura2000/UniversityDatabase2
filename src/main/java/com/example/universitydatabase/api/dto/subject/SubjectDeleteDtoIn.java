package com.example.universitydatabase.api.dto.subject;

import javax.validation.constraints.Size;

public class SubjectDeleteDtoIn {

  @Size(min = 24, max = 24)
  private String id;
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
