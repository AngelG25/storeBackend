package com.portfolio.api;

import com.portfolio.api.models.Client;

import java.util.List;
import java.util.UUID;

public interface ClientApi {

  /**
   * Creates a client and a cart with assignation
   * @param client that will be created
   */
  void createClient(Client client);

  /**
   * Updates a client
   * @param client with the modified fields
   */
  void updateClient(Client client);

  /**
   * Deletes a client from the database
   * @param id of the client to be removed
   */
  void deleteClient(UUID id);

  /**
   * Finds a client by the id
   * @param id of the client to be found
   * @return client with the specified id
   */
  Client getClient(UUID id);

  /**
   * Find a client by the email
   * @param email of the client to be found
   * @return client with the specified email
   */
  Client getClientByEmail(String email);

  /**
   * Gets all the clients in the database
   * @return all the clients
   */
  List<Client> getClients();


}
