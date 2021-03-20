package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.PrimaryProviderMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryProviderMapRepository extends CrudRepository<PrimaryProviderMap, Long> {

}
