package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.model.Role;
import org.compilers.cryptoyard.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling Roles
 */
@Service
public class RoleService extends CYService {
    @Autowired
    RoleRepository repository;

    /**
     * Get a role from the database representing regular users
     *
     * @return User-role from the database
     */
    public Role getRegularUserRole() {
        return findOrCreateRole(Role.REGULAR_USER);
    }

    /**
     * Get a role from the database representing admin users
     *
     * @return Admin-role from the database
     */
    public Role getAdminRole() {
        return findOrCreateRole(Role.ADMIN);
    }


    /**
     * Finds a role in the database. If none found, create one, store it in the DB
     *
     * @param roleName Name of the role
     * @return Role from the database
     */
    private Role findOrCreateRole(String roleName) {
        Optional<Role> existingRole = repository.findByName(roleName);
        if (existingRole.isPresent()) {
            return existingRole.get();
        }
        Role role = new Role(roleName);
        return repository.save(role);
    }
}
