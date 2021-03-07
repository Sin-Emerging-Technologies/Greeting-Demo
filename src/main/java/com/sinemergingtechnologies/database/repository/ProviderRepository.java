package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {

}
