package com.portfolio.utils;

import com.portfolio.api.models.Product;
import com.portfolio.dao.ProductDao;

import java.util.UUID;

public class ProductCreation {

  public static final String ID_PRODUCT ="aeb158ee-1572-4764-85bd-a00b1239ee28";

  public static Product createProduct() {
    return Product.builder()
        .idProduct(UUID.fromString(ID_PRODUCT))
        .name("milk")
        .price(3.4)
        .quantity(4)
        .code("PR001")
        .isInStock(true)
        .build();
  }

  public static ProductDao createProductDao() {
    ProductDao productDao = new ProductDao();
    productDao.setIdProduct(UUID.fromString(ID_PRODUCT));
    productDao.setQuantity(4);
    productDao.setInStock(true);
    productDao.setCode("PR001");
    productDao.setName("milk");
    productDao.setPrice(3.4);
    return productDao;
  }
}
