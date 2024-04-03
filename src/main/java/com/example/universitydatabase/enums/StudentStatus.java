package com.example.universitydatabase.enums;

/**
 * list of possible status for {@link com.example.universitydatabase.entity.Student}
 */
public enum StudentStatus {

  FIRST_YEAR("First year student"),
  SECOND_YEAR("Second year student"),
  THIRD_YEAR("Third year student"),
  GRADUATED("Graduated student"),
  DROPOUT("Dropout student");

  StudentStatus(String value) {
    this.value = value;
  }

  private String value;

  public String getValue() {
    return value;
  }
}
