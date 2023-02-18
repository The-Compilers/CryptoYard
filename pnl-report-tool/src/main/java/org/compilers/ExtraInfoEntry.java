package org.compilers;

/**
 * Represents one unit of extra information for a financial transaction, provided by the user.
 *
 * @param utcTimestamp UTC timestamp, including milliseconds.
 * @param type         The type of the information
 * @param hint         Hint given to the user (in case this information is missing)
 */
public record ExtraInfoEntry(long utcTimestamp, ExtraInfoType type, String hint) {
}
