package org.compilers.cryptoyard.services;

import org.compilers.cryptoyard.exchange.api.WSApiChannel;
import org.compilers.cryptoyard.messages.*;
import org.springframework.stereotype.Service;

/**
 * The service which will receive and store current spot prices
 */
@Service
public class SpotPriceService extends CYService {

    @Override
    protected boolean onEvent(Event event) {
        if (!super.onEvent(event)) {
            logger.error("An event not handled: " + event); // TODO - handle the event here
        }
        return false;
    }

    @Override
    protected void onStart() {
        logger.info("App started, start spot price watching!");
        subscribeTo(ESpotPriceUpdate.class);
        sendMessage(new CApiSubscribe(WSApiChannel.SPOT_PRICES));
    }
}
