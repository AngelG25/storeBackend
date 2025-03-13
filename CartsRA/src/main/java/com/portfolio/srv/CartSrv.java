package com.portfolio.srv;

import com.portfolio.api.CartApi;
import com.portfolio.api.models.Cart;
import com.portfolio.api.models.Product;
import com.portfolio.dao.CartDao;
import com.portfolio.repositories.CartRepository;
import com.portfolio.srv.utils.CartMapper;
import com.portfolio.srv.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.io.IOException;
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
  public Cart getCartByClientId(UUID clientId) {
    return cartRepository.getCartByIdClient(clientId);
  }

  @Override
  public List<Cart> getCarts() {
    return cartRepository.findAll()
        .stream()
        .map(cartMapper::toCart)
        .toList();
  }

  @Override
  public double getOverallPrice(UUID clientId) {
    return 0;
  }

  @Override
  public void addProductToCart(UUID productId, UUID clientId) throws IOException, InterruptedException {
    try {
      Product product = HttpUtils.getProductById(productId.toString());
      log.info(product);

    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Error al comunicarse con el microservicio de productos", e);
    }
  }

  @Override
  public void removeProductFromCart(UUID productId, UUID clientId) {

  }
}
