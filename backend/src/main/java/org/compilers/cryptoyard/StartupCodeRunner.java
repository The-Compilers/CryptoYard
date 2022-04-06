package org.compilers.cryptoyard;

import org.compilers.cryptoyard.messages.EAppLifecycle;
import org.compilers.cryptoyard.messages.MessageDispatcher;
import org.compilers.cryptoyard.services.SpotPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Executes necessary code on startup - when the application is loaded
 */
@Component
public class StartupCodeRunner implements ApplicationListener<ApplicationReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MessageDispatcher dispatcher;
    @Autowired
    private SpotPriceService spotPriceService;

    /**
     * This method is executed when the app is started
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Application started");
        initializeAppLifecycleSubscriptions();
    }

    /**
     * All services subscribe to app lifecycle events by default
     */
    private void initializeAppLifecycleSubscriptions() {
        dispatcher.subscribe(EAppLifecycle.class, spotPriceService);

    }
}
