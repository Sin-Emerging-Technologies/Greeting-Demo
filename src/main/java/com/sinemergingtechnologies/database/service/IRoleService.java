package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Role;

import java.util.List;
import java.util.Optional;

public abstract interface IRoleService {

    abstract List<Role> findAll();
    abstract List<Role> findByName(String name);
    abstract Optional<Role> findById(Integer id);

    abstract Role save(Role role);

    abstract void deleteById(Integer id);
}
