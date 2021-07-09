package com.open.assignment.agreement.overview.api.client;

import com.open.assignment.agreement.overview.configuration.ApiClientConfiguration;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "agreementsApiClient", url = "${agreementsApi.baseUrl}",
    configuration = ApiClientConfiguration.class)
public interface AgreementsApiClient {

  @GetMapping(value = "/{userId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  Response getAgreements(@PathVariable("userId") String userId);
}
