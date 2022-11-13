package org.compilers.cryptoyard.repositories;

import org.compilers.cryptoyard.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Optional<ApiKey> findFirstOneByUserId(long userId);
}
