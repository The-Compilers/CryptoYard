package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.model.ApiKey;
import org.compilers.cryptoyard.model.User;
import org.compilers.cryptoyard.repositories.ApiKeyRepository;
import org.springframework.stereotype.Service;

/**
 * Handles Binance API keys
 */
@Service
public class ApiKeyService extends CYService {
    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    /**
     * Save API key and secret to the database
     *
     * @param apiKey    API key
     * @param apiSecret API secret
     * @param user      The owner of the key
     */
    public void saveKey(String apiKey, String apiSecret, User user) {
        ApiKey key = new ApiKey(apiKey, apiSecret, user);
        apiKeyRepository.save(key);
    }
}
