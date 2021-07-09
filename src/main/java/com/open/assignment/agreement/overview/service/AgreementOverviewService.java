package com.open.assignment.agreement.overview.service;

import static com.open.assignment.agreement.overview.enums.CardStatusEnum.BLOCKED;

import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import com.open.assignment.agreement.overview.model.AggregatedProductDetails;
import com.open.assignment.agreement.overview.model.AggregatedProducts;
import com.open.assignment.agreement.overview.model.DebitCardDetails;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import lombok.extern.log4j.Log4j2;

@Named
@Singleton
@Log4j2
// not using spring @Service annotations for any future plan of being framework agnostic
public class AgreementOverviewService {

  private final AgreementsApiService agreementsApiService;

  private final AccountsApiService accountsApiService;

  private final DebitCardApiService debitCardApiService;

  @Inject
  public AgreementOverviewService(
      AgreementsApiService agreementsApiService,
      AccountsApiService accountsApiService,
      DebitCardApiService debitCardApiService
  ) {
    this.agreementsApiService = agreementsApiService;
    this.accountsApiService = accountsApiService;
    this.debitCardApiService = debitCardApiService;
  }

  public AggregatedProducts getAggregatedProducts(String userId) throws AgreementOverviewException {
    var agreements = agreementsApiService.fetchAgreementsInfo(userId);
    List<AggregatedProductDetails> products = new ArrayList<>();
    for (var agreement : agreements.getAgreementList()) {
      var productDetails = new AggregatedProductDetails();
      productDetails.setType(agreement.getType());
      var account = accountsApiService.fetchAccountDetails(agreement.getAccount());
      productDetails.setAccountNo(account.getAccountNo());
      productDetails.setBalance(account.getBalance());
      productDetails.setCreated(account.getCreated());
      productDetails.setUserId(account.getUserId());
      var debitCards = new ArrayList<DebitCardDetails>();
      for (var debitCard : account.getDebitCards()) {
        var debitCardDetails = debitCardApiService.fetchDebitCardDetails(debitCard.getCardId());
        if (BLOCKED != debitCardDetails.getStatus()) {
          debitCards.add(debitCardDetails);
        }
      }
      productDetails.setDebitCards(debitCards);
      products.add(productDetails);
    }
    return new AggregatedProducts(products);
  }

}
