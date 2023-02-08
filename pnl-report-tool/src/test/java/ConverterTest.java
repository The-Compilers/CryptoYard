import org.compilers.Converter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConverterTest {
  @Test
  public void testTimestampConversion() {
    assertEquals(1571834540000L, Converter.stringToTimestamp("2019-10-23 12:42:20"));
    assertEquals(1571846455000L, Converter.stringToTimestamp("2019-10-23 16:00:55"));
  }
}
