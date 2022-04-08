package org.compilers.cryptoyard.services;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.TickerEvent;
import com.binance.api.client.domain.market.TickerPrice;
import org.compilers.cryptoyard.exchange.api.WSApiStream;
import org.compilers.cryptoyard.messages.CApiSubscribe;
import org.compilers.cryptoyard.messages.Command;
import org.compilers.cryptoyard.messages.ETickerPriceUpdate;
import org.compilers.cryptoyard.messages.MessageDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A service which communicates with exchange, using ApiClient class(es)
 */
@Service
public class ExchangeApiService extends CYService {
    @Value("${cy.watched.coins}")
    String[] watchedCoins;

    @Autowired
    MessageDispatcher dispatcher;

    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance();

    @PostConstruct
    protected void subscribeToCommands() {
        logger.info("subscribeToCommands");
        subscribeTo(CApiSubscribe.class);
    }

    /**
     * This method is called when another service sends a command to this service
     *
     * @param command The received command
     * @return True when command processed, false otherwise
     */
    @Override
    protected boolean onCommand(Command command) {
        boolean handled = super.onCommand(command);
        if (!handled) {
            if (command instanceof CApiSubscribe) {
                handled = subscribeToStream(((CApiSubscribe) command).getStream());
            }
        }
        return handled;
    }

    /**
     * Subscribe to a WebSocket stream
     *
     * @param stream The stream to subscribe to
     * @return
     */
    private boolean subscribeToStream(WSApiStream stream) {
        logger.info("Subscribe to " + stream);
        switch (stream) {
            case TICKER_PRICES:
                BinanceApiWebSocketClient client = factory.newWebSocketClient();
                String symbols = getWatchedCoinSymbols();
                logger.info("Subscribing to ticker price updates for " + symbols);
                client.onTickerEvent(symbols, this::onTickerPriceUpdate);
                break;
        }
        return true;
    }

    /**
     * Get a comma-separated market symbols for coins in our "watchlist"
     *
     * @return Comma-separated symbols
     */
    private String getWatchedCoinSymbols() {
        return Arrays.stream(this.watchedCoins).map(s -> s.toLowerCase() + "usdt")
                .collect(Collectors.joining(","));
    }

    /**
     * This method is called when new ticker price is received from the exchange.
     *
     * @param event Ticker price event from the exchange
     */
    private void onTickerPriceUpdate(TickerEvent event) {
        dispatcher.send(new ETickerPriceUpdate(convertEventToTickerPrice(event)));
    }

    /**
     * Convert exchange event (with lots of unnecessary info) to simple TickerPrice
     *
     * @param event Event received from the exchange
     * @return Ticker price
     */
    private TickerPrice convertEventToTickerPrice(TickerEvent event) {
        TickerPrice price = new TickerPrice();
        price.setPrice(event.getBestAskPrice());
        price.setSymbol(event.getSymbol());
        return price;
    }
}
