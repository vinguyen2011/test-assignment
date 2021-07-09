package com.open.assignment.agreement.overview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Agreements {

  @JsonProperty("agreements")
  private List<Agreement> agreementList;
}
