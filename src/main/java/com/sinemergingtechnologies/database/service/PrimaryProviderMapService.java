package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.PrimaryProviderMap;
import com.sinemergingtechnologies.database.repository.PrimaryProviderMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrimaryProviderMapService implements IPrimaryProviderMapService {

    @Autowired
    private PrimaryProviderMapRepository primaryProviderMapRepository;

    @Override
    public List<PrimaryProviderMap> findAll() {
        return (List<PrimaryProviderMap>) primaryProviderMapRepository.findAll();
    }

    @Override
    public PrimaryProviderMap save(PrimaryProviderMap primaryProviderMap) {
        return (PrimaryProviderMap) primaryProviderMapRepository.save(primaryProviderMap);
    }

    @Override
    public Optional<PrimaryProviderMap> findById(Long id) {
        return primaryProviderMapRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        primaryProviderMapRepository.deleteById(id);
    }
}
