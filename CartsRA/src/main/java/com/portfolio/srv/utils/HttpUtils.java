package com.portfolio.srv.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.api.exceptions.MicroserviceCommunicationException;
import com.portfolio.api.exceptions.ProductNotFoundException;
import com.portfolio.api.models.Product;
import org.springframework.data.mapping.MappingException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpUtils {

  private HttpUtils() {}

  private static final HttpClient httpClient = HttpClient.newHttpClient();
  private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  public static Product getValidProduct(String productId) throws MappingException {

    String url = "http://localhost:8080/api/v1/products/isValidProduct/" + productId;

    return configRequest(productId, url);

  }

  public static Product getProduct(String productId) throws MappingException {

    String url = "http://localhost:8080/api/v1/products/" + productId;

    return configRequest(productId, url);

  }

  private static Product configRequest(String productId, String url) {
    final HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .header("Accept", "application/json")
        .build();

    final HttpResponse<String> response = sendProductRequest(request);

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

  private static HttpResponse<String> sendProductRequest(final HttpRequest request) {
    try {
      return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new MicroserviceCommunicationException("Error communicating with Product microservice");
    }

  }
}
