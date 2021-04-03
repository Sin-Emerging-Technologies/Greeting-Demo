package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;

import com.sinemergingtechnologies.database.model.Client;
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

import static com.sinemergingtechnologies.database.utils.ClientUtils.validClient;
import static com.sinemergingtechnologies.database.utils.PrimaryProviderMapUtils.validPrimaryProviderMap;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;
    @Autowired
    private IProviderService providerService;
    @Autowired
    private IPrimaryProviderMapService primaryProviderMapService;

    private Client sampleClient = new Client(
            "firstname",
            "lastname",
            "email",
            "city",
            "us_state",
            "pass",
            "confirm"
    );

//    These paths are case-sensitive it appears
    @GetMapping("/")
    private List<Client> getClients(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<Client> clients = (List<Client>) clientService.findAll();
        if (clients.size() < 1) {
            System.out.println("no clients found");
        }

        return clients;
    }

   @PostMapping("/login")
   private ResponseEntity attemptLogin(@RequestBody LoginAttempt loginAttempt) {
       System.out.println("fake client login attempt");
       System.out.println(loginAttempt.toString());

       List<Client> clients = (List<Client>) clientService.findByEmail(loginAttempt.getEmail());

       if (clients.size() < 1 || clients.size() > 1) {
           System.out.println("Error - Expected 1 client but found " + clients.size());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sampleClient);
       }

       Client clientWithEmail = clients.get(0);
       System.out.println(clientWithEmail.toString());

       if (!loginAttempt.getPass().equals(clientWithEmail.getPass())) {
           System.out.println("Error - invalid password");
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(sampleClient);
       }

      return ResponseEntity.ok(clientWithEmail);
   }

    @PostMapping("/")
    private ResponseEntity<Client> newClient(@RequestBody Client newClient) {
        System.out.println("Attempting to create new client");

        if (!validClient(newClient)) {
            return ResponseEntity.badRequest().build();
        }

        Client createdClient = clientService.save(newClient);

        if (!validClient(createdClient)) {
            return ResponseEntity.notFound().build();
        }

        // get list of providers
        List<Provider> providers = providerService.findAll();

        int randomIndex = (int) Math.floor(Math.random()*providers.size());
        PrimaryProviderMap map = new PrimaryProviderMap(
                newClient.getClient_uuid(),
                newClient.getId(),
                providers.get(randomIndex).getProvider_uuid(),
                providers.get(randomIndex).getId()
        );

        PrimaryProviderMap savedMap = primaryProviderMapService.save(map);

        if (!validPrimaryProviderMap(savedMap)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createdClient);
        }

        return ResponseEntity.ok(createdClient);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Client> getSingleClient(@PathVariable("id") Long id) {
        System.out.println("Searching for client with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Client> foundSingleClient = clientService.findById(id);

        if (!foundSingleClient.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSingleClient.get());

    }

    @PutMapping("/{id}")
    private ResponseEntity<Client> updateClient(@RequestBody Client clientToUpdate, @PathVariable Long id) {
        System.out.println("Updating client with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Client> foundSingleClient = clientService.findById(id);

        if (!foundSingleClient.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Client preUpdateClient = foundSingleClient.get();

        preUpdateClient.setFirstname(clientToUpdate.getFirstname());
        preUpdateClient.setLastname(clientToUpdate.getLastname());
        preUpdateClient.setEmail(clientToUpdate.getEmail());
        preUpdateClient.setCity(clientToUpdate.getCity());
        preUpdateClient.setUs_state(clientToUpdate.getUs_state());
        preUpdateClient.setPass(clientToUpdate.getPass());
        preUpdateClient.setConfirm(clientToUpdate.getConfirm());

        Client updatedClient = clientService.save(preUpdateClient);

        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteClient(@PathVariable Long id) {
        System.out.println("Deleting client with id " + id + ".");
        clientService.deleteById(id);

        Optional<Client> foundSingleClient = clientService.findById(id);

        if (foundSingleClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}
