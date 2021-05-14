package com.sinemergingtechnologies.database.controller;

import com.sinemergingtechnologies.database.model.RolesMap;
import com.sinemergingtechnologies.database.service.RolesMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/rolesMap")
public class RolesMapController {

    @Autowired
    private RolesMapService rolesMapService;

    @GetMapping("/")
    public String testResponse() {
        return "hello";
    }

    @GetMapping("/get")
    public String findRolesMaps() {
        List<RolesMap> rolesMaps = (List<RolesMap>) rolesMapService.findAll();
        System.out.println(rolesMaps.size());
        System.out.println(rolesMaps.get(0));
        return "hello";
    }
}
