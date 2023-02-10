package org.compilers;

import java.util.LinkedList;
import java.util.List;
import static org.compilers.Converter.utcTimeToString;

/**
 * Contains one financial asset transaction, consisting of several AccountChanges.
 * For example, purchasing a cryptocurrency may consist of three changes: buy + sell + fee.
 */
public class Transaction {
  List<RawAccountChange> atomicOperations = new LinkedList<>();
  private final long utcTime;

  /**
   * Create a new transaction.
   *
   * @param utcTime UTC timestamp of the transaction.
   */
  public Transaction(long utcTime) {
    this.utcTime = utcTime;
  }

  public void append(RawAccountChange change) {
    atomicOperations.add(change);
  }

  @Override
  public String toString() {
    return "Transaction@" + utcTimeToString(utcTime);
  }
}
