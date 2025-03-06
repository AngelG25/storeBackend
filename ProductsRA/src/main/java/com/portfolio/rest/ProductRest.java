package com.portfolio.rest;

import com.portfolio.api.ProductApi;
import com.portfolio.api.models.Product;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

@RestController
@ApplicationScope
@RequiredArgsConstructor
@Tag(name = "Products")
@RequestMapping("/products")
public class ProductRest {

  private final ProductApi productApi;

  @GetMapping("/")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Product> getProducts() {
    return productApi.findProducts();
  }

  @GetMapping("{idProduct}")
  @Produces(MediaType.APPLICATION_JSON)
  public Product getProduct(@PathVariable UUID idProduct) {
    return productApi.findProductById(idProduct);
  }

  @PostMapping("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public void createProduct(@RequestBody Product product) {
    productApi.createProduct(product);
  }

  @PutMapping("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateProduct(@RequestBody Product product) {
    productApi.updateProduct(product);
  }

  @PatchMapping("/{idProduct}")
  public void outStockProduct(@PathVariable UUID idProduct) {
    productApi.outStockProduct(idProduct);
  }

  @PatchMapping("/{idProduct}/{quantity}")
  public void restockProduct(@PathVariable UUID idProduct, @PathVariable double quantity) {
    productApi.restockProduct(idProduct, quantity);
  }

  @DeleteMapping("/{idProduct}")
  public void deleteProduct(@PathVariable UUID idProduct) {
    productApi.deleteProduct(idProduct);
  }
}
