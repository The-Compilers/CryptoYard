package org.compilers.cryptoyard.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String greeting() {
        return "Hello, Spring!";
    }
}
