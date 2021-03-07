package com.sinemergingtechnologies.database.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.sinemergingtechnologies.database.model.Client;
import com.sinemergingtechnologies.database.model.Tuple;
import com.sinemergingtechnologies.database.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {

    @Autowired
    private IClientService clientService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private Client sampleClient = new Client(
            "firstname",
            "lastname",
            "email",
            "city",
            "us_state",
            "pass",
            "confirm"
    );

    private Tuple<UUID, BigInteger> getId() {
        UUID uuid = UUID.randomUUID();
        BigInteger bigInt = new BigInteger(uuid.toString().replace("-", ""), 16);
        return new Tuple<>(uuid, bigInt);
    }

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
    private Client newClient(@RequestBody Client newClient) {
        System.out.println("Creating new client");
        return clientService.save(newClient);
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

//    @PutMapping("/clients/{id}")
//    private Client updateClient(@RequestBody Client updatedClient, @PathVariable Long id) {
//
//        return clientService.findById(id)
//                .map(client -> {
//                    client.setFirstname(updatedClient.getFirstname());
//                    client.setLastname(updatedClient.getLastname());
//                    client.setEmail(updatedClient.getEmail());
//                    client.setCity(updatedClient.getCity());
//                    client.setUs_state(updatedClient.getUs_state());
//                    client.setPass(updatedClient.getPass());
//                    client.setConfirm(updatedClient.getConfirm());
//
//                    return clientService.save(client);
//                })
//                .orElseGet(() -> {
//                    updatedClient.setId(id);
//                    return clientService.save(updatedClient);
//                });
//    }

//    @DeleteMapping("/employees/{id}")
//    void deleteEmployee(@PathVariable Long id) {
//        clientService.deleteById(id);
//    }

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
















