package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sinemergingtechnologies.database.model.ProviderClientMap;
import com.sinemergingtechnologies.database.service.IProviderClientMapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sinemergingtechnologies.database.utils.ProviderClientMapUtils.validProviderClientMap;

@RestController
@RequestMapping("/providerclientmap")
public class ProviderClientMapController {

    @Autowired
    private IProviderClientMapService clientService;

    private ProviderClientMap sampleProviderClientMap = new ProviderClientMap(
            UUID.fromString("22a86c9f-52c8-4b5b-a65e-72f4d4573ab9"),
            2497365424921398107L,
            UUID.fromString("22a86c9f-52c8-4b5b-a65e-72f4d4573ab9"),
            2497365424921398107L
    );

    //    These paths are case-sensitive it appears
    @GetMapping("/")
    private List<ProviderClientMap> getProviderClientMaps(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<ProviderClientMap> clients = (List<ProviderClientMap>) clientService.findAll();
        if (clients.size() > 0) {
            ProviderClientMap firstProviderClientMap = (ProviderClientMap) clients.get(0);
            System.out.println(firstProviderClientMap.toString());
            System.out.println(firstProviderClientMap.hashCode());
            System.out.println(firstProviderClientMap.equals(firstProviderClientMap));
            System.out.println(sampleProviderClientMap.equals(firstProviderClientMap));
        } else {
            System.out.println("no ProviderClientMaps found");
        }

        return clients;
    }

    @PostMapping("/")
    private ResponseEntity<ProviderClientMap> newProviderClientMap(@RequestBody ProviderClientMap newProviderClientMap) {
        System.out.println("Attempting to create new ProviderClientMap");
        if (!validProviderClientMap(newProviderClientMap)) {
            return ResponseEntity.badRequest().build();
        }
        ProviderClientMap createdProviderClientMap = clientService.save(newProviderClientMap);

        if (createdProviderClientMap == null || createdProviderClientMap.getId() < 1) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdProviderClientMap);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ProviderClientMap> getSingleProviderClientMap(@PathVariable("id") Long id) {
        System.out.println("Searching for ProviderClientMap with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ProviderClientMap> foundSingleProviderClientMap = clientService.findById(id);

        if (!foundSingleProviderClientMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSingleProviderClientMap.get());

    }

    @PutMapping("/{id}")
    private ResponseEntity<ProviderClientMap> updateProviderClientMap(@RequestBody ProviderClientMap clientToUpdate, @PathVariable Long id) {
        System.out.println("Updating ProviderClientMap with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ProviderClientMap> foundSingleProviderClientMap = clientService.findById(id);

        if (!foundSingleProviderClientMap.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ProviderClientMap preUpdateProviderClientMap = foundSingleProviderClientMap.get();

        preUpdateProviderClientMap.setClient_uuid(clientToUpdate.getClient_uuid());
        preUpdateProviderClientMap.setId(clientToUpdate.getId());
        preUpdateProviderClientMap.setProvider_uuid(clientToUpdate.getProvider_uuid());
        preUpdateProviderClientMap.setProvider_id(clientToUpdate.getProvider_id());

        ProviderClientMap updatedProviderClientMap = clientService.save(preUpdateProviderClientMap);

        return ResponseEntity.ok(updatedProviderClientMap);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteProviderClientMap(@PathVariable Long id) {
        System.out.println("ProviderClientMap client with id " + id + ".");
        clientService.deleteById(id);

        Optional<ProviderClientMap> foundSingleProviderClientMap = clientService.findById(id);

        if (foundSingleProviderClientMap.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}
