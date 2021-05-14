package com.sinemergingtechnologies.database.controller;

import com.sinemergingtechnologies.database.model.Role;
import com.sinemergingtechnologies.database.model.RolesMap;
import com.sinemergingtechnologies.database.service.RolesMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rolesMap")
public class RolesMapController {

    @Autowired
    private RolesMapService rolesMapService;

    @GetMapping("/test")
    public String testResponse() {
        return "hello";
    }

    @GetMapping("/")
    public String findRolesMaps() {
        List<RolesMap> rolesMaps = (List<RolesMap>) rolesMapService.findAll();
        System.out.println(rolesMaps.size());
        System.out.println(rolesMaps.get(0));
        return "hello";
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolesMap> findRolesMapsById(@PathVariable("id") Integer id) {
        Optional<RolesMap> rolesMap = rolesMapService.findById(id);

        if (!rolesMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rolesMap.get());
    }
}
