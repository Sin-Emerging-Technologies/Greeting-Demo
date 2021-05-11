package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.User;

import java.util.List;
import java.util.Optional;

public abstract interface IUserRepository {

    abstract List<User> findAll();
    abstract User save(User user);
    abstract Optional<User> findById(Long id);
    abstract void deleteById(Long id);
    abstract List<User> findByEmail(String email);
}
