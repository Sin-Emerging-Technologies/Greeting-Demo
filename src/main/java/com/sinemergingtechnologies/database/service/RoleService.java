package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.Role;
import com.sinemergingtechnologies.database.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }
    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role user) {
        return (Role) roleRepository.save(user);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }


}
