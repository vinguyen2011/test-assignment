package com.open.assignment.agreement.overview.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.open.assignment.agreement.overview.enums.CardStatusEnum;
import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import com.open.assignment.agreement.overview.model.Account;
import com.open.assignment.agreement.overview.model.Agreement;
import com.open.assignment.agreement.overview.model.Agreements;
import com.open.assignment.agreement.overview.model.DebitCard;
import com.open.assignment.agreement.overview.model.DebitCardDetails;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for AgreementOverviewService Mockito is used to prepare mocks, verification and
 * stubbing
 */
@ExtendWith(MockitoExtension.class)
class AgreementOverviewServiceTest {

  public static final String USER_ID = "User-id";
  public static final String ACCOUNT_NUM = "1234567890";
  public static final String DEBIT_CARD_NUM = "1111";

  @Mock
  private AgreementsApiService agreementsApiService;

  @Mock
  private AccountsApiService accountsApiService;

  @Mock
  private DebitCardApiService debitCardApiService;

  @Mock
  private Agreements agreements;

  @Mock
  private Agreement agreement;

  @Mock
  private Account account;

  @Mock
  private DebitCard debitCard;

  @Mock
  private DebitCardDetails debitCardDetails;

  @InjectMocks
  private AgreementOverviewService agreementOverviewService;

  @Test
  @DisplayName("test getAggregatedProducts happy flow")
  void testHappyFlowGetAggregatedProducts() throws AgreementOverviewException {
    when(agreementsApiService.fetchAgreementsInfo(anyString())).thenReturn(agreements);
    when(agreements.getAgreementList()).thenReturn(List.of(agreement));
    when(agreement.getAccount()).thenReturn(ACCOUNT_NUM);
    when(accountsApiService.fetchAccountDetails(anyString())).thenReturn(account);
    when(account.getDebitCards()).thenReturn(List.of(debitCard));
    when(debitCard.getCardId()).thenReturn(DEBIT_CARD_NUM);
    when(debitCardApiService.fetchDebitCardDetails(anyString())).thenReturn(debitCardDetails);
    when(debitCardDetails.getStatus()).thenReturn(CardStatusEnum.ACTIVE);

    var products = agreementOverviewService.getAggregatedProducts(USER_ID);
    assertNotNull(products);
    assertThat(products.getProducts(), hasSize(1));
    assertThat(products.getProducts().get(0).getDebitCards(), hasSize(1));
  }

  @Test
  @DisplayName("test getAggregatedProducts happy flow when debit card is Blocked")
  void testCardBlockedGetAggregatedProducts() throws AgreementOverviewException {
    when(agreementsApiService.fetchAgreementsInfo(anyString())).thenReturn(agreements);
    when(agreements.getAgreementList()).thenReturn(List.of(agreement));
    when(agreement.getAccount()).thenReturn(ACCOUNT_NUM);
    when(accountsApiService.fetchAccountDetails(anyString())).thenReturn(account);
    when(account.getDebitCards()).thenReturn(List.of(debitCard));
    when(debitCard.getCardId()).thenReturn(DEBIT_CARD_NUM);
    when(debitCardApiService.fetchDebitCardDetails(anyString())).thenReturn(debitCardDetails);
    when(debitCardDetails.getStatus()).thenReturn(CardStatusEnum.BLOCKED);

    var products = agreementOverviewService.getAggregatedProducts(USER_ID);
    assertNotNull(products);
    assertThat(products.getProducts(), hasSize(1));
    assertThat(products.getProducts().get(0).getDebitCards(), hasSize(0));

  }

  @Test
  @DisplayName("test getAggregatedProducts Empty agreements ")
  void testEmptyAgreementsGetAggregatedProducts() throws AgreementOverviewException {
    when(agreementsApiService.fetchAgreementsInfo(anyString())).thenReturn(agreements);
    when(agreements.getAgreementList()).thenReturn(new ArrayList<>());
    var products = agreementOverviewService.getAggregatedProducts(USER_ID);
    assertNotNull(products);
    assertThat(products.getProducts(), hasSize(0));
  }

  @Test
  @DisplayName("test getAggregatedProducts Exception flow ")
  void testExceptionGetAggregatedProducts() throws AgreementOverviewException {
    doThrow(AgreementOverviewException.class).when(agreementsApiService)
        .fetchAgreementsInfo(anyString());
    assertThrows(AgreementOverviewException.class, () -> {
      agreementOverviewService.getAggregatedProducts(USER_ID);
    });
  }

}