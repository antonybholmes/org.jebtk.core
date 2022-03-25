/**
 * Copyright 2016 Antony Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jebtk.core.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;
import javax.swing.table.TableModel;

import org.jebtk.core.TableData;
import org.jebtk.core.collections.ArrayUtils;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.stream.Stream;
import org.jebtk.core.text.Splitter;
import org.jebtk.core.text.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Functions for IO and file operations.
 * 
 * @author Antony Holmes
 *
 */
public class Io {

  /**
   * The constant MAX_COPY_SIZE.
   */
  public static final int MAX_COPY_SIZE = (32 * 1024 * 1024); // - (32 * 1024);

  /**
   * The constant FILE_EXT_TXT.
   */
  public static final String FILE_EXT_TXT = "txt";

  /**
   * The constant CSV_EXTENSION.
   */
  public static final String FILE_EXT_CSV = "csv";

  /**
   * The constant LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(Io.class);

  /**
   * The constant PWD.
   */
  public static final Path PWD = PathUtils.getPath(System.getProperty("user.dir"));

  /**
   * Read sequences.
   *
   * @param file the file
   * @return the map
   */
  public static final Map<String, String> readSequences(Path file) {
    return readSequences(file, false);
  }

  /**
   * Read sequences.
   *
   * @param file      the file
   * @param shortName the short name
   * @return the map
   */
  public static final Map<String, String> readSequences(Path file, boolean shortName) {
    Map<String, String> sequences = new HashMap<String, String>();

    try {
      // System.out.println(file.toString());

      BufferedReader reader = FileUtils.newBufferedReader(file);

      String line;

      Pattern fastaHeaderPattern = Pattern.compile("^>\\s*(.+)");

      Matcher fastaHeaderMatcher;

      String name = null;

      try {
        while ((line = reader.readLine()) != null) {
          fastaHeaderMatcher = fastaHeaderPattern.matcher(line);

          if (fastaHeaderMatcher.find()) {
            name = fastaHeaderMatcher.group(1);

            // replace asterixes in the name with capital X to make seaches
            // easier
            name = name.replaceAll("\\*", "X");

            // name = Text.removeRegexChars(name);

            if (shortName) {
              name = name.split("\\s")[0];
            }

            if (name.length() != 0) {
              sequences.put(name, "");
            }
          } else {
            if (name.length() != 0) {
              if (line.length() > 0) {
                sequences.put(name, sequences.get(name) + line);
                // System.out.println("seq:" + name + ":" + sequences.get(name)
                // + line);
              }
            }
          }
        }
      } finally {
        reader.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return sequences;
  }

  /**
   * Read alignments.
   *
   * @param file       the file
   * @param delimiter  the delimiter
   * @param alignments the alignments
   */
  public static final void readAlignments(Path file, String delimiter, Map<String, List<List<String>>> alignments) {
    try {
      BufferedReader reader = FileUtils.newBufferedReader(file);

      String line;
      String name;
      List<List<String>> lines;

      try {
        while ((line = reader.readLine()) != null) {
          List<String> tokens = TextUtils.fastSplit(line, delimiter);

          name = tokens.get(0);
          // System.out.println(name);

          name = name.replaceAll("^\\s+", "");
          name = name.replaceAll("\\*", "X");

          if (!tokens.get(1).equals("+")) {
            continue;
          }

          if (alignments.containsKey(name)) {
            alignments.get(name).add(tokens);
          } else {
            lines = new ArrayList<List<String>>();

            lines.add(tokens);

            alignments.put(name, lines);
          }
        }
      } finally {
        reader.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Removes the comments.
   *
   * @param in      the in
   * @param out     the out
   * @param comment the comment
   */
  public static final void removeComments(Path in, Path out, String comment) {
    try {
      // System.out.println(file.toString());

      BufferedReader reader = FileUtils.newBufferedReader(in); // new
                                                               // BufferedReader(new
                                                               // FileReader(in));
      BufferedWriter writer = FileUtils.newBufferedWriter(out); // FileUtils.newBufferedReader(file);

      String line;

      try {
        while ((line = reader.readLine()) != null) {
          if (line.startsWith(comment)) {
            continue;
          }

          writer.write(line);
          writer.newLine();
        }
      } finally {
        reader.close();
        writer.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Removes the blank lines.
   *
   * @param in  the in
   * @param out the out
   */
  public static final void removeBlankLines(Path in, Path out) {
    try {
      // System.out.println(file.toString());

      BufferedReader reader = FileUtils.newBufferedReader(in); // new
                                                               // BufferedReader(new
                                                               // FileReader(in));
      BufferedWriter writer = FileUtils.newBufferedWriter(out); // FileUtils.newBufferedReader(file);

      String line;

      try {
        while ((line = reader.readLine()) != null) {
          if (isEmptyLine(line)) {
            continue;
          }

          writer.write(line);
          writer.newLine();
        }
      } finally {
        reader.close();
        writer.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a table into memory.
   *
   * @param file             the file
   * @param delimiter        the delimiter
   * @param columnHeaderMode the column header mode
   * @param rowHeaderMode    the row header mode
   * @return the table data
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final TableData<String> loadTable(Path file, char delimiter, boolean columnHeaderMode,
      boolean rowHeaderMode) throws IOException {
    return loadTable(file, delimiter, columnHeaderMode, rowHeaderMode, null);
  }

  /**
   * Loads a table into memory.
   *
   * @param file             the file
   * @param delimiter        the delimiter
   * @param columnHeaderMode the column header mode
   * @param rowHeaderMode    the row header mode
   * @param columns          the columns
   * @return the table data
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final TableData<String> loadTable(Path file, char delimiter, boolean columnHeaderMode,
      boolean rowHeaderMode, List<Integer> columns) throws IOException {

    BufferedReader reader = FileUtils.newBufferedReader(file);

    TableData<String> table = new TableData<String>();

    String line;

    List<String> row;

    List<String> tokens;

    try {
      if (columnHeaderMode) {
        tokens = TextUtils.fastSplitRemoveQuotes(reader.readLine(), TextUtils.TAB_DELIMITER_CHAR);

        table.columnHeadings = new ArrayList<String>();

        for (String name : tokens) {
          table.columnHeadings.add(name);
        }
      }

      if (rowHeaderMode) {
        table.rowHeader = new ArrayList<String>();
      }

      boolean subset = columns != null && columns.size() > 0;

      int s = rowHeaderMode ? 1 : 0;

      while ((line = reader.readLine()) != null) {
        if (Io.isEmptyLine(line)) {
          continue;
        }

        // System.err.println("table line " + file);

        tokens = TextUtils.fastSplitRemoveQuotes(line, TextUtils.TAB_DELIMITER_CHAR);

        if (rowHeaderMode) {
          table.rowHeader.add(tokens.get(0));
        }

        row = new ArrayList<String>();

        if (subset) {
          for (int c : columns) {
            row.add(tokens.get(c));
          }
        } else {
          // add all columns

          for (int i = s; i < tokens.size(); ++i) {
            row.add(tokens.get(i));
          }
        }

        table.addRow(row);
      }
    } finally {
      reader.close();
    }

    return table;

  }

  /**
   * Load double table.
   *
   * @param file             the file
   * @param delimiter        the delimiter
   * @param columnHeaderMode the column header mode
   * @param rowHeaderMode    the row header mode
   * @param columns          the columns
   * @return the table data
   */
  public static final TableData<Double> loadDoubleTable(Path file, String delimiter, boolean columnHeaderMode,
      boolean rowHeaderMode, List<Integer> columns) {
    try {
      // System.out.println(file.toString());

      BufferedReader reader = FileUtils.newBufferedReader(file);

      TableData<Double> table = new TableData<Double>();

      String line;

      List<Double> row;

      String[] tokens;

      try {
        if (columnHeaderMode) {
          tokens = reader.readLine().split(delimiter);

          table.columnHeadings = new ArrayList<String>();

          for (String name : tokens) {
            table.columnHeadings.add(name);
          }
        }

        if (rowHeaderMode) {
          table.rowHeader = new ArrayList<String>();
        }

        boolean subset = columns != null && columns.size() > 0;

        int s = rowHeaderMode ? 1 : 0;

        while ((line = reader.readLine()) != null) {
          if (Io.isEmptyLine(line)) {
            continue;
          }

          tokens = line.split(delimiter);

          if (rowHeaderMode) {
            table.rowHeader.add(tokens[0]);
          }

          row = new ArrayList<Double>();

          if (subset) {
            for (int c : columns) {
              row.add(Double.parseDouble(tokens[c]));
            }
          } else {
            // add all columns

            for (int i = s; i < tokens.length; ++i) {
              row.add(Double.parseDouble(tokens[i]));
            }
          }

          table.addRow(row);
          System.out.println(TextUtils.join(row, TextUtils.TAB_DELIMITER));
        }
      } finally {
        reader.close();
      }

      return table;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Write table.
   *
   * @param <T>       the generic type
   * @param table     the table
   * @param delimiter the delimiter
   * @param file      the file
   */
  public static final <T> void writeTable(TableData<T> table, String delimiter, Path file) {
    try {
      // System.out.println("creating table " + out.getAbsolutePath());

      BufferedWriter writer = FileUtils.newBufferedWriter(file);

      try {
        if (table.columnHeadings != null) {
          writer.write(Stream.of(table.columnHeadings).asString().join(delimiter));
          writer.newLine();
        }

        boolean rowHeader = table.rowHeader != null;

        if (table.size() != 0) {
          for (int i = 0; i < table.size(); ++i) {
            if (rowHeader) {
              writer.write(table.rowHeader.get(i));
              writer.write(delimiter);
            }

            writer.write(TextUtils.join(table.getRow(i), delimiter));
            writer.newLine();
          }
        }
      } finally {
        writer.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Write table.
   *
   * @param columnNames the column names
   * @param data        the data
   * @param delimiter   the delimiter
   * @param file        the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void writeTable(String[] columnNames, List<Object[]> data, String delimiter, Path file)
      throws IOException {
    // System.out.println("creating table " + out.getAbsolutePath());

    BufferedWriter writer = FileUtils.newBufferedWriter(file);

    try {
      writer.write(TextUtils.join(columnNames, delimiter));
      writer.newLine();

      for (int i = 0; i < data.size(); ++i) {
        writer.write(TextUtils.join(data.get(i), delimiter));
        writer.newLine();
      }
    } finally {
      writer.close();
    }
  }

  /**
   * Writes a list to file.
   *
   * @param <T>  the generic type
   * @param list the list
   * @param out  the out
   */
  public static final <T> void writeList(List<T> list, Path out) {
    try {
      BufferedWriter writer = FileUtils.newBufferedWriter(out);

      try {
        for (T item : list) {
          writer.write(item.toString());
          writer.newLine();
        }
      } finally {
        writer.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a list from file assuming one entry per line.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final List<String> loadList(Path file, boolean skipHeader) throws IOException {
    LOG.info("Load list from {}, {}...", file, skipHeader);

    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    List<String> rows = new ArrayList<String>();

    Splitter splitter = Splitter.onTab();

    try {

      if (skipHeader) {
        reader.readLine();
      }

      while ((line = reader.readLine()) != null) {
        rows.add(splitter.text(line).get(0));
      }
    } finally {
      reader.close();
    }

    return rows;
  }

  /**
   * Gets the header.
   *
   * @param file the file
   * @return the header
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final List<String> getHeader(Path file) throws IOException {
    LOG.info("Load list from {}...", file);

    BufferedReader reader = FileUtils.newBufferedReader(file);

    List<String> cols = null;

    try {
      cols = TextUtils.tabSplit(reader.readLine());
    } finally {
      reader.close();
    }

    return cols;
  }

  /**
   * Load csv list.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final String[] loadCSVList(Path file, boolean skipHeader) throws IOException {
    LOG.info("Load list from {}, {}...", file, skipHeader);

    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    List<String> rows = new ArrayList<String>();

    try {

      if (skipHeader) {
        reader.readLine();
      }

      while ((line = reader.readLine()) != null) {
        List<String> tokens = TextUtils.parseCSVLine(line);

        rows.add(tokens.get(0));
      }
    } finally {
      reader.close();
    }

    return (String[]) rows.toArray();
  }

  /**
   * Gets the CSV header.
   *
   * @param file the file
   * @return the CSV header
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final List<String> getCSVHeader(Path file) throws IOException {
    LOG.info("Load list from {}...", file);

    BufferedReader reader = FileUtils.newBufferedReader(file);

    List<String> cols = null;

    try {
      cols = TextUtils.fastSplit(reader.readLine(), TextUtils.COMMA_DELIMITER);
    } finally {
      reader.close();
    }

    return cols;
  }

  /**
   * Load double list.
   *
   * @param file the file
   * @return the list
   */
  public static final List<Double> loadDoubleList(Path file) {
    try {
      // System.out.println(file.toString());

      BufferedReader reader = FileUtils.newBufferedReader(file);

      String line;

      List<Double> row = new ArrayList<Double>();

      try {
        while ((line = reader.readLine()) != null) {
          try {
            row.add(Double.parseDouble(line));
          } catch (NumberFormatException nfe) {
            // do nothing
          }
        }
      } finally {
        reader.close();
      }

      return row;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Gets the lines.
   *
   * @param file the file
   * @return the lines
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final List<String> getLines(Path file) throws IOException {
    List<String> lines = new ArrayList<String>();

    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    try {
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } finally {
      reader.close();
    }

    return lines;
  }

  /**
   * Returns the first line of a file.
   *
   * @param file the file
   * @return the head
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final String getHead(Path file) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    try {
      line = reader.readLine();
    } finally {
      reader.close();
    }

    return line;
  }

  /**
   * Read map.
   *
   * @param file            the file
   * @param delimiter       the delimiter
   * @param headerMode      the header mode
   * @param columns         the columns
   * @param referenceColumn the reference column
   * @param map             the map
   * @return the list
   */
  public static final List<String> readMap(Path file, String delimiter, boolean headerMode, List<Integer> columns,
      int referenceColumn, Map<String, String> map) {
    try {
      // System.out.println(file.toString());

      BufferedReader reader = FileUtils.newBufferedReader(file);

      String line;

      List<String> header = null;

      List<String> row;

      List<String> tokens;

      if (headerMode) {
        line = reader.readLine();

        header = TextUtils.fastSplit(line, delimiter);
      }

      boolean subset = columns != null && columns.size() > 0;

      try {
        while ((line = reader.readLine()) != null) {
          if (subset) {
            tokens = TextUtils.fastSplit(line, delimiter);

            row = new ArrayList<String>();

            for (int c : columns) {
              row.add(tokens.get(c));
            }
          } else {
            // add all columns
            row = TextUtils.fastSplit(line, delimiter);
          }

          map.put(row.get(referenceColumn), TextUtils.join(row, delimiter));
        }
      } finally {
        reader.close();
      }

      return header;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Read table.
   *
   * @param file       the file
   * @param delimiter  the delimiter
   * @param table      the table
   * @param headerMode the header mode
   * @return the list
   */
  public static final List<String> readTable(Path file, String delimiter, List<List<String>> table,
      boolean headerMode) {
    try {
      // System.out.println(file.toString());

      BufferedReader reader = FileUtils.newBufferedReader(file);

      String line;

      List<String> header = null;

      try {
        if (headerMode) {
          header = TextUtils.fastSplit(reader.readLine(), delimiter);
        }

        while ((line = reader.readLine()) != null) {
          if (Io.isEmptyLine(line)) {
            continue;
          }

          table.add(TextUtils.fastSplit(line, delimiter));
        }
      } finally {
        reader.close();
      }

      return header;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Returns a list of files from a text file containing a list of files.
   *
   * @param file the file
   * @return the files from list
   */
  public static final List<Path> getFilesFromList(Path file) {
    List<Path> files = new ArrayList<Path>();

    try {
      // System.out.println(file.toString());

      BufferedReader reader = FileUtils.newBufferedReader(file);

      String line;

      try {
        while ((line = reader.readLine()) != null) {
          files.add(PathUtils.getPath(line));
        }
      } finally {
        reader.close();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return files;
  }

  /**
   * Returns a sorted list of files from a directory.
   *
   * @param dir    the dir
   * @param filter the filter
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final List<Path> listFiles(Path dir, FileFilter filter) throws IOException {

    List<Path> files = FileUtils.ls(dir, filter);

    if (CollectionUtils.isNullOrEmpty(files)) {
      return Collections.emptyList();
    }

    List<Path> ret = new ArrayList<Path>();

    for (Path file : files) {
      ret.add(file);
    }

    Collections.sort(ret);

    return ret;
  }

  /**
   * Return a sorted list of the directories in a directory.
   *
   * @param dir the dir
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final List<Path> listDirectories(Path dir) throws IOException {
    List<Path> files = FileUtils.ls(dir);

    List<Path> ret = new ArrayList<Path>();

    for (Path file : files) {
      if (FileUtils.isDirectory(file)) {
        continue;
      }

      ret.add(file);
    }

    Collections.sort(ret);

    return ret;
  }

  /**
   * Deletes a file from the system.
   *
   * @param file the file
   * @return true, if successful
   */
  public static final boolean delete(File file) {
    // Make sure the file or directory exists and isn't write protected
    if (!file.exists()) {
      return false;
    }

    // If it is a directory, make sure it is empty
    if (file.isDirectory()) {
      if (file.listFiles().length > 0) {
        return false;
      }
    }

    // Attempt to delete it
    boolean success = file.delete();

    LOG.info("Deleting temp file {} {}...", file, success);

    return success;
  }

  /**
   * Creates the file.
   *
   * @param dir  the dir
   * @param file the file
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final Path createFile(Path dir, Path file) throws IOException {
    return dir.resolve(file);
  }

  /**
   * Creates the file.
   *
   * @param dir  the dir
   * @param file the file
   * @return the path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final Path createFile(Path dir, String file) throws IOException {
    return dir.resolve(file);
  }

  /**
   * Creates the file.
   *
   * @param dir  the dir
   * @param file the file
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final File createFile(File dir, File file) throws IOException {
    return new File(dir, file.getAbsolutePath());
  }

  /**
   * Concatenate multiple files together.
   *
   * @param files the files
   * @param out   the out
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void catFiles(List<Path> files, Path out) throws IOException {
    BufferedWriter writer = FileUtils.newBufferedWriter(out);

    String line;

    try {
      for (Path file : files) {
        BufferedReader reader = FileUtils.newBufferedReader(file);

        try {
          while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
          }
        } finally {
          reader.close();
        }
      }
    } finally {
      writer.close();
    }
  }

  /**
   * Makes a copy of a file using the NIO method and also attempts to use a normal
   * copy method if that fails.
   *
   * @param sourceFile the source file
   * @param destFile   the dest file
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final boolean copyFile(File sourceFile, File destFile) throws IOException {
    System.err.println("Copying " + sourceFile + " to " + destFile);

    try {
      copyFileNio(sourceFile, destFile);

      return true;
    } catch (IOException e) {
      // some sort of copy error so try again using original method

      System.err.println("Error using NIO copy, trying NIO block method...");

      try {
        copyFileNioBlock(sourceFile, destFile);

        return true;
      } catch (IOException e2) {
        System.err.println("Error using NIO block copy, trying original method...");

        try {
          copyFileOriginal(sourceFile, destFile);

          return true;
        } catch (IOException e3) {
          System.err.println("Error using block copy");

          return false;
        }
      }
    }

    /*
     * try { try { // method required to copy with windows file copy limits
     * 
     * source = new FileInputStream(sourceFile).getChannel(); destination = new
     * FileOutputStream(destFile).getChannel();
     * 
     * long size = source.size(); long position = 0;
     * 
     * while (position < size) { position += source.transferTo(position,
     * MAX_COPY_SIZE, destination); } } finally { if(source != null) {
     * source.close(); }
     * 
     * if(destination != null) { destination.close(); } } } catch (Exception e) { //
     * some sort of copy error so try again
     * 
     * System.err.println("Error using NIO copy, trying original method...");
     * 
     * copyFileOriginal(sourceFile, destFile); }
     */
  }

  /**
   * Copy file.
   *
   * @param source the source
   * @param dest   the dest
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final boolean copyFile(Path source, Path dest) throws IOException {
    return FileUtils.copy(source, dest);
  }

  /**
   * Makes a file copy using NIO.
   *
   * @param source the source
   * @param dest   the dest
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void copyFileNio(File source, File dest) throws IOException {
    FileInputStream inChannel = new FileInputStream(source);
    FileOutputStream outChannel = new FileOutputStream(source);

    try {
      inChannel.getChannel().transferTo(0, inChannel.getChannel().size(), outChannel.getChannel());
    } finally {
      inChannel.close();

      outChannel.close();
    }

    /*
     * if(!destFile.exists()) { destFile.createNewFile(); }
     * 
     * FileChannel source = null; FileChannel destination = null;
     * 
     * try { source = new FileInputStream(sourceFile).getChannel(); destination =
     * new FileOutputStream(destFile).getChannel(); destination.transferFrom(source,
     * 0, source.size()); } finally { if(source != null) { source.close(); }
     * 
     * if(destination != null) { destination.close(); } }
     */

    /*
     * try { try { // method required to copy with windows file copy limits
     * 
     * source = new FileInputStream(sourceFile).getChannel(); destination = new
     * FileOutputStream(destFile).getChannel();
     * 
     * long size = source.size(); long position = 0;
     * 
     * while (position < size) { position += source.transferTo(position,
     * MAX_COPY_SIZE, destination); } } finally { if(source != null) {
     * source.close(); }
     * 
     * if(destination != null) { destination.close(); } } } catch (Exception e) { //
     * some sort of copy error so try again
     * 
     * System.err.println("Error using NIO copy, trying original method...");
     * 
     * copyFileOriginal(sourceFile, destFile); }
     */
  }

  /**
   * Copy file nio block.
   *
   * @param sourceFile the source file
   * @param destFile   the dest file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void copyFileNioBlock(File sourceFile, File destFile) throws IOException {
    FileInputStream source = new FileInputStream(sourceFile);
    FileOutputStream destination = new FileOutputStream(destFile);

    FileChannel sourceChannel = source.getChannel();
    FileChannel destinationChannel = destination.getChannel();

    long size = sourceChannel.size();
    long position = 0;

    try {
      // method required to copy with windows file copy limits

      while (position < size) {
        position += sourceChannel.transferTo(position, MAX_COPY_SIZE, destinationChannel);
      }
    } finally {
      source.close();
      sourceChannel.close();

      destination.close();
      destinationChannel.close();
    }
  }

  /**
   * Makes a copy of a file using the conventional Java IO operations.
   *
   * @param in  the in
   * @param out the out
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void copyFileOriginal(File in, File out) throws IOException {
    FileInputStream fis = new FileInputStream(in);
    FileOutputStream fos = new FileOutputStream(out);

    try {
      byte[] buf = new byte[1024];
      int i = 0;

      while ((i = fis.read(buf)) != -1) {
        fos.write(buf, 0, i);
      }
    } finally {
      fis.close();
      fos.close();
    }
  }

  /**
   * Make a directory and create parent directories if necessary.
   *
   * @param dir the dir
   * @return true, if successful
   */
  public static final boolean makeDirectory(File dir) {
    if (dir.isDirectory()) {
      return true;
    }

    LOG.info("Creating directory {}", dir);

    return dir.mkdirs();
  }

  /**
   * Make directory.
   *
   * @param dir the dir
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final boolean makeDirectory(Path dir) throws IOException {
    return FileUtils.mkdir(dir);
  }

  /**
   * Move file.
   *
   * @param source      the source
   * @param destination the destination
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final boolean moveFile(File source, File destination) throws IOException {
    boolean success = copyFile(source, destination);

    if (success) {
      // Once the source is copied, delete it
      success = delete(source);

      if (success) {
        LOG.info("{} moved to {}.", source.getAbsolutePath(), destination.getAbsolutePath());
      } else {
        LOG.error("{} could not be deleted.", source.getAbsolutePath());
      }
    } else {
      LOG.error("{} could not be moved to {}.", source.getAbsolutePath(), destination.getAbsolutePath());
    }

    return success;
  }

  /**
   * Checks if is empty line.
   *
   * @param line the line
   * @return true, if is empty line
   */
  public static final boolean isEmptyLine(String line) {
    // return line == null || line.length() == 0 ||
    // line.startsWith(Text.TAB_DELIMITER) ||
    // line.startsWith(Text.COMMA_DELIMITER)
    // || line.startsWith(Text.NEWLINE);
    return TextUtils.isNullOrEmpty(line) || line.startsWith(TextUtils.NEW_LINE);
  }

  /**
   * Returns true if line is not null and has length > 0.
   * 
   * @param line
   * @return
   */
  public static final boolean isLine(String line) {
    return !TextUtils.isNullOrEmpty(line) && line.length() > 0;
  }

  /**
   * Adds a file extension to a file name. This method will check to ensure it
   * does not create duplicate endings such as .txt.txt, but it will allow
   * .csv.txt for example.
   *
   * @param file      the file
   * @param extension the extension
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final File addExtension(final File file, final String extension) throws IOException {
    String s = file.getCanonicalPath();

    if (!s.toLowerCase().endsWith("." + extension)) {
      s += "." + extension;
    }

    return new File(s);
  }

  /**
   * Adds the extension.
   *
   * @param file      the file
   * @param extension the extension
   * @return the path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final Path addExtension(final Path file, final String extension) throws IOException {
    return PathUtils.addExtension(file, extension);
  }

  /**
   * Replace a file extension with another.
   *
   * @param file      the file
   * @param extension the extension
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path replaceExtension(Path file, String extension) throws IOException {
    String s = PathUtils.toString(file);

    s = s.replaceFirst("\\.\\w+$", "." + extension);

    return PathUtils.getPath(s);
  }

  /**
   * Load text file.
   *
   * @param file the file
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final String loadTextFile(final Path file) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    // get the header

    StringBuilder buffer = new StringBuilder();

    try {
      while ((line = reader.readLine()) != null) {
        line = reader.readLine();
        buffer.append(line).append(TextUtils.NEW_LINE);
      }
    } finally {
      reader.close();
    }

    return buffer.toString();
  }

  /**
   * Load text file.
   *
   * @param file     the file
   * @param textArea the text area
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void loadTextFile(Path file, JTextArea textArea) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    // get the header

    textArea.setText("");

    try {
      while ((line = reader.readLine()) != null) {
        textArea.append(line);
        textArea.append(TextUtils.NEW_LINE);
      }
    } finally {
      reader.close();
    }
  }

  /**
   * Writes table model data to a text file using a delimiter.
   *
   * @param file      the file
   * @param model     the model
   * @param delimiter the delimiter
   * @return true, if successful
   */
  public static final boolean writeTableToFile(Path file, TableModel model, String delimiter) {
    try {
      BufferedWriter out = FileUtils.newBufferedWriter(file);

      try {
        for (int i = 0; i < model.getColumnCount(); ++i) {
          out.write(TextUtils.quote(model.getColumnName(i)));

          if (i < model.getColumnCount() - 1) {
            out.write(delimiter);
          }
        }

        out.newLine();

        for (int i = 0; i < model.getRowCount(); ++i) {
          for (int j = 0; j < model.getColumnCount(); ++j) {
            out.write(TextUtils.quote(model.getValueAt(i, j).toString()));

            if (j < model.getColumnCount() - 1) {
              out.write(delimiter);
            }
          }

          out.newLine();
        }
      } finally {
        out.close();
      }

      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * Write table to file.
   *
   * @param file      the file
   * @param header    the header
   * @param rows      the rows
   * @param delimiter the delimiter
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void writeTableToFile(Path file, List<String> header, List<List<String>> rows, String delimiter)
      throws IOException {

    BufferedWriter out = FileUtils.newBufferedWriter(file);

    try {
      out.write(TextUtils.join(header, delimiter));

      out.newLine();

      for (List<String> row : rows) {
        out.write(TextUtils.join(row, delimiter));

        out.newLine();
      }
    } finally {
      out.close();
    }
  }

  /**
   * Writes text directly to a file.
   *
   * @param file the file
   * @param text the text
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void write(Path file, String text) throws IOException {
    BufferedWriter out = FileUtils.newBufferedWriter(file);

    try {
      out.write(text);
      out.newLine();
    } finally {
      out.close();
    }
  }

  /**
   * Gets the file ext.
   *
   * @param file the file
   * @return the file ext
   */
  public static String getFileExt(Path file) {
    return PathUtils.getFileExt(file);
  }

  /**
   * Returns the extension of a file based on its name containing a period
   * followed by an extension.
   *
   * @param file the file
   * @return the file extension
   */
  public static String getFileExt(File file) {
    return getFileExt(file.getName());
  }

  /**
   * Returns the file extension (e.g. txt) from a file name
   *
   * @param file the file
   * @return the file extension
   */
  public static String getFileExt(String file) {
    return file.toLowerCase().substring(file.lastIndexOf(".") + 1);
  }

  /**
   * Returns the portion of the file name after the first period is encountered.
   *
   * @param file the file
   * @return the file ext long
   */
  public static String getFileExtLong(String file) {
    int i = file.indexOf(".");

    String lf = file.toLowerCase();

    if (i != -1) {
      return lf.substring(i + 1);
    } else {
      return lf;
    }
  }

  /**
   * Remove the contents of a directory but leave the directory inplace.
   *
   * @param directory the directory
   */
  public static void clearDirectory(File directory) {
    Deque<File> stack = new ArrayDeque<File>();

    for (File file : directory.listFiles()) {
      if (file.getName().contains("..")) {
        continue;
      }

      stack.push(file);
    }

    while (stack.size() > 0) {
      File file = stack.pop();

      if (file.isDirectory()) {
        if (file.listFiles().length > 0) {
          for (File f : file.listFiles()) {
            if (f.getName().contains("..")) {
              continue;
            }

            stack.push(f);
          }
        } else {
          delete(file);
        }
      } else {
        delete(file);
      }
    }
  }

  /**
   * Return a byte as an unsigned value.
   *
   * @param b the b
   * @return the int
   */
  public static int unsignedToSigned(byte b) {
    return b & 0xff;
  }

  /**
   * Convert a byte array to an unsigned byte array stored as integers.
   *
   * @param buf the buf
   * @return the int[]
   */
  public static int[] unsignedToSigned(byte[] buf) {
    int[] ret = new int[buf.length];

    for (int i = 0; i < buf.length; ++i) {
      ret[i] = unsignedToSigned(buf[i]);
    }

    return ret;
  }

  /**
   * Int to char.
   *
   * @param buf the buf
   * @return the char[]
   */
  public static char[] intToChar(int[] buf) {
    char[] ret = new char[buf.length];

    for (int i = 0; i < buf.length; ++i) {
      ret[i] = (char) buf[i];
    }

    return ret;
  }

  /**
   * Int to char.
   *
   * @param buf the buf
   * @return the char[]
   */
  public static char[] intToChar(byte[] buf) {
    char[] ret = new char[buf.length];

    for (int i = 0; i < buf.length; ++i) {
      ret[i] = (char) buf[i];
    }

    return ret;
  }

  /**
   * Returns the name of the file, minus any extension.
   *
   * @param file the file
   * @return the name
   */
  public static String getName(File file) {
    return file.getName().substring(0, file.getName().lastIndexOf("."));
  }

  /**
   * Find a file matching a pattern in a dir.
   *
   * @param dir     the dir
   * @param pattern the pattern
   * @return the file
   */
  public static File find(File dir, String pattern) {
    for (File file : dir.listFiles()) {
      if (file.getName().contains(pattern)) {
        return file;
      }
    }

    return null;
  }

  /**
   * Starts with.
   *
   * @param dir     the dir
   * @param pattern the pattern
   * @return the file
   */
  public static File startsWith(File dir, String pattern) {
    for (File file : dir.listFiles()) {
      if (file.getName().startsWith(pattern)) {
        return file;
      }
    }

    return null;
  }

  /**
   * Find all files in a directory matching a set of patterns. Each file must
   * match all of the patterns.
   *
   * @param dir      the dir
   * @param patterns the patterns
   * @return the list
   */
  public static List<File> findAll(File dir, String... patterns) {
    List<File> ret = new ArrayList<File>();

    for (File file : dir.listFiles()) {
      boolean found = true;

      for (String pattern : patterns) {
        if (!file.getName().contains(pattern)) {
          found = false;
          break;
        }
      }

      if (found) {
        ret.add(file);
      }
    }

    return ret;
  }

  /**
   * Gets the column.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @return the column
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String[] getColumn(Path file, boolean skipHeader) throws IOException {
    return getColumn(file, skipHeader, 0);
  }

  /**
   * Return a column from a tab delimited file.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @param column     the column
   * @return the column
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String[] getColumn(Path file, boolean skipHeader, int column) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    List<String> strings = new ArrayList<String>();

    String line;
    List<String> tokens;

    Splitter split = Splitter.onTab();

    try {
      if (skipHeader) {
        reader.readLine();
      }

      while ((line = reader.readLine()) != null) {
        tokens = split.text(line);

        strings.add(tokens.get(column));
      }
    } finally {
      reader.close();
    }
    
    String[] ret = new String[strings.size()]; 
    ret = strings.toArray(ret); 

    return ret;
  }

  /**
   * Returns true if file has a txt extension in its name.
   *
   * @param file the file
   * @return the checks for txt ext
   */
  public static boolean getHasTxtExt(File file) {
    return getFileExt(file).equals(FILE_EXT_TXT);
  }

  /**
   * Create a tab indented string.
   * 
   * @param s
   * @return
   * @throws IOException
   */
  public static void tabIndent(Writer writer, String s) throws IOException {
    tabIndent(writer, s, 1);
  }

  /**
   * Create a tab indented string.
   * 
   * @param s    String to indent.
   * @param tabs Number of tabs to indent by.
   * 
   * @return String s tab indented.
   * @throws IOException
   */
  public static void tabIndent(Writer writer, String s, int tabs) throws IOException {
    tabs(writer, tabs);

    writer.write(s);
  }

  public static void tabs(Writer writer, int tabs) throws IOException {
    for (int i = 0; i < tabs; ++i) {
      writer.write(TextUtils.TAB_DELIMITER);
    }
  }

  public static void join(BufferedWriter writer, String... items) throws IOException {
    join(TextUtils.TAB_DELIMITER, writer, items);
  }

  private static void join(String delimiter, BufferedWriter writer, String... items) throws IOException {
    if (ArrayUtils.isNullOrEmpty(items)) {
      return;
    }

    writer.write(items[0]);

    if (items.length > 1) {
      for (int i = 1; i < items.length; ++i) {
        writer.write(delimiter);
        writer.write(items[i]);
      }
    }
  }

}
