package com.open.assignment.agreement.overview.model;

import com.open.assignment.agreement.overview.enums.CardStatusEnum;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebitCardDetails {

  private String id;

  private CardStatusEnum status;

  private BigInteger cardNumber;

  private Integer sequenceNumber;

  private String cardHolder;

  private Limit atmLimit;

  private Limit posLimit;

  private Boolean contactless;

}
