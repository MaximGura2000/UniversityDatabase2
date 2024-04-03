package com.example.universitydatabase.exception.appbase.interfaces;

import com.example.universitydatabase.exception.appbase.DefaultErrorCode;
import com.example.universitydatabase.exception.appbase.enums.ErrorType;
import java.io.Serializable;

/**
 * ErrorCode interface to be used
 */
public interface ErrorCode extends Serializable {

  String getErrorCode();

  ErrorType getType();

  static ErrorCode system(String code) {
    return new DefaultErrorCode(code, ErrorType.SYSTEM);
  }

  static ErrorCode unavailable(String code) {
    return new DefaultErrorCode(code, ErrorType.UNAVAILABLE);
  }

  static ErrorCode application(String code) {
    return new DefaultErrorCode(code, ErrorType.APPLICATION);
  }
}
