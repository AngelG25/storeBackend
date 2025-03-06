package com.portfolio.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
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

  @JsonProperty(value = "code", access = JsonProperty.Access.READ_ONLY)
  @NotNull
  String code;

  @JsonProperty("name")
  @NotNull
  String name;

  @JsonProperty("description")
  @NotNull
  String description;

  @JsonProperty("price")
  @NotNull
  double price;

  @JsonProperty("creationDate")
  @NotNull
  Instant creationDate;

  @JsonProperty("updateDate")
  @NotNull
  Instant updateDate;
}
