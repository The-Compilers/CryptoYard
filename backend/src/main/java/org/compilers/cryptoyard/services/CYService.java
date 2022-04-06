package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.messages.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Base class for all services (which can exchange messages)
 */
public abstract class CYService {
    @Autowired
    private MessageDispatcher dispatcher;

    /**
     * Subscribe to application-lifecycle events.
     * Note: this method is called right after this component (and all it's dependencies) is initialized by Spring!
     */
    @PostConstruct
    public void subscribeToAppLifecycle() {
        dispatcher.subscribe(EAppLifecycle.class, this);
    }

    /**
     * This method is called when a message is received from another service
     *
     * @param message The message from another service
     */
    public void onMessage(Message message) {
        if (message instanceof Event) {
            onEvent((Event) message);
        } else if (message instanceof Command) {
            onCommand((Command) message);
        }
    }

    /**
     * A command is received from another service.
     * Override this in child classes!
     *
     * @param command The received command
     */
    protected void onCommand(Command command) {
    }

    /**
     * An event is received from another service.
     * Override this in child classes!
     *
     * @param event The received event
     */
    protected void onEvent(Event event) {
    }

    /**
     * Subscribe to receive messages on a specific topic
     * @param topic The topic (message class) to subscribe to
     */
    protected <C extends Message> void subscribeTo(Class<C> topic) {
        dispatcher.subscribe(topic, this);
    }

    /**
     * Broadcast a message to other services (which have subscribed to the topic of this message)
     * @param message The message to send
     */
    protected void sendMessage(Message message) {
        dispatcher.send(message);
    }
}
