package com.example.universitydatabase.exception;

public class SubjectRuntimeException extends AppRuntimeException{

  private static final String SUB_APP_SUBJECT_CREATE = "subject/create";
  private static final String INVALID_DTO_IN = "/invalidDtoIn";
  private static final String INVALID_DTO_IN_MESSAGE = "DtoIn is not valid.";

  public SubjectRuntimeException(SubjectRuntimeException.Error error, Throwable cause, Object... params) {
    super(error.getCode(), error.getMessage(), cause, params);
  }

  public SubjectRuntimeException(SubjectRuntimeException.Error error, Object... params) {
    super(error.getCode(), error.getMessage(), params);
  }

  public SubjectRuntimeException(String code, String message, Object... params) {
    super(code, message, params);
  }

  public SubjectRuntimeException(String code, String message, Throwable cause, Object... params) {
    super(code, message, cause, params);
  }

  public enum Error {
    INVALID_DTO_IN_CREATE(SUB_APP_SUBJECT_CREATE + INVALID_DTO_IN, INVALID_DTO_IN_MESSAGE);

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
