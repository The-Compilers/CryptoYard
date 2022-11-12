package org.compilers.cryptoyard.controllers;

import org.compilers.cryptoyard.dto.ApiKeyRequest;
import org.compilers.cryptoyard.model.User;
import org.compilers.cryptoyard.security.AccessUserDetails;
import org.compilers.cryptoyard.services.UserService;
import org.compilers.cryptoyard.services.ApiKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST API controller for user updates
 */
@CrossOrigin
@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final ApiKeyService apiKeyService;

    public UserController(UserService userService, ApiKeyService apiKeyService) {
        this.userService = userService;
        this.apiKeyService = apiKeyService;
    }

    @PostMapping("{username}/api-key")
    public ResponseEntity<?> setApiKey(@PathVariable String username, @RequestBody ApiKeyRequest request) {
        Optional<AccessUserDetails> authenticatedUser = userService.getAuthenticatedUser();
        if (authenticatedUser.isEmpty() || username == null || !username.equals(authenticatedUser.get().getUsername())) {
            return new ResponseEntity<>("Can change API key only for the authorized user", HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = userService.findByUsername(username);
        if (user.isEmpty()) {
            return new ResponseEntity<>("Oops, user deleted?", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            apiKeyService.saveKey(request.apiKey(), request.apiSecret(), user.get());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok("");
    }
}
