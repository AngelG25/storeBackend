package com.portfolio.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class ProductDao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "idProduct")
  private UUID idProduct;

  @Column(name = "code", unique = true, nullable = false)
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "quantity")
  private double quantity;

  @Column(name = "price")
  private double price;

  @Column(name = "isInStock")
  private boolean isInStock;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  @Column(name = "creationDate", updatable = false)
  private Instant creationDate;

  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  @Column(name = "updateDate")
  private Instant updateDate;

}
