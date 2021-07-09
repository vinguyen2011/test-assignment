package com.open.assignment.agreement.overview.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PeriodUnitEnum {

  @JsonProperty("PER_DAY")
  PER_DAY,

  @JsonProperty("PER_MONTH")
  PER_MONTH,

  @JsonProperty("PER_WEEK")
  PER_WEEK

}
