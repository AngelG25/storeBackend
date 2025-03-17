package com.portfolio.rest;

import com.portfolio.api.ClientApi;
import com.portfolio.api.models.Client;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

@RestController
@ApplicationScope
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientRest {

  private final ClientApi clientApi;

  @GetMapping("/")
  public List<Client> getClients() {
    return clientApi.getClients();
  }

  @PostMapping("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public void createClient(@RequestBody Client client) {
    clientApi.createClient(client);
  }

  @PutMapping("/")
  @Consumes(MediaType.APPLICATION_JSON)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateClient(@RequestBody Client client) {
    clientApi.updateClient(client);
  }

  @DeleteMapping("/{idClient}")
  public void deleteClient(@PathVariable UUID idClient) {
    clientApi.deleteClient(idClient);
  }

  @GetMapping("/findById/{idClient}")
  public Client getClientById(@PathVariable UUID idClient) {
    return clientApi.getClientById(idClient);
  }

  @GetMapping("/findByEmail/{email}")
  public Client getClientByEmail(@PathVariable String email) {
    return clientApi.getClientByEmail(email);
  }

}
