package com.open.assignment.agreement.overview.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

  private static final long serialVersionUID = -6605574051478790278L;

  private final String errorCode;

  private final String errorMessage;

}
