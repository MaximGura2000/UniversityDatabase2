package com.example.universitydatabase.exception;

import com.example.universitydatabase.exception.appbase.abstracts.AppRuntimeException;
import com.example.universitydatabase.exception.appbase.interfaces.ErrorCode;
import java.util.Map;

/**
 * AppRuntimeExceptions for SubjectAbl
 */
public class SubjectRuntimeException extends AppRuntimeException {

  private static final String SUB_APP_SUBJECT_CREATE = "subject/create";
  private static final String SUB_APP_SUBJECT_GET = "subject/get";
  private static final String SUB_APP_SUBJECT_LIST = "subject/list";
  private static final String SUB_APP_SUBJECT_DELETE = "subject/delete";
  private static final String SUB_APP_SUBJECT_UPDATE = "subject/update";
  private static final String SUB_APP_SUBJECT_UPDATE_SHORT_NAME = "subject/updateShortName";
  private static final String INVALID_DTO_IN = "/invalidDtoIn";
  private static final String INVALID_DTO_IN_MESSAGE = "DtoIn is not valid.";
  public static final String MONGO_EXCEPTION = "mongoException";
  public static final String MONGO_QUERY = "Unexpected error occurs during mongo query.";
  public static final String MISSING_SUBJECT = "/missingSubject";
  public static final String SUBJECT_WITH_ID_NOT_EXIST = "Subject with this id: %s not exist.";
  public static final String SUBJECT_WITH_SHORT_NAME_NOT_EXIST = "Subject with this shortName: %s not exist.";
  public static final String SHOULD_CONTAIN_ID_OR_SHORT_NAME = "DtoIn should contain id or shortName.";

  public SubjectRuntimeException(ErrorCode error, String message, Object... params) {
    super(error, message, params);
  }

  public SubjectRuntimeException(SubjectRuntimeException.Error error, Throwable cause) {
    super(error.getCode(), error.getMessage(), cause);
  }

  public SubjectRuntimeException(SubjectRuntimeException.Error error, Map<String, ?> paramsMap) {
    super(error.getCode(), error.getMessage(), paramsMap, null);
  }

  public enum Error {
    INVALID_DTO_IN_CREATE(ErrorCode.application(SUB_APP_SUBJECT_CREATE + INVALID_DTO_IN), INVALID_DTO_IN_MESSAGE),
    INVALID_DTO_IN_GET(ErrorCode.application(SUB_APP_SUBJECT_GET + INVALID_DTO_IN), INVALID_DTO_IN_MESSAGE),
    INVALID_DTO_IN_UPDATE(ErrorCode.application(SUB_APP_SUBJECT_UPDATE + INVALID_DTO_IN), INVALID_DTO_IN_MESSAGE),
    INVALID_DTO_IN_UPDATE_SHORT_NAME(ErrorCode.application(SUB_APP_SUBJECT_UPDATE_SHORT_NAME + INVALID_DTO_IN), INVALID_DTO_IN_MESSAGE),
    INVALID_DTO_IN_DELETE(ErrorCode.application(SUB_APP_SUBJECT_DELETE + INVALID_DTO_IN), INVALID_DTO_IN_MESSAGE),
    INVALID_DTO_IN_LIST(ErrorCode.application(SUB_APP_SUBJECT_LIST + INVALID_DTO_IN), INVALID_DTO_IN_MESSAGE),
    INVALID_MIN_AND_MAX_CREDIT_VALUES(ErrorCode.application(SUB_APP_SUBJECT_LIST + INVALID_DTO_IN), "Minimum credit value : %s can't be higher than maximum: %s"),
    ALREADY_EXIST_CREATE(ErrorCode.application(SUB_APP_SUBJECT_CREATE + "/alreadyExist"), "Subject with %s shortName already exist."),
    MISSING_ID_AND_SHORT_NAME_UPDATE(ErrorCode.application(SUB_APP_SUBJECT_UPDATE + INVALID_DTO_IN), SHOULD_CONTAIN_ID_OR_SHORT_NAME),
    MISSING_ID_AND_SHORT_NAME_GET(ErrorCode.application(SUB_APP_SUBJECT_GET + INVALID_DTO_IN), SHOULD_CONTAIN_ID_OR_SHORT_NAME),
    MISSING_ID_AND_SHORT_NAME_DELETE(ErrorCode.application(SUB_APP_SUBJECT_DELETE + INVALID_DTO_IN), SHOULD_CONTAIN_ID_OR_SHORT_NAME),
    MISSING_SUBJECT_FROM_ID_GET(ErrorCode.application(SUB_APP_SUBJECT_GET + MISSING_SUBJECT), SUBJECT_WITH_ID_NOT_EXIST),
    MISSING_SUBJECT_FROM_SHORT_NAME_GET(ErrorCode.application(SUB_APP_SUBJECT_GET + MISSING_SUBJECT), SUBJECT_WITH_SHORT_NAME_NOT_EXIST),
    MISSING_SUBJECT_FROM_ID_UPDATE(ErrorCode.application(SUB_APP_SUBJECT_UPDATE + MISSING_SUBJECT), SUBJECT_WITH_ID_NOT_EXIST),
    MISSING_SUBJECT_FROM_SHORT_NAME_UPDATE(ErrorCode.application(SUB_APP_SUBJECT_UPDATE + MISSING_SUBJECT), SUBJECT_WITH_SHORT_NAME_NOT_EXIST),
    MISSING_SUBJECT_FROM_ID_DELETE(ErrorCode.application(SUB_APP_SUBJECT_DELETE + MISSING_SUBJECT), SUBJECT_WITH_ID_NOT_EXIST),
    MISSING_SUBJECT_FROM_SHORT_NAME_DELETE(ErrorCode.application(SUB_APP_SUBJECT_DELETE + MISSING_SUBJECT), SUBJECT_WITH_SHORT_NAME_NOT_EXIST),
    MISSING_SUBJECT_FROM_ID_UPDATE_SHORT_NAME(ErrorCode.application(SUB_APP_SUBJECT_UPDATE_SHORT_NAME + MISSING_SUBJECT), SUBJECT_WITH_ID_NOT_EXIST),
    MONGO_ERROR_CREATE(ErrorCode.application(SUB_APP_SUBJECT_CREATE + MONGO_EXCEPTION), MONGO_QUERY),
    MONGO_ERROR_GET(ErrorCode.application(SUB_APP_SUBJECT_GET + MONGO_EXCEPTION), MONGO_QUERY),
    MONGO_ERROR_DELETE(ErrorCode.application(SUB_APP_SUBJECT_DELETE + MONGO_EXCEPTION), MONGO_QUERY),
    MONGO_ERROR_UPDATE(ErrorCode.application(SUB_APP_SUBJECT_UPDATE + MONGO_EXCEPTION), MONGO_QUERY),
    MONGO_ERROR_UPDATE_SHORT_NAME(ErrorCode.application(SUB_APP_SUBJECT_UPDATE_SHORT_NAME + MONGO_EXCEPTION), MONGO_QUERY),
    MONGO_ERROR_LIST(ErrorCode.application(SUB_APP_SUBJECT_LIST + MONGO_EXCEPTION), MONGO_QUERY);

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
