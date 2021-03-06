package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Client;
import com.sinemergingtechnologies.database.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }
}
