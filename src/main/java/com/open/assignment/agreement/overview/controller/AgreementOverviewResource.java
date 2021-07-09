package com.open.assignment.agreement.overview.controller;

import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import com.open.assignment.agreement.overview.model.AggregatedProducts;
import com.open.assignment.agreement.overview.service.AgreementOverviewService;
import com.open.assignment.agreement.overview.validator.AgreementOverviewValidator;
import javax.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for fetching aggregated product details
 */
@RestController
@Log4j2
public class AgreementOverviewResource {

  private final AgreementOverviewService agreementOverviewService;

  private final AgreementOverviewValidator validator;

  @Inject
  public AgreementOverviewResource(AgreementOverviewService agreementOverviewService,
      AgreementOverviewValidator validator) {
    this.agreementOverviewService = agreementOverviewService;
    this.validator = validator;
  }

  @GetMapping(value = "/agreement-overview/{user}")
  public ResponseEntity<AggregatedProducts> getAggregatedProductDetails(
      @PathVariable("user") String userId) throws AgreementOverviewException {
    validator.validateInputs(userId);
    log.info("fetching product details for userId {}", userId);
    return ResponseEntity.ok().body(agreementOverviewService.getAggregatedProducts(userId));
  }
}
