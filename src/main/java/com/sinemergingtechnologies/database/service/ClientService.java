package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Client;
import com.sinemergingtechnologies.database.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }

    @Override
    public Client save(Client client) {
        return (Client) clientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}
