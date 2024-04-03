package com.example.universitydatabase.entity;

import java.time.Instant;

/**
 * Abstract class for all objects which will be stored at MongoDB
 */
public class AbstractMongoEntity {

  private String id;
  private Instant createEntityTime;
  private Instant modifyEntityTime;
  private int modifyNumber;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Instant getCreateEntityTime() {
    return createEntityTime;
  }

  public void setCreateEntityTime(Instant createEntityTime) {
    this.createEntityTime = createEntityTime;
  }

  public Instant getModifyEntityTime() {
    return modifyEntityTime;
  }

  public void setModifyEntityTime(Instant modifyEntityTime) {
    this.modifyEntityTime = modifyEntityTime;
  }

  public int getModifyNumber() {
    return modifyNumber;
  }

  public void setModifyNumber(int modifyNumber) {
    this.modifyNumber = modifyNumber;
  }
}
