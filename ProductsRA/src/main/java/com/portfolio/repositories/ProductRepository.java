package com.portfolio.repositories;

import com.portfolio.dao.ProductDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductDao, UUID> {

  boolean existsByCode(String code);

}
