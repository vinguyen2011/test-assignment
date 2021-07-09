package com.open.assignment.agreement.overview.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AgreementTypeEnum {
  @JsonProperty("IBAN")
  IBAN
}
