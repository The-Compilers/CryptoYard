package org.compilers;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Storage of counts for operations of different types.
 */
public class OperationMultiSet {
  private Map<Operation, Integer> operationCounts = new EnumMap<>(Operation.class);

  /**
   * Create a multiset, add all the given operations to the set.
   *
   * @param operations A list containing one or several operations
   */
  public OperationMultiSet(Operation... operations) {
    for (Operation operation : operations) {
      add(operation, 1);
    }
  }

  public OperationMultiSet() {
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OperationMultiSet that = (OperationMultiSet) o;
    return Objects.equals(operationCounts, that.operationCounts);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operationCounts);
  }


  /**
   * Add operations to the set.
   *
   * @param operation Type of operations
   * @param count     How many operations to add
   */
  public void add(Operation operation, int count) {
    if (operationCounts.containsKey(operation)) {
      count += operationCounts.get(operation);
    }
    operationCounts.put(operation, count);
  }

  /**
   * Get the number of operations of specific type.
   *
   * @param operation The type of operation to check
   * @return The number of operations of this type stored here, zero if none.
   */
  public int getOperationCount(Operation operation) {
    Integer count = operationCounts.get(operation);
    return count != null ? count : 0;
  }
}
