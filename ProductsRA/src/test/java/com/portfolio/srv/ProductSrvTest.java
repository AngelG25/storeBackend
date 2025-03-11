package com.portfolio.srv;

import com.portfolio.api.exceptions.ExistingProductException;
import com.portfolio.api.exceptions.PersistException;
import com.portfolio.api.exceptions.ProductNotFoundException;
import com.portfolio.api.models.Product;
import com.portfolio.dao.ProductDao;
import com.portfolio.repositories.ProductRepository;
import com.portfolio.srv.utils.ProductCreation;
import com.portfolio.srv.utils.ProductMapper;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductSrvTest extends ProductCreation {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;

  @InjectMocks
  private ProductSrv productSrv;

  private static final UUID PRODUCT_UUID = UUID.randomUUID();

  @Test
  void testFindProducts() {
    when(productRepository.findAll()).thenReturn(List.of(createProductDao()));
    when(productMapper.productToDto(any(ProductDao.class))).thenReturn(createProduct());
    assertNotNull(productSrv.findProducts());
    verify(productMapper, times(1)).productToDto(any(ProductDao.class));
    verify(productRepository, only()).findAll();
  }

  @Test
  void testFindProductsNotInStock() {
    ProductDao productDao = createProductDao();
    productDao.setInStock(false);
    when(productRepository.findAll()).thenReturn(List.of(productDao));
    assertEquals(List.of(), productSrv.findProducts());
    verify(productRepository, only()).findAll();
    verifyNoInteractions(productMapper);
  }

  @Test
  void testFindById() {
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(createProductDao()));
    when(productMapper.productToDto(any(ProductDao.class))).thenReturn(createProduct());
    assertNotNull(productSrv.findProductById(PRODUCT_UUID));
    verify(productRepository, only()).findById(PRODUCT_UUID);
    verify(productMapper, only()).productToDto(any(ProductDao.class));
  }

  @Test
  void testFindByIdNotInStock() {
    ProductDao productDao = createProductDao();
    productDao.setInStock(false);
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    assertThrows(ProductNotFoundException.class, () -> productSrv.findProductById(PRODUCT_UUID));
    verify(productRepository, only()).findById(PRODUCT_UUID);
    verifyNoInteractions(productMapper);
  }

  @Test
  void testCreateProduct() {
    Product product = createProduct();
    when(productRepository.existsByCode(product.getCode())).thenReturn(false);
    when(productMapper.productToDao(product)).thenReturn(createProductDao());
    productSrv.createProduct(product);
    verify(productRepository, times(1)).existsByCode(product.getCode());
    verify(productRepository, times(1)).save(any(ProductDao.class));
    verify(productMapper, only()).productToDao(product);
  }

  @Test
  void testCreateProductAlreadyExists() {
    Product product = createProduct();
    when(productRepository.existsByCode(product.getCode())).thenReturn(true);
    assertThrows(ExistingProductException.class, () -> productSrv.createProduct(product));
    verify(productRepository, only()).existsByCode(product.getCode());
    verifyNoInteractions(productMapper);
  }

  @Test
  void testCreateProductPersistenceException() {
    Product product = createProduct();
    when(productRepository.existsByCode(product.getCode())).thenReturn(false);
    when(productMapper.productToDao(product)).thenReturn(createProductDao());
    when(productRepository.save(any(ProductDao.class))).thenThrow(PersistenceException.class);
    assertThrows(PersistException.class, () -> productSrv.createProduct(product));
    verify(productRepository, times(1)).existsByCode(product.getCode());
    verify(productRepository, times(1)).save(any(ProductDao.class));
    verify(productMapper, only()).productToDao(product);
  }


}
