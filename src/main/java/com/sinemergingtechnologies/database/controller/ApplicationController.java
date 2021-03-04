package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.sinemergingtechnologies.database.model.Client;
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

//    These paths are case-sensitive it appears
    @GetMapping("/clients")
    public List<Client> getClients(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<Client> clients = (List<Client>) clientService.findAll();
        if (clients.size() > 0) {
            System.out.println(clients.get(0).toString());
        } else {
            System.out.println("no clients found");
        }

        return clients;
    }

    @GetMapping("/test")
    public String testRoute(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello test";
    }
    @GetMapping("/testobj")
    public Client testObj(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Client(
                counter.incrementAndGet(),
                String.format(template, name), //"firstname",
                "lastname",
                "email",
                "city",
                "us_state",
                "pass",
                "confirm"
        );
    }
}