package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.PrimaryProviderMap;

import java.util.List;
import java.util.Optional;

public abstract interface IPrimaryProviderMapService {

    abstract List<PrimaryProviderMap> findAll();
    abstract PrimaryProviderMap save(PrimaryProviderMap primaryProviderMap);
    abstract Optional<PrimaryProviderMap> findById(Long id);
    abstract void deleteById(Long id);
}
