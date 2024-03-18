package com.example.universitydatabase.exception;

import com.example.universitydatabase.exception.appbase.abstracts.AppRuntimeException;
import com.example.universitydatabase.exception.appbase.interfaces.ErrorCode;
import java.util.Map;

public class SubjectRuntimeException extends AppRuntimeException {

  private static final String SUB_APP_SUBJECT_CREATE = "subject/create";
  private static final String INVALID_DTO_IN = "/invalidDtoIn";
  private static final String INVALID_DTO_IN_MESSAGE = "DtoIn is not valid.";
  public static final String MONGO_EXCEPTION = "mongoException";
  public static final String MONGO_QUERY = "Unexpected error occurs during mongo query.";

  public SubjectRuntimeException(SubjectRuntimeException.Error error, Throwable cause) {
    super(error.getCode(), error.getMessage(), cause);
  }

  public SubjectRuntimeException(SubjectRuntimeException.Error error, Map<String, ?> paramsMap) {
    super(error.getCode(), error.getMessage(), paramsMap);
  }

  public enum Error {
    INVALID_DTO_IN_CREATE(ErrorCode.application(SUB_APP_SUBJECT_CREATE + INVALID_DTO_IN), INVALID_DTO_IN_MESSAGE),
    MONGO_ERROR_CREATE(ErrorCode.application(SUB_APP_SUBJECT_CREATE + MONGO_EXCEPTION), MONGO_QUERY);

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
