package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sinemergingtechnologies.database.model.User;
import com.sinemergingtechnologies.database.model.PrimaryProviderMap;
import com.sinemergingtechnologies.database.service.IUserService;
import com.sinemergingtechnologies.database.service.IPrimaryProviderMapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sinemergingtechnologies.database.utils.PrimaryProviderMapUtils.validPrimaryProviderMap;
import static com.sinemergingtechnologies.database.utils.UserUtils.validUser;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/primaryprovidermap")
public class PrimaryProviderMapController {

    @Autowired
    private IPrimaryProviderMapService primaryProviderMapService;
    @Autowired
    private IUserService userService;

    private PrimaryProviderMap samplePrimaryProviderMap = new PrimaryProviderMap(
            UUID.fromString("88888888-4444-4444-4444-bbbbbbbbbbbb"),
            1234567890123456789L,
            UUID.fromString("22a86c9f-52c8-4b5b-a65e-72f4d4573ab9"),
            2497365424921398107L
    );

    //    These paths are case-sensitive it appears
    @GetMapping("/")
    private List<PrimaryProviderMap> getPrimaryProviderMaps(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<PrimaryProviderMap> primaryProviderMaps = (List<PrimaryProviderMap>) primaryProviderMapService.findAll();
        if (primaryProviderMaps.size() > 0) {
            // PrimaryProviderMap firstPrimaryProviderMap = (PrimaryProviderMap) primaryProviderMaps.get(0);
        }

        System.out.println("Found " + primaryProviderMaps.size() + " PrimaryProviderMaps");

        return primaryProviderMaps;
    }

    @PostMapping("/")
    private ResponseEntity<PrimaryProviderMap> newPrimaryProviderMap(@RequestBody PrimaryProviderMap newPrimaryProviderMap) {
        System.out.println("Attempting to create new PrimaryProviderMap");

        if (!validPrimaryProviderMap(newPrimaryProviderMap)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> provider = userService.findById(newPrimaryProviderMap.getProvider_id());
        Optional<User> client = userService.findById(newPrimaryProviderMap.getId());

        if (
            !client.isPresent() ||
            !provider.isPresent() ||
            !validUser(client.get()) ||
            !validUser(provider.get())
        ) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        PrimaryProviderMap createdPrimaryProviderMap = primaryProviderMapService.save(newPrimaryProviderMap);

        if (!validPrimaryProviderMap(createdPrimaryProviderMap)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdPrimaryProviderMap);
    }

    @GetMapping("/{id}")
    private ResponseEntity<PrimaryProviderMap> getSinglePrimaryProviderMap(@PathVariable("id") Long id) {
        System.out.println("Searching for PrimaryProviderMap with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<PrimaryProviderMap> foundSinglePrimaryProviderMap = primaryProviderMapService.findById(id);

        if (!foundSinglePrimaryProviderMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSinglePrimaryProviderMap.get());

    }

    @PutMapping("/{id}")
    private ResponseEntity<PrimaryProviderMap> updatePrimaryProviderMap(@RequestBody PrimaryProviderMap primaryProviderMapToUpdate, @PathVariable Long id) {
        System.out.println("Updating PrimaryProviderMap with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<PrimaryProviderMap> foundSinglePrimaryProviderMap = primaryProviderMapService.findById(id);

        if (!foundSinglePrimaryProviderMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        PrimaryProviderMap preUpdatePrimaryProviderMap = foundSinglePrimaryProviderMap.get();

        preUpdatePrimaryProviderMap.setClient_uuid(primaryProviderMapToUpdate.getClient_uuid());
        preUpdatePrimaryProviderMap.setId(primaryProviderMapToUpdate.getId());
        preUpdatePrimaryProviderMap.setProvider_uuid(primaryProviderMapToUpdate.getProvider_uuid());
        preUpdatePrimaryProviderMap.setProvider_id(primaryProviderMapToUpdate.getProvider_id());

        PrimaryProviderMap updatedPrimaryProviderMap = primaryProviderMapService.save(preUpdatePrimaryProviderMap);

        return ResponseEntity.ok(updatedPrimaryProviderMap);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deletePrimaryProviderMap(@PathVariable Long id) {
        System.out.println("PrimaryProviderMap with id " + id + ".");
        primaryProviderMapService.deleteById(id);

        Optional<PrimaryProviderMap> foundSinglePrimaryProviderMap = primaryProviderMapService.findById(id);

        if (foundSinglePrimaryProviderMap.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}
