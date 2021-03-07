package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sinemergingtechnologies.database.model.Client;
import com.sinemergingtechnologies.database.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sinemergingtechnologies.database.utils.ClientUtils.validClient;

@RestController
public class ApplicationController {

    @Autowired
    private IClientService clientService;

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
    @GetMapping("/clients")
    private List<Client> getClients(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<Client> clients = (List<Client>) clientService.findAll();
        if (clients.size() > 0) {
            Client firstClient = (Client) clients.get(0);
            System.out.println(firstClient.toString());
            System.out.println(firstClient.hashCode());
            System.out.println(firstClient.equals(firstClient));
            System.out.println(sampleClient.equals(firstClient));
        } else {
            System.out.println("no clients found");
        }

        return clients;
    }

    @PostMapping("/clients")
    private ResponseEntity<Client> newClient(@RequestBody Client newClient) {
        if (!validClient(newClient)) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("Creating new client");
        Client createdClient = clientService.save(newClient);

        if (createdClient == null || createdClient.getId() < 1) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdClient);
    }

    @GetMapping("/clients/{id}")
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

    @PutMapping("/clients/{id}")
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

    @DeleteMapping("/clients/{id}")
    private ResponseEntity deleteClient(@PathVariable Long id) {
        System.out.println("Deleting client with id " + id + ".");
        clientService.deleteById(id);

        Optional<Client> foundSingleClient = clientService.findById(id);

        if (foundSingleClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }

    @GetMapping("/test")
    public String testRoute(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello test";
    }
    @GetMapping("/testobj/{id}")
    public String testObj(@RequestParam(value = "name", defaultValue = "World") String name,
                          @PathVariable("id") String idString) {
        Long id = UUID.fromString(idString).getMostSignificantBits() & Long.MAX_VALUE;
        return id.toString();
    }
}
















