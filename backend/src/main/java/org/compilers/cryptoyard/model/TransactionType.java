package org.compilers.cryptoyard.model;

/**
 * All the different types of transactions the user can perform in the Exchange
 */
public enum TransactionType {
    Buy("buy"),
    Sell("sell"),
    Deposit("deposit"),
    Withdrawal("withdraw"),
    CardPurchase("card_purchase"),
    SavingsInterest("interest"),
    DustCollection("dust"),
    FiatExchange("fiat_exchange"),
    AutoInvest("auto_invest"),
    AssetDividend("dividend");
    final String value;
    TransactionType(String value) {
        this.value = value;
    }
}
