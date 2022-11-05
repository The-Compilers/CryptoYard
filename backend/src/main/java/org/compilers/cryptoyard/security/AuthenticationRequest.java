package org.compilers.cryptoyard.security;

/**
 * Data that the user will send in the login request
 */
public record AuthenticationRequest(String username, String password) {}
