package com.open.assignment.agreement.overview.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AggregatedProducts {

  private List<AggregatedProductDetails> products;


}
