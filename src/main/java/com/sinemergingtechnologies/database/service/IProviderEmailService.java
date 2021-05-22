package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.ProviderEmail;

import java.util.List;
import java.util.Optional;

public abstract interface IProviderEmailService {

    abstract List<ProviderEmail> findAll();
    abstract ProviderEmail save(ProviderEmail providerEmail);
    abstract Optional<ProviderEmail> findById(Long id);
    abstract void deleteById(Long id);
    abstract List<ProviderEmail> findByProviderEmail(String providerEmail);
}
