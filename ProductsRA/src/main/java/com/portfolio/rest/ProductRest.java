package com.portfolio.rest;

import com.portfolio.api.ProductApi;
import com.portfolio.api.models.Product;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

@RestController
@ApplicationScope
@RequiredArgsConstructor
@Tag(name = "Products", description = "This API serve all functionality for Product management")
@RequestMapping("/products")
public class ProductRest {

  private final ProductApi productApi;

  @GetMapping("/")
  @Operation(summary = "Finds all the products in stock",
             description = "Finds all the products in the database with isInStock true")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Products found correctly"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Produces(MediaType.APPLICATION_JSON)
  public List<Product> getProducts() {
    return productApi.findProducts();
  }

  @GetMapping("{idProduct}")
  @Operation(summary = "Finds a product specified by id",
             description = "Finds the product with the specified id in the database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Products found correctly"),
      @ApiResponse(responseCode = "404", description = "Product not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Produces(MediaType.APPLICATION_JSON)
  public Product getProduct(@PathVariable UUID idProduct) {
    return productApi.findProductById(idProduct);
  }

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a product",
      description = "Create the product with the specified attributes in the database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product created correctly"),
      @ApiResponse(responseCode = "409", description = "Product with specified code already exists"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Consumes(MediaType.APPLICATION_JSON)
  public void createProduct(@RequestBody Product product) {
    productApi.createProduct(product);
  }

  @PutMapping("/")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Updates a product",
      description = "Updates the product with the specified attributes in the database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product updated correctly"),
      @ApiResponse(responseCode = "404", description = "Product with specified id not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateProduct(@RequestBody Product product) {
    productApi.updateProduct(product);
  }

  @PatchMapping("/{idProduct}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Marks a product with no stock",
      description = "Marks a product with no stock and quantity 0")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product set with no stock"),
      @ApiResponse(responseCode = "404", description = "Product not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public void outStockProduct(@PathVariable UUID idProduct) {
    productApi.outStockProduct(idProduct);
  }

  @PatchMapping("/{idProduct}/{quantity}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Restock a product",
      description = "Marks a product with stock and the specified quantity")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product set with stock"),
      @ApiResponse(responseCode = "404", description = "Product not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public void restockProduct(@PathVariable UUID idProduct, @PathVariable double quantity) {
    productApi.restockProduct(idProduct, quantity);
  }

  @DeleteMapping("/{idProduct}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Removes a product",
      description = "Removes a product from the database")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product removed"),
      @ApiResponse(responseCode = "404", description = "Product not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public void deleteProduct(@PathVariable UUID idProduct) {
    productApi.deleteProduct(idProduct);
  }

  @GetMapping("/isValidProduct/{idProduct}")
  public boolean isValidProduct(@PathVariable UUID idProduct) {
    return productApi.isEligibleProduct(idProduct);
  }
}
