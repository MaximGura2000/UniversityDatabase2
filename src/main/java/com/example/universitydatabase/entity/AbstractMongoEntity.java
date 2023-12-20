package com.example.universitydatabase.entity;

import java.time.Instant;

public class AbstractMongoEntity {

  private String id;
  private Instant ceTime;
  private Instant meTime;
  private int modifyNumber;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Instant getCeTime() {
    return ceTime;
  }

  public void setCeTime(Instant ceTime) {
    this.ceTime = ceTime;
  }

  public Instant getMeTime() {
    return meTime;
  }

  public void setMeTime(Instant meTime) {
    this.meTime = meTime;
  }

  public int getModifyNumber() {
    return modifyNumber;
  }

  public void setModifyNumber(int modifyNumber) {
    this.modifyNumber = modifyNumber;
  }
}
