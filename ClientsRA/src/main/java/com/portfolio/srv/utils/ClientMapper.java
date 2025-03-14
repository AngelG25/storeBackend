package com.portfolio.srv.utils;

import com.portfolio.api.models.Client;
import com.portfolio.dao.ClientDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CartMapper.class)
public interface ClientMapper {

  @Mapping(target = "cartDao", source = "cart")
  ClientDao toClientDao(Client client);

  Client toClient(ClientDao clientDao);
}
