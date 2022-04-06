package org.compilers.cryptoyard;

import org.compilers.cryptoyard.messages.AppLifecycleEventType;
import org.compilers.cryptoyard.messages.EAppLifecycle;
import org.compilers.cryptoyard.messages.MessageDispatcher;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Autowired
    private MessageDispatcher dispatcher;

    /**
     * This method is executed when the app is started - after all the services are initialized
     * Notify all services that the app is ready
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Application started");
        broadcastAppStartEvent();
    }

    /**
     * Send a "hi, the app is started!" event to all services
     */
    private void broadcastAppStartEvent() {
        dispatcher.send(new EAppLifecycle(AppLifecycleEventType.STARTED));
    }
}
