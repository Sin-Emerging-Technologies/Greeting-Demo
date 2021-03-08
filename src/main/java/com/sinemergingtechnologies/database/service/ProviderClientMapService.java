package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.ProviderClientMap;
import com.sinemergingtechnologies.database.repository.ProviderClientMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderClientMapService implements IProviderClientMapService {

    @Autowired
    private ProviderClientMapRepository providerClientMapRepository;

    @Override
    public List<ProviderClientMap> findAll() {
        return (List<ProviderClientMap>) providerClientMapRepository.findAll();
    }

    @Override
    public ProviderClientMap save(ProviderClientMap providerClientMap) {
        return (ProviderClientMap) providerClientMapRepository.save(providerClientMap);
    }

    @Override
    public Optional<ProviderClientMap> findById(Long id) {
        return providerClientMapRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        providerClientMapRepository.deleteById(id);
    }
}
