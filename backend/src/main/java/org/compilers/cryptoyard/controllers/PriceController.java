package org.compilers.cryptoyard.controllers;

import com.binance.api.client.domain.market.TickerPrice;
import org.compilers.cryptoyard.services.TickerPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * REST API controller exposing public prices
 */
@RestController
@RequestMapping("prices")
public class PriceController {
    @Autowired
    private TickerPriceService tickerPriceService;

    /**
     * Get all stored ticker prices
     *
     * @return Ticker prices
     */
    @GetMapping("ticker")
    public Collection<TickerPrice> getSpotPrices() {
        return tickerPriceService.getPrices();
    }
}
