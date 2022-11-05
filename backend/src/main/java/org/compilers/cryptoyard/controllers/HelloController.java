package org.compilers.cryptoyard.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dummy Hello-world controller, as a proof of concept (for testing)
 */
@CrossOrigin
@RestController
public class HelloController {

    @GetMapping("/public/hello")
    public String greeting() {
        return "Hello, CryptoYard!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/hello")
    public String admin() {
        return "Hello, admin!";
    }
}
