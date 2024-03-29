package org.compilers.cryptoyard.dto;

/**
 * Data that the user will send in the login request
 */
public record AuthenticationRequest(String username, String password) {}
