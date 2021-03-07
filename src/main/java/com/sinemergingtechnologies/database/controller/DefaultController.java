package com.sinemergingtechnologies.database.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping("/test")
    public String testRoute(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello DefaultController";
    }

    @GetMapping("/testobj/{id}")
    public String testObj(@RequestParam(value = "name", defaultValue = "World") String name,
                          @PathVariable("id") String idString) {
        Long id = UUID.fromString(idString).getMostSignificantBits() & Long.MAX_VALUE;
        return id.toString();
    }
}
