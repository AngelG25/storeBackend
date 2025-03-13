package com.portfolio.repositories;

import com.portfolio.api.models.Cart;
import com.portfolio.dao.CartDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartDao, UUID> {

  Cart getCartByIdClient(UUID idClient);

}
