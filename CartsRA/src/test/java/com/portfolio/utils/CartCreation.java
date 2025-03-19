package com.portfolio.utils;

import com.portfolio.api.models.Cart;
import com.portfolio.dao.CartDao;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.portfolio.utils.ProductCreation.createProduct;
import static com.portfolio.utils.ProductCreation.createProductDao;

public class CartCreation {

  public static final String ID_CART = "452f2c83-89b5-402f-868e-c6bc697a2bde";
  public static final String ID_CLIENT = "ba98ee4a-2ac8-4d6f-b90f-2b13234f21fe";

  public static Cart createCart() {
    return Cart.builder()
        .idCart(UUID.fromString(ID_CART))
        .idClient(UUID.fromString(ID_CLIENT))
        .updateDate(Instant.now())
        .products(List.of(createProduct(), createProduct()))
        .build();
  }

  public static CartDao createCartDao() {
    CartDao cartDao = new CartDao();
    cartDao.setIdCart(UUID.fromString(ID_CART));
    cartDao.setIdClient(UUID.fromString(ID_CLIENT));
    cartDao.setUpdateDate(Instant.now());
    cartDao.setProducts(List.of(createProductDao(), createProductDao()));
    return cartDao;
  }
}
