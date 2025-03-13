package com.portfolio.srv.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.api.models.Product;
import com.portfolio.dao.ProductDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@NoArgsConstructor
public class HttpUtils {

  private static final HttpClient httpClient = HttpClient.newHttpClient();
  private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  public static Product getProductById(String productId) throws IOException, InterruptedException {

    String url = "http://localhost:8080/api/v1/products/isValidProduct/" + productId;

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .header("Accept", "application/json")
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
      return objectMapper.readValue(response.body(), Product.class);
    } else {
      throw new RuntimeException("Error al obtener el producto: " + response.statusCode());
    }

  }
}
