package com.portfolio.srv;

import com.portfolio.api.CartApi;
import com.portfolio.api.exceptions.CartNotFoundException;
import com.portfolio.api.exceptions.MicroserviceCommunicationException;
import com.portfolio.api.exceptions.PersistException;
import com.portfolio.api.exceptions.ProductNotFoundException;
import com.portfolio.api.models.Cart;
import com.portfolio.api.models.Product;
import com.portfolio.dao.CartDao;
import com.portfolio.repositories.CartRepository;
import com.portfolio.srv.utils.CartMapper;
import com.portfolio.srv.utils.HttpUtils;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@ApplicationScope
@Log4j2
public class CartSrv implements CartApi {

  private final CartRepository cartRepository;
  private final CartMapper cartMapper;

  @Override
  public UUID createCart(Cart cart) {
    final CartDao cartDao = cartMapper.toCartDao(cart);
    cartRepository.save(cartDao);
    return cartDao.getIdCart();
  }

  @Override
  public void updateCart(Cart cart) {
    final CartDao updatedCart = cartMapper.toCartDao(cart);
    try {
      cartRepository.findById(cart.getIdCart())
          .map(productDao -> cartRepository.save(updatedCart))
          .orElseThrow(() -> new CartNotFoundException("Cart with id: " + cart.getIdCart() + "could not be found"));
    } catch (PersistenceException e) {
      throw new PersistException("Error while saving the cart: " + e.getMessage());    }

  }

  @Override
  public Cart getCartByClientId(UUID idClient) {
    return cartRepository.findByIdClient(idClient)
        .map(cartMapper::toCart)
        .orElseThrow(() -> new CartNotFoundException(idClient.toString()));
  }

  @Override
  public List<Cart> getCarts() {
    return cartRepository.findAll()
        .stream()
        .map(cartMapper::toCart)
        .toList();
  }

  @Override
  public double getOverallPrice(UUID idClient) {
    Cart cart = findCartByClientId(idClient);
    return cart.getProducts()
        .stream()
        .mapToDouble(Product::getPrice)
        .sum();
  }

  @Override
  public void addProductToCart(UUID idProduct, UUID idClient)
      throws ProductNotFoundException, MicroserviceCommunicationException, MappingException {
    final Product product = HttpUtils.getValidProduct(idProduct.toString());
    Cart cart = addProductToCart(idClient, product);
    cartRepository.save(cartMapper.toCartDao(cart));
  }

  @Override
  public void removeProductFromCart(UUID productId, UUID clientId) {
    final Product product = HttpUtils.getProduct(productId.toString());
    Cart cart = findCartByClientId(clientId);
    cart.getProducts().remove(product);
  }

  private Cart findCartByClientId(UUID clientId) {
    return cartRepository.findByIdClient(clientId)
        .map(cartMapper::toCart)
        .orElseThrow(() -> new CartNotFoundException("Cart with client id " + clientId.toString() + " not found"));
  }

  private Cart addProductToCart(final UUID clientId, final Product product) {
    Cart cart = findCartByClientId(clientId);
    List<Product> products = cart.getProducts();
    products.add(product);
    cart = cart.toBuilder()
        .products(products)
        .build();
    return cart;
  }
}
