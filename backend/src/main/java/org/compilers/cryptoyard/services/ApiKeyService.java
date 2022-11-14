package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.model.ApiKey;
import org.compilers.cryptoyard.repositories.ApiKeyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * Save API key and secret to the database. If a key exists already, it is replaced.
     *
     * @param apiKey    API key
     * @param apiSecret API secret
     * @param userId    ID of the key's owning user
     */
    public void saveKey(String apiKey, String apiSecret, long userId) {
        Optional<ApiKey> existingKey = apiKeyRepository.findFirstOneByUserId(userId);
        ApiKey key = existingKey.orElse(new ApiKey());
        key.setApiKey(apiKey);
        key.setApiSecret(apiSecret);
        apiKeyRepository.save(key);
    }

    /**
     * Get the key from the database
     * @param userId ID of the owner user
     * @return ApiKey object
     * @throws NullPointerException When key not found for given user
     */
    public ApiKey getKey(Long userId) throws NullPointerException {
        Optional<ApiKey> key = apiKeyRepository.findFirstOneByUserId(userId);
        if (key.isEmpty()) {
            throw new NullPointerException("Key not found");
        }
        return key.get();
    }
}
