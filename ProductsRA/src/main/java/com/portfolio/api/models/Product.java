package com.portfolio.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;
import java.util.UUID;

@Builder(toBuilder = true)
@Value
@JsonTypeName("PRODUCT")
@Jacksonized
public class Product {

  @JsonProperty(value = "idProduct", access = JsonProperty.Access.READ_ONLY)
  @NotNull
  UUID idProduct;

  @JsonProperty(value = "code")
  @NotNull
  String code;

  @JsonProperty("name")
  @NotNull
  String name;

  @JsonProperty("description")
  @NotNull
  String description;

  @JsonProperty("quantity")
  @NotNull
  double quantity;

  @JsonProperty("price")
  @NotNull
  double price;

  @JsonProperty("isInStock")
  @NotNull
  boolean isInStock;

  @JsonProperty("creationDate")
  @EqualsAndHashCode.Exclude
  @NotNull
  Instant creationDate;

  @JsonProperty("updateDate")
  @EqualsAndHashCode.Exclude
  @NotNull
  Instant updateDate;
}
