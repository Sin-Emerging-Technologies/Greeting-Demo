package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.RolesMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesMapRepository extends CrudRepository<RolesMap, Integer> {
    Optional<RolesMap> findByUserid(Long userId);
    List<RolesMap> findByRoleid(Integer role_id);
}
