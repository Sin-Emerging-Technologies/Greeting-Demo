package com.sinemergingtechnologies.database.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.sinemergingtechnologies.database.model.Client;
import com.sinemergingtechnologies.database.model.Tuple;
import com.sinemergingtechnologies.database.service.IClientService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApplicationController {

    @Autowired
    private IClientService clientService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private Client newClient = new Client(
//            counter.incrementAndGet(),
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
    public List<Client> getClients(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<Client> clients = (List<Client>) clientService.findAll();
        if (clients.size() > 0) {
            Client firstClient = (Client) clients.get(0);
            System.out.println(firstClient.toString());
            System.out.println(firstClient.hashCode());
            System.out.println(firstClient.equals(firstClient));
            System.out.println(newClient.equals(firstClient));
        } else {
            System.out.println("no clients found");
        }
        // System.out.println(getId().x);

        return clients;
    }

    @PutMapping("/client")
    public Client createClient(@RequestParam(value = "name", defaultValue = "World") String name) {
//        List<Client> clients = (List<Client>) clientService.findAll();
//        if (clients.size() > 0) {
//            Client firstClient = (Client) clients.get(0);
//            System.out.println(firstClient.toString());
//            System.out.println(firstClient.hashCode());
//            System.out.println(firstClient.equals(firstClient));
//            System.out.println(newClient.equals(firstClient));
//        } else {
//            System.out.println("no clients found");
//        }
        // System.out.println(getId().x);
        System.out.println("Created new client");
        return newClient;
    }

    @GetMapping("/test")
    public String testRoute(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello test";
    }
    @GetMapping("/testobj")
    public Client testObj(@RequestParam(value = "name", defaultValue = "World") String name) {

        return newClient;
    }
}