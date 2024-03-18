package com.example.universitydatabase.exception.appbase;

import com.example.universitydatabase.exception.appbase.interfaces.ErrorCode;
import com.example.universitydatabase.exception.appbase.enums.ErrorType;

/**
 * Default implementation of {@link ErrorCode}
 */
public class DefaultErrorCode implements ErrorCode {

  private final String code;

  private final ErrorType type;

  public DefaultErrorCode(String code, ErrorType errorType) {
    this.code = code;
    this.type = errorType;
  }

  @Override
  public String getErrorCode() {
    return code;
  }

  @Override
  public ErrorType getType() {
    return type;
  }

  @Override
  public String toString() {
    return code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultErrorCode errorCode = (DefaultErrorCode) o;
    return this.code.equals(errorCode.code) && this.type.equals(errorCode.type);
  }
}
