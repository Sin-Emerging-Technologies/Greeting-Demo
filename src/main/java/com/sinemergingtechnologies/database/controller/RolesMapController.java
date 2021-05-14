package com.sinemergingtechnologies.database.controller;

import com.sinemergingtechnologies.database.model.Role;
import com.sinemergingtechnologies.database.model.RolesMap;
import com.sinemergingtechnologies.database.service.RolesMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.sinemergingtechnologies.database.utils.RoleUtils.validRole;
import static com.sinemergingtechnologies.database.utils.RolesMapUtils.validRolesMap;

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
    public ResponseEntity<List<RolesMap>>findRolesMaps() {
        List<RolesMap> rolesMaps = (List<RolesMap>) rolesMapService.findAll();
        return ResponseEntity.ok(rolesMaps);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<RolesMap> findRolesMapsById(@PathVariable("id") Integer id) {
        Optional<RolesMap> rolesMap = rolesMapService.findById(id);

        if (!rolesMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rolesMap.get());
    }

    @GetMapping("/userid/{id}")
    public ResponseEntity<RolesMap> findRolesMapsByUserId(@PathVariable("id") Long id) {
        Optional<RolesMap> rolesMaps = rolesMapService.findByUserid(id);

        if (!rolesMaps.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rolesMaps.get());
    }

    @GetMapping("/roleid/{id}")
    public ResponseEntity<List<RolesMap>> findRolesMapsByRoleId(@PathVariable("id") Integer id) {
        List<RolesMap> rolesMaps = rolesMapService.findByRoleid(id);

        if (rolesMaps.size() < 1) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rolesMaps);
    }

    @PostMapping("/")
    public ResponseEntity<RolesMap> createRolesMaps(@RequestBody RolesMap newRolesMap) {
        System.out.println("Attempting to create new RolesMap");

        if (!validRolesMap(newRolesMap)) {
            System.out.println("?");
            return ResponseEntity.badRequest().build();
        }

        Optional<RolesMap> existingRoleForUser = rolesMapService.findByUserid(newRolesMap.getUserid());

        if (existingRoleForUser.isPresent()) {
            System.out.println("RolesMap entry already exists for this user");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        RolesMap createdRolesMap = rolesMapService.save(newRolesMap);

        if (!validRolesMap(createdRolesMap)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdRolesMap);
    }

    @PutMapping("/{id}")
    private ResponseEntity<RolesMap> updateRole(@RequestBody RolesMap rolesMapToUpdate, @PathVariable Integer id) {
        System.out.println("Updating RolesMap with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<RolesMap> foundSingleRolesMap = rolesMapService.findById(id);

        if (!foundSingleRolesMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        RolesMap preUpdateRolesMap = foundSingleRolesMap.get();

        preUpdateRolesMap.setUserid(rolesMapToUpdate.getUserid());
        preUpdateRolesMap.setRoleid(rolesMapToUpdate.getRoleid());

        RolesMap updatedRolesMap = rolesMapService.save(preUpdateRolesMap);

        return ResponseEntity.ok(updatedRolesMap);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteRole(@PathVariable Integer id) {
        System.out.println("Deleting rolesMap with id " + id + ".");
        rolesMapService.deleteById(id);

        Optional<RolesMap> foundSingleRolesMap = rolesMapService.findById(id);

        if (foundSingleRolesMap.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}
