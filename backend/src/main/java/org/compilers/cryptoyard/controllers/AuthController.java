package org.compilers.cryptoyard.controllers;

import org.compilers.cryptoyard.dto.AuthenticationRequest;
import org.compilers.cryptoyard.dto.AuthenticationResponse;
import org.compilers.cryptoyard.model.User;
import org.compilers.cryptoyard.security.*;
import org.compilers.cryptoyard.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


/**
 * Controller responsible for authentication
 */
@CrossOrigin
@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class.getSimpleName());

    /**
     * HTTP POST request to /authenticate
     *
     * @param authenticationRequest The request JSON object containing username and password
     * @return OK + JWT token; Or UNAUTHORIZED
     */
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.username(),
                    authenticationRequest.password()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.username());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    /**
     * HTTP DELETE request to /users/delete - delete the user
     *
     * @param authenticationRequest The request JSON object containing username and password
     * @return OK; Or UNAUTHORIZED
     */
    @DeleteMapping("/close-account")
    public ResponseEntity<?> closeAccount(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.username(),
                    authenticationRequest.password()));
            userService.delete(authenticationRequest.username());
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok("User deleted");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request) {
        try {
            User user = userService.createNewUser(request);
            logger.info("User created, logging in...");
            String jwt = jwtUtil.generateToken(new AccessUserDetails(user));
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (Exception e) {
            String error = e instanceof DataIntegrityViolationException ? "Data integrity requirements not met!"
                    : e.getMessage();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
