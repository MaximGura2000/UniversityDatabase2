package com.example.universitydatabase.api.dto.subject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubjectCreateDtoIn {

  @NotNull
  @Size(min = 5, max = 25)
  private String subjectName;
  @Size(min = 3, max = 3)
  @NotNull
  private String shortName;
  @NotNull
  @Min(0) @Max(15)
  private Integer subjectCredits;

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public Integer getSubjectCredits() {
    return subjectCredits;
  }

  public void setSubjectCredits(Integer subjectCredits) {
    this.subjectCredits = subjectCredits;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  @Override
  public String toString() {
    return "SubjectCreateDtoIn{" +
        "subjectName='" + subjectName + '\'' +
        ", subjectCredits=" + subjectCredits +
        '}';
  }
}
