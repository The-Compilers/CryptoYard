package org.compilers.cryptoyard.security;

import org.compilers.cryptoyard.model.Role;
import org.compilers.cryptoyard.model.User;
import org.compilers.cryptoyard.repositories.UserRepository;
import org.compilers.cryptoyard.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Provides AccessUserDetails needed for authentication
 */
@Service
public class AccessUserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            // Did not find the user by username? Try email! The sign-in form allows to enter either username or email!
            user = userRepository.findByEmail(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User " + username + "not found");
            }
        }
        return new AccessUserDetails(user.get());
    }

    public User createNewUser(String username, String email, String password) throws Exception {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isPresent()) {
            throw new Exception("Username already taken");
        }
        existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new Exception("Email already taken");
        }
        String hashedPassword = encoder.encode(password);
        User user = new User(username, email, hashedPassword);
        user = userRepository.save(user);
        Role userRole = roleService.getRegularUserRole();
        user.addRole(userRole);
        user = userRepository.save(user);

        return user;
    }
}
