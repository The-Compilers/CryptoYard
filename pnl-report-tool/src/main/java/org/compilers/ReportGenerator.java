package org.compilers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Logic for generation of the PNL report.
 */
public class ReportGenerator {
  private final String inputFilePath;
  private final String outputFilePath;
  private final String homeCurrency;
  private final String extraFilePath;

  /**
   * Create a new report generator.
   *
   * @param inputFilePath  Path to the CVS input file (exported from Binance)
   * @param outputFilePath Path to the output file where the report will be written
   * @param homeCurrency   Home currency - used for profit-and-loss calculations
   * @param extraFilePath  Path to a CSV file where necessary extra information is stored
   * @throws IOException When some error happened during input file reading or output file writing
   */
  public ReportGenerator(String inputFilePath, String outputFilePath,
                         String homeCurrency, String extraFilePath) throws IOException {

    this.inputFilePath = inputFilePath;
    this.outputFilePath = outputFilePath;
    this.homeCurrency = homeCurrency;
    this.extraFilePath = extraFilePath;
  }

  /**
   * Analyze Transaction CSV file exported from Binance, generate a report, write it in
   * the output file.
   */
  public void createReport() throws IOException {
    List<RawAccountChange> accountChanges = readAccountChanges(inputFilePath);
    List<Transaction> rawTransactions = groupTransactionsByTimestamp(accountChanges);
    List<Transaction> transactions = clarifyTransactionTypes(rawTransactions);
    ExtraInfo necessaryUserInfo = detectNecessaryExtraInfo(transactions);
    ExtraInfo providedUserInfo = readUserProvidedExtraInfo();
    ExtraInfo missingInfo = detectMissingInfo(necessaryUserInfo, providedUserInfo);
    if (missingInfo.isEmpty()) {
      Report report = generateReport(transactions);
      writeReportToFile(report, outputFilePath);
    } else {
      printMissingInfoRequirement(missingInfo);
    }
  }

  private ExtraInfo detectNecessaryExtraInfo(List<Transaction> transactions) {
    ExtraInfo necessaryInfo = new ExtraInfo();
    for (Transaction t : transactions) {
      ExtraInfoEntry necessaryExtraInfo = t.getNecessaryExtraInfo();
      if (necessaryExtraInfo != null) {
        necessaryInfo.add(necessaryExtraInfo);
      }
    }
    return necessaryInfo;
  }

  private ExtraInfo detectMissingInfo(ExtraInfo necessaryInfo, ExtraInfo providedInfo) {
    ExtraInfo missingInfo = new ExtraInfo();
    for (ExtraInfoEntry necessaryEntry : necessaryInfo.getAllEntries()) {
      if (!providedInfo.contains(necessaryEntry)) {
        missingInfo.add(necessaryEntry);
      }
    }
    return missingInfo;
  }

  private ExtraInfo readUserProvidedExtraInfo() throws IOException {
    CsvFileParser csvParser = new CsvFileParser(extraFilePath);

    ExtraInfo extraInfo = new ExtraInfo();

    while (csvParser.hasMoreRows()) {
      extraInfo.add(createExtraInfoEntryFromCsvRow(csvParser.readNextRow()));
    }

    return extraInfo;
  }

  private ExtraInfoEntry createExtraInfoEntryFromCsvRow(String[] csvRow) throws IOException {
    return new ExtraInfoEntry(parseLong(csvRow[0]), ExtraInfoType.fromString(csvRow[1]),
        parseDecimalString(csvRow[2]));
  }

  private void printMissingInfoRequirement(ExtraInfo missingInfo) {
    System.out.println("Provide the necessary information in the extra-info file `"
        + extraFilePath + "`: ");
    for (ExtraInfoEntry mi : missingInfo.getAllEntries()) {
      System.out.println(mi.utcTimestamp() + "," + mi.type() + "," + mi.hint());
    }
  }

  /**
   * Go through a list of raw transactions, look at their atomic changes, decide the type of each
   * transaction: Deposit, Buy, Saving interest, etc.
   *
   * @param rawTransactions Raw transactions
   * @return List of the same transactions, but with specific types
   */
  private List<Transaction> clarifyTransactionTypes(List<Transaction> rawTransactions) {
    List<Transaction> transactions = new LinkedList<>();
    for (Transaction rawTransaction : rawTransactions) {
      Transaction transaction = rawTransaction.clarifyTransactionType();
      if (transaction != null) {
        transactions.add(transaction);
      }
    }
    return transactions;
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

  private static Long parseLong(String s) throws IOException {
    try {
      return Long.parseLong(s);
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
    // TODO
    throw new UnsupportedOperationException();
  }

  private void writeReportToFile(Report report, String outputFilePath) {
    // TODO
    throw new UnsupportedOperationException();
  }
}
