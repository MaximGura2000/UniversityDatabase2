package com.example.universitydatabase.exception.appbase.abstracts;

import com.example.universitydatabase.exception.appbase.interfaces.ErrorCode;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.NonNull;

public abstract class BaseRuntimeException extends RuntimeException {

  private static final Logger LOGGER = LogManager.getLogger(BaseRuntimeException.class);

  private final String id;
  private final ZonedDateTime createTime;
  private final ErrorCode code;
  private final String message;

  /**
   * Buffers the full exception message
   */
  private String fullMessage;

  protected BaseRuntimeException(ErrorCode code, String message, Throwable cause, Object... messageParams) {
    super(cause);
    this.id = UUID.randomUUID().toString().replace("-", "");
    this.createTime = ZonedDateTime.now();
    this.code = (code == null) ? AppRuntimeException.UNEXPECTED_ERROR : code;
    this.message = (message == null) ? "Unexpected application error occurred." : formatMessage(message, messageParams);
  }

  protected BaseRuntimeException(@NonNull String id, ZonedDateTime createTime, ErrorCode code, String message, Throwable cause, Object... messageParams) {
    super(cause);
    this.id = id;
    this.createTime = createTime != null ? createTime : ZonedDateTime.now();
    this.code = (code == null) ? AppRuntimeException.UNEXPECTED_ERROR : code;
    this.message = (message == null) ? "Unexpected application error occurred." : formatMessage(message, messageParams);
  }

  /**
   * Formats exception message. In case that format fails provides fallback format.
   *
   * @param message message pattern
   * @param params parameters
   * @return formatted message
   */
  protected static String formatMessage(final String message, final Object... params) {
    if (message == null) {
      return null;
    }
    try {
      return String.format(message, params);
    } catch (Exception e) {
      LOGGER.error("Detect throwable: ", e);
      return formatFallbackMessage(message, params);
    }
  }

  /**
   * Fallback format for exception message.
   * @param message - message pattern
   * @param params - message parameters
   * @return fallback message
   */
  private static String formatFallbackMessage(final String message, final Object... params) {
    StringBuilder stringBuilder = new StringBuilder("Message Pattern: ").append(message);
    if (params != null) {
      stringBuilder.append(", ");

      for (int i = 0; i < params.length; i++) {
        Object p = params[i];
        String className = null;
        String toString = null;

        if (p != null) {
          className = p.getClass().getName();

          //check if toString is executed without errors
          try {
            toString = p.toString();
          } catch (Exception e) {
            LOGGER.error("Throwable detected", e);
            toString = "throws : " + e.toString();
          }
        }
        if (i > 0) {
          stringBuilder.append(", ");
        }
        stringBuilder.append("Parameter ").append(i + 1).append(": class=").append(className).append("").append(" toString=")
            .append(toString);
      }
    }

    return stringBuilder.toString();
  }

  public String getId() {
    return id;
  }

  public ErrorCode getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    if (this.fullMessage == null) {
      this.fullMessage = composeFullMessage();
    }

    return this.fullMessage;
  }

  private String composeFullMessage() {
    final String isoTime = DateTimeFormatter.ISO_DATE_TIME.format(this.createTime.withZoneSameInstant(ZoneOffset.UTC));
    final String className = getClass().getName();
    Throwable cause = getCause();
    final String causeMessage;
    if (cause != null) {
      causeMessage = " -> cause:" + cause;
    } else {
      causeMessage = "";
    }
    //  UU.APP/UNEXPECTED_APP_ERROR:Unexpected application error occurred.[BaseRuntimeException,id,iso-time] -> cause:NestedMessage -> cause:NestedMessage
    final char separator = ':';
    return isoTime + separator
        + id + separator
        + className + separator
        + code + separator
        + message
        + causeMessage;
  }
}
