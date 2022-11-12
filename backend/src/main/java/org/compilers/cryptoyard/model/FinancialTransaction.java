package org.compilers.cryptoyard.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A financial transaction executed by a user in the currency exchange.
 * Note: all amounts and prices are stored as String to avoid rounding errors!
 */
@Entity
@Table(name = "transaction")
public class FinancialTransaction {
    @Id
    private long id;
    private String idInExchange;
    private TransactionType type;
    String baseCurrency;
    String quoteCurrency;
    // Time when this transaction was started (For example, a limit-buy order placed)
    long startTimeMs;
    // Time when this transaction was completed (For example, a limit-buy order was closed)
    long finishTimeMs;
    String baseCurrencyAmount;
    String quoteCurrencyAmount;
    String averagePrice;
    String fee;
    String feeCurrency;
    @ManyToOne
    User user;
    String profitLossInHC;
}
