# Binance Transaction API

This document summarizes the available Binance APIs for fetching the necessary transactions. These
are necessary for the [PNL report](reports.md).

## Executed buy-orders and sell-orders

Spot account/Trade
API: [All Orders](https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data)

URL: `HTTP GET /api/v3/allOrders`

Support in the Binance Java library:

* API:
  [`BinanceApiService.getAllOrders`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiRestClientImpl.java#L166)
* REST
  client: [`BinanceApiRestClient.getAllOrders`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L198)

## Deposits

Only for crypto-deposits!

Wallet Endpoints
API: [Deposit History](https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data)

URL: `HTTP GET /sapi/v1/capital/deposit/hisrec`

Support in the Binance Java library:

* API:
  [`BinanceApiService.getDepositHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiRestClientImpl.java#L237)
* REST
  client: [`BinanceApiRestClient.getDepositHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L302)

## Withdrawals

Only for crypto-withdrawals!

Wallet Endpoints
API: [Withdraw History](https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data)

URL: `HTTP GET /sapi/v1/capital/withdraw/history`

Support in the Binance Java library:

* API:
  [`BinanceApiService.getWithdrawHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiRestClientImpl.java#L245)
* REST client:
    * [`BinanceApiRestClient.getWithdrawHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L346)
    * [`BinanceApiRestClient.getWithdrawHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L356) (
      with default values)

## Fiat Deposits and Withdrawals

Fiat Endpoints
API: [Get Fiat Deposit/Withdraw History](https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data)

URL: `GET /sapi/v1/fiat/orders`

Note: this endpoint must be used both for SEPA-bank-transfer deposits and EUR/USD purchases with
credit card!

Support in the Binance Java library:

* API:
  [`BinanceApiService.getFiatDepositOrWithdrawalHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiService.java#L307)
* REST client:
    * [`BinanceApiRestClient.getFiatDepositHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L443)
    * [`BinanceApiRestClient.getRecentFiatDepositHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L460)
    * [`BinanceApiRestClient.getFiatWithdrawHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L471)
    * [`BinanceApiRestClient.getRecentFiatWithdrawHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L489)

## Cryptocurrency purchase with a credit card

Note: this endpoint reports only BTC (and other crypto) purchases with a credit card (or other
fiat-payment type). For Fiat-currency purchase with a credit card (for example, when you transfer
EUR from your credit card to Binance), use the endpoint for Fiat deposit history.

Fiat Endpoints
API: [Get Fiat Payments History](https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data)

URL: `GET /sapi/v1/fiat/payments`

Support in the Binance Java library:

* API:
  [`BinanceApiService.getFiatPaymentHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiService.java#L319)
* REST client:
    * [`BinanceApiRestClient.getFiatPaymentHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L501)
    * [`BinanceApiRestClient.getRecentFiatPaymentHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L520)

## Interest on savings

Savings Endpoint
API: [Get Interest History](https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data-2)

URL: `GET /sapi/v1/lending/union/interestHistory`

Support in the Binance Java library:

* API:
  [`BinanceApiService.getSavingsInterestHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiService.java#L349)
* REST client:
    * [`BinanceApiRestClient.getSavingsInterestHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L535)
    * [`BinanceApiRestClient.getRecentSavingsInterestHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L557)

## _Dust collection_

Wallet Endpoints API: [DustLog](https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data)

URL: `GET /sapi/v1/asset/dribblet`

Support in the Binance Java library:

* API:
  [`BinanceApiService.getDustLog`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiService.java#L251)
* REST client:
    * [`BinanceApiRestClient.getDustTransferHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L282)
    * [`BinanceApiRestClient.getRecentDustTransferHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L288)

## Asset dividends

Wallet Endpoints
API: [Asset Dividend Record](https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data)

URL: `GET /sapi/v1/asset/assetDividend`

Support in the Binance Java library:

* API:
  [`BinanceApiService.getAssetDividendRecord`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiService.java#L273)
* REST client:
    * [`BinanceApiRestClient.getAssetDividendHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L387)
    * [`BinanceApiRestClient.getRecentAssetDividendHistory`](https://github.com/The-Compilers/binance-java-api/blob/master/src/main/java/com/binance/api/client/BinanceApiRestClient.java#L406)

## Auto-invest transactions

TODO - support those.

It seems that currently there is no API for
it: https://dev.binance.vision/t/is-auto-invest-supported-by-api/10122/2

The transactions are exported in the CSV file exported by the "Generate all statements" operation.

Also, [this endpoint](https://binance-docs.github.io/apidocs/spot/en/#lending-account-user_data)
includes the current summary of the auto-invest balances, but no transaction log:

## API limits

Binance has [API rate limits](https://binance-docs.github.io/apidocs/spot/en/#limits) for protection
against spam. The limitations vary based on the type of the request, with the following options:

* Limitation per IP address
* Limitation per user account (UID)

Each endpoint has a specification - it's weight and the type of used rate limit (IP or account).

The following limits apply currently (Last checked January 2023):

* Endpoints related to /api/*:
    * IP-limits:
        * 1200 weight per minute
        * 6100 weight per 5 minutes
    * Order-related requests:
        * 50 weight per 10 seconds
        * 160000 weight per day (24 hours)
* Endpoints related to /sapi/*:
    * Common IP-limit: 12000 weight per minute
    * UID limit: 180000 weight per minute

In every successful response the following headers are included letting the user know the currently
used weights:

* For `/api/*` endpoints:
    * For IP-limits: X-MBX-USED-WEIGHT-(intervalNum)(intervalLetter)
    * For order-limits per UID: X-MBX-ORDER-COUNT-(intervalNum)(intervalLetter)
* For `/sapi/*` endpoints:
    * For IP-limits: X-SAPI-USED-IP-WEIGHT-1M
    * For UID-limits: X-SAPI-USED-UID-WEIGHT-1M
