package com.open.assignment.agreement.overview.service;

import static com.open.assignment.agreement.overview.exception.ErrorKeys.NOT_FOUND;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.TECHNICAL_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.assignment.agreement.overview.api.client.AgreementsApiClient;
import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import com.open.assignment.agreement.overview.model.Agreements;
import com.open.assignment.agreement.overview.model.Error;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

/**
 * Service used to refine the response from AgreementsApiClient
 */
@Named
@Singleton
@Log4j2
public class AgreementsApiService {

  private final AgreementsApiClient agreementsApiClient;

  private final SharedApiClientService sharedApiClientService;

  private final ObjectMapper objectMapper;

  @Inject
  public AgreementsApiService(
      AgreementsApiClient agreementsApiClient,
      SharedApiClientService sharedApiClientService,
      ObjectMapper objectMapper) {
    this.agreementsApiClient = agreementsApiClient;
    this.sharedApiClientService = sharedApiClientService;
    this.objectMapper = objectMapper;
  }

  public Agreements fetchAgreementsInfo(String userId) throws AgreementOverviewException {
    Agreements agreements = null;
    try (var response = agreementsApiClient.getAgreements(userId)) {
      var responseString = sharedApiClientService.convertFeignResponseToString(response);
      if (response.status() == HttpStatus.OK.value()) {
        agreements = objectMapper.readValue(responseString, Agreements.class);
      } else if (response.status() == HttpStatus.NOT_FOUND.value()) {
        log.error(" agreements not found for userId {} ", userId);
        throw new AgreementOverviewException(NOT_FOUND.getErrorCode(),
            NOT_FOUND.getErrorMessage());
      } else if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        var error = objectMapper.readValue(responseString, Error.class);
        throw new AgreementOverviewException(TECHNICAL_ERROR.getErrorCode(),
            error.getErrorMessage());
      }
    } catch (IOException | RuntimeException exception) {
      log.error("Exception occurred in processing Agreements API Response", exception);
      throw new AgreementOverviewException(TECHNICAL_ERROR.getErrorCode(),
          TECHNICAL_ERROR.getErrorMessage());
    }
    return agreements;
  }

}
