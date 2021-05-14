package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;

import com.sinemergingtechnologies.database.model.Role;

import com.sinemergingtechnologies.database.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sinemergingtechnologies.database.utils.RoleUtils.validRole;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RoleService roleService;

    private Role sampleRole = new Role("sampleRoleTitle");

    @GetMapping("/")
    private List<Role> getRoles(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<Role> roles = (List<Role>) roleService.findAll();
        if (roles.size() < 1) {
            System.out.println("no roles found");
        }

        return roles;
    }

    @PostMapping("/")
    private ResponseEntity<Role> createNewRole(@RequestBody Role newRole) {
        System.out.println("Attempting to create new role");

        if (!validRole(newRole)) {
            System.out.println("Did you add the role as an enum?");
            return ResponseEntity.badRequest().build();
        }

        List<Role> existingRoles = roleService.findByRoleTitle(newRole.getRoleTitle());

        if (existingRoles.size() > 0) {
            System.out.println("Role entry already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Role createdRole = roleService.save(newRole);

        if (!validRole(createdRole)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdRole);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Role> getSingleRole(@PathVariable("id") Long id) {
        System.out.println("Searching for role with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Role> foundSingleRole = roleService.findById(id);

        if (!foundSingleRole.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSingleRole.get());

    }

    @PutMapping("/{id}")
    private ResponseEntity<Role> updateRole(@RequestBody Role roleToUpdate, @PathVariable Long id) {
        System.out.println("Updating role with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Role> foundSingleRole = roleService.findById(id);

        if (!foundSingleRole.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Role preUpdateRole = foundSingleRole.get();

        preUpdateRole.setRoleTitle(roleToUpdate.getRoleTitle());

        Role updatedRole = roleService.save(preUpdateRole);

        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteRole(@PathVariable Long id) {
        System.out.println("Deleting role with id " + id + ".");
        roleService.deleteById(id);

        Optional<Role> foundSingleRole = roleService.findById(id);

        if (foundSingleRole.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}

