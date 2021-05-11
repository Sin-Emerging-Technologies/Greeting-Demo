package com.sinemergingtechnologies.database.service;

import com.sinemergingtechnologies.database.model.User;
import com.sinemergingtechnologies.database.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<User> findAll() {
        return (List<User>) clientRepository.findAll();
    }

    @Override
    public User save(User user) {
        return (User) clientRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return clientRepository.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<User> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
}
