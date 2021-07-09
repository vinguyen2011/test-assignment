package com.open.assignment.agreement.overview.service;

import static com.open.assignment.agreement.overview.exception.ErrorKeys.NOT_FOUND;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.TECHNICAL_ERROR;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.assignment.agreement.overview.api.client.AccountsApiClient;
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
class AccountsApiServiceTest {

  public static final String ACCOUNT_NUM = "1234567890";

  @Mock
  private SharedApiClientService sharedApiClientService;

  @Mock
  private AccountsApiClient accountsApiClient;

  @Spy
  private ObjectMapper objectMapper;

  @Mock
  private Response response;

  @InjectMocks
  private AccountsApiService accountsApiService;

  @Test
  @DisplayName("test fetchAccountDetails happy flow")
  void testHappyFlowFetchAccountDetails() throws AgreementOverviewException, IOException {
    when(accountsApiClient.getAccountDetails(anyString())).thenReturn(response);
    String accountResponse = "{\n"
        + "  \"account\": \"IBAN1234567891\",\n"
        + "  \"balance\": 200,\n"
        + "  \"created\": \"12-10-2018\",\n"
        + "  \"user\": \"User1\",\n"
        + "  \"debitCards\": [\n"
        + "    {\n"
        + "      \"cardId\": \"4444\"\n"
        + "    },\n"
        + "    {\n"
        + "      \"cardId\": \"5555\"\n"
        + "    }\n"
        + "  ]\n"
        + "}";
    when(sharedApiClientService.convertFeignResponseToString(response))
        .thenReturn(accountResponse);
    when(response.status()).thenReturn(HttpStatus.OK.value());
    var account = accountsApiService.fetchAccountDetails(ACCOUNT_NUM);
    assertNotNull(account);
    assertThat(account.getDebitCards(), hasSize(2));
  }

  @Test
  @DisplayName("test fetchAccountDetails not found response")
  void testNotFoundResponseFetchAccountDetails() throws IOException {
    when(accountsApiClient.getAccountDetails(anyString())).thenReturn(response);
    when(sharedApiClientService.convertFeignResponseToString(response)).thenReturn("");
    when(response.status()).thenReturn(HttpStatus.NOT_FOUND.value());
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          accountsApiService.fetchAccountDetails(ACCOUNT_NUM);
        });
    assertEquals(NOT_FOUND.getErrorCode(), exception.getErrorCode());
  }

  @Test
  @DisplayName("test fetchAccountDetails internal error response")
  void testIntErrorResponseFetchAccountDetails() throws IOException {
    when(accountsApiClient.getAccountDetails(anyString())).thenReturn(response);
    String errorResponse = "{\n"
        + "  \"error\": \"error\"\n"
        + "}";
    when(sharedApiClientService.convertFeignResponseToString(response)).thenReturn(errorResponse);
    when(response.status()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          accountsApiService.fetchAccountDetails(ACCOUNT_NUM);
        });
    assertEquals(TECHNICAL_ERROR.getErrorCode(), exception.getErrorCode());
    assertEquals("error", exception.getErrorMessage());
  }

  @Test
  @DisplayName("test fetchAccountDetails IO Exception")
  void testIOExceptionFetchAccountDetails() throws IOException {
    when(accountsApiClient.getAccountDetails(anyString())).thenReturn(response);
    doThrow(IOException.class).when(sharedApiClientService).convertFeignResponseToString(response);
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          accountsApiService.fetchAccountDetails(ACCOUNT_NUM);
        });
    assertEquals(TECHNICAL_ERROR.getErrorCode(), exception.getErrorCode());
    assertEquals(TECHNICAL_ERROR.getErrorMessage(), exception.getErrorMessage());
  }
}