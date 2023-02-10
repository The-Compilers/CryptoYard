package org.compilers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Logic for generation of the PNL report.
 */
public class ReportGenerator {

  /**
   * Analyze Transaction CSV file exported from Binance, generate a report, write it in
   * the output file.
   *
   * @param inputFilePath  Path to the CVS input file (exported from Binance)
   * @param outputFilePath Path to the output file where the report will be written
   * @throws IOException When some error happened during input file reading or output file writing
   */
  public void createReport(String inputFilePath, String outputFilePath) throws IOException {
    List<RawAccountChange> accountChanges = readAccountChanges(inputFilePath);
    List<Transaction> transactions = groupTransactionsByTimestamp(accountChanges);
    Report report = generateReport(transactions);
    writeReportToFile(report, outputFilePath);
  }


  private static List<RawAccountChange> readAccountChanges(String inputFilePath)
      throws IOException {
    CsvFileParser csvParser = new CsvFileParser(inputFilePath);

    String[] headerRow = csvParser.readNextRow();
    checkHeaderRowFormat(headerRow);

    List<RawAccountChange> accountChanges = new LinkedList<>();
    RawAccountChange previousChange = null;
    while (csvParser.hasMoreRows()) {
      String[] row = csvParser.readNextRow();
      RawAccountChange change = createAccountChangeFromCsvRow(row);
      if (previousChange != null && previousChange.getUtcTime() > change.getUtcTime()) {
        throw new IOException("Decreasing timestamp detected: " + previousChange + " -> " + change);
      }
      accountChanges.add(change);
      previousChange = change;
    }

    return accountChanges;
  }

  private static void checkHeaderRowFormat(String[] headerRow) throws IOException {
    if (headerRow.length != 7 || !"User_ID".equals(headerRow[0])
        || !"UTC_Time".equals(headerRow[1])
        || !"Account".equals(headerRow[2])
        || !"Operation".equals(headerRow[3])
        || !"Coin".equals(headerRow[4])
        || !"Change".equals(headerRow[5])
        || !"Remark".equals(headerRow[6])) {
      throw new IOException("Invalid header row format: " + String.join(",", headerRow));
    }
  }

  private static RawAccountChange createAccountChangeFromCsvRow(String[] row) throws IOException {
    if (row.length != 7) {
      throw new IOException("Invalid row format: " + String.join(",", row));
    }
    long utcTimestamp = Converter.stringToUtcTimestamp(row[1]);
    AccountType accountType = AccountType.fromString(row[2]);
    Operation operation = Operation.fromString(row[3]);
    String asset = row[4];
    String change = parseDecimalString(row[5]);
    String remark = row[6];
    return new RawAccountChange(utcTimestamp, accountType, operation, asset, change, remark);
  }


  private static String parseDecimalString(String s) throws IOException {
    try {
      Double.parseDouble(s);
      return s;
    } catch (NumberFormatException e) {
      throw new IOException("Invalid number format: " + s);
    }
  }

  private List<Transaction> groupTransactionsByTimestamp(List<RawAccountChange> accountChanges) {
    List<Transaction> transactions = new LinkedList<>();
    RawAccountChange lastChange = null;
    Transaction transaction = null;
    for (RawAccountChange change : accountChanges) {
      if (lastChange == null || lastChange.getUtcTime() != change.getUtcTime()) {
        transaction = new Transaction(change.getUtcTime());
        transactions.add(transaction);
      }
      transaction.append(change);
      lastChange = change;
    }
    return transactions;
  }

  private Report generateReport(List<Transaction> transactions) {
    throw new UnsupportedOperationException();
  }

  private void writeReportToFile(Report report, String outputFilePath) {
    throw new UnsupportedOperationException();
  }
}
