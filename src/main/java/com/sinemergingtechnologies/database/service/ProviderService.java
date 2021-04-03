package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Client;
import com.sinemergingtechnologies.database.model.Provider;
import com.sinemergingtechnologies.database.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService  implements IProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public List<Provider> findAll() {
        return (List<Provider>) providerRepository.findAll();
    }

    @Override
    public Provider save(Provider client) {
        return (Provider) providerRepository.save(client);
    }

    @Override
    public Optional<Provider> findById(Long id) {
        return providerRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        providerRepository.deleteById(id);
    }

    @Override
    public List<Provider> findByEmail(String email) {
        return providerRepository.findByEmail(email);
    }
}
