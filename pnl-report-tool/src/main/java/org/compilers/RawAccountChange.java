package org.compilers;

import static org.compilers.Converter.utcTimeToString;

/**
 * One single, atomic account change (part of a larger transaction).
 */
public class RawAccountChange {
  private final long utcTime;
  private final AccountType account;
  private final Operation operation;
  private final String asset;
  private final String changeAmount;
  private final String remark;

  /**
   * Create a new account-change record.
   *
   * @param utcTime      UTC timestamp, with milliseconds
   * @param account      The account that was used (Spot, Earn, etc)
   * @param operation    Performed operation
   * @param asset        The involved asset (coin or fiat)
   * @param changeAmount The amount of the change - how much coin was added/removed
   *                     to/from the accounts
   * @param remark       A comment
   */
  public RawAccountChange(long utcTime, AccountType account, Operation operation,
                          String asset, String changeAmount, String remark) {
    this.utcTime = utcTime;
    this.account = account;
    this.operation = operation;
    this.asset = asset;
    this.changeAmount = changeAmount;
    this.remark = remark;
  }

  /**
   * Get UTC timestamp of the change action, with milliseconds.
   *
   * @return The UTC time when this change was made to the account
   */
  public long getUtcTime() {
    return utcTime;
  }

  @Override
  public String toString() {
    return "RawAccountChange{"
        + "utcTime=" + utcTimeToString(utcTime)
        + ", account=" + account
        + ", operation=" + operation
        + ", asset='" + asset + '\''
        + ", changeAmount='" + changeAmount + '\''
        + ", remark='" + remark + '\''
        + '}';
  }
}
