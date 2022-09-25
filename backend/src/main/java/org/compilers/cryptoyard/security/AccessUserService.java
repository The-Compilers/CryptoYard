package org.compilers.cryptoyard.security;

import org.compilers.cryptoyard.model.Role;
import org.compilers.cryptoyard.model.User;
import org.compilers.cryptoyard.repositories.UserRepository;
import org.compilers.cryptoyard.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

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
        Role userRole = roleService.getRegularUserRole();
        user.addRole(userRole);
        return userRepository.save(user);
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
}
