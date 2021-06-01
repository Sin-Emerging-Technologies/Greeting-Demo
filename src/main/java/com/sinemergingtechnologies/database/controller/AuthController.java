package com.sinemergingtechnologies.database.controller;

import com.sinemergingtechnologies.database.Enums;
import com.sinemergingtechnologies.database.model.Role;
import com.sinemergingtechnologies.database.model.User;
import com.sinemergingtechnologies.database.repository.RoleRepository;
import com.sinemergingtechnologies.database.repository.UserRepository;
import com.sinemergingtechnologies.database.security.jwt.JwtUtils;
import com.sinemergingtechnologies.database.security.payload.request.LoginRequest;
import com.sinemergingtechnologies.database.security.payload.response.JwtResponse;
import com.sinemergingtechnologies.database.security.payload.response.MessageResponse;
import com.sinemergingtechnologies.database.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/")
    public String defaultGet() {
        return "default GET  succeeded";
    }

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        ResponseEntity res = ResponseEntity.ok(new JwtResponse().toBuilder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
            .build());

        return res;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User userCreateReq) {
        if (userRepository.findByUsername(userCreateReq.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.findByEmail(userCreateReq.getEmail()).size() > 0) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User().toBuilder()
                .id(userCreateReq.getId())
                .uuid(userCreateReq.getUuid())
                .firstname(userCreateReq.getFirstname())
                .lastname(userCreateReq.getLastname())
                .city(userCreateReq.getCity())
                .us_state(userCreateReq.getUs_state())
                .username(userCreateReq.getUsername())
                .email(userCreateReq.getEmail())
                .password(encoder.encode(userCreateReq.getPassword()))
                .roles(userCreateReq.getRoles())
                .build();
        Set<Role> requestRoles = userCreateReq.getRoles();
        Set<Role> roles = new HashSet<>();

        // Give user default Role of Client if no roles received
        if (requestRoles == null) {
            Optional<Role> userRoleOpt = roleRepository.findByName(Enums.RoleTitles.CLIENT.name());
//            if (userRole.isPresent())
            Role userRole = userRoleOpt.get();
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            requestRoles.forEach(role -> {
                switch (role.getName()) {
                    case "PROVIDER":
                        Role providerRole = roleRepository.findByName(Enums.RoleTitles.PROVIDER.name())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(providerRole);

                        break;
                    default:
                        Role clientRole = roleRepository.findByName(Enums.RoleTitles.CLIENT.name())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(clientRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
