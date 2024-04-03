package com.example.universitydatabase.api.dto.subject;

import com.example.universitydatabase.dao.ListPage;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * DtoIn body for subject/list command
 */
public class SubjectListDtoIn {

  @Min(0) @Max(15)
  private Integer minCredit;
  @Min(0) @Max(15)
  private Integer maxCredit;
  private boolean sortByName = false;
  private boolean sortByShortName = false;
  private boolean sortByCredits = false;
  private ListPage listPage;

  public Integer getMinCredit() {
    return minCredit;
  }

  public void setMinCredit(Integer minCredit) {
    this.minCredit = minCredit;
  }

  public Integer getMaxCredit() {
    return maxCredit;
  }

  public void setMaxCredit(Integer maxCredit) {
    this.maxCredit = maxCredit;
  }

  public boolean isSortByName() {
    return sortByName;
  }

  public void setSortByName(boolean sortByName) {
    this.sortByName = sortByName;
  }

  public boolean isSortByShortName() {
    return sortByShortName;
  }

  public void setSortByShortName(boolean sortByShortName) {
    this.sortByShortName = sortByShortName;
  }

  public boolean isSortByCredits() {
    return sortByCredits;
  }

  public void setSortByCredits(boolean sortByCredits) {
    this.sortByCredits = sortByCredits;
  }

  public ListPage getListPage() {
    return listPage;
  }

  public void setListPage(ListPage listPage) {
    this.listPage = listPage;
  }
}
