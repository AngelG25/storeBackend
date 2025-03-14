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
@JsonTypeName("CLIENT")
@Jacksonized
public class Client {

  @JsonProperty(value = "idClient")
  @NotNull
  UUID idClient;

  @JsonProperty(value = "iban")
  @NotNull
  String iban;

  @JsonProperty(value = "name")
  @NotNull
  String name;

  @JsonProperty(value = "surname")
  @NotNull
  String surname;

  @JsonProperty(value = "phone")
  @NotNull
  String phone;

  @JsonProperty(value = "email")
  @NotNull
  String email;

  @JsonProperty(value = "address")
  @NotNull
  String address;

  @JsonProperty(value = "cart")
  @NotNull
  Cart cart;

  @JsonProperty("creationDate")
  @EqualsAndHashCode.Exclude
  @NotNull
  Instant creationDate;

  @JsonProperty("updateDate")
  @EqualsAndHashCode.Exclude
  @NotNull
  Instant updateDate;

}
