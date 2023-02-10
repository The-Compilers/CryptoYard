package org.compilers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for conversion between different formats.
 */
public class Converter {
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  // Need this to ensure that all time strings are parsed correctly in the UTC timezone
  static {
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
  }

  /**
   * Convert a timestamp string in the format "yyyy-MM-dd hh:mm:ss" (such as "2022-12-20 20:48:22")
   * to a unix timestamp in UTC timezone, with milliseconds.
   *
   * @param timeString The timestamp string
   * @return Unix timestamp, with milliseconds (UTC timezone)
   * @throws IllegalArgumentException When the time string format is incorrect
   */
  public static long stringToUtcTimestamp(String timeString) throws IllegalArgumentException {
    try {
      Date parsedDate = dateFormat.parse(timeString);
      return parsedDate.getTime();
    } catch (ParseException e) {
      throw new IllegalArgumentException("Invalid time string: " + timeString);
    }
  }

  /**
   * Format a UTC timestamp as a string in the format YYYY-MM-dd HH:mm:ss.
   *
   * @param utcTimestamp UTC timestamp, including milliseconds
   * @return Formatted string
   */
  public static String utcTimeToString(long utcTimestamp) {
    return dateFormat.format(new Date(utcTimestamp));
  }

}
