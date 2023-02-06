package org.compilers;

import java.io.IOException;

public class ReportGenerator {
  public static void main(String[] args) {
    try {
      String inputFilePath = getInputFilePath(args);
      String outputFilePath = getOutputFilePath(args);
      List<RawAccountChanges> accountChanges = readAccountChanges(inputFilePath);
      List<Transaction> transactions = groupTransactionsByTimestamp(accountChanges);
      Report report = generateReport(transactions);
      writeReportToFile(report, outputFilePath);
    } catch (IOException e) {
      System.out.println("Report generation failed: " + e.getMessage());
    }
  }
}