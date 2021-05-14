package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.RolesMap;
import com.sinemergingtechnologies.database.repository.RolesMapRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesMapService implements IRolesMapService {

    @Autowired
    private RolesMapRepository rolesMapRepository;

    @Override
    public List<RolesMap> findAll() {
        return (List<RolesMap>) rolesMapRepository.findAll();
    }
    @Override
    public Optional<RolesMap> findById(Integer id) {
        return rolesMapRepository.findById(id);
    }
//    @Override
//    public List<RolesMap> findByRoleTitle(String roleTitle) {
//        return rolesMapRepository.findByRoleTitle(roleTitle);
//    }
//
//    @Override
//    public RolesMap save(RolesMap user) {
//        return (RolesMap) rolesMapRepository.save(user);
//    }
//
//    @Override
//    public void deleteById(Integer id) {
//        rolesMapRepository.deleteById(id);
//    }
}
