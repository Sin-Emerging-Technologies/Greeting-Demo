package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.ProviderEmail;
import com.sinemergingtechnologies.database.repository.ProviderEmailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderEmailService implements IProviderEmailService {

    @Autowired
    private ProviderEmailRepository providerEmailRepository;

    @Override
    public List<ProviderEmail> findAll() {
        return (List<ProviderEmail>) providerEmailRepository.findAll();
    }

    @Override
    public ProviderEmail save(ProviderEmail providerEmail) {
        return (ProviderEmail) providerEmailRepository.save(providerEmail);
    }

    @Override
    public Optional<ProviderEmail> findById(Long id) {
        return providerEmailRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        providerEmailRepository.deleteById(id);
    }

    @Override
    public List<ProviderEmail> findByProviderEmail(String providerEmail) {
        return providerEmailRepository.findByProviderEmail(providerEmail);
    }
}
