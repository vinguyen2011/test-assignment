package com.open.assignment.agreement.overview.validator;

import static com.open.assignment.agreement.overview.exception.ErrorKeys.USER_ID_INVALID;

import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import javax.inject.Named;
import lombok.extern.log4j.Log4j2;

@Named
@Log4j2
public class AgreementOverviewValidator {

  private static final String PATTERN_ALPHA_NUMERIC = "^[a-zA-Z0-9]+$";

  public void validateInputs(String userId) throws AgreementOverviewException {
    if (userId == null || userId.isBlank() || !userId.matches(PATTERN_ALPHA_NUMERIC)) {
      throw new AgreementOverviewException(USER_ID_INVALID.getErrorCode(),
          USER_ID_INVALID.getErrorMessage());
    }
  }
}
