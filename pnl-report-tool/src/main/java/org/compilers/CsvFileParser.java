package org.compilers;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parses standard CSV files.
 */
public class CsvFileParser {
  private final BufferedReader reader;

  private String nextRow = null;

  private boolean isEndReached = false;

  /**
   * Create a new CSV file parser, try to open the CSV file.
   *
   * @param inputFilePath Path to the CSV file to process
   * @throws IOException When the file is not found or could not be read
   */
  public CsvFileParser(String inputFilePath) throws IOException {
    try {
      reader = new BufferedReader(new FileReader(new File(inputFilePath)));
    } catch (FileNotFoundException e) {
      throw new IOException("File not found: " + inputFilePath);
    }
  }

  /**
   * Check if there are unread rows left in the CSV file.
   *
   * @return True if there are more rows to read, false otherwise
   */
  public boolean hasMoreRows() {
    if (isEndReached) {
      return true;
    }

    // Check if there is another row available, buffer it
    if (nextRow == null) {
      fetchAndBufferNextRow();
    }

    return !isEndReached;
  }

  /**
   * Fetch the next row from the file, buffer it in the nextRow variable.
   * If end of file is reached, store null in nextRow.
   * Also update the "isEndReached" state.
   */
  private void fetchAndBufferNextRow() {
    try {
      nextRow = reader.readLine();
    } catch (IOException e) {
      nextRow = null;
    }
    if (nextRow == null) {
      isEndReached = true;
    }
  }

  /**
   * Read the next row from the CSV file, split it in separate cell-values.
   *
   * @return Values of each cell in the retrieved CSV-row.
   */
  public String[] readNextRow() {
    if (nextRow == null) {
      fetchAndBufferNextRow();
    }

    String[] result = null;
    if (nextRow != null) {
      result = removeEmptyDoubleQuotes(nextRow.split(","));
      nextRow = null; // Clear the cached row
    }
    return result;
  }

  /**
   * Go through all values, if a value is "", replace it with a simple empty string.
   *
   * @param values The CSV-values to check
   * @return The same values, where "\"\"" is replaced with ""
   */
  private String[] removeEmptyDoubleQuotes(String[] values) {
    for (int i = 0; i < values.length; ++i) {
      if ("\"\"".equals(values[i])) {
        values[i] = "";
      }
    }
    return values;
  }
}
