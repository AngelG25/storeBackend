package com.portfolio.rest;

import com.portfolio.api.CartApi;
import com.portfolio.api.models.Cart;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@ApplicationScope
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartRest {

  private final CartApi cartApi;

  @PostMapping("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public UUID createCart(@RequestBody Cart cart) {
    return cartApi.createCart(cart);
  }

  @PutMapping("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateCart(@RequestBody Cart cart) {
    cartApi.updateCart(cart);
  }

  @GetMapping("/{idClient}")
  @Produces(MediaType.APPLICATION_JSON)
  public Cart getCartByClientId(@PathVariable UUID idClient) {
    return cartApi.getCartByClientId(idClient);
  }

  @GetMapping("/")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Cart> getCarts() {
    return cartApi.getCarts();
  }

  @GetMapping("/getOverallPrice/{idClient}")
  public double getOverallPrice(@PathVariable UUID idClient) {
    return cartApi.getOverallPrice(idClient);
  }

  @PatchMapping("/addProduct/{idProduct}/{idClient}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addProductToCart(@PathVariable UUID idProduct, @PathVariable UUID idClient)
      throws IOException, InterruptedException {
    cartApi.addProductToCart(idProduct, idClient);
  }

  @PatchMapping("/removeProduct/{idProduct}/{idClient}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeProductFromCart(@PathVariable UUID idProduct, @PathVariable UUID idClient) {
    cartApi.removeProductFromCart(idProduct, idClient);
  }

}
