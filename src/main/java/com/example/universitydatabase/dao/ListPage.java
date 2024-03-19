package com.example.universitydatabase.dao;

public class ListPage {

  public static final int DEFAULT_PAGE_INDEX = 0;
  public static final int DEFAULT_PAGE_SIZE = 100;

  private Integer pageIndex;
  private Integer pageSize;

  public ListPage() {
    this.pageIndex = DEFAULT_PAGE_INDEX;
    this.pageSize = DEFAULT_PAGE_SIZE;
  }

  public ListPage(Integer pageIndex, Integer pageSize) {
    this.pageIndex = pageIndex;
    this.pageSize = pageSize;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
}
