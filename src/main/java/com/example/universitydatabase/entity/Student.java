package com.example.universitydatabase.entity;

import com.example.universitydatabase.enums.StudentStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends Person {

  private boolean stipend;
  Map<Subject, Integer> subjectList = new HashMap<>();
  List<Teacher> teacherList = new ArrayList<>();
  private StudentStatus studentStatus;

  public boolean isStipend() {
    return stipend;
  }

  public void setStipend(boolean stipend) {
    this.stipend = stipend;
  }

  public Map<Subject, Integer> getSubjectList() {
    return subjectList;
  }

  public void setSubjectList(Map<Subject, Integer> subjectList) {
    this.subjectList = subjectList;
  }

  public List<Teacher> getTeacherList() {
    return teacherList;
  }

  public void setTeacherList(List<Teacher> teacherList) {
    this.teacherList = teacherList;
  }

  public StudentStatus getStudentStatus() {
    return studentStatus;
  }

  public void setStudentStatus(StudentStatus studentStatus) {
    this.studentStatus = studentStatus;
  }

  @Override
  public String toString() {
    return this.getName() + " " + this.getSurname() + ". Id:" + this.getId();
  }
}
