# Report generation logic

This document describes the rules used for generating reports for (Norwegian) tax authorities.

## Requirements

The tax authorities requests the following information:

1. Profit/loss (PNL) report of each transaction
2. The balance of assets (amount of each currency) in the wallet at the end of each year (December 31st 23:59:59).

While individual transactions may have PNL reported in USD currency (base fiat currency in all exchanges), the final
profit must be converted in the user's home-currency (such as NOK).

## Required transaction information

To calculate the necessary reports, the following transactions must be collected from the cryptocurrency exchange
(Binance or other):

1. Executed buy-orders
2. Executed sell-orders
3. Cryptocurrency deposits
4. Cryptocurrency withdrawals
5. Fiat currency deposits
6. Fiat currency withdrawals
7. Fiat currency purchase with a credit card
8. Cryptocurrency purchase with a credit card
9. Interest on savings
10. _Dust collection_
11. Fiat currency exchange (such as EUR/NOK)

## Stored information

To calculate the necessary reports, the following information must be stored on the backend:

* List of all transactions
* Wallet snapshot after each transaction

Note: the storage may be temporary, during the lifecycle of the report generation. To avoid privacy issues, the user
could request deletion of all the stored data from the server when the report is ready.

## Data structures

The necessary information can be stored in the following data structures:

```
// Stores information about a transaction: buy order, sell order, etc
Transaction:
    id: long
    idInExchange: String
    type: TransactionTypeEnum
    baseCurrency: String
    quoteCurrency: String
    timestamp: long
    baseCurrencyAmount: Decimal
    quoteCurrencyAmount: Decimal
    averagePrice: Decimal
    fee: Decimal
    feeCurrency: String
    user: User
    profitLossInUsd: Decimal

// The different transaction types
TransactionTypeEnum: (BuyOrder, SellOrder, CoinDeposit, CoinWithdrawal, 
    FiatDeposit, FiathWithdrawal, FiatCardPurchase, CoinCardPurchase,
    SavingsInterest, DustCollection, FiatExchange)

// Snapshot of the whole wallet after a specific transaction. Contains a list of CurrencyBalanceSnapshot objects
WalletSnapshot:
    id: long
    timestamp: long
    transaction: Transaction
    currencySnapshots: List<CurrencyBalanceSnapshot>
    
// Snapshot of one particular currency at a specific time moment
CurrencyBalanceSnapshot:
    id: long
    timestamp: long
    currency: String
    amount: Decimal
    averageObtainPrice: Decimal
```

Note: all money-related amounts are represented as Decimal here, to avoid rounding problems with double. The specific
data type to be used is selected during implementation.

## Calculation algorithm

The following general rules apply:

* Transactions are collected from the exchange API
* Some transactions may need user's manual input:
    * Fiat currency exchange - the used exchange rate
    * Crypto deposit - the obtaining price of the deposited cryptocurrency, in USD
    * Cryptocurrency withdrawal - the realised sell-price of the cryptocurrency, in USD
* All the available information is collected from the exchange first, incomplete transactions are stored temporarily.
  The user is then asked to fill in the necessary information manually. Afterwards, the transactions are completed with
  the new information (Profit/Loss) and wallet snapshots are generated.
* After each transaction, the following is re-calculated:
    * Amount and average obtaining price for each asset involved in the transaction
    * Profit/Loss in USD for this specific transaction (if any)
    * New wallet snapshot - all the assets in the wallet
    * Current "running" Profit/loss

Profit/Loss is converted from USD to user's home currency (for example, NOK) on:

* December 31st of every year
* Withdrawal of a fiat currency (USD or EUR)

In buy-transactions, one currency is purchased (called _base currency_) while another currency is sold (called _quote
currency_). In sell transactions the base currency is sold and quote currency is purchased.

### Rules for calculation for each transaction type

The following calculations are performed for each transaction, based on the transaction type.

#### Buy order

An executed buy order means that:

* A cryptocurrency has been purchased (base currency)
* Either fiat currency or cryptocurrency has been sold (quote currency)
* A fee has been paid (in either cryptocurrency or fiat currency)

Note: For EUR/USD and other fiat/USD markets the calculation would be the same as for crypto/USD market.

If transaction.feeCurrency is USD:

* feeInUsd = transaction.fee
* wallet.usd.amount -= feeInUsd

If transaction.feeCurrency is not USD:

* feeInUsd = transaction.fee * (wallet.(transaction.feeCurrency).averageObtainPrice)
* wallet.(transaction.feeCurrency).amount -= transaction.fee

Wallet changes for crypto/USD buy order:

* usdSpentInTransaction = transaction.baseAmount * transaction.price
* wallet.usd.amount -= usdSpentInTransaction
* usdSpentInTransaction += feeInUsd

Wallet changes for crypto/crypto buy order:

* quoteCoinSpentInTransaction = transaction.baseAmount * transaction.price
* wallet.quoteCoin.amount -= quoteCoinSpentInTransaction
* usdSpentInTransaction = quoteCoinSpentInTransaction * wallet.quoteCoin.averageObtainPrice + feeInUsd

Common wallet changes for both crypto/USD and crypto/crypto buy order:

* wallet.baseCurrency.amount += transaction.baseAmount
* totalUsdSpentForCoin = usdSpentInTransaction + (wallet.baseCurrency.averageObtainPrice * wallet.baseCurrency.amount)
* wallet.baseCurrency.averageObtainPrice = totalUsdSpentForCoin / wallet.baseCurrency.amount
* PNL is unchanged

#### Sell order

TBD

#### Cryptocurrency deposit

TBD

#### Cryptocurrency withdrawal

TBD

#### Fiat currency deposit

TBD

#### Fiat currency withdrawal

TBD

#### Credit-card purchase

TBD - Crypto
TBD - fiat

#### Savings interest

TBD

#### Dust collection

TBD

#### Fiat currency exchange

TBD
