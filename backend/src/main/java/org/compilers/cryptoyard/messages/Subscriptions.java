package org.compilers.cryptoyard.messages;

import org.compilers.cryptoyard.services.CYService;

/**
 * Maintains subscriptions for each topic (message type)
 */
public class Subscriptions {
    /**
     * Add a subscription
     * @param topic The topic to subscribe to
     * @param subscriber The subscriber for the topic
     * @param <C> A class extending the general Message class
     */
    public <C extends Message> void add(Class<C> topic, CYService subscriber) {
        // TODO
    }

    /**
     * Remove a subscription
     * @param topic The topic to subscribe to
     * @param subscriber The subscriber for the topic
     * @param <C> A class extending the general Message class
     */
    public <C extends Message> void remove(Class<C> topic, CYService subscriber) {
        // TODO
    }
}
