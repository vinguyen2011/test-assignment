package com.open.assignment.agreement.overview.service;

import static com.open.assignment.agreement.overview.enums.CardStatusEnum.ACTIVE;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.NOT_FOUND;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.TECHNICAL_ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.assignment.agreement.overview.api.client.DebitCardApiClient;
import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import feign.Response;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class DebitCardApiServiceTest {


  public static final String DEBIT_CARD_NUM = "1111";

  @Mock
  private SharedApiClientService sharedApiClientService;

  @Mock
  private DebitCardApiClient debitCardApiClient;

  @Spy
  private ObjectMapper objectMapper;

  @Mock
  private Response response;

  @InjectMocks
  private DebitCardApiService debitCardApiService;

  @Test
  @DisplayName("test fetchDebitCardDetails happy flow")
  void testHappyFlowFetchDebitCardDetails() throws AgreementOverviewException, IOException {
    when(debitCardApiClient.getDebitCardDetails(anyString())).thenReturn(response);
    String accountResponse = "{\n"
        + "\t\"id\": \"1111\",\n"
        + "\t\"status\": \"ACTIVE\",\n"
        + "\t\"cardNumber\": 1111,\n"
        + "\t\"sequenceNumber\": 1,\n"
        + "\t\"cardHolder\": \"User1\",\n"
        + "\t\"atmLimit\": {\n"
        + "\t\t\"limit\": 3000,\n"
        + "\t\t\"periodUnit\": \"PER_WEEK\"\n"
        + "\t},\n"
        + "\t\"posLimit\": {\n"
        + "\t\t\"limit\": 50,\n"
        + "\t\t\"periodUnit\": \"PER_MONTH\"\n"
        + "\t},\n"
        + "\t\"contactless\" : true\n"
        + "}";
    when(sharedApiClientService.convertFeignResponseToString(response))
        .thenReturn(accountResponse);
    when(response.status()).thenReturn(HttpStatus.OK.value());
    var debitCardDetails = debitCardApiService.fetchDebitCardDetails(DEBIT_CARD_NUM);
    assertNotNull(debitCardDetails);
    assertThat(debitCardDetails.getStatus(), is(ACTIVE));
  }

  @Test
  @DisplayName("test fetchDebitCardDetails not found response")
  void testNotFoundResponseFetchDebitCardDetails() throws IOException {
    when(debitCardApiClient.getDebitCardDetails(anyString())).thenReturn(response);
    when(sharedApiClientService.convertFeignResponseToString(response)).thenReturn("");
    when(response.status()).thenReturn(HttpStatus.NOT_FOUND.value());
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          debitCardApiService.fetchDebitCardDetails(DEBIT_CARD_NUM);
        });
    assertEquals(NOT_FOUND.getErrorCode(), exception.getErrorCode());
  }

  @Test
  @DisplayName("test fetchDebitCardDetails internal error response")
  void testIntErrorResponseFetchDebitCardDetails() throws IOException {
    when(debitCardApiClient.getDebitCardDetails(anyString())).thenReturn(response);
    String errorResponse = "{\n"
        + "  \"error\": \"error\"\n"
        + "}";
    when(sharedApiClientService.convertFeignResponseToString(response)).thenReturn(errorResponse);
    when(response.status()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          debitCardApiService.fetchDebitCardDetails(DEBIT_CARD_NUM);
        });
    assertEquals(TECHNICAL_ERROR.getErrorCode(), exception.getErrorCode());
    assertEquals("error", exception.getErrorMessage());
  }

  @Test
  @DisplayName("test fetchDebitCardDetails IO Exception")
  void testIOExceptionFetchDebitCardDetails() throws IOException {
    when(debitCardApiClient.getDebitCardDetails(anyString())).thenReturn(response);
    doThrow(IOException.class).when(sharedApiClientService).convertFeignResponseToString(response);
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          debitCardApiService.fetchDebitCardDetails(DEBIT_CARD_NUM);
        });
    assertEquals(TECHNICAL_ERROR.getErrorCode(), exception.getErrorCode());
    assertEquals(TECHNICAL_ERROR.getErrorMessage(), exception.getErrorMessage());
  }

}