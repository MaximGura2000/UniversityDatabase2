package com.example.universitydatabase;

import com.example.universitydatabase.exception.appbase.abstracts.AppRuntimeException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SubAppExceptionHandler {

  @ExceptionHandler(AppRuntimeException.class)
  public ResponseEntity<Object> handleJsonMappingException(AppRuntimeException exception) {
    // Custom error handling logic here
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("code", resolveHttpStatus(exception).value());
    errorResponse.put("status", resolveHttpStatus(exception));
    errorResponse.put("error", exception.toString());
    errorResponse.put("message", exception.getLocalizedMessage());
    if (exception.getParamMap() != null && !exception.getParamMap().isEmpty()) {
      errorResponse.put("invalidValuesMap", exception.getParamMap());
    }

    return ResponseEntity.badRequest().body(errorResponse);
  }

  private HttpStatus resolveHttpStatus(AppRuntimeException exception) {
    HttpStatus status;
    switch (exception.getCode().getType()) {
      case SYSTEM:
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        break;
      case APPLICATION:
        status = HttpStatus.BAD_REQUEST;
        break;
      case UNAVAILABLE:
        status = HttpStatus.SERVICE_UNAVAILABLE;
        break;
      default:
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        break;
    }
    return status;
  }
}
