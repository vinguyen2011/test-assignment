package com.open.assignment.agreement.overview.service;

import feign.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class SharedApiClientService {

  /**
   * common method to convert feign client response to String
   *
   * @param response feign client response
   * @return responseString
   * @throws IOException IOException
   */
  public String convertFeignResponseToString(Response response) throws IOException {
    var outputStream = new ByteArrayOutputStream();
    var inputStream = response.body().asInputStream();
    inputStream.transferTo(outputStream);
    return outputStream.toString(StandardCharsets.UTF_8.name());
  }
}
