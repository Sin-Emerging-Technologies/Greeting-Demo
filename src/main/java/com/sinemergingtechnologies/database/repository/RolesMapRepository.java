package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.RolesMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesMapRepository extends CrudRepository<RolesMap, Integer> {
}
