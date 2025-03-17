package com.portfolio.repositories;

import com.portfolio.dao.ClientDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientDao, UUID> {

  Optional<ClientDao> findClientByEmail(String email);

}
