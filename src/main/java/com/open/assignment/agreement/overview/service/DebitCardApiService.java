package com.open.assignment.agreement.overview.service;

import static com.open.assignment.agreement.overview.exception.ErrorKeys.NOT_FOUND;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.TECHNICAL_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.assignment.agreement.overview.api.client.DebitCardApiClient;
import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import com.open.assignment.agreement.overview.model.DebitCardDetails;
import com.open.assignment.agreement.overview.model.Error;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

/**
 * Service used to refine the response from DebitCardApiClient
 */
@Named
@Singleton
@Log4j2
public class DebitCardApiService {

  private final DebitCardApiClient debitCardApiClient;

  private final SharedApiClientService sharedApiClientService;

  private final ObjectMapper objectMapper;

  @Inject
  public DebitCardApiService(
      DebitCardApiClient debitCardApiClient,
      SharedApiClientService sharedApiClientService,
      ObjectMapper objectMapper) {
    this.debitCardApiClient = debitCardApiClient;
    this.sharedApiClientService = sharedApiClientService;
    this.objectMapper = objectMapper;
  }

  public DebitCardDetails fetchDebitCardDetails(String debitCardNum)
      throws AgreementOverviewException {
    DebitCardDetails debitCardDetails = null;
    try (var response = debitCardApiClient.getDebitCardDetails(debitCardNum)) {
      var responseString = sharedApiClientService.convertFeignResponseToString(response);
      if (response.status() == HttpStatus.OK.value()) {
        debitCardDetails = objectMapper.readValue(responseString, DebitCardDetails.class);
      } else if (response.status() == HttpStatus.NOT_FOUND.value()) {
        log.error(" debitCard not found for card number {} ", debitCardNum);
        throw new AgreementOverviewException(NOT_FOUND.getErrorCode(),
            NOT_FOUND.getErrorMessage());
      } else if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        var error = objectMapper.readValue(responseString, Error.class);
        throw new AgreementOverviewException(TECHNICAL_ERROR.getErrorCode(),
            error.getErrorMessage());
      }
    } catch (IOException | RuntimeException exception) {
      log.error("Exception occurred in processing DebitCard API Response", exception);
      throw new AgreementOverviewException(TECHNICAL_ERROR.getErrorCode(),
          TECHNICAL_ERROR.getErrorMessage());
    }
    return debitCardDetails;
  }

}
