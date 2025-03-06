package com.portfolio.srv.utils;

import com.portfolio.api.models.Product;
import com.portfolio.dao.ProductDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  ProductDao productToDao(Product product);

  Product productToDto(ProductDao productDao);
}
