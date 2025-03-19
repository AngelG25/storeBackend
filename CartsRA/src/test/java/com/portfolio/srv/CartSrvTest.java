package com.portfolio.srv;

import com.portfolio.api.exceptions.CartNotFoundException;
import com.portfolio.api.exceptions.PersistException;
import com.portfolio.api.models.Cart;
import com.portfolio.dao.CartDao;
import com.portfolio.repositories.CartRepository;
import com.portfolio.srv.utils.CartMapper;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.portfolio.utils.CartCreation.ID_CART;
import static com.portfolio.utils.CartCreation.ID_CLIENT;
import static com.portfolio.utils.CartCreation.createCart;
import static com.portfolio.utils.CartCreation.createCartDao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartSrvTest {

  @InjectMocks
  CartSrv cartSrv;

  @Mock
  CartRepository cartRepository;

  @Mock
  CartMapper cartMapper;

  private static Cart cart;
  private static CartDao cartDao;

  @BeforeEach
  void setUp() {
    cartDao = createCartDao();
    cart = createCart();
  }

  @Test
  void testCreateCart() {
    when(cartMapper.toCartDao(cart)).thenReturn(cartDao);
    when(cartRepository.save(cartDao)).thenReturn(cartDao);
    assertEquals(UUID.fromString(ID_CART), cartSrv.createCart(cart));
    verify(cartMapper, only()).toCartDao(cart);
    verify(cartRepository, only()).save(cartDao);
  }

  @Test
  void testUpdateCart() {
    when(cartMapper.toCartDao(cart)).thenReturn(cartDao);
    when(cartRepository.findById(UUID.fromString(ID_CART))).thenReturn(Optional.ofNullable(cartDao));
    when(cartRepository.save(cartDao)).thenReturn(cartDao);
    cartSrv.updateCart(cart);
    verify(cartMapper, only()).toCartDao(cart);
    verify(cartRepository, times(1)).save(cartDao);
    verify(cartRepository, times(1)).findById(UUID.fromString(ID_CART));
  }

  @Test
  void testUpdateCartNotFound() {
    when(cartMapper.toCartDao(cart)).thenReturn(cartDao);
    when(cartRepository.findById(UUID.fromString(ID_CART))).thenThrow(CartNotFoundException.class);
    assertThrows(CartNotFoundException.class, () -> cartSrv.updateCart(cart));
    verify(cartMapper, only()).toCartDao(cart);
    verify(cartRepository, only()).findById(UUID.fromString(ID_CART));
  }

  @Test
  void testUpdatePersistException() {
    when(cartMapper.toCartDao(cart)).thenReturn(cartDao);
    when(cartRepository.findById(UUID.fromString(ID_CART))).thenReturn(Optional.ofNullable(cartDao));
    when(cartRepository.save(cartDao)).thenThrow(PersistenceException.class);
    assertThrows(PersistException.class, () -> cartSrv.updateCart(cart));
    verify(cartMapper, only()).toCartDao(cart);
    verify(cartRepository, times(1)).findById(UUID.fromString(ID_CART));
    verify(cartRepository, times(1)).save(cartDao);
  }

  @Test
  void testGetCartByClientId() {
    when(cartRepository.findByIdClient(UUID.fromString(ID_CLIENT))).thenReturn(Optional.ofNullable(cartDao));
    when(cartMapper.toCart(cartDao)).thenReturn(cart);
    assertNotNull(cartSrv.getCartByClientId(UUID.fromString(ID_CLIENT)));
    verify(cartRepository, only()).findByIdClient(UUID.fromString(ID_CLIENT));
    verify(cartMapper, only()).toCart(cartDao);
  }

  @Test
  void testGetCartByClientIdCartNotFound() {
    UUID idClient = UUID.fromString(ID_CLIENT);
    when(cartRepository.findByIdClient(UUID.fromString(ID_CLIENT))).thenReturn(Optional.empty());
    assertThrows(CartNotFoundException.class, () -> cartSrv.getCartByClientId(idClient));
    verify(cartRepository, only()).findByIdClient(UUID.fromString(ID_CLIENT));
  }

  @Test
  void testGetCarts() {
    when(cartRepository.findAll()).thenReturn(List.of(cartDao));
    when(cartMapper.toCart(cartDao)).thenReturn(cart);
    assertNotNull(cartSrv.getCarts());
    verify(cartRepository, only()).findAll();
    verify(cartMapper, only()).toCart(cartDao);
  }

  @Test
  void testGetOverallPrice() {
    when(cartRepository.findByIdClient(UUID.fromString(ID_CLIENT))).thenReturn(Optional.ofNullable(cartDao));
    when(cartMapper.toCart(cartDao)).thenReturn(cart);
    assertEquals(6.8, cartSrv.getOverallPrice(UUID.fromString(ID_CLIENT)));
    verify(cartRepository, only()).findByIdClient(UUID.fromString(ID_CLIENT));
    verify(cartMapper, only()).toCart(cartDao);
  }

}
