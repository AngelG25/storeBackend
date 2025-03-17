package com.portfolio.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Client")
public class ClientDao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "idClient", unique = true)
  private UUID idClient;

  @Column(name = "iban", unique = true)
  private String iban;

  @Column(name = "name")
  private String name;

  @Column(name = "surname")
  private String surname;

  @Column(name = "phone", unique = true)
  private String phone;

  @Column(name = "email", unique = true, updatable = false)
  private String email;

  @Column(name = "address")
  private String address;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  @Column(name = "creationDate", updatable = false)
  private Instant creationDate;

  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  @Column(name = "updateDate")
  private Instant updateDate;

}
