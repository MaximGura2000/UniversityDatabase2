package com.example.universitydatabase.api.dto.subject;

public class SubjectCreateDtoIn {

  private String subjectName;
  private int subjectCredits;

  public String getSubjectName() {
    return subjectName;
  }

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public int getSubjectCredits() {
    return subjectCredits;
  }

  public void setSubjectCredits(int subjectCredits) {
    this.subjectCredits = subjectCredits;
  }
}
