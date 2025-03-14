package com.portfolio.api;

import com.portfolio.api.models.Cart;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CartApi {

  /**
   * Creates a cart, will be called from client microservice
   * @param cart parameters of the cart to be created
   * @return the cart created
   */
  UUID createCart(Cart cart);

  /**
   * Get the cart of a specific client
   * @param clientId id of the client
   * @return the cart associated to the client
   */
  Cart getCartByClientId(UUID clientId);

  /**
   * Gets all the carts, this will only be accessible by admin
   * @return list of all the carts
   */
  List<Cart> getCarts();

  /**
   * Gets the overall price from the cart of the client
   * @param clientId id of the client
   * @return the price of everything inside the cart
   */
  double getOverallPrice(UUID clientId);

  /**
   * Add a product to the cart with a quantity of 1
   * @param productId the product that will be added
   * @param clientId the client who will receive the product
   */
  void addProductToCart(UUID productId, UUID clientId) throws IOException, InterruptedException;

  /**
   * Removes a product from the cart, reduce its quantity by 1
   * @param productId the product that will be removed
   * @param clientId the client who will receive the product
   */
  void removeProductFromCart(UUID productId, UUID clientId);
}
