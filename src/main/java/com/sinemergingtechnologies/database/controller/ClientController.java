package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;

import com.sinemergingtechnologies.database.model.User;
import com.sinemergingtechnologies.database.model.LoginAttempt;
import com.sinemergingtechnologies.database.model.PrimaryProviderMap;
import com.sinemergingtechnologies.database.model.Provider;
import com.sinemergingtechnologies.database.service.IClientService;

import com.sinemergingtechnologies.database.service.IPrimaryProviderMapService;
import com.sinemergingtechnologies.database.service.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static com.sinemergingtechnologies.database.utils.ClientUtils.validUser;
import static com.sinemergingtechnologies.database.utils.PrimaryProviderMapUtils.validPrimaryProviderMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class ClientController {

    @Autowired
    private IClientService clientService;
    @Autowired
    private IProviderService providerService;
    @Autowired
    private IPrimaryProviderMapService primaryProviderMapService;

    private User sampleUser = new User(
            "firstname",
            "lastname",
            "email",
            "city",
            "us_state",
            "password",
            "confirm"
    );

//    These paths are case-sensitive it appears
    @GetMapping("/")
    private List<User> getUsers(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<User> users = (List<User>) clientService.findAll();
        if (users.size() < 1) {
            System.out.println("no users found");
        }

        return users;
    }

   @PostMapping("/login")
   private ResponseEntity attemptLogin(@RequestBody LoginAttempt loginAttempt) {
       System.out.println("fake user login attempt");
       System.out.println(loginAttempt.toString());

       List<User> users = (List<User>) clientService.findByEmail(loginAttempt.getEmail());

       if (users.size() < 1 || users.size() > 1) {
           System.out.println("Error - Expected 1 user but found " + users.size());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sampleUser);
       }

       User userWithEmail = users.get(0);
       System.out.println(userWithEmail.toString());

       String candidate_password = loginAttempt.getPassword();
       String stored_hash = userWithEmail.getPassword();
       if (!BCrypt.checkpw(candidate_password, stored_hash)) {
           System.out.println("Error - invalid password");
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(sampleUser);
       }
        // need to implement a session token and timeout
      return ResponseEntity.ok(userWithEmail);
   }

    @PostMapping("/")
    private ResponseEntity<User> createNewUser(@RequestBody User newUser) {
        System.out.println("Attempting to create new user");

        if (!validUser(newUser)) {
            return ResponseEntity.badRequest().build();
        }

        List<User> existingUsers = clientService.findByEmail(newUser.getEmail());

        if (existingUsers.size() > 0) {
            System.out.println("User entry already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User createdUser = clientService.save(newUser);

        if (!validUser(createdUser)) {
            return ResponseEntity.notFound().build();
        }

        // get list of providers
        List<Provider> providers = providerService.findAll();

        int randomIndex = (int) Math.floor(Math.random()*providers.size());
        PrimaryProviderMap map = new PrimaryProviderMap(
                newUser.get_uuid(),
                newUser.getId(),
                providers.get(randomIndex).getProvider_uuid(),
                providers.get(randomIndex).getId()
        );

        PrimaryProviderMap savedMap = primaryProviderMapService.save(map);

        if (!validPrimaryProviderMap(savedMap)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createdUser);
        }

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    private ResponseEntity<User> getSingleUser(@PathVariable("id") Long id) {
        System.out.println("Searching for user with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> foundSingleUser = clientService.findById(id);

        if (!foundSingleUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSingleUser.get());

    }

    @PutMapping("/{id}")
    private ResponseEntity<User> updateUser(@RequestBody User userToUpdate, @PathVariable Long id) {
        System.out.println("Updating user with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> foundSingleUser = clientService.findById(id);

        if (!foundSingleUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        User preUpdateUser = foundSingleUser.get();

        preUpdateUser.setFirstname(userToUpdate.getFirstname());
        preUpdateUser.setLastname(userToUpdate.getLastname());
        preUpdateUser.setEmail(userToUpdate.getEmail());
        preUpdateUser.setCity(userToUpdate.getCity());
        preUpdateUser.setUs_state(userToUpdate.getUs_state());
        preUpdateUser.setPassword(userToUpdate.getPassword());
        preUpdateUser.setConfirm(userToUpdate.getConfirm());

        User updatedUser = clientService.save(preUpdateUser);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteUser(@PathVariable Long id) {
        System.out.println("Deleting user with id " + id + ".");
        clientService.deleteById(id);

        Optional<User> foundSingleUser = clientService.findById(id);

        if (foundSingleUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}
