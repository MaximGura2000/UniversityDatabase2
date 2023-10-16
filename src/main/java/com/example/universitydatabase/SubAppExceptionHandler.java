package com.example.universitydatabase;

import com.fasterxml.jackson.databind.JsonMappingException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SubAppExceptionHandler {

  @ExceptionHandler(JsonMappingException.class)
  public ResponseEntity<Object> handleJsonMappingException(JsonMappingException ex) {
    // Custom error handling logic here
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
    errorResponse.put("error", "Bad Request");
    errorResponse.put("message", ex.getLocalizedMessage());

    return ResponseEntity.badRequest().body(errorResponse);
  }
}
