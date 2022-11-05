package org.compilers.cryptoyard.security;

/**
 * Data that the user will send in the SignUp request
 */
public record SignUpRequest(String username, String email, String password, String repeatedPassword) {}
