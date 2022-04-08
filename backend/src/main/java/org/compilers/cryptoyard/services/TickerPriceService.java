package org.compilers.cryptoyard.services;

import com.binance.api.client.domain.market.TickerPrice;
import org.compilers.cryptoyard.exchange.api.WSApiStream;
import org.compilers.cryptoyard.messages.*;
import org.compilers.cryptoyard.model.IndexedTickerPriceStorage;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * The service which will receive and store current ticker prices
 */
@Service
public class TickerPriceService extends CYService {
    private final IndexedTickerPriceStorage prices = new IndexedTickerPriceStorage();

    /**
     * Handle an event received from another service
     * @param event The received event
     * @return true if the event is handled, false otherwise
     */
    @Override
    protected boolean onEvent(Event event) {
        boolean handled = super.onEvent(event);
        if (!handled) {
            if (event instanceof ETickerPriceUpdate) {
                handled = onPriceUpdate(((ETickerPriceUpdate) event).getPrice());
            }
        }
        if (!handled) {
            logger.error("An event not handled: " + event); // TODO - handle the event here
        }
        return handled;
    }

    /**
     * This method is called when new ticker price updates are received
     *
     * @param price Price received in the last update
     * @return True when all events were handled
     */
    private boolean onPriceUpdate(TickerPrice price) {
        if (!this.prices.contains(price.getSymbol())) {
            logger.info("Got first ticker price for " + price.getSymbol() + " = " + price.getPrice()
                    + " [" + this.prices.size() + "]");
        }
        this.prices.put(price);
        return true;
    }

    /**
     * Initialize subscriptions. This method is called when the app is strated
     */
    @Override
    protected void onStart() {
        logger.info("App started, start ticker price watching!");
        subscribeTo(ETickerPriceUpdate.class);
        sendMessage(new CApiSubscribe(WSApiStream.TICKER_PRICES));
    }

    /**
     * Get all recent ticker prices
     * @return Recent ticker prices
     */
    public Collection<TickerPrice> getPrices() {
        return this.prices.getPriceList();
    }
}
