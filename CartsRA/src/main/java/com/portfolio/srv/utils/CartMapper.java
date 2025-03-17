package com.portfolio.srv.utils;

import com.portfolio.api.models.Cart;
import com.portfolio.dao.CartDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartMapper {

  CartDao toCartDao(Cart cart);

  Cart toCart(CartDao cartDao);
}
