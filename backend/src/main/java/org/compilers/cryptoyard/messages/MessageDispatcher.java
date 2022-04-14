package org.compilers.cryptoyard.messages;

import org.compilers.cryptoyard.services.CYService;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Responsible for managing subscriptions and forwarding messages to necessary services.
 */
@Component
public class MessageDispatcher {
    private final Subscriptions subscriptions = new Subscriptions();
    /**
     * Register listener for a specific topic. All messages of the current topic will be forwarded to the listener
     * @param topic Topic to subscribe to - a message class
     * @param subscriber The service to which all the messages on the given topic will be forwarded
     */
    public <C extends Message> void subscribe(Class<C> topic, CYService subscriber) {
        subscriptions.add(topic, subscriber);
    }

    /**
     * Unregister listener for a specific topic. All messages of the current topic will be forwarded to the listener
     * @param topic Topic to subscribe to - a message class
     * @param subscriber The listener service which will no longer get all messages forwarded
     * @param <C> A subclass for Message
     */
    public <C extends Message> void unsubscribe(Class<C> topic, CYService subscriber) {
        subscriptions.remove(topic, subscriber);
    }

    /**
     * Send a message to all listeners
     * @param message The message to send
     */
    public void send(Message message) {
        Set<CYService> subscribers = subscriptions.getSubscribersFor(message);
        for (CYService subscriber: subscribers) {
            subscriber.onMessage(message);
        }
    }
}
