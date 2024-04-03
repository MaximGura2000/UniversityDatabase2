package com.example.universitydatabase.api.dto.subject;

import com.example.universitydatabase.entity.Subject;
import java.util.List;

/**
 * DtoOut for subject/list command
 */
public class SubjectListDtoOut {

  private List<Subject> subjectList;

  public List<Subject> getSubjectList() {
    return subjectList;
  }

  public void setSubjectList(List<Subject> subjectList) {
    this.subjectList = subjectList;
  }
}
