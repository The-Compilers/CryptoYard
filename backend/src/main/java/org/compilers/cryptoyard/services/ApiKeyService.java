package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.model.ApiKey;
import org.compilers.cryptoyard.model.User;
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
     * @param user      The owner of the key
     * @throws IllegalArgumentException When some passed arguments are incorrect
     */
    public void saveKey(String apiKey, String apiSecret, User user) throws IllegalArgumentException {
        if (apiKey == null || "".equals(apiKey)) throw new IllegalArgumentException("API key can't be null");
        if (apiSecret == null || "".equals(apiSecret)) throw new IllegalArgumentException("API secret can't be null");
        if (user == null) throw new IllegalArgumentException("API key owner can't be null");
        Optional<ApiKey> existingKey = apiKeyRepository.findFirstOneByUserId(user.getId());
        ApiKey key = existingKey.orElse(new ApiKey());
        key.setApiKey(apiKey);
        key.setApiSecret(apiSecret);
        key.setUser(user);
        apiKeyRepository.save(key);
    }

    /**
     * Get the key from the database
     *
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

    /**
     * Delete the key for a given user from the database
     *
     * @param userId ID of the owner of the key
     * @throws NullPointerException When key not found for given user
     */
    public void deleteKey(Long userId) {
        Optional<ApiKey> key = apiKeyRepository.findFirstOneByUserId(userId);
        if (key.isEmpty()) {
            throw new NullPointerException("Key not found");
        }
        apiKeyRepository.delete(key.get());
    }
}
