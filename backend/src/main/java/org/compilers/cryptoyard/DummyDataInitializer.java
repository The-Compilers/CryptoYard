package org.compilers.cryptoyard;

import org.compilers.cryptoyard.model.Role;
import org.compilers.cryptoyard.model.User;
import org.compilers.cryptoyard.repositories.RoleRepository;
import org.compilers.cryptoyard.repositories.UserRepository;
import org.compilers.cryptoyard.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * A class which inserts some dummy data into the database, when Spring Boot app has started
 */
@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    private final Logger logger = LoggerFactory.getLogger("DummyInit");

    /**
     * This method is called when the application is ready (loaded)
     *
     * @param event Event which we don't use :)
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Optional<User> existingChuckUser = userRepository.findByUsername("chuck");
        if (existingChuckUser.isEmpty()) {
            logger.info("Importing test data...");
            User chuck = new User("chuck", "microsoft@chuck.com", "$2a$12$/NoknpFFPDlzL3kBryJfsur0yeYC2JFqAs7Fd79ypMP6PN/mtSYmC");
            User dave = new User("dave", "dangerous@dave.com", "$2a$10$nwbEjYKgcomq2rjUPge2JegqI.y4zEcNqRMPdqwFnd1ytorNCQM/y");
            Role user = roleService.getRegularUserRole();
            Role admin = roleService.getAdminRole();
            chuck.addRole(user);
            chuck.addRole(admin);
            dave.addRole(user);

            userRepository.save(chuck);
            userRepository.save(dave);

            logger.info("DONE importing test data");
        } else {
            logger.info("Users already in the database, not importing anything");
        }
    }
}
