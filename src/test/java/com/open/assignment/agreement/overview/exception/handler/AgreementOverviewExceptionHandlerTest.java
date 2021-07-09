package com.open.assignment.agreement.overview.exception.handler;

import static com.open.assignment.agreement.overview.exception.ErrorKeys.NOT_FOUND;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.TECHNICAL_ERROR;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.USER_ID_INVALID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class AgreementOverviewExceptionHandlerTest {

  @Mock
  private AgreementOverviewException exception;

  @Mock
  private Exception genericException;

  @InjectMocks
  private AgreementOverviewExceptionHandler handler;

  @Test
  @DisplayName("test applicationExceptionHandler for Bad Request")
  void testBadRequestApplicationExceptionHandler() {
    when(exception.getErrorCode()).thenReturn(USER_ID_INVALID.getErrorCode());
    var responseEntity = handler.applicationExceptionHandler(exception);
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorCode(),
        is(USER_ID_INVALID.getErrorCode()));
  }

  @Test
  @DisplayName("test applicationExceptionHandler for Not Found")
  void testNotFoundApplicationExceptionHandler() {
    when(exception.getErrorCode()).thenReturn(NOT_FOUND.getErrorCode());
    var responseEntity = handler.applicationExceptionHandler(exception);
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorCode(),
        is(NOT_FOUND.getErrorCode()));
  }

  @Test
  @DisplayName("test applicationExceptionHandler for application processing Error")
  void testApplicationErrorApplicationExceptionHandler() {
    when(exception.getErrorCode()).thenReturn("Blah");
    var responseEntity = handler.applicationExceptionHandler(exception);
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorCode(), is("Blah"));
  }

  @Test
  @DisplayName("test applicationExceptionHandler for Exception")
  void testExceptionApplicationExceptionHandler() {
    when(genericException.getMessage()).thenReturn("Blah");
    var responseEntity = handler.genericExceptionHandler(genericException);
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorCode(),
        is(TECHNICAL_ERROR.getErrorCode()));
    assertThat(Objects.requireNonNull(responseEntity.getBody()).getErrorMessage(),
        is(TECHNICAL_ERROR.getErrorMessage()));
  }
}