package com.example.universitydatabase.exception.appbase.abstracts;

import com.example.universitydatabase.exception.appbase.interfaces.ErrorCode;
import java.time.ZonedDateTime;
import java.util.Map;
import org.springframework.http.HttpHeaders;

public abstract class AppRuntimeException extends BaseRuntimeException{

  public static final ErrorCode UNEXPECTED_ERROR = ErrorCode.system("UNEXPECTED_APP_ERROR");

  private Map<String, ?> paramMap;
  private Map<String, ?> dtoOut;
  private HttpHeaders responseHeaders;

  protected AppRuntimeException(ErrorCode code, String message, Object... params) {
    this(code, message, null, params);
  }

  protected AppRuntimeException(ErrorCode code, String message, Throwable cause, Object... params) {
    this(code, message, null, cause, params);
  }

  protected AppRuntimeException(ErrorCode code, String message, Map<String, ?> paramMap, Throwable cause, Object... params) {
    this(code, message, paramMap, cause, null, params);
  }
  protected AppRuntimeException(ErrorCode code, String message, Map<String, ?> paramMap, Throwable cause, Map<String, ?> dtoOut, Object... params) {
    super(code, message, cause, params);
    this.paramMap = paramMap;
    // this.uuAppErrorMap.addError(this);
    this.dtoOut = dtoOut;
  }

}
