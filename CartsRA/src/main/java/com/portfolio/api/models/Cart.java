package com.portfolio.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@Value
@JsonTypeName("CART")
@Jacksonized
public class Cart {

  @JsonProperty(value = "idCart")
  @NotNull
  UUID idCart;

  @JsonProperty(value = "idClient")
  UUID idClient;

  @JsonProperty(value = "products")
  List<Product> products;

  @JsonProperty("updateDate")
  @EqualsAndHashCode.Exclude
  @NotNull
  Instant updateDate;

}
