package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Client;

import java.util.List;

public abstract interface IClientService {

    abstract List<Client> findAll();
}
