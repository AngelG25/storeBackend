package com.portfolio.srv.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.api.exceptions.MicroserviceCommunicationException;
import com.portfolio.api.exceptions.ProductNotFoundException;
import com.portfolio.api.models.Product;
import lombok.NoArgsConstructor;
import org.springframework.data.mapping.MappingException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@NoArgsConstructor
public class HttpUtils {

  private static final HttpClient httpClient = HttpClient.newHttpClient();
  private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  public static Product getProductById(String productId) throws MappingException {

    String url = "http://localhost:8080/api/v1/products/isValidProduct/" + productId;

    HttpResponse<String> response = sendProductRequest(url);

    if (response.statusCode() == 200) {
      try {
        return objectMapper.readValue(response.body(), Product.class);
      } catch (JsonProcessingException e) {
        throw new MappingException("Error while mapping the response from product microservice: " + e.getMessage());
      }
    } else {
      throw new ProductNotFoundException("Product with id: " + productId + " couldn't be found, ERROR: " + response.statusCode());
    }

  }

  private static HttpResponse<String> sendProductRequest(final String url) {
    final HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .header("Accept", "application/json")
        .build();
    try {
      return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new MicroserviceCommunicationException("Error communicating with Product microservice");
    }

  }
}
