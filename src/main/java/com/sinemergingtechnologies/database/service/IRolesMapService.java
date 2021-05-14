package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.RolesMap;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public abstract interface IRolesMapService {

    abstract List<RolesMap> findAll();
    abstract Optional<RolesMap> findById(Integer id);
    abstract Optional<RolesMap> findByUserid(Long userId);
    abstract List<RolesMap> findByRoleid(Integer role_id);
//
//    abstract Role save(RolesMap rolesMap);
//
//    abstract void deleteById(Integer id);
}
