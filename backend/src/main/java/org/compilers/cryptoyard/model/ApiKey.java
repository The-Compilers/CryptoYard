package org.compilers.cryptoyard.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Exchange API key for a user
 */
@Entity
@Data
public class ApiKey {
    @Id
    @GeneratedValue
    private Long id;
    private String apiKey;
    private String apiSecret;
    @ManyToOne
    private User user;
    public ApiKey() {}

    public ApiKey(String apiKey, String apiSecret, User user) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.user = user;
    }
}
