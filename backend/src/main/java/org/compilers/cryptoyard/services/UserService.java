package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.model.User;
import org.compilers.cryptoyard.repositories.UserRepository;
import org.compilers.cryptoyard.security.AccessUserDetails;
import org.compilers.cryptoyard.security.SignUpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Provides details and operations related to users. Also needed for Spring Security
 */
@Service
public class UserService extends CYService implements UserDetailsService {
    private final RoleService roleService;
    private final UserRepository userRepository;

    // Username of the default admin user. When someone signs up with this username, they get admin rights
    private final static String DEFAULT_ADMIN_USERNAME = "admin";

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(RoleService roleService, UserRepository userRepository) {
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    /**
     * This function is needed for Spring Security
     *
     * @param usernameOrEmail the username (or email!) identifying the user whose data is required.
     * @return The UserDetails object, needed by Spring Security
     * @throws UsernameNotFoundException When the user with given username (or email) is not found
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<User> user = findByUsernameOrEmail(usernameOrEmail);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User " + usernameOrEmail + "not found");
        }
        return new AccessUserDetails(user.get());
    }

    /**
     * Find a user by username, or email (in the database)
     *
     * @return The user or Optional.empty() if it is not found in the database
     */
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        Optional<User> user = userRepository.findByUsername(usernameOrEmail);
        if (user.isEmpty()) {
            // Did not find the user by username? Try email! The sign-in form allows to enter either username or email!
            user = userRepository.findByEmail(usernameOrEmail);
        }
        return user;
    }

    /**
     * Find a user by username (in the database)
     *
     * @return The user or Optional.empty() if it is not found in the database
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get the currently authenticated user, Optional.empty() if no authenticated user in this session
     */
    public Optional<AccessUserDetails> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<AccessUserDetails> userDetails;
        if (authentication != null) {
            userDetails = Optional.of((AccessUserDetails) authentication.getPrincipal());
        } else {
            userDetails = Optional.empty();
        }
        return userDetails;
    }

    public User createNewUser(SignUpRequest request) throws Exception {
        String username = request.username();
        String email = request.email();
        String password = request.password();
        if (username == null || "".equals(username)) {
            throw new Exception("Username can't be empty");
        }
        if (email == null || "".equals(email)) {
            throw new Exception("Email can't be empty");
        }
        if (password == null || "".equals(password)) {
            throw new Exception("Password can't be empty");
        }
        if (request.repeatedPassword() == null || !request.repeatedPassword().equals(request.password())) {
            throw new Exception("Passwords must match");
        }
        checkPasswordRequirements(password);
        checkEmailFormat(email);
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
        user.addRole(roleService.getRegularUserRole());
        if (shouldGetAdminRights(username)) {
            user.addRole(roleService.getAdminRole());
        }
        return userRepository.save(user);
    }

    /**
     * Check if user with the provided username should get admin rights
     *
     * @param username The username to check
     * @return True if the provided username signals that this is an admin user
     */
    private boolean shouldGetAdminRights(String username) {
        return DEFAULT_ADMIN_USERNAME.equals(username);
    }

    // Email-matching regex, from https://www.baeldung.com/java-email-validation-regex
    final static Pattern EMAIL_REGEX = Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            Pattern.CASE_INSENSITIVE);

    /**
     * Checks if the provided email is in correct format
     *
     * @param email Email to check
     * @throws Exception Throws exception on error, does nothing on success.
     */
    private void checkEmailFormat(String email) throws Exception {
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new Exception("Email in incorrect format");
        }
    }

    // 6-20 chars, lowercase, uppercase, digits
    final static Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$");

    /**
     * Checks if the provided password meets the minimum requirements. Throws and exception on error
     *
     * @param password The password to check
     * @throws Exception Throws exception on error, does nothing on success.
     */
    private void checkPasswordRequirements(String password) throws Exception {
        if (!PASSWORD_REGEX.matcher(password).matches()) {
            throw new Exception("Password must be 6-20 characters long and include lowercase, uppercase and digits");
        }
    }

    /**
     * Deletes a user
     *
     * @param username The username of the user to delete
     * @throws IllegalArgumentException If user not found
     */
    public void delete(String username) throws IllegalArgumentException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }
}
