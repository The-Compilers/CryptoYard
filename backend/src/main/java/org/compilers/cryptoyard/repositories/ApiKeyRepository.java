package org.compilers.cryptoyard.repositories;

import org.compilers.cryptoyard.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
}
