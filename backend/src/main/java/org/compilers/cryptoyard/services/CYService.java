package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.messages.MessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base class for all services (which can exchange messages)
 */
public abstract class CYService {
    @Autowired
    private MessageDispatcher dispatcher;
}
