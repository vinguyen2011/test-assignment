package com.open.assignment.agreement.overview.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CardStatusEnum {

  @JsonProperty("BLOCKED")
  BLOCKED,
  @JsonProperty("ACTIVE")
  ACTIVE

}
