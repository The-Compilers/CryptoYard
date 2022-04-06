package org.compilers.cryptoyard.messages;

import org.compilers.cryptoyard.exchange.api.WSApiChannel;

/**
 * A command to subscribe to a specific channel for the exchange WebSocket API
 */
public class CApiSubscribe extends Command {
    private WSApiChannel type;

    public CApiSubscribe(WSApiChannel type) {
        this.type = type;
    }
}
