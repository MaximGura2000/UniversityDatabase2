package com.example.universitydatabase.entity;

/**
 * Class for Subject representation
 */
public class Subject extends AbstractMongoEntity {

  private String subjectName;
  private String shortName;
  private int subjectCredits;

  public void setSubjectName(String subjectName) {
    this.subjectName = subjectName;
  }

  public String getSubjectName() {
    return subjectName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
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
