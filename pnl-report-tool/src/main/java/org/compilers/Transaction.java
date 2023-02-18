package org.compilers;

import static org.compilers.Converter.utcTimeToString;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains one financial asset transaction, consisting of several AccountChanges.
 * For example, purchasing a cryptocurrency may consist of three changes: buy + sell + fee.
 */
public class Transaction {
  Map<Operation, List<RawAccountChange>> atomicAccountChanges = new EnumMap<>(Operation.class);
  protected final long utcTime;

  /**
   * Create a new transaction.
   *
   * @param utcTime UTC timestamp of the transaction.
   */
  public Transaction(long utcTime) {
    this.utcTime = utcTime;
  }

  public Transaction(Transaction t) {
    this.atomicAccountChanges = t.atomicAccountChanges;
    this.utcTime = t.utcTime;
  }

  /**
   * Append a change to the transaction.
   *
   * @param change An atomic account change
   */
  public void append(RawAccountChange change) {
    if (!atomicAccountChanges.containsKey(change.getOperation())) {
      atomicAccountChanges.put(change.getOperation(), new LinkedList<>());
    }
    List<RawAccountChange> changeList = atomicAccountChanges.get(change.getOperation());
    changeList.add(change);
  }

  @Override
  public String toString() {
    return "Transaction@" + utcTimeToString(utcTime);
  }

  /**
   * Look at the registered raw account changes, find out what kind of transaction this is:
   * deposit, withdrawal, buy, savings interest, dust transfer, etc.
   *
   * @return A transaction with specific type, with the same atomic operations
   */
  public Transaction clarifyTransactionType() {
    if (consistsOf(Operation.DEPOSIT)) {
      return new DepositTransaction(this);
    }
    // TODO - implement other transaction types
    return null;
  }

  private boolean consistsOf(Operation... operations) {
    return getOperationMultiSet().equals(new OperationMultiSet(operations));
  }

  private OperationMultiSet getOperationMultiSet() {
    OperationMultiSet operationMultiSet = new OperationMultiSet();
    for (Map.Entry<Operation, List<RawAccountChange>> entry : atomicAccountChanges.entrySet()) {
      operationMultiSet.add(entry.getKey(), entry.getValue().size());
    }
    return operationMultiSet;
  }

  /**
   * Get the first account change with the given type.
   *
   * @param operation Operation type
   * @return The first change or null if no change of this type is found.
   */
  protected RawAccountChange getFirstChangeOfType(Operation operation) {
    List<RawAccountChange> changes = atomicAccountChanges.get(operation);
    return changes != null && !changes.isEmpty() ? changes.get(0) : null;
  }
}
