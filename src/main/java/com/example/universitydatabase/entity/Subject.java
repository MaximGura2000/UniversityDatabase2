package com.example.universitydatabase.entity;

public class Subject {

  private String subjectName;
  private int subjectCredits;

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public int getSubjectCredits() {
    return subjectCredits;
  }

  public void setSubjectCredits(int subjectCredits) {
    this.subjectCredits = subjectCredits;
  }

  @Override
  public String toString() {
    return "Subject: " + this.getSubjectName() + ". Credits: " + this.getSubjectCredits();
  }
}
