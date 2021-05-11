package com.sinemergingtechnologies.database.repository;

import com.sinemergingtechnologies.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<User, Long> {
    List<User> findByEmail(String email);
}
