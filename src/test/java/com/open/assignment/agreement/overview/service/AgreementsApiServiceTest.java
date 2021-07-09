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
import com.open.assignment.agreement.overview.api.client.AgreementsApiClient;
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
class AgreementsApiServiceTest {

  public static final String USER_ID = "User-id";
  @Mock
  private SharedApiClientService sharedApiClientService;

  @Mock
  private AgreementsApiClient agreementsApiClient;

  @Spy
  private ObjectMapper objectMapper;

  @Mock
  private Response response;

  @InjectMocks
  private AgreementsApiService agreementsApiService;

  @Test
  @DisplayName("test fetchAgreementsInfo happy flow")
  void testHappyFlowFetchAgreementsInfo() throws AgreementOverviewException, IOException {
    when(agreementsApiClient.getAgreements(anyString())).thenReturn(response);
    String agreementsResponse = "{\n"
        + "  \"agreements\": [\n"
        + "    {\n"
        + "      \"type\": \"IBAN\",\n"
        + "      \"account\": \"1234567890\"\n"
        + "    },\n"
        + "    {\n"
        + "      \"type\": \"IBAN\",\n"
        + "      \"account\": \"1234567891\"\n"
        + "    }\n"
        + "  ]\n"
        + "}";
    when(sharedApiClientService.convertFeignResponseToString(response))
        .thenReturn(agreementsResponse);
    when(response.status()).thenReturn(HttpStatus.OK.value());
    var agreements = agreementsApiService.fetchAgreementsInfo(USER_ID);
    assertNotNull(agreements);
    assertThat(agreements.getAgreementList(), hasSize(2));
  }

  @Test
  @DisplayName("test fetchAgreementsInfo not found response")
  void testNotFoundResponseFetchAgreementsInfo() throws IOException {
    when(agreementsApiClient.getAgreements(anyString())).thenReturn(response);
    when(sharedApiClientService.convertFeignResponseToString(response)).thenReturn("");
    when(response.status()).thenReturn(HttpStatus.NOT_FOUND.value());
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          agreementsApiService.fetchAgreementsInfo(USER_ID);
        });
    assertEquals(NOT_FOUND.getErrorCode(), exception.getErrorCode());
  }

  @Test
  @DisplayName("test fetchAgreementsInfo internal error response")
  void testIntErrorResponseFetchAgreementsInfo() throws IOException {
    when(agreementsApiClient.getAgreements(anyString())).thenReturn(response);
    String errorResponse = "{\n"
        + "  \"error\": \"error\"\n"
        + "}";
    when(sharedApiClientService.convertFeignResponseToString(response)).thenReturn(errorResponse);
    when(response.status()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          agreementsApiService.fetchAgreementsInfo(USER_ID);
        });
    assertEquals(TECHNICAL_ERROR.getErrorCode(), exception.getErrorCode());
    assertEquals("error", exception.getErrorMessage());
  }

  @Test
  @DisplayName("test fetchAgreementsInfo IO Exception")
  void testIOExceptionFetchAgreementsInfo() throws IOException {
    when(agreementsApiClient.getAgreements(anyString())).thenReturn(response);
    doThrow(IOException.class).when(sharedApiClientService).convertFeignResponseToString(response);
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          agreementsApiService.fetchAgreementsInfo(USER_ID);
        });
    assertEquals(TECHNICAL_ERROR.getErrorCode(), exception.getErrorCode());
    assertEquals(TECHNICAL_ERROR.getErrorMessage(), exception.getErrorMessage());
  }
}