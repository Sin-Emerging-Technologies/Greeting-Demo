package com.sinemergingtechnologies.database.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sinemergingtechnologies.database.model.LoginAttempt;
import com.sinemergingtechnologies.database.model.Provider;
import com.sinemergingtechnologies.database.service.IProviderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sinemergingtechnologies.database.utils.ProviderUtils.validProvider;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/providers")
public class ProviderController {

    @Autowired
    private IProviderService providerService;

    private Provider sampleProvider = new Provider(
            "firstname",
            "lastname",
            "email",
            "city",
            "us_state",
            "pass",
            "confirm",
            "provider_type"
    );

    //    These paths are case-sensitive it appears
    @GetMapping("/")
    private List<Provider> getProviders(@RequestParam(value = "name", defaultValue = "World") String name) {
        List<Provider> providers = (List<Provider>) providerService.findAll();
        if (providers.size() > 0) {
            Provider firstProvider = (Provider) providers.get(0);
            System.out.println(firstProvider.toString());
            System.out.println(firstProvider.hashCode());
            System.out.println(firstProvider.equals(firstProvider));
            System.out.println(sampleProvider.equals(firstProvider));
        } else {
            System.out.println("no providers found");
        }

        return providers;
    }

    @PostMapping("/login")
    private ResponseEntity attemptLogin(@RequestBody LoginAttempt loginAttempt) {
        System.out.println("fake prpovider login attempt");
        System.out.println(loginAttempt.toString());

        List<Provider> providers = (List<Provider>) providerService.findByEmail(loginAttempt.getEmail());

        if (providers.size() < 1 || providers.size() > 1) {
            System.out.println("Error - Expected 1 provider but found " + providers.size());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sampleProvider);
        }

        Provider providerWithEmail = providers.get(0);
        System.out.println(providerWithEmail.toString());

        if (!loginAttempt.getPass().equals(providerWithEmail.getPass())) {
            System.out.println("Error - invalid password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(sampleProvider);
        }

        return ResponseEntity.ok(providerWithEmail);
    }

    @PostMapping("/")
    private ResponseEntity<Provider> newProvider(@RequestBody Provider newProvider) {
        if (!validProvider(newProvider)) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("Creating new provider");
        Provider createdProvider = providerService.save(newProvider);

        if (!validProvider(createdProvider)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(createdProvider);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Provider> getSingleProvider(@PathVariable("id") Long id) {
        System.out.println("Searching for provider with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Provider> foundSingleProvider = providerService.findById(id);

        if (!foundSingleProvider.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundSingleProvider.get());

    }

    @PutMapping("/{id}")
    private ResponseEntity<Provider> updateProvider(@RequestBody Provider providerToUpdate, @PathVariable Long id) {
        System.out.println("Updating provider with id " + id + ".");

        if(id == null || id < 1) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Provider> foundSingleProvider = providerService.findById(id);

        if (!foundSingleProvider.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Provider preUpdateProvider = foundSingleProvider.get();

        preUpdateProvider.setFirstname(providerToUpdate.getFirstname());
        preUpdateProvider.setLastname(providerToUpdate.getLastname());
        preUpdateProvider.setEmail(providerToUpdate.getEmail());
        preUpdateProvider.setCity(providerToUpdate.getCity());
        preUpdateProvider.setUs_state(providerToUpdate.getUs_state());
        preUpdateProvider.setPass(providerToUpdate.getPass());
        preUpdateProvider.setConfirm(providerToUpdate.getConfirm());

        Provider updatedProvider = providerService.save(preUpdateProvider);

        return ResponseEntity.ok(updatedProvider);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity deleteProvider(@PathVariable Long id) {
        System.out.println("Deleting provider with id " + id + ".");
        providerService.deleteById(id);

        Optional<Provider> foundSingleProvider = providerService.findById(id);

        if (foundSingleProvider.isPresent()) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }

        return ResponseEntity.ok(id);
    }

    @GetMapping("/test")
    public String testRoute(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "hello ProviderController";
    }

    @GetMapping("/testobj/{id}")
    public String testObj(@RequestParam(value = "name", defaultValue = "World") String name,
                          @PathVariable("id") String idString) {
        Long id = UUID.fromString(idString).getMostSignificantBits() & Long.MAX_VALUE;
        return id.toString();
    }
}
