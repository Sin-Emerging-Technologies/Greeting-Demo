package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.ProviderEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderEmailRepository extends CrudRepository<ProviderEmail, Long> {
    List<ProviderEmail> findByProviderEmail(String providerEmail);
}
