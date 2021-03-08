package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sinemergingtechnologies.database.model.PrimaryProviderMap;
import com.sinemergingtechnologies.database.service.IPrimaryProviderMapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sinemergingtechnologies.database.utils.PrimaryProviderMapUtils.validPrimaryProviderMap;

@RestController
@RequestMapping("/primaryprovidermap")
public class PrimaryProviderMapController {

    @Autowired
    private IPrimaryProviderMapService clientService;

    private PrimaryProviderMap samplePrimaryProviderMap = new PrimaryProviderMap(
            UUID.fromString("22a86c9f-52c8-4b5b-a65e-72f4d4573ab9"),
            2497365424921398107L,
            UUID.fromString("22a86c9f-52c8-4b5b-a65e-72f4d4573ab9"),
            2497365424921398107L
    );

    //    These paths are case-sensitive it appears
    @GetMapping("/")
    private List<PrimaryProviderMap> getPrimaryProviderMaps(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<PrimaryProviderMap> clients = (List<PrimaryProviderMap>) clientService.findAll();
        if (clients.size() > 0) {
            PrimaryProviderMap firstPrimaryProviderMap = (PrimaryProviderMap) clients.get(0);
            System.out.println(firstPrimaryProviderMap.toString());
            System.out.println(firstPrimaryProviderMap.hashCode());
            System.out.println(firstPrimaryProviderMap.equals(firstPrimaryProviderMap));
            System.out.println(samplePrimaryProviderMap.equals(firstPrimaryProviderMap));
        } else {
            System.out.println("no PrimaryProviderMaps found");
        }

        return clients;
    }

    @PostMapping("/")
    private ResponseEntity<PrimaryProviderMap> newPrimaryProviderMap(@RequestBody PrimaryProviderMap newPrimaryProviderMap) {
        System.out.println("Attempting to create new PrimaryProviderMap");
        if (!validPrimaryProviderMap(newPrimaryProviderMap)) {
            return ResponseEntity.badRequest().build();
        }
        PrimaryProviderMap createdPrimaryProviderMap = clientService.save(newPrimaryProviderMap);

        if (createdPrimaryProviderMap == null || createdPrimaryProviderMap.getId() < 1) {
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

        Optional<PrimaryProviderMap> foundSinglePrimaryProviderMap = clientService.findById(id);

        if (!foundSinglePrimaryProviderMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSinglePrimaryProviderMap.get());

    }

    @PutMapping("/{id}")
    private ResponseEntity<PrimaryProviderMap> updatePrimaryProviderMap(@RequestBody PrimaryProviderMap clientToUpdate, @PathVariable Long id) {
        System.out.println("Updating PrimaryProviderMap with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<PrimaryProviderMap> foundSinglePrimaryProviderMap = clientService.findById(id);

        if (!foundSinglePrimaryProviderMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        PrimaryProviderMap preUpdatePrimaryProviderMap = foundSinglePrimaryProviderMap.get();

        preUpdatePrimaryProviderMap.setClient_uuid(clientToUpdate.getClient_uuid());
        preUpdatePrimaryProviderMap.setId(clientToUpdate.getId());
        preUpdatePrimaryProviderMap.setProvider_uuid(clientToUpdate.getProvider_uuid());
        preUpdatePrimaryProviderMap.setProvider_id(clientToUpdate.getProvider_id());

        PrimaryProviderMap updatedPrimaryProviderMap = clientService.save(preUpdatePrimaryProviderMap);

        return ResponseEntity.ok(updatedPrimaryProviderMap);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deletePrimaryProviderMap(@PathVariable Long id) {
        System.out.println("PrimaryProviderMap client with id " + id + ".");
        clientService.deleteById(id);

        Optional<PrimaryProviderMap> foundSinglePrimaryProviderMap = clientService.findById(id);

        if (foundSinglePrimaryProviderMap.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}
