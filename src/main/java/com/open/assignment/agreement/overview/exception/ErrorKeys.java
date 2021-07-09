package com.open.assignment.agreement.overview.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorKeys {

  USER_ID_INVALID("USER_ID_INVALID", "invalid user id"),
  NOT_FOUND("NOT_FOUND", "resource not found"),
  TECHNICAL_ERROR("TECHNICAL_ERROR", "something went wrong");

  private final String errorCode;

  private final String errorMessage;

}
