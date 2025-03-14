package com.portfolio.srv;

import com.portfolio.api.ClientApi;
import com.portfolio.api.models.Cart;
import com.portfolio.api.models.Client;
import com.portfolio.dao.ClientDao;
import com.portfolio.repositories.ClientRepository;
import com.portfolio.srv.utils.CartMapper;
import com.portfolio.srv.utils.ClientMapper;
import com.portfolio.srv.utils.HttpUtils;
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
  private final CartMapper cartMapper;

  @Override
  public void createClient(Client client) {
    UUID idCart = httpUtils.createCart();
    ClientDao clientDao = clientMapper.toClientDao(client);
    clientDao.setIdCart(idCart);
    clientRepository.save(clientDao);
  }

  @Override
  public void updateClient(Client client) {

  }

  @Override
  public void deleteClient(UUID id) {

  }

  @Override
  public Client getClient(UUID id) {
    return null;
  }

  @Override
  public Client getClientByEmail(String email) {
    return null;
  }

  @Override
  public List<Client> getClients() {
    return List.of();
  }

  private UUID cartCreation() {
    return httpUtils.createCart();
  }
}
