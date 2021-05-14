package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.RolesMap;

import java.util.List;

public abstract interface IRolesMapService {

    abstract List<RolesMap> findAll();
//    abstract List<Role> findByRoleTitle(String roleTitle);
//    abstract Optional<Role> findById(Integer id);
//
//    abstract Role save(Role role);
//
//    abstract void deleteById(Integer id);
}
