package com.sinemergingtechnologies.database.controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rolesMap")
public class RolesMapController {
    @GetMapping("/")
    public String findRolesMaps() {
        return "hello";
    }
}
