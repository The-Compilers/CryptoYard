package org.compilers.cryptoyard.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ApiKey {
    @Id
    @GeneratedValue
    private Long id;
    private String apiKey;
    private String apiSecret;
    @ManyToOne
    private User user;

}
