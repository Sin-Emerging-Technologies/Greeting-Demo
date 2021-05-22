package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;

import com.sinemergingtechnologies.database.model.ProviderEmail;

import com.sinemergingtechnologies.database.service.ProviderEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sinemergingtechnologies.database.utils.ProviderEmailUtils.validProviderEmail;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/providerEmails")
public class ProviderEmailController {

    @Autowired
    private ProviderEmailService providerEmailService;

    private ProviderEmail sampleProviderEmail = new ProviderEmail("providerEmails");

    @GetMapping("/")
    private List<ProviderEmail> getProviderEmails(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<ProviderEmail> providerEmails = (List<ProviderEmail>) providerEmailService.findAll();
        if (providerEmails.size() < 1) {
            System.out.println("no providerEmails found");
        }

        return providerEmails;
    }

    @PostMapping("/")
    private ResponseEntity<ProviderEmail> createNewProviderEmail(@RequestBody ProviderEmail newProviderEmail) {
        System.out.println("Attempting to create new ProviderEmail");

        if (!validProviderEmail(newProviderEmail)) {
            return ResponseEntity.badRequest().build();
        }

        List<ProviderEmail> existingProviderEmails = providerEmailService.findByProviderEmail(newProviderEmail.getProviderEmail());

        if (existingProviderEmails.size() > 0) {
            System.out.println("ProviderEmail entry already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        ProviderEmail createdProviderEmail = providerEmailService.save(newProviderEmail);

        if (!validProviderEmail(createdProviderEmail)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdProviderEmail);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ProviderEmail> getSingleProviderEmail(@PathVariable("id") Long id) {
        System.out.println("Searching for ProviderEmail with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ProviderEmail> foundSingleProviderEmail = providerEmailService.findById(id);

        if (!foundSingleProviderEmail.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSingleProviderEmail.get());

    }

    @PutMapping("/{id}")
    private ResponseEntity<ProviderEmail> updateProviderEmail(@RequestBody ProviderEmail providerEmailToUpdate, @PathVariable Long id) {
        System.out.println("Updating provideremail with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<ProviderEmail> foundSingleProviderEmail = providerEmailService.findById(id);

        if (!foundSingleProviderEmail.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ProviderEmail preUpdateProviderEmail = foundSingleProviderEmail.get();

        preUpdateProviderEmail.setProviderEmail(providerEmailToUpdate.getProviderEmail());

        ProviderEmail updatedProviderEmail = providerEmailService.save(preUpdateProviderEmail);

        return ResponseEntity.ok(updatedProviderEmail);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteProviderEmail(@PathVariable Long id) {
        System.out.println("Deleting providerEmail with id " + id + ".");
        providerEmailService.deleteById(id);

        Optional<ProviderEmail> foundSingleProviderEmail = providerEmailService.findById(id);

        if (foundSingleProviderEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }
}

