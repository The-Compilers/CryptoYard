package org.compilers.cryptoyard.dto;

/**
 * Data that the user will send in the "Set API key" request
 */
public record ApiKeyRequest(String apiKey, String apiSecret) {}
