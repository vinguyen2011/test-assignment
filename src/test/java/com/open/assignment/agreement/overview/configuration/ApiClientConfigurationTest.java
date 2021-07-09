package com.open.assignment.agreement.overview.configuration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import feign.okhttp.OkHttpClient;
import javax.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApiClientConfiguration.class)
class ApiClientConfigurationTest {

  @Inject
  private OkHttpClient okHttpClient;

  @Test
  @DisplayName("clinent initialization")
  void testClientBeanInitialization() {
    assertNotNull(okHttpClient);
  }

}