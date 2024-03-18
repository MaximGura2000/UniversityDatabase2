package com.example.universitydatabase.exception;

import com.example.universitydatabase.exception.appbase.abstracts.AppRuntimeException;
import com.example.universitydatabase.exception.appbase.interfaces.ErrorCode;
import java.util.Map;

public class DatastoreRuntimeException extends AppRuntimeException {

  public DatastoreRuntimeException(DatastoreRuntimeException.Error error, Map<String, Object> params) {
    super(error.getCode(), error.getMessage(), params);
  }

  public DatastoreRuntimeException(DatastoreRuntimeException.Error error, Exception e, Map<String, Object> params) {
    super(error.getCode(), error.getMessage(), e.getCause(), params);
  }

  public enum Error {
    SOI_EXCEEDED(ErrorCode.system("dao/soiExceeded"), "Size of entity exceeded."),
    NOI_EXCEEDED(ErrorCode.system("dao/noiExceeded"), "Number of entity exceeded."),
    NOT_UNIQUE_ID(ErrorCode.system("dao/notUniqueId"), "Attributes violate index uniqueness"),
    DATASTORE_UNEXPECTED_ERROR(UNEXPECTED_ERROR, "Unexpected exception occurred.");

    private final ErrorCode code;

    private final String message;

    Error(ErrorCode code, String message) {
      this.code = code;
      this.message = message;
    }

    public ErrorCode getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }
  }
}
