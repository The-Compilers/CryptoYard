package org.compilers.cryptoyard.messages;

import com.binance.api.client.domain.market.TickerPrice;

/**
 * An event signalling ticker-price update
 */
public class ETickerPriceUpdate extends Event {
    TickerPrice price;

    /**
     * Create a ticker-price-update event
     *
     * @param price A ticker price received from the exchange
     */
    public ETickerPriceUpdate(TickerPrice price) {
        this.price = price;
    }

    /**
     * Get the price included in this update
     *
     * @return Ticker price
     */
    public TickerPrice getPrice() {
        return price;
    }
}
