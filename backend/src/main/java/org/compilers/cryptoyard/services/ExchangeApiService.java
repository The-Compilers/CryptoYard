package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.messages.CApiSubscribe;
import org.compilers.cryptoyard.messages.Command;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * A service which communicates with exchange, using ApiClient class(es)
 */
@Service
public class ExchangeApiService extends CYService {
    @PostConstruct
    protected void subscribeToCommands() {
        logger.info("subscribeToCommands");
        subscribeTo(CApiSubscribe.class);
    }

    @Override
    protected boolean onCommand(Command command) {
        boolean handled = super.onCommand(command);
        if (!handled) {
            // TODO
            logger.info("onCommand " + command);
        }
        return handled;
    }
}
