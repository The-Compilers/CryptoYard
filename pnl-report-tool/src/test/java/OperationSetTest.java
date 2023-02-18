import org.compilers.Operation;
import org.compilers.OperationMultiSet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OperationSetTest {
  @Test
  public void testAdd() {
    OperationMultiSet operationMultiSet = new OperationMultiSet();
    assertEquals(0, operationMultiSet.getOperationCount(Operation.DEPOSIT));

    operationMultiSet.add(Operation.WITHDRAW, 3);
    assertEquals(0, operationMultiSet.getOperationCount(Operation.DEPOSIT));
    assertEquals(3, operationMultiSet.getOperationCount(Operation.WITHDRAW));

    operationMultiSet.add(Operation.DEPOSIT, 2);
    assertEquals(2, operationMultiSet.getOperationCount(Operation.DEPOSIT));
    assertEquals(3, operationMultiSet.getOperationCount(Operation.WITHDRAW));

    operationMultiSet.add(Operation.DEPOSIT, 2);
    assertEquals(4, operationMultiSet.getOperationCount(Operation.DEPOSIT));
    assertEquals(3, operationMultiSet.getOperationCount(Operation.WITHDRAW));
  }

  @Test
  public void testEquality() {
    OperationMultiSet s1 = new OperationMultiSet();
    OperationMultiSet s2 = new OperationMultiSet();
    assertEquals(s1, s2);
    assertNotEquals(null, s1);

    s1.add(Operation.DEPOSIT, 4);
    assertNotEquals(s1, s2);

    s2.add(Operation.DEPOSIT, 4);
    assertEquals(s1, s2);

    s1.add(Operation.BUY, 7);
    s1.add(Operation.BUY, 3);
    s1.add(Operation.SELL, 4);
    s1.add(Operation.SELL, 2);
    s2.add(Operation.SELL, 2);
    s2.add(Operation.BUY, 5);
    s2.add(Operation.SELL, 2);
    s2.add(Operation.BUY, 5);
    s2.add(Operation.SELL, 2);
    assertEquals(s1, s2);

    s1.add(Operation.WITHDRAW, 7);
    assertNotEquals(s1, s2);
  }
}
