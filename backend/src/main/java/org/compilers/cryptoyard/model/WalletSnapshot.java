package org.compilers.cryptoyard.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

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
