package com.open.assignment.agreement.overview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Agreements {

  @JsonProperty("agreements")
  private List<Agreement> agreementList;
}
