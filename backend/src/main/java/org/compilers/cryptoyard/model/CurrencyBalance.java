package org.compilers.cryptoyard.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * The amount of a specific currency in the wallet at a specific time
 */
@Entity
public class CurrencyBalance {
    @Id
    private long id;
    private long timestampMs;
    private String currency;
    private String amount;
    @ManyToOne
    private WalletSnapshot walletSnapshot;
}
