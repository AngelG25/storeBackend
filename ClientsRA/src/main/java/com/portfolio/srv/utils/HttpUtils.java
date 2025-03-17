package com.portfolio.srv.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.portfolio.api.exceptions.CartNotCreatedException;
import com.portfolio.api.exceptions.MicroserviceCommunicationException;
import com.portfolio.api.models.Cart;
import com.portfolio.dao.CartDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.MappingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Log4j2
public class HttpUtils {

  private static final HttpClient httpClient = HttpClient.newHttpClient();
  private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  private final CartMapper cartMapper;

  public UUID createCart() {

    String url = "http://localhost:8081/api/v1/carts/";
    CartDao cartDao = new CartDao();
    cartDao.setProducts(new ArrayList<>());
    Cart cart = cartMapper.toCart(cartDao);

    return configRequest(cart, url);

  }

  public void updateCart(UUID idCart, UUID idClient) {
    String url = "http://localhost:8081/api/v1/carts/";
    Cart cart = Cart.builder()
        .idCart(idCart)
        .idClient(idClient)
        .products(new ArrayList<>())
        .build();
    String cartJson;
    try {
      cartJson = objectMapper.writeValueAsString(cart);
    } catch (JsonProcessingException e) {
      throw new MappingException(e);
    }
    final HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .PUT(HttpRequest.BodyPublishers.ofString(cartJson, StandardCharsets.UTF_8))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .build();

    final HttpResponse<String> response = sendProductRequest(request);

    if (response.statusCode() == 200) {
      log.info("Successfully updated cart with id " + idCart);
    } else {
      throw new CartNotCreatedException("Cart couldn't be created, ERROR: " + response.statusCode());
    }
  }

  private static UUID configRequest(Cart cart, String url) {

    String cartJson = null;
    try {
      cartJson = objectMapper.writeValueAsString(cart);
    } catch (JsonProcessingException e) {
      throw new MappingException(e);
    }
    final HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .POST(HttpRequest.BodyPublishers.ofString(cartJson, StandardCharsets.UTF_8))
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .build();

    final HttpResponse<String> response = sendProductRequest(request);

    if (response.statusCode() == 200) {
      String cleanUuid = response.body().replace("\"", "");
      return UUID.fromString(cleanUuid);
    } else {
      throw new CartNotCreatedException("Cart couldn't be created, ERROR: " + response.statusCode());
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
