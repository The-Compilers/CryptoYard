package org.compilers.cryptoyard.messages;

import org.compilers.cryptoyard.services.CYService;

import java.util.*;

/**
 * Maintains subscriptions for each topic (message type)
 */
public class Subscriptions {
    private final Map<Object, Set<CYService>> subscriptions = new HashMap<>();

    /**
     * Add a subscription
     *
     * @param topic      The topic to subscribe to
     * @param subscriber The subscriber for the topic
     * @param <C>        A class extending the general Message class
     */
    public <C extends Message> void add(Class<C> topic, CYService subscriber) {
        Set<CYService> subscribers = subscriptions.get(topic);
        if (subscribers == null) {
            subscribers = new HashSet<>();
            subscriptions.put(topic, subscribers);
        }
        subscribers.add(subscriber);
    }

    /**
     * Remove a subscription
     *
     * @param topic      The topic to subscribe to
     * @param subscriber The subscriber for the topic
     * @param <C>        A class extending the general Message class
     */
    public <C extends Message> void remove(Class<C> topic, CYService subscriber) {
        Set<CYService> subscribers = subscriptions.get(topic);
        if (subscribers != null) {
            subscribers.remove(subscriber);
        }
    }

    /**
     * Get all subscribers for a particular message type
     *
     * @param topic The topic to check subscribers for
     * @return Set of subscribers. Empty set if no subscribers exist
     */
    public Set<CYService> getSubscribersFor(Message topic) {
        Set<CYService> subscribers = null;
        if (topic != null) {
            subscribers = subscriptions.get(topic.getClass());
        }
        if (subscribers == null) {
            subscribers = new HashSet<>();
        }

        return subscribers;
    }
}
