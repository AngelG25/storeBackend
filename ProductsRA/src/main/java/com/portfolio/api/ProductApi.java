package com.portfolio.api;

import com.portfolio.api.models.Product;

import java.util.List;
import java.util.UUID;

public interface ProductApi {

  /**
   * Finds all the products in the database that are in stock
   *
   * @param pageIndex int value of page index
   * @param pageSize int value of page size
   * @return List of products that are in stock
   */
  List<Product> findProducts(int pageIndex, int pageSize);

  /**
   * Finds the product with the id indicated if it is in stock
   * @param id of the product to find
   * @return product found with the id indicated
   */
  Product findProductById(UUID id);

  /**
   * Creates a product in the database
   *
   * @param product with the attributes that will be created
   */
  void createProduct(Product product);

  /**
   * Updates a product in the database
   *
   * @param product with the modified attributes to be updated
   */
  void updateProduct(Product product);

  /**
   * Sets the stock of the product to zero
   *
   * @param id of the product that will be out stocked
   */
  void outStockProduct(UUID id);

  /**
   * Restock the product with the id and quantity associated
   * @param id of the product that will be restocked
   * @param quantity of the product
   */
  void restockProduct(UUID id, double quantity);

  /**
   * Delete product with the id indicated
   * @param id of the product that will be deleted
   */
  void deleteProduct(UUID id);

  /**
   * Gets the product that can be added to the cart.
   * @param id the id of the product
   * @return the product that is in stock and can be added to the cart
   */
  Product getProductInStock(UUID id);

}
