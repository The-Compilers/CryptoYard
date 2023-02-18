package org.compilers;

/**
 * A transaction of depositing a crypto to the Binance account.
 */
public class DepositTransaction extends Transaction {
  public DepositTransaction(Transaction t) {
    super(t);
  }

  @Override
  public String toString() {
    RawAccountChange change = getChange();
    return "Deposit " + change.getAmount() + " " + change.getAsset()
        + " @ " + Converter.utcTimeToString(utcTime);
  }

  private RawAccountChange getChange() {
    return getFirstChangeOfType(Operation.DEPOSIT);
  }

}
