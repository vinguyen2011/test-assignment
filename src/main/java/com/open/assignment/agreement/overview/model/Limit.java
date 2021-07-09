package com.open.assignment.agreement.overview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.open.assignment.agreement.overview.enums.PeriodUnitEnum;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Limit {

  @JsonProperty("limit")
  private BigInteger cardLimit;

  private PeriodUnitEnum periodUnit;

}
