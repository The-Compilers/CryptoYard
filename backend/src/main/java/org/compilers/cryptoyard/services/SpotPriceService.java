package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.exchange.api.WSApiChannel;
import org.compilers.cryptoyard.messages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The service which will receive and store current spot prices
 */
@Service
public class SpotPriceService extends CYService {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    @Override
    protected void onEvent(Event event) {
        if (event instanceof EAppLifecycle) {
            EAppLifecycle appLifecycleEvent = (EAppLifecycle) event;
            if (appLifecycleEvent.getType() == AppLifecycleEventType.STARTED) {
                logger.info("App started, start spot price watching!");
                subscribeTo(ESpotPriceUpdate.class);
                sendMessage(new CApiSubscribe(WSApiChannel.SPOT_PRICES));
            }
        }
    }
}
