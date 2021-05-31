package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.User;
import com.sinemergingtechnologies.database.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User save(User user) {
        if (isValidUser(user)) {
            return (User) userRepository.save(user);
        }
        throw new NullPointerException("Error saving user - isValidUser() returned false");
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isValidUser(User user) {
        if (user.getPassword() == null) return false;
        if (user.getEmail() == null) return false;
        if (user.getId() == null) return false;
        if (user.getUuid() == null) return false;

        if (user.getPassword().length() < 1) return false;
        if (user.getEmail().length() < 1) return false;
        if (user.getId() < 1) return false;
        if (user.getUuid().toString().length() < 1) return false;

        return false;
    }
}
