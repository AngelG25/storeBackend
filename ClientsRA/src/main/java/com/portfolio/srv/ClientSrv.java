package com.portfolio.srv;

import com.portfolio.api.ClientApi;
import com.portfolio.api.exceptions.ClientNotFoundException;
import com.portfolio.api.exceptions.PersistException;
import com.portfolio.api.models.Client;
import com.portfolio.dao.ClientDao;
import com.portfolio.repositories.ClientRepository;
import com.portfolio.srv.utils.ClientMapper;
import com.portfolio.srv.utils.HttpUtils;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@ApplicationScope
@Log4j2
public class ClientSrv implements ClientApi {

  private final ClientRepository clientRepository;
  private final ClientMapper clientMapper;
  private final HttpUtils httpUtils;

  @Override
  public void createClient(Client client) {
    UUID idCart = httpUtils.createCart();
    ClientDao clientDao = clientMapper.toClientDao(client);
    clientDao.setIdCart(idCart);
    clientRepository.save(clientDao);
    httpUtils.updateCart(idCart, clientDao.getIdClient());
  }

  @Override
  public void updateClient(Client client) {
    clientRepository.findById(client.getIdClient())
        .orElseThrow(() -> new ClientNotFoundException("Client not found"));
    clientRepository.save(clientMapper.toClientDao(client));
  }

  @Override
  public void deleteClient(UUID id) {
    try {
      clientRepository.deleteById(id);
    } catch (PersistenceException e) {
      throw new PersistException("Client with: " + id + " could not be deleted");
    }
  }

  @Override
  public Client getClientById(UUID id) {
    return clientRepository.findById(id)
        .map(clientMapper::toClient)
        .orElseThrow(() -> new ClientNotFoundException("Client with id " + id + " not found"));
  }

  @Override
  public Client getClientByEmail(String email) {
    return clientRepository.findClientByEmail(email)
        .map(clientMapper::toClient)
        .orElseThrow(() -> new ClientNotFoundException("Client with email " + email + " not found"));
  }

  @Override
  public List<Client> getClients() {
    return clientRepository.findAll()
        .stream()
        .map(clientMapper::toClient)
        .toList();
  }

}
