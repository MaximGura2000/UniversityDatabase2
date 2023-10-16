package com.example.universitydatabase.exception;

import java.time.ZonedDateTime;

public class AppRuntimeException extends RuntimeException{

  public static final String UNEXPECTED_ERROR = "UNEXPECTED_APP_ERROR";

  private String code;
  private String message;
  private ZonedDateTime createdDateTime;

  public AppRuntimeException(String code, String message, Object... params) {
    this(code, message, null, params);
  }

  public AppRuntimeException(String code, String message, Throwable cause, Object... params) {
    super(cause);
    this.createdDateTime = ZonedDateTime.now();
    this.code = (code == null) ? UNEXPECTED_ERROR : code;
    this.message = (message == null) ? "Unexpected application error occurred." : formatMessage(message, params);
  }

  protected final String formatMessage(String message, Object... params) {
    return String.format(message, params);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ZonedDateTime getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(ZonedDateTime createdDateTime) {
    this.createdDateTime = createdDateTime;
  }
}
