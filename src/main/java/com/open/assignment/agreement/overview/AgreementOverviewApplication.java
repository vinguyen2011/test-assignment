package com.open.assignment.agreement.overview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spring boot application exposing rest endpoint for fetching aggregated product details
 */
@SpringBootApplication
@EnableFeignClients
public class AgreementOverviewApplication {

  public static void main(String[] args) {
    SpringApplication.run(AgreementOverviewApplication.class, args);
  }
}
