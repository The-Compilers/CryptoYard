package org.compilers.cryptoyard.messages;

import org.compilers.cryptoyard.exchange.api.WSApiStream;

/**
 * A command to subscribe to a specific channel for the exchange WebSocket API
 */
public class CApiSubscribe extends Command {
    private final WSApiStream stream;

    /**
     * Command asking to subscribe to a particular WebSocket stream
     * @param stream The stream to subscribe to
     */
    public CApiSubscribe(WSApiStream stream) {
        super();
        this.stream = stream;
    }

    public WSApiStream getStream() {
        return stream;
    }
}
