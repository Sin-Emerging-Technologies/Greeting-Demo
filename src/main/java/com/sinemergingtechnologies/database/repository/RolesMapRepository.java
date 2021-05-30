package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.RolesMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesMapRepository extends JpaRepository<RolesMap, Long> {
    Optional<RolesMap> findByUserid(Long userId);
    List<RolesMap> findByRoleid(Integer role_id);
}
