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
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.doThrow;
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

  static Product product;
  private static ProductDao productDao;
  private static final UUID PRODUCT_UUID = UUID.randomUUID();

  @BeforeEach
  void configTest() {
    product = createProduct();
    product = product.toBuilder()
        .idProduct(PRODUCT_UUID)
        .build();
    productDao = createProductDao();
    productDao.setIdProduct(PRODUCT_UUID);
  }
  
  @Test
  void testFindProducts() {
    when(productRepository.findAll()).thenReturn(List.of(productDao));
    when(productMapper.productToDto(any(ProductDao.class))).thenReturn(product);
    assertNotNull(productSrv.findProducts());
    verify(productMapper, times(1)).productToDto(any(ProductDao.class));
    verify(productRepository, only()).findAll();
  }

  @Test
  void testFindProductsNotInStock() {
    productDao.setInStock(false);
    when(productRepository.findAll()).thenReturn(List.of(productDao));
    assertEquals(List.of(), productSrv.findProducts());
    verify(productRepository, only()).findAll();
    verifyNoInteractions(productMapper);
  }

  @Test
  void testFindById() {
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    when(productMapper.productToDto(any(ProductDao.class))).thenReturn(product);
    assertNotNull(productSrv.findProductById(PRODUCT_UUID));
    verify(productRepository, only()).findById(PRODUCT_UUID);
    verify(productMapper, only()).productToDto(any(ProductDao.class));
  }

  @Test
  void testFindByIdNotInStock() {
    productDao.setInStock(false);
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    assertThrows(ProductNotFoundException.class, () -> productSrv.findProductById(PRODUCT_UUID));
    verify(productRepository, only()).findById(PRODUCT_UUID);
    verifyNoInteractions(productMapper);
  }

  @Test
  void testCreateProduct() {
    when(productRepository.existsByCode(product.getCode())).thenReturn(false);
    when(productMapper.productToDao(product)).thenReturn(productDao);
    productSrv.createProduct(product);
    verify(productRepository, times(1)).existsByCode(product.getCode());
    verify(productRepository, times(1)).save(any(ProductDao.class));
    verify(productMapper, only()).productToDao(product);
  }

  @Test
  void testCreateProductAlreadyExists() {
    when(productRepository.existsByCode(product.getCode())).thenReturn(true);
    assertThrows(ExistingProductException.class, () -> productSrv.createProduct(product));
    verify(productRepository, only()).existsByCode(product.getCode());
    verifyNoInteractions(productMapper);
  }

  @Test
  void testCreateProductPersistenceException() {
    when(productRepository.existsByCode(product.getCode())).thenReturn(false);
    when(productMapper.productToDao(product)).thenReturn(productDao);
    when(productRepository.save(any(ProductDao.class))).thenThrow(PersistenceException.class);
    assertThrows(PersistException.class, () -> productSrv.createProduct(product));
    verify(productRepository, times(1)).existsByCode(product.getCode());
    verify(productRepository, times(1)).save(any(ProductDao.class));
    verify(productMapper, only()).productToDao(product);
  }

  @Test
  void testUpdateProduct() {
    when(productMapper.productToDao(product)).thenReturn(productDao);
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    when(productRepository.save(productDao)).thenReturn(productDao);
    productSrv.updateProduct(product);
    verify(productMapper, only()).productToDao(product);
    verify(productRepository, times(1)).save(productDao);
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
  }

  @Test
  void testUpdateProductNotFound() {
    when(productMapper.productToDao(product)).thenReturn(productDao);
    assertThrows(ProductNotFoundException.class, () -> productSrv.updateProduct(product));
    verify(productMapper, only()).productToDao(product);
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
  }

  @Test
  void testUpdateProductPersistenceException() {
    when(productMapper.productToDao(product)).thenReturn(productDao);
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    when(productRepository.save(productDao)).thenThrow(PersistenceException.class);
    assertThrows(PersistException.class, () -> productSrv.updateProduct(product));
    verify(productMapper, only()).productToDao(product);
    verify(productRepository, times(1)).save(productDao);
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
  }

  @Test
  void testOutStockProduct() {
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    when(productRepository.save(productDao)).thenReturn(productDao);
    productSrv.outStockProduct(PRODUCT_UUID);
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
    ProductDao outStockProduct = productDao;
    outStockProduct.setInStock(false);
    outStockProduct.setQuantity(0.0);
    verify(productRepository, times(1)).save(outStockProduct);
    verifyNoInteractions(productMapper);
  }

  @Test
  void testOutStockNotFoundProduct() {
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.empty());
    assertThrows(ProductNotFoundException.class, () -> productSrv.outStockProduct(PRODUCT_UUID));
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
    verifyNoInteractions(productMapper);
  }

  @Test
  void testOutStockProductPersistenceException() {
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    when(productRepository.save(productDao)).thenThrow(PersistenceException.class);
    assertThrows(PersistException.class, () -> productSrv.outStockProduct(PRODUCT_UUID));
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
    verify(productRepository, times(1)).save(any(ProductDao.class));
    verifyNoInteractions(productMapper);
  }

  @Test
  void testRestockProduct() {
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    when(productRepository.save(productDao)).thenReturn(productDao);
    productSrv.restockProduct(PRODUCT_UUID, 3);
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
    ProductDao reStockedProduct = productDao;
    reStockedProduct.setInStock(true);
    reStockedProduct.setQuantity(3.0);
    verify(productRepository, times(1)).save(reStockedProduct);
    verifyNoInteractions(productMapper);
  }

  @Test
  void testRestockNotFoundProduct() {
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.empty());
    assertThrows(ProductNotFoundException.class, () -> productSrv.restockProduct(PRODUCT_UUID, 3));
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
    verifyNoInteractions(productMapper);
  }

  @Test
  void testRestockProductPersistenceException() {
    when(productRepository.findById(PRODUCT_UUID)).thenReturn(Optional.of(productDao));
    when(productRepository.save(productDao)).thenThrow(PersistenceException.class);
    assertThrows(PersistException.class, () -> productSrv.restockProduct(PRODUCT_UUID, 3));
    verify(productRepository, times(1)).findById(PRODUCT_UUID);
    verify(productRepository, times(1)).save(any(ProductDao.class));
    verifyNoInteractions(productMapper);
  }

  @Test
  void testDeleteProduct() {
    productSrv.deleteProduct(PRODUCT_UUID);
    verify(productRepository, times(1)).deleteById(PRODUCT_UUID);
    verifyNoInteractions(productMapper);
  }

  @Test
  void testDeleteProductNotFound() {
    doThrow(PersistenceException.class).when(productRepository).deleteById(PRODUCT_UUID);
    assertThrows(PersistException.class, () -> productSrv.deleteProduct(PRODUCT_UUID));
    verify(productRepository, times(1)).deleteById(PRODUCT_UUID);
    verifyNoInteractions(productMapper);
  }

}
