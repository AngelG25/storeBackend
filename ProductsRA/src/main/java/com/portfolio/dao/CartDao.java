package com.portfolio.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Cart")
public class CartDao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "idCart", unique = true)
  private UUID idCart;

  @Column(name = "idClient", updatable = false)
  private UUID idClient;

  @OneToMany(mappedBy = "cartDao", cascade = CascadeType.ALL)
  @JoinColumn(name = "products")
  private List<ProductDao> products;

  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  @Column(name = "updateDate")
  private Instant updateDate;
}
