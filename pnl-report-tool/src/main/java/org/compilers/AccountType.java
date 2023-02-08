package org.compilers;

import java.io.IOException;

/**
 * The available account types.
 */
public enum AccountType {
  SPOT, EARN;

  /**
   * Create the enum from a Capitalized string.
   *
   * @param s Capitalized string - the format used in the CSV files
   * @return Corresponding Enum value
   * @throws IOException When an unexpected s value is provided
   */
  public static AccountType fromString(String s) throws IOException {
    return switch (s) {
      case "Spot" -> SPOT;
      case "Earn" -> EARN;
      default -> throw new IOException("Invalid account type string: " + s);
    };
  }
}
