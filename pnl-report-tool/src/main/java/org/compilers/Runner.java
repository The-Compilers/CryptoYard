package org.compilers;

import java.io.IOException;

/**
 * The main application runner - handles command-line arguments, calls the necessary logic.
 */
public class Runner {

  /**
   * The main entrypoint of the application.
   *
   * @param args Command line arguments. Expected values: first argument is path to the
   *             input CSV-file, the second argument is path to the output file where the result
   *             will be written.
   */
  public static void main(String[] args) {
    try {
      String inputFilePath = getInputFilePath(args);
      String outputFilePath = getOutputFilePath(args);
      ReportGenerator reportGenerator = new ReportGenerator();
      reportGenerator.createReport(inputFilePath, outputFilePath);
    } catch (IOException e) {
      System.out.println("Report generation failed: " + e.getMessage());
    }
  }

  private static String getInputFilePath(String[] args) throws IOException {
    if (args.length < 1) {
      throw new IOException(
          "The first command-line argument must contain path to the input file (CSV)");
    }

    return args[0];
  }

  private static String getOutputFilePath(String[] args) throws IOException {
    if (args.length < 2) {
      throw new IOException(
          "The second command-line argument must contain path to the output file");
    }
    return args[0];
  }
}
