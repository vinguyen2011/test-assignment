package com.open.assignment.agreement.overview.service;

import static com.open.assignment.agreement.overview.exception.ErrorKeys.NOT_FOUND;
import static com.open.assignment.agreement.overview.exception.ErrorKeys.TECHNICAL_ERROR;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.assignment.agreement.overview.api.client.AccountsApiClient;
import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import com.open.assignment.agreement.overview.model.Account;
import com.open.assignment.agreement.overview.model.Error;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

/**
 * Service used to refine the response from AccountsApiClient
 */
@Named
@Singleton
@Log4j2
public class AccountsApiService {

  private final AccountsApiClient accountsApiClient;

  private final SharedApiClientService sharedApiClientService;

  private final ObjectMapper objectMapper;

  @Inject
  public AccountsApiService(
      AccountsApiClient accountsApiClient,
      SharedApiClientService sharedApiClientService,
      ObjectMapper objectMapper) {
    this.accountsApiClient = accountsApiClient;
    this.sharedApiClientService = sharedApiClientService;
    this.objectMapper = objectMapper;
  }

  public Account fetchAccountDetails(String accountNum) throws AgreementOverviewException {
    Account account = null;
    try (var response = accountsApiClient.getAccountDetails(accountNum)) {
      var responseString = sharedApiClientService.convertFeignResponseToString(response);
      if (response.status() == HttpStatus.OK.value()) {
        account = objectMapper.readValue(responseString, Account.class);
      } else if (response.status() == HttpStatus.NOT_FOUND.value()) {
        log.error(" Account not found for account number {} ", accountNum);
        throw new AgreementOverviewException(NOT_FOUND.getErrorCode(),
            NOT_FOUND.getErrorMessage());
      } else if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
        var error = objectMapper.readValue(responseString, Error.class);
        throw new AgreementOverviewException(TECHNICAL_ERROR.getErrorCode(),
            error.getErrorMessage());
      }
    } catch (IOException | RuntimeException exception) {
      log.error("Exception occurred in processing Accounts API Response", exception);
      throw new AgreementOverviewException(TECHNICAL_ERROR.getErrorCode(),
          TECHNICAL_ERROR.getErrorMessage());
    }
    return account;
  }

}
