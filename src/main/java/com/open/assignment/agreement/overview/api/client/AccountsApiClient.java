package com.open.assignment.agreement.overview.api.client;

import com.open.assignment.agreement.overview.configuration.ApiClientConfiguration;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "accountsApiClient", url = "${accountsApi.baseUrl}",
    configuration = ApiClientConfiguration.class)
public interface AccountsApiClient {

  @GetMapping(value = "/{accountNum}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  Response getAccountDetails(@PathVariable("accountNum") String accountNum);
}
