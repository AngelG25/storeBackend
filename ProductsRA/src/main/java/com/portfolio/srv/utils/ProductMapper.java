package com.portfolio.srv.utils;

import com.portfolio.api.models.Product;
import com.portfolio.dao.ProductDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductDao productToDao(Product product);

  @Mapping(source = "inStock", target = "isInStock")
  Product productToDto(ProductDao productDao);
}
