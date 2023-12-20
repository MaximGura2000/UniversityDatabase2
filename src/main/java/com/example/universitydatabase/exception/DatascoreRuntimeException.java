package com.example.universitydatabase.exception;

public class DatascoreRuntimeException extends AppRuntimeException{

  public DatascoreRuntimeException(String code, String message, Object... params) {
    super(code, message, params);
  }

  public DatascoreRuntimeException(String code, String message, Throwable cause, Object... params) {
    super(code, message, cause, params);
  }

  public DatascoreRuntimeException(DatascoreRuntimeException.Error error, Object... params) {
    super(error.getCode(), error.getMessage(), params);

  }

  public enum Error {
    SOI_EXCEEDED("dao/soiExceeded" , "Size of entity exceeded."),
    NOI_EXCEEDED("dao/noiExceeded" , "Number of entity exceeded."),
    NOT_UNIQUE_ID("dao/notUniqueId", "Attributes violate index uniqueness"),
    DATASTORE_UNEXPECTED_ERROR(UNEXPECTED_ERROR, "Unexpected exception occurred.");

    private final String code;

    private final String message;

    Error(String code, String message) {
      this.code = code;
      this.message = message;
    }

    public String getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }
  }
}
