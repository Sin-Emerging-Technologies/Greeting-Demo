package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Client;

import java.util.List;
import java.util.Optional;

public abstract interface IClientService {

    abstract List<Client> findAll();
    abstract Client save(Client client);
    abstract Optional<Client> findById(Long id);
}
