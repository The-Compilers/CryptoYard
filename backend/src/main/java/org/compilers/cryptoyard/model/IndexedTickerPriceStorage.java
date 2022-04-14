package org.compilers.cryptoyard.model;

import com.binance.api.client.domain.market.TickerPrice;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Holds a list of most recent spot prices. Indexed for fast lookup.
 * Encapsulates implementation of price storage (for easier change later)
 */
public class IndexedTickerPriceStorage {
    private final ConcurrentMap<String, TickerPrice> priceStorage = new ConcurrentHashMap<>();

    /**
     * Check if the storage already has stored price for the given symbol
     *
     * @param symbol market symbol (BTCUSDT, etc)
     * @return true when price for this symbol is stored, false otherwise
     */
    public boolean contains(String symbol) {
        return priceStorage.containsKey(symbol);
    }

    /**
     * Store a ticker price
     *
     * @param price The price to store
     */
    public void put(TickerPrice price) {
        priceStorage.put(price.getSymbol(), price);
    }

    /**
     * Get the number of stored prices
     *
     * @return number of stored prices
     */
    public int size() {
        return priceStorage.size();
    }

    /**
     * Get an iterable price collection
     *
     * @return Ticker price collection
     */
    public Collection<TickerPrice> getPriceList() {
        return priceStorage.values();
    }
}
