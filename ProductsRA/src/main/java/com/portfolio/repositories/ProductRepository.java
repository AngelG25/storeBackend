package com.portfolio.repositories;

import com.portfolio.dao.ProductDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductDao, UUID> {

  boolean existsByCode(String code);

}
