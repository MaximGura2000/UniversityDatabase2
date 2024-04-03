package com.example.universitydatabase.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for Teacher representation
 */
public class Teacher extends Person {

  List<Student> studentList = new ArrayList<>();

  public List<Student> getStudentList() {
    return studentList;
  }

  public void setStudentList(List<Student> studentList) {
    this.studentList = studentList;
  }

  @Override
  public String toString() {
    return this.getName() + " " + this.getSurname() + ". Id: " + this.getId();
  }
}
