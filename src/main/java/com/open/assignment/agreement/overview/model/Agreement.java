package com.open.assignment.agreement.overview.model;

import com.open.assignment.agreement.overview.enums.AgreementTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Agreement {

  private AgreementTypeEnum type;

  private String account;
}
