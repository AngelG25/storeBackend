package com.portfolio.api;

import com.portfolio.api.models.Product;

import java.util.List;
import java.util.UUID;

public interface ProductApi {

  List<Product> findProducts();

  Product findProductById(UUID id);

  void createProduct(Product product);

  void updateProduct(Product product);

  void deleteProduct(UUID id);

}
