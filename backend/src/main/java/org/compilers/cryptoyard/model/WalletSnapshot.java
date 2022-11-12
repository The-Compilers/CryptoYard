package org.compilers.cryptoyard.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * Snapshot of the whole wallet for a specific user at the specific time moment.
 * Contains balances of all the currencies in the wallet at that moment.
 */
@Entity
public class WalletSnapshot {
    @Id
    private long id;
    private long timestampMs;
    @OneToOne
    private FinancialTransaction transaction;
    @OneToMany(mappedBy = "walletSnapshot")
    private List<CurrencyBalance> currencyBalances;
}
