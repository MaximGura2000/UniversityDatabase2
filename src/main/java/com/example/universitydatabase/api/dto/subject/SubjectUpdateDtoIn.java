package com.example.universitydatabase.api.dto.subject;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class SubjectUpdateDtoIn {

  @Size(min = 24, max = 24)
  private String id;
  @Size(min = 3, max = 3)
  private String shortName;
  @Min(0) @Max(15)
  private Integer subjectCredits;
  @Size(min = 5, max = 25)
  private String subjectName;

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

  public Integer getSubjectCredits() {
    return subjectCredits;
  }

  public void setSubjectCredits(Integer subjectCredits) {
    this.subjectCredits = subjectCredits;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }
}
