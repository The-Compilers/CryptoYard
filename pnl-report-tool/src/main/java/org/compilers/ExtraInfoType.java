package org.compilers;

import java.io.IOException;

/**
 * The different types of necessary extra-information.
 */
public enum ExtraInfoType {
  ASSET_PRICE;

  /**
   * Construct an ExtraInfoType from a string.
   *
   * @param s The string value
   * @return Corresponding enum value
   * @throws IOException When the provided string does not correspond to any enum value
   */
  public static ExtraInfoType fromString(String s) throws IOException {
    return switch (s) {
      case "ASSET_PRICE" -> ASSET_PRICE;
      default -> throw new IOException("Invalid extra-info string: " + s);
    };
  }
}
