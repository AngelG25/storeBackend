package com.portfolio.repositories;

import com.portfolio.dao.CartDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartDao, UUID> {

  Optional<CartDao> findByIdClient(UUID idClient);

}
