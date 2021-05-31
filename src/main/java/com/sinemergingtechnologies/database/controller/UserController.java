package com.sinemergingtechnologies.database.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sinemergingtechnologies.database.Enums;
import com.sinemergingtechnologies.database.model.*;

import com.sinemergingtechnologies.database.security.payload.request.LoginRequest;
import com.sinemergingtechnologies.database.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

//import static com.sinemergingtechnologies.database.utils.RolesMapUtils.validRolesMap;

import static com.sinemergingtechnologies.database.utils.RolesMapUtils.validRolesMap;
import static com.sinemergingtechnologies.database.utils.UserUtils.validUser;
import static com.sinemergingtechnologies.database.utils.PrimaryProviderMapUtils.validPrimaryProviderMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PrimaryProviderMapService primaryProviderMapService;
    @Autowired
    private RoleService rolesService;
    @Autowired
    private RolesMapService rolesMapService;
    @Autowired
    private ProviderEmailService providerEmailService;

    private User sampleUser = new User(
            "username",
            "firstname",
            "lastname",
            "email",
            "city",
            "us_state",
            "password"
    );

//    These paths are case-sensitive it appears
    @GetMapping("/")
    private List<User> getUsers(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<User> users = (List<User>) userService.findAll();
        if (users.size() < 1) {
            System.out.println("no users found");
        }

        return users;
    }

   @PostMapping("/login")
   private ResponseEntity attemptLogin(@RequestBody LoginRequest loginAttempt) {
       System.out.println("fake user login attempt");
       System.out.println(loginAttempt.toString());

       List<User> users = (List<User>) userService.findByEmail(loginAttempt.getEmail());

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
            System.out.println("Error with received user data");
            return ResponseEntity.badRequest().build();
        }

        List<User> existingUsers = userService.findByEmail(newUser.getEmail());

        if (existingUsers.size() > 0) {
            System.out.println("User entry already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        User createdUser = userService.save(newUser);

        if (!validUser(createdUser)) {
            System.out.println("Error saving user data");
            return ResponseEntity.notFound().build();
        }

         boolean isProvider = false;
         List<ProviderEmail> recognizedProviderEmails = providerEmailService.findAll();

         for (int i=0; i<recognizedProviderEmails.size(); i++) {
             String recognizedProviderEmail = recognizedProviderEmails.get(i).getProviderEmail();
             if (createdUser.getEmail().contains(recognizedProviderEmail)) {
                 isProvider = true;
             }
         }

         RolesMap rolesMap = new RolesMap();
         rolesMap.setUserid(createdUser.getId());
         if (isProvider) {
             rolesMap.setRoleid(Enums.RoleTitles.PROVIDER.ordinal());
         } else {
             rolesMap.setRoleid(Enums.RoleTitles.CLIENT.ordinal());
         }

         RolesMap createdRolesMap = rolesMapService.save(rolesMap);
         if (!validRolesMap(createdRolesMap)) {
             System.out.println("Error saving roles map");
             return ResponseEntity.notFound().build();
         }

         if (!isProvider) {
            // find and assign a random provider
            List<RolesMap> providerMaps = rolesMapService.findByRoleid(Enums.RoleTitles.PROVIDER.ordinal());

            List<User> providerUsers = new ArrayList<User>();
            for (int i=0; i<providerMaps.size(); i++) {
                Long userId = providerMaps.get(i).getUserid();
                Optional<User> provider = userService.findById(userId);
                if (provider.isPresent()) {
                    providerUsers.add(provider.get());
                }
            }
            System.out.println(String.format("Found %o providers", providerUsers.size()));

            int randomIndex = (int) Math.floor(Math.random() * providerUsers.size());
            PrimaryProviderMap map = new PrimaryProviderMap(
                  newUser.getUuid(),
                  newUser.getId(),
                  providerUsers.get(randomIndex).getUuid(),
                providerUsers.get(randomIndex).getId()
            );

            PrimaryProviderMap savedMap = primaryProviderMapService.save(map);

            if (!validPrimaryProviderMap(savedMap)) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createdUser);
            }
         }

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    private ResponseEntity<User> getSingleUser(@PathVariable("id") Long id) {
        System.out.println("Searching for user with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> foundSingleUser = userService.findById(id);

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

        Optional<User> foundSingleUser = userService.findById(id);

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

        User updatedUser = userService.save(preUpdateUser);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteUser(@PathVariable Long id) {
        System.out.println("Deleting user with id " + id + ".");
        userService.deleteById(id);

        Optional<User> foundSingleUser = userService.findById(id);

        if (foundSingleUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}
