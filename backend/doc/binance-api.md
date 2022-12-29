# Binance Transaction API

This document summarizes the available Binance APIs for fetching the necessary transactions. These
are necessary for the [PNL report](reports.md).

## Executed buy-orders and sell-orders

Spot account/Trade
API: [All Orders](https://binance-docs.github.io/apidocs/spot/en/#all-orders-user_data)

URL: `HTTP GET /api/v3/allOrders`

Support in the Binance Java
library: [`BinanceApiService.getAllOrders`](https://github.com/binance-exchange/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiRestClientImpl.java#L119)

## Deposits

Wallet Endpoints
API: [Deposit History](https://binance-docs.github.io/apidocs/spot/en/#deposit-history-supporting-network-user_data)

URL: `HTTP GET /sapi/v1/capital/deposit/hisrec`

Support in the Binance Java
library: [`BinanceApiService.getDepositHistory`](https://github.com/binance-exchange/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiRestClientImpl.java#L162)

## Withdrawals

Wallet Endpoints
API: [Withdraw History](https://binance-docs.github.io/apidocs/spot/en/#withdraw-history-supporting-network-user_data)

URL: `HTTP GET /sapi/v1/capital/withdraw/history`

Warning: Binance Java library uses OLD endpoint: `/wapi/v3/withdrawHistory.html`

The deprecated API implementation (must be
tested): [`BinanceApiService.getWithdrawHistory`](https://github.com/binance-exchange/binance-java-api/blob/master/src/main/java/com/binance/api/client/impl/BinanceApiService.java#L166)

See [this pull request](https://github.com/binance-exchange/binance-java-api/pull/397) for patches.

## Fiat Deposits and Withdrawals

Fiat Endpoints
API: [Get Fiat Deposit/Withdraw History](https://binance-docs.github.io/apidocs/spot/en/#get-fiat-deposit-withdraw-history-user_data)

URL: `GET /sapi/v1/fiat/orders`

Note: this must be tested! Unclear, which transactions show up here.

Not supported in Binance Java library !!!

## Currency purchase with a credit card

Fiat Endpoints
API: [Get Fiat Payments History](https://binance-docs.github.io/apidocs/spot/en/#get-fiat-payments-history-user_data)

URL: `GET /sapi/v1/fiat/payments`

Note: this must be tested! Unclear, which transactions show up here.

Not supported in Binance Java library !!!

## Interest on savings

Savings Endpoint
API: [Get Interest History](https://binance-docs.github.io/apidocs/spot/en/#get-interest-history-user_data-2)

URL: `GET /sapi/v1/lending/union/interestHistory`

Not supported in Binance Java library !!!

Also: check out this: Wallet
Endpoints - [Asset Dividend Record](https://binance-docs.github.io/apidocs/spot/en/#asset-dividend-record-user_data)

## _Dust collection_

Wallet Endpoints API: [DustLog](https://binance-docs.github.io/apidocs/spot/en/#dustlog-user_data)

URL: `GET /sapi/v1/asset/dribblet`

Not supported in Binance Java library !!!
