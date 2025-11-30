package org.ac.cst8277.Khan.Yasar.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenStore tokenStore;

    // Requirement: Authenticate and return a UUID Token
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody User loginRequest) {
        return userRepository.findByUsername(loginRequest.getUsername())
            .map(user -> {
                String token = UUID.randomUUID().toString();
                tokenStore.activeTokens.put(token, user);
                return ResponseEntity.ok(token);
            })
            .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid User"));
    }

    // Internal API: Called by MessagingService to check if a token is valid
    @GetMapping("/validate")
    public Mono<ResponseEntity<User>> validateToken(@RequestParam String token) {
        User user = tokenStore.activeTokens.get(token);
        if (user != null) {
            return Mono.just(ResponseEntity.ok(user));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // Requirement I: Get All Users
    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Requirement III: Add User
    @PostMapping
    public Mono<User> addUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }
}