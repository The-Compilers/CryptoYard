package org.compilers.cryptoyard.messages;

/**
 * An event dispatched on specific moments of the application lifecycle - when the app is started, when shutdown
 * is requested, etc.
 */
public class EAppLifecycle extends Event {
    private final AppLifecycleEventType type;

    /**
     * Create an app-lifecycle event
     *
     * @param type The type of event that happened
     */
    public EAppLifecycle(AppLifecycleEventType type) {
        this.type = type;
    }

    /**
     * Get the type of the event: STARTED, SHUTDOWN, etc
     *
     * @return The type of the event
     */
    public AppLifecycleEventType getType() {
        return type;
    }
}
