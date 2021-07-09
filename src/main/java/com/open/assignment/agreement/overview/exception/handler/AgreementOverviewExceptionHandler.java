package com.open.assignment.agreement.overview.exception.handler;

import static com.open.assignment.agreement.overview.exception.ErrorKeys.NOT_FOUND;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.TECHNICAL_ERROR;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.USER_ID_INVALID;

import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import com.open.assignment.agreement.overview.exception.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception Handler to handle application exceptions and generic exception
 */
@ControllerAdvice
@Log4j2
public class AgreementOverviewExceptionHandler {

  HttpStatus httpStatus;

  @ExceptionHandler(AgreementOverviewException.class)
  public ResponseEntity<ErrorResponse> applicationExceptionHandler(
      AgreementOverviewException exception) {
    log.error("AgreementOverviewException occurred ", exception);
    if (USER_ID_INVALID.getErrorCode().equals(exception.getErrorCode())) {
      httpStatus = HttpStatus.BAD_REQUEST;
    }
    if (NOT_FOUND.getErrorCode().equals(exception.getErrorCode())) {
      httpStatus = HttpStatus.NOT_FOUND;
    } else {
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    var errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());
    return new ResponseEntity<>(errorResponse, httpStatus);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> genericExceptionHandler(Exception exception) {
    log.error("Runtime Exception occurred ", exception);
    var errorResponse
        = new ErrorResponse(TECHNICAL_ERROR.getErrorCode(), TECHNICAL_ERROR.getErrorMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
