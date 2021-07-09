package com.open.assignment.agreement.overview.validator;


import static com.open.assignment.agreement.overview.exception.ErrorKeys.USER_ID_INVALID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.open.assignment.agreement.overview.exception.AgreementOverviewException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AgreementOverviewValidatorTest {

  private AgreementOverviewValidator validator;

  @BeforeEach
  public void setUp() {
    validator = new AgreementOverviewValidator();
  }

  @Test
  @DisplayName("test for null userId")
  void testValidateInputsNullUserId() {
    testForValidation(null);
  }

  @Test
  @DisplayName("test for empty userId")
  void testValidateInputsEmptyUserId() {
    testForValidation("");
  }

  private void testForValidation(String s) {
    AgreementOverviewException exception =
        assertThrows(AgreementOverviewException.class, () -> {
          validator.validateInputs(s);
        });
    assertEquals(USER_ID_INVALID.getErrorCode(), exception.getErrorCode());
  }

  @Test
  @DisplayName("test for non-alpha numeric userId")
  void testValidateInputsNonAlphaNumericUserId() {
    testForValidation("User&%(^");
  }

  @Test
  @DisplayName("test for alpha numeric userId")
  void testValidateInputAlphaNumericUserId() {
    assertDoesNotThrow(() -> {
      validator.validateInputs("User1");
    });
  }

}