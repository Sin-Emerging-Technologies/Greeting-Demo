package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Role;

import java.util.List;
import java.util.Optional;

public abstract interface IRoleService {

    abstract List<Role> findAll();
    abstract Role save(Role role);
    abstract Optional<Role> findById(Long id);
    abstract void deleteById(Long id);
    abstract List<Role> findByRoleTitle(String roleTitle);
}
