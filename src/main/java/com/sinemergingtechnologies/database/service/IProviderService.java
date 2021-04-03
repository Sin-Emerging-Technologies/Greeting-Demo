package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Provider;

import java.util.List;
import java.util.Optional;

public abstract interface IProviderService {

    abstract List<Provider> findAll();
    abstract Provider save(Provider client);
    abstract Optional<Provider> findById(Long id);
    abstract void deleteById(Long id);
    abstract List<Provider> findByEmail(String email);
}
