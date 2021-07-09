package com.open.assignment.agreement.overview.configuration;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign Client provider to support HTTP/2
 */
@Configuration
public class ApiClientConfiguration {

  @Bean
  public OkHttpClient client() {
    return new OkHttpClient();
  }
}
