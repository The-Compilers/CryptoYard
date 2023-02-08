package org.compilers;

import java.io.IOException;

/**
 * Possible account-change operations.
 */
public enum Operation {
  BUY, SELL, FEE, DEPOSIT, WITHDRAW, TRANSACTION_RELATED, DISTRIBUTION, SAVINGS_DISTRIBUTION,
  BNB_VAULT_REWARDS, BUY_CRYPTO, CASHBACK_VOUCHER, COMMISSION_REBATE, FIAT_DEPOSIT,
  SIMPLE_EARN_FLEXIBLE_SUBSCRIPTION, SIMPLE_EARN_FLEXIBLE_REDEMPTION, SIMPLE_EARN_FLEXIBLE_INTEREST,
  SMALL_ASSETS_EXCHANGE_BNB, AUTO_INVEST_TRANSACTION;

  /**
   * Convert a capitalized string to a corresponding enum.
   *
   * @param s A capitalized string, as used in the CSV file
   * @return Corresponding Enum value
   * @throws IOException When an unexpected s value is received
   */
  public static Operation fromString(String s) throws IOException {
    return switch (s) {
      case "Buy" -> BUY;
      case "Sell" -> SELL;
      case "Fee" -> FEE;
      case "Deposit" -> DEPOSIT;
      case "Withdraw" -> WITHDRAW;
      case "Transaction Related" -> TRANSACTION_RELATED;
      case "Distribution" -> DISTRIBUTION;
      case "Savings Distribution" -> SAVINGS_DISTRIBUTION;
      case "BNB Vault Rewards" -> BNB_VAULT_REWARDS;
      case "Buy Crypto" -> BUY_CRYPTO;
      case "Cashback Voucher" -> CASHBACK_VOUCHER;
      case "Commission Rebate" -> COMMISSION_REBATE;
      case "Fiat Deposit" -> FIAT_DEPOSIT;
      case "Simple Earn Flexible Subscription" -> SIMPLE_EARN_FLEXIBLE_SUBSCRIPTION;
      case "Simple Earn Flexible Redemption" -> SIMPLE_EARN_FLEXIBLE_REDEMPTION;
      case "Simple Earn Flexible Interest" -> SIMPLE_EARN_FLEXIBLE_INTEREST;
      case "Small Assets Exchange BNB" -> SMALL_ASSETS_EXCHANGE_BNB;
      case "Auto Invest Transaction" -> AUTO_INVEST_TRANSACTION;
      default -> throw new IOException("Invalid operation string: " + s);
    };
  }
}
