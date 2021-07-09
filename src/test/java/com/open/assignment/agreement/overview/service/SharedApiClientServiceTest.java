package com.open.assignment.agreement.overview.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import feign.Response;
import feign.Response.Body;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SharedApiClientServiceTest {

  @Mock
  private Response response;

  @Mock
  private Body body;

  @Mock
  private InputStream inputStream;

  @InjectMocks
  private SharedApiClientService sharedApiClientService;

  @Test
  @DisplayName("test convertFeignResponseToString")
  void testConvertFeignResponseToString() throws IOException {
    when(response.body()).thenReturn(body);
    when(body.asInputStream()).thenReturn(inputStream);
    var result = sharedApiClientService.convertFeignResponseToString(response);
    assertTrue(result.isBlank());
  }

}