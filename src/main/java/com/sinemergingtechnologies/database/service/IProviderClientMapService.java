package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.ProviderClientMap;

import java.util.List;
import java.util.Optional;

public abstract interface IProviderClientMapService {

    abstract List<ProviderClientMap> findAll();
    abstract ProviderClientMap save(ProviderClientMap providerClientMap);
    abstract Optional<ProviderClientMap> findById(Long id);
    abstract void deleteById(Long id);
}
