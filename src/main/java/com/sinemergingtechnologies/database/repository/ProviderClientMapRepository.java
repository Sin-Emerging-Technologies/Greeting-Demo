package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.ProviderClientMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderClientMapRepository extends CrudRepository<ProviderClientMap, Long> {

}
