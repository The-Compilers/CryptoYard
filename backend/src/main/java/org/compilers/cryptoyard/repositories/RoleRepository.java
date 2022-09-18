package org.compilers.cryptoyard.repositories;

import org.compilers.cryptoyard.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
