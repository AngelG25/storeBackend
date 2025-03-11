package com.portfolio.srv.utils;

import com.portfolio.api.models.Product;
import com.portfolio.dao.ProductDao;

public class ProductCreation {

  public Product createProduct() {
    return Product.builder()
        .name("milk")
        .price(3.4)
        .quantity(4)
        .code("PR001")
        .isInStock(true)
        .build();
  }

  public ProductDao createProductDao() {
    ProductDao productDao = new ProductDao();
    productDao.setQuantity(4);
    productDao.setInStock(true);
    productDao.setCode("PR001");
    productDao.setName("milk");
    productDao.setPrice(3.4);
    return productDao;
  }
}
