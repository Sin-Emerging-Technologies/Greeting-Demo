package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.PrimaryProviderMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryProviderMapRepository extends JpaRepository<PrimaryProviderMap, Long> {

}
