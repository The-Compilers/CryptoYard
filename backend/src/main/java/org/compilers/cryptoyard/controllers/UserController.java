package org.compilers.cryptoyard.controllers;

import org.compilers.cryptoyard.dto.ApiKeyRequest;
import org.compilers.cryptoyard.model.ApiKey;
import org.compilers.cryptoyard.model.User;
import org.compilers.cryptoyard.security.AccessUserDetails;
import org.compilers.cryptoyard.services.UserService;
import org.compilers.cryptoyard.services.ApiKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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

    /**
     * Get API key for the current user
     *
     * @param username Username of the key owner, must match the currently authenticated user
     * @return 200OK with API key in the body, or error code with error message
     */
    @GetMapping("{username}/api-key")
    public ResponseEntity<String> getApiKey(@PathVariable String username) {
        try {
            User user = getKeyOwner(username);
            ApiKey key = apiKeyService.getKey(user.getId());
            Thread.sleep(2000);
            return ResponseEntity.ok(key.getApiKey());
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        } catch (NullPointerException e) {
            return ResponseEntity.ok("");
        } catch (InterruptedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get owner of the key or throw exception with 403 Unauthorized code
     *
     * @param username Username of the key owner, must match the currently authenticated user
     * @return User object
     */
    private User getKeyOwner(String username) throws HttpClientErrorException.Unauthorized {
        Optional<AccessUserDetails> authenticatedUser = userService.getAuthenticatedUser();
        if (authenticatedUser.isEmpty() || username == null || !username.equals(authenticatedUser.get().getUsername())) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED,
                    "Can change API key only for the authorized user");
        }
        Optional<User> user = userService.findByUsername(username);
        if (user.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Oops, user deleted?");
        }
        return user.get();
    }

    /**
     * Save a new API key (or update existing)
     *
     * @param username Username of the key owner, must match the current user
     * @param request  ApiKey data
     * @return empty 200 OK response or error codes (unauthorized, bad request)
     */
    @PostMapping("{username}/api-key")
    public ResponseEntity<?> setApiKey(@PathVariable String username, @RequestBody ApiKeyRequest request) {
        try {
            User user = getKeyOwner(username);
            apiKeyService.saveKey(request.apiKey(), request.apiSecret(), user);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("");
    }

    /**
     * Delete the currently stored API key for the current user
     *
     * @param username Username of the key owner, must match the current user
     * @return empty 200 OK response or error codes (unauthorized, bad request)
     */
    @DeleteMapping("{username}/api-key")
    public ResponseEntity<?> deleteApiKey(@PathVariable String username) {
        try {
            User user = getKeyOwner(username);
            apiKeyService.deleteKey(user.getId());
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("");
    }
}
