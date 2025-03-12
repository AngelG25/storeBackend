package com.portfolio.srv;

import com.portfolio.api.ProductApi;
import com.portfolio.api.exceptions.ExistingProductException;
import com.portfolio.api.exceptions.PersistException;
import com.portfolio.api.exceptions.ProductNotFoundException;
import com.portfolio.api.models.Product;
import com.portfolio.dao.ProductDao;
import com.portfolio.repositories.ProductRepository;
import com.portfolio.srv.utils.ProductMapper;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@ApplicationScope
@RequiredArgsConstructor
public class ProductSrv implements ProductApi {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  private static final String PRODUCT_WITH_ID = "Product with id: ";
  private static final String NOT_FOUND = " not found";
  private static final String ERROR_SAVING = "Error while saving the product: ";

  @Override
  public List<Product> findProducts() {
    return productRepository.findAll()
        .stream()
        .filter(ProductDao::isInStock)
        .map(productMapper::productToDto)
        .toList();
  }

  @Override
  public Product findProductById(UUID id) {
    return productRepository.findById(id)
        .filter(ProductDao::isInStock)
        .map(productMapper::productToDto)
        .orElseThrow(() -> new ProductNotFoundException(PRODUCT_WITH_ID + id + NOT_FOUND));
  }

  @Override
  public void createProduct(Product product) {
    try {
      if (productRepository.existsByCode(product.getCode())) {
        throw new ExistingProductException("The product with id: " + product.getCode() + " already exists");
      }
      productRepository.save(productMapper.productToDao(product));
    } catch (PersistenceException e) {
      throw new PersistException(ERROR_SAVING + e.getMessage());
    }
  }

  @Override
  public void updateProduct(Product product) {
    final ProductDao updatedProduct = productMapper.productToDao(product);
    try {
      productRepository.findById(product.getIdProduct())
          .map(productDao -> productRepository.save(updatedProduct))
          .orElseThrow(() -> new ProductNotFoundException(PRODUCT_WITH_ID + product.getIdProduct() + NOT_FOUND));
    } catch (PersistenceException e) {
      throw new PersistException(ERROR_SAVING + e.getMessage());
    }
  }

  @Override
  public void outStockProduct(UUID id) {
    try {
      productRepository.findById(id)
          .map(productDao -> {
            productDao.setInStock(false);
            productDao.setQuantity(0.0);
            return productRepository.save(productDao);
          })
          .orElseThrow(() -> new ProductNotFoundException(PRODUCT_WITH_ID + id + NOT_FOUND));
    } catch (PersistenceException e) {
      throw new PersistException(ERROR_SAVING + e.getMessage());
    }

  }

  @Override
  public void restockProduct(UUID id, double quantity) {
    try {
      productRepository.findById(id)
          .map(productDao -> {
            productDao.setQuantity(productDao.getQuantity() + quantity);
            productDao.setInStock(true);
            return productRepository.save(productDao);
          })
          .orElseThrow(() -> new ProductNotFoundException(PRODUCT_WITH_ID + id + NOT_FOUND));
    } catch (PersistenceException e) {
      throw new PersistException(ERROR_SAVING + e.getMessage());
    }
  }

  @Override
  public void deleteProduct(UUID id) {
    try {
      productRepository.deleteById(id);
    } catch (PersistenceException e) {
      throw new PersistException(PRODUCT_WITH_ID + id + " could not be deleted");
    }
  }

  @Override
  public boolean isEligibleProduct(UUID id) {
    return productRepository.findById(id)
        .map(ProductDao::isInStock)
        .orElse(false);
  }
}
