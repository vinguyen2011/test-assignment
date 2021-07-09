package com.open.assignment.agreement.overview.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import com.open.assignment.agreement.overview.model.AggregatedProducts;
import com.open.assignment.agreement.overview.service.AgreementOverviewService;
import com.open.assignment.agreement.overview.validator.AgreementOverviewValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AgreementOverviewResourceTest {

  public static final String USER_ID = "User-id";

  @Mock
  private AgreementOverviewService agreementOverviewService;

  @Mock
  private AgreementOverviewValidator validator;

  @Mock
  private AggregatedProducts aggregatedProducts;

  @InjectMocks
  private AgreementOverviewResource agreementOverviewResource;

  @Test
  @DisplayName("test for getAggregatedProductDetails happy flow")
  void testHappyFlowGetAggregatedProductDetails() throws AgreementOverviewException {
    doNothing().when(validator).validateInputs(anyString());
    when(agreementOverviewService.getAggregatedProducts(anyString()))
        .thenReturn(aggregatedProducts);
    var responseEntity = agreementOverviewResource.getAggregatedProductDetails(USER_ID);
    assertThat(responseEntity.getBody(), is(aggregatedProducts));
  }

  @Test
  @DisplayName("test for getAggregatedProductDetails exception flow")
  void testExceptionFlowGetAggregatedProductDetails() throws AgreementOverviewException {
    doThrow(AgreementOverviewException.class).when(validator).validateInputs(anyString());
    assertThrows(AgreementOverviewException.class, () -> {
      agreementOverviewResource.getAggregatedProductDetails(USER_ID);
    });
  }
}