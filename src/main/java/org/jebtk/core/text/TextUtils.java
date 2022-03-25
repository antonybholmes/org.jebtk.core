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
package org.jebtk.core.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jebtk.core.collections.ArrayUtils;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.sys.SysUtils;

/**
 * Functions for manipulating strings.
 * 
 * @author Antony Holmes
 *
 */
public class TextUtils {

  /**
   * The constant DELIMITER_REGEX.
   */
  public static final String DELIMITER_REGEX = "\\t";

  /**
   * The constant TAB_DELIMITER.
   */
  public static final String TAB_DELIMITER = "\t";

  /**
   * The constant COMMA_DELIMITER.
   */
  public static final String COMMA_DELIMITER = ",";

  /** The Constant FORMATTED_COMMA_DELIMITER. */
  public static final String FORMATTED_COMMA_DELIMITER = ", ";

  /**
   * The constant EQUALS_DELIMITER.
   */
  public static final String EQUALS_DELIMITER = "=";

  /**
   * The constant COLON_DELIMITER.
   */
  public static final String COLON_DELIMITER = ":";

  /**
   * The constant QUOTE.
   */
  public static final String QUOTE = "\"";

  /**
   * The constant SPACE_DELIMITER.
   */
  public static final String SPACE_DELIMITER = " ";

  /**
   * The constant PERIOD_DELIMITER.
   */
  public static final String PERIOD_DELIMITER = ".";

  /**
   * The constant AMPERSAND_DELIMITER.
   */
  public static final String AMPERSAND_DELIMITER = "&";

  /**
   * The constant NULL.
   */
  public static final String NULL = "null";

  /**
   * System dependent new line character.
   */
  public static final String NEW_LINE = System.getProperty("line.separator");

  /**
   * The constant NEW_LINE_DELIMITER.
   */
  public static final String NEW_LINE_DELIMITER = "\n";

  /**
   * The constant UNIX_NEW_LINE.
   */
  public static final char UNIX_NEW_LINE = '\n';

  /**
   * The constant PERIOD.
   */
  public static final String PERIOD = ".";

  /**
   * The constant SEMI_COLON_DELIMITER.
   */
  public static final String SEMI_COLON_DELIMITER = ";";

  /**
   * The constant DASH_DELIMITER.
   */
  public static final String DASH_DELIMITER = "-";

  /**
   * The constant TAB_DELIMITER_CHAR.
   */
  public static final char TAB_DELIMITER_CHAR = '\t';

  /**
   * The constant COMMA_DELIMITER_CHAR.
   */
  public static final char COMMA_DELIMITER_CHAR = ',';

  /**
   * The constant DASH_DELIMITER_CHAR.
   */
  public static final char DASH_DELIMITER_CHAR = '-';

  /**
   * The constant COLON_DELIMITER_CHAR.
   */
  public static final char COLON_DELIMITER_CHAR = ':';

  public static final char SEMI_COLON_DELIMITER_CHAR = ';';

  /**
   * The constant SPACE_DELIMITER_CHAR.
   */
  public static final char SPACE_DELIMITER_CHAR = ' ';

  /**
   * The constant FORMATTED_LIST_DELIMITER.
   */
  public static final String FORMATTED_LIST_DELIMITER = ", ";

  /**
   * The constant PERIOD_DELIMITER_CHAR.
   */
  public static final char PERIOD_DELIMITER_CHAR = '.';

  /**
   * The constant SINGLE_QUOTE.
   */
  public static final String SINGLE_QUOTE = "'";

  /**
   * The constant EMPTY_STRING.
   */
  public static final String EMPTY_STRING = "";

  /** The Constant STRICT_INT_PATTERN. */
  public static final Pattern STRICT_INT_PATTERN = Pattern.compile("([-+]?[0-9]+([eE][-+]?[0-9]+)?)");

  public static final Pattern STRICT_DOUBLE_PATTERN = Pattern
      .compile("-?(?:0|[1-9][0-9]*)\\.?[0-9]+([e|E][+-]?[0-9]+)?");

  private static final String DIGIT_REGEX = "(\\p{Digit}+)";
  private static final String HEX_DIGITS_REGEX = "(\\p{XDigit}+)";
  private static final String HEX_REGEX = "(0[xX]" + HEX_DIGITS_REGEX + ")";
  private static final String PM_REGEX = "([+-]?)";

  private static final String EXP_REGEX = "([eE]" + PM_REGEX + DIGIT_REGEX + ")";
  private static final String NUM_REGEX = "(" + DIGIT_REGEX + "\\.?" + DIGIT_REGEX + "?" + EXP_REGEX + "?)";

  private static final String NUMBER_REGEX = "(" + PM_REGEX + "(NaN|Infinity|Inf|" + NUM_REGEX + "|" + HEX_REGEX + "))";

  /** The Constant NUMBER_PATTERN. */
  public static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);

  public static final Pattern INT_PARSE_PATTERN = Pattern.compile("(\\-|\\+)?[0-9]+");

  /** The Constant VAR_YEAR_PATTERN. */
  public static final Pattern VAR_YEAR_PATTERN = Pattern.compile("\\$\\{year\\}");

  // an exponent is 'e' or 'E' followed by an optionally
  // signed decimal integer.
  private static final String EXP = "[eE][+-]?" + DIGIT_REGEX;
  private static final String FP_REGEX = ("([+-]?(" + // Optional sign character
      "NaN|" + // "NaN" string
      "Infinity|" + // "Infinity" string

      // A decimal floating-point string representing a finite positive
      // number without a leading sign has at most five basic pieces:
      // Digits . Digits ExponentPart FloatTypeSuffix
      //
      // Since this method allows integer-only strings as input
      // in addition to strings of floating-point literals, the
      // two sub-patterns below are simplifications of the grammar
      // productions from section 3.10.2 of
      // The Java™ Language Specification.

      // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
      "(((" + DIGIT_REGEX + "(\\.)?(" + DIGIT_REGEX + "?)(" + EXP + ")?)|" +

      // . Digits ExponentPart_opt FloatTypeSuffix_opt
      "(\\.(" + DIGIT_REGEX + ")(" + EXP + ")?)|" +

      // Hexadecimal strings
      "((" +
      // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
      "(0[xX]" + HEX_DIGITS_REGEX + "(\\.)?)|" +

      // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
      "(0[xX]" + HEX_DIGITS_REGEX + "?(\\.)" + HEX_DIGITS_REGEX + ")" +

      ")[pP][+-]?" + DIGIT_REGEX + "))" + "[fFdD]?)))");

  public static final Pattern FP_PATTERN = Pattern.compile(FP_REGEX);

  public static final String SIMPLE_HEX_REGEX = "(0[xX]" + HEX_DIGITS_REGEX + ")";

  public static final Pattern SIMPLE_HEX_PATTERN = Pattern.compile(SIMPLE_HEX_REGEX);

  /**
   * The constant ELLIPSIS.
   */
  public static final String ELLIPSIS = "...";

  /**
   * The constant NEWLINE_CHAR.
   */
  public static final char NEWLINE_CHAR = '\n';

  /**
   * The constant CARRIAGE_RETURN_CHAR.
   */
  public static final char CARRIAGE_RETURN_CHAR = '\r';

  /**
   * The constant QUOTATION_CHAR.
   */
  public static final char QUOTATION_CHAR = '"';

  /** The Constant SINGLE_QUOTATION_CHAR. */
  public static final char SINGLE_QUOTATION_CHAR = '\'';

  /**
   * A standard representation of n/a to indicate absent data where null is not
   * appropriate.
   */
  public static final String NA = "n/a";

  /**
   * The constant OPEN_PARENTHESES.
   */
  private static final String OPEN_PARENTHESES = "(";

  /**
   * The constant CLOSE_PARENTHESES.
   */
  private static final String CLOSE_PARENTHESES = ")";

  /**
   * The constant UNDERSCORE_DELIMITER.
   */
  public static final String UNDERSCORE_DELIMITER = "_";

  public static final String FORMAT_DELIMITER = "{}";

  /**
   * The constant TRUE.
   */
  public static final String TRUE = "true";

  /**
   * The constant FALSE.
   */
  public static final String FALSE = "false";

  /**
   * The constant STRING_EMPTY_NULL_WARNING.
   */
  public static final String STRING_EMPTY_NULL_WARNING = "String is null or empty";

  /** The Constant DOUBLE_SPACE. */
  public static final String DOUBLE_SPACE = "  ";

  /** The Constant NAN. */
  public static final String NAN = "NaN";

  /** Lookup table for characters mapping between 0 and 25. */
  public static final char[] CAPITAL_ALPHABET_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
      'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

  /** The Constant SENTENCE_CASE_REGEX. */
  private final static Pattern SENTENCE_CASE_REGEX = Pattern.compile("(^|[\\.\\!\\?])\\s*([a-z])");

  /** The Constant TITLE_CASE_REGEX. */
  private final static Pattern TITLE_CASE_REGEX = Pattern.compile("(^|\\s)([a-z])");

  /** The Constant SPACE_RUN_REGEX. */
  private final static Pattern SPACE_RUN_REGEX = Pattern.compile("\\s+");

  public static final List<String> EMPTY_LIST = Collections.emptyList();

  /**
   * Instantiates a new text utils.
   */
  private TextUtils() {
    // Do nothing
  }

  /**
   * Returns true if the string is either null or empty. Often this is considered
   * effectively the same state, i.e. absence of information.
   * 
   * @param s A string to test.
   * @return True if the string is empty or null.
   */
  public static boolean isNullOrEmpty(final String s) {
    return s == null || s.isEmpty();
  }

  /**
   * If string s is null or empty, return alt else return s. This can be used as
   * quick replacement function for cleaning parameters.
   * 
   * @param s
   * @param alt
   * @return
   */
  public static String isNullOrEmpty(final String s, String alt) {
    return isNullOrEmpty(s) ? alt : s;
  }

  /**
   * Returns true if the string is null, empty or 'n/a'.
   * 
   * @param s
   * @return
   */
  public static boolean isNullEmptyNA(final String s) {
    return isNullOrEmpty(s) || s.toLowerCase().equals(NA);
  }

  /*
   * public static final String format4DP(double value) { DecimalFormat format =
   * new DecimalFormat(NUMBER_FORMAT_4DP);
   * 
   * return format.format(value); }
   * 
   * public static final String format2DP(double value) { DecimalFormat format =
   * new DecimalFormat(NUMBER_FORMAT_2DP);
   * 
   * return format.format(value); }
   * 
   * public static final List<String> format2DP(List<Double> values) {
   * List<String> ret = new ArrayList<String>(values.size());
   * 
   * DecimalFormat format = new DecimalFormat(NUMBER_FORMAT_2DP);
   * 
   * for (double value : values) { ret.add(format.format(value)); }
   * 
   * return ret; }
   * 
   * public static final String format1DP(double value) { DecimalFormat format =
   * new DecimalFormat(NUMBER_FORMAT_1DP);
   * 
   * return format.format(value); }
   * 
   * public static final String commaFormat(int value) { DecimalFormat format =
   * new DecimalFormat(NUMBER_FORMAT_COMMAS);
   * 
   * return format.format(value); }
   */

  /**
   * Create a string enclosed in parentheses.
   *
   * @param o An object that will be converted to a string representation.
   * @return A string enclosed in parentheses.
   */
  public static String parenthesis(Object o) {
    StringBuilder buffer = new StringBuilder();

    try {
      parenthesis(o, buffer);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return buffer.toString();
  }

  /**
   * Create a string enclosed in parentheses.
   *
   * @param o      the o
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void parenthesis(Object o, Appendable buffer) throws IOException {
    buffer.append("(").append(o.toString()).append(")");
  }

  /**
   * Square brackets.
   *
   * @param text the text
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String squareBrackets(String text) {
    StringBuilder buffer = new StringBuilder();

    try {
      squareBracket(text, buffer);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return buffer.toString();
  }

  /**
   * Square bracket.
   *
   * @param text   the text
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void squareBracket(String text, Appendable buffer) throws IOException {
    buffer.append("[").append(text).append("]");
  }

  /**
   * Quote.
   *
   * @param s the s
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void quote(String[] s) throws IOException {
    for (int i = 0; i < s.length; ++i) {
      s[i] = quote(s[i]);
    }
  }

  /**
   * Quote.
   *
   * @param s the s
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void quote(List<String> s) throws IOException {
    for (int i = 0; i < s.size(); ++i) {
      s.set(i, quote(s.get(i)));
    }
  }

  /**
   * Quote.
   *
   * @param s     the s
   * @param quote the quote
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void quote(List<String> s, String quote) throws IOException {
    for (int i = 0; i < s.size(); ++i) {
      s.set(i, quote(s.get(i), quote));
    }
  }

  /**
   * Single quote.
   *
   * @param s the s
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void singleQuote(List<String> s) throws IOException {
    for (int i = 0; i < s.size(); ++i) {
      s.set(i, singleQuote(s.get(i)));
    }
  }

  /**
   * Adds quotation marks around a string.
   *
   * @param text the text
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final String quote(String text) throws IOException {
    StringBuilder buffer = new StringBuilder();

    quote(text, buffer);

    return buffer.toString();
  }

  /**
   * Append a quoted string to an existing buffer.
   *
   * @param text   the text
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void quote(String text, Appendable buffer) throws IOException {
    quote(text, QUOTE, buffer);
  }

  /**
   * Single quote.
   *
   * @param text the text
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final String singleQuote(String text) throws IOException {
    StringBuilder buffer = new StringBuilder();

    singleQuote(text, buffer);

    return buffer.toString();
  }

  /**
   * Single quote.
   *
   * @param text   the text
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void singleQuote(String text, Appendable buffer) throws IOException {
    quote(text, SINGLE_QUOTE, buffer);
  }

  /**
   * Create a quoted string.
   *
   * @param text  the text
   * @param quote the quote
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final String quote(String text, String quote) throws IOException {
    StringBuilder buffer = new StringBuilder();

    quote(text, quote, buffer);

    return buffer.toString();
  }

  /**
   * Adds quotation marks around a string on an existing buffer.
   *
   * @param text   the text
   * @param quote  the quote
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void quote(String text, String quote, Appendable buffer) throws IOException {
    if (text.startsWith(quote)) {
      return;
    }

    buffer.append(quote).append(text).append(quote);
  }

  /**
   * Removes regex characters from a string so if the string is not misinterpreted
   * when used in a regex match.
   *
   * @param text the text
   * @return the string
   */
  public static final String removeRegexChars(String text) {
    return text.replaceAll("^F-", "").replaceAll("\\?", "").replaceAll("\\+", "").replaceAll("\\*", "X");
  }

  /**
   * Removes leading and trailing whitespace from a string (similar to Perl).
   *
   * @param text the text
   * @return the string
   */
  public static final String chomp(String text) {
    return text.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
  }

  /**
   * Split.
   *
   * @param line  the line
   * @param regex the regex
   * @return the list
   */
  public static final List<String> split(String line, String regex) {
    String cleaned = removeQuotes(line);

    String[] tokens = cleaned.split(regex);

    List<String> list = new ArrayList<>();

    for (String token : tokens) {
      list.add(removeTrailingWhitespace(token));
    }

    return list;
  }

  /**
   * First tab split.
   *
   * @param line the line
   * @return the string
   */
  public static final String firstTabSplit(String line) {
    return firstSplit(line, TAB_DELIMITER);
  }

  /**
   * First split.
   *
   * @param line      the line
   * @param delimiter the delimiter
   * @return the string
   */
  public static final String firstSplit(String line, String delimiter) {
    return line.substring(0, line.indexOf(delimiter));
  }

  /**
   * Fast split.
   *
   * @param line  the line
   * @param split the split
   * @param n     the n
   * @return the list
   */
  public static final List<String> fastSplit(String line, String split, int n) {
    List<String> list = new ArrayList<>();

    /*
     * boolean stop = false;
     * 
     * int i = 0; int c = 0;
     * 
     * while (!stop) { int j = line.indexOf(split, i);
     * 
     * if (j == -1) { break; }
     * 
     * if (c == n) { break; }
     * 
     * list.add(line.substring(i, j));
     * 
     * i = j + 1;
     * 
     * ++c; }
     * 
     * return list;
     */

    int i = 0;
    int j = line.indexOf(split); // First substring
    int c = 0;
    int l = split.length();

    while (j >= 0 && c < n - 1) {
      list.add(line.substring(i, j));
      // System.out.println("tokens:" + line.substring(i, j));
      i = j + l;
      j = line.indexOf(split, i); // Rest of substrings

      ++c;
    }

    if (i < line.length() && list.size() < n) {
      // we skipped to the last token so just add the rest of the line
      list.add(line.substring(i));
    }

    return list;
  }

  /**
   * Fast splits a line into at most n tokens.
   *
   * @param line  the line
   * @param split the split
   * @param n     the n
   * @return the list
   */
  public static final List<String> fastSplit(String line, char split, int n) {
    List<String> list = new ArrayList<>(n);

    int i = 0;
    int j = line.indexOf(split);

    int c = 0;

    while (j >= 0 && c < n - 1) {
      list.add(line.substring(i, j));
      i = j + 1;
      j = line.indexOf(split, i); // Rest of substrings

      ++c;
    }

    if (i < line.length() && list.size() < n) {
      list.add(line.substring(i));
    }

    return list;
  }

  /**
   * Fast split.
   *
   * @param line      the line
   * @param delimiter the delimiter
   * @return the list
   */
  public static final List<String> fastSplit(String line, String delimiter) {
    int n = line.length();

    List<String> list = new ArrayList<>(n);

    int i = 0;
    int j = line.indexOf(delimiter);

    int l = delimiter.length();

    while (j != -1) {
      list.add(line.substring(i, j));

      i = j + l;
      j = line.indexOf(delimiter, i);
    }

    if (i < n) {
      list.add(line.substring(i));
    }

    return list;
  }

  /**
   * Fast split.
   *
   * @param line      the line
   * @param delimiter the split
   * @return the list
   */
  public static final List<String> fastSplit(String line, char delimiter) {
    int n = line.length();

    List<String> list = new ArrayList<>(n);

    int i = 0;
    int j = line.indexOf(delimiter); // First substring

    while (j >= 0) {
      list.add(line.substring(i, j));
      // System.out.println("tokens:" + line.substring(i, j));
      i = j + 1;
      j = line.indexOf(delimiter, i); // Rest of substrings
    }

    if (i < n) {
      list.add(line.substring(i));
    }

    return list;
  }

  /**
   * Fast split.
   *
   * @param line      the line
   * @param delimiter the delimiter
   * @return the list
   */
  public static final List<String> fastSplit(String line, Pattern delimiter) {
    List<String> list = new ArrayList<>();

    Matcher matcher = delimiter.matcher(line);

    int i = 0;
    int j = 0; // First substring

    while (matcher.find()) {
      j = matcher.start();

      String s = line.substring(i, j);

      list.add(s);

      i = matcher.end() + 1;
    }

    if (i < line.length()) {
      list.add(line.substring(i));
    }

    return list;
  }

  /**
   * Fast split remove quotes.
   *
   * @param line      the line
   * @param delimiter the delimiter
   * @return the list
   */
  public static List<String> fastSplitRemoveQuotes(String line, String delimiter) {
    String cleaned = removeQuotes(line);

    return fastSplit(cleaned, delimiter);
  }

  /**
   * Fast split remove quotes.
   *
   * @param line the line
   * @return the list
   */
  public static final List<String> fastSplitRemoveQuotes(String line) {
    return fastSplitRemoveQuotes(line, TAB_DELIMITER_CHAR);
  }

  /**
   * Fast split remove quotes.
   *
   * @param line      the line
   * @param delimiter the split
   * @return the list
   */
  public static final List<String> fastSplitRemoveQuotes(String line, char delimiter) {
    String cleaned = removeQuotes(line);

    return fastSplit(cleaned, delimiter);
  }

  /**
   * Fast split remove quotes.
   *
   * @param line      the line
   * @param delimiter the split
   * @param n         the n
   * @return the list
   */
  public static final List<String> fastSplitRemoveQuotes(String line, char delimiter, int n) {
    String cleaned = removeQuotes(line);

    return fastSplit(cleaned, delimiter, n);
  }

  /**
   * Parses an integer guaranteeing not to throw an error and return -1 should an
   * error occur.
   *
   * @param field the field
   * @return the int
   */
  public static final int scanInt(String field) {
    return scanInt(field, Integer.MIN_VALUE);
  }

  /**
   * Parses an integer guaranteeing not to throw an error and return a default
   * value should an error occur.
   *
   * @param field        the field
   * @param defaultValue the default value
   * @return the int
   */
  public static final int scanInt(String field, int defaultValue) {
    int v = parseInt(field);

    return v;
  }

  /**
   * Looks for a double in a string or returns Double.MIN_VALUE if a double cannot
   * be found. By extension this method cannot be used to parse Double.MIN_VALUE,
   * but we assume that this value is unlikely to occur since it is at the extreme
   * of representation and likely means there is some kind of problem with the
   * value.
   *
   * @param field the field
   * @return the double
   */
  public static final Double scanDouble(String field) {
    return scanDouble(field, Double.MIN_VALUE);
  }

  /**
   * Parses a string for a double value, returning a default value if there is an
   * error. This method negates needing an error handler for number parsing since
   * it is guaranteed to return a double.
   *
   * @param field        the field
   * @param defaultValue the default value
   * @return the double
   */
  public static final Double scanDouble(String field, double defaultValue) {
    double v = parseDouble(field);

    return v;
  }

  /**
   * Parses an integer field as a double and then converts back to int to provide
   * more robust handling of ints written as floating point numbers in files.
   *
   * @param field the field
   * @return the int
   */
  public static final int parseInt(String field) {
    // If it's an int, parse it normally
    if (INT_PARSE_PATTERN.matcher(field).matches()) {
      return Integer.parseInt(field);
    } else {
      // Convert to double and parse it.
      return (int) parseDouble(field);
    }
  }

  /**
   * Parse a string as long. If the string appears to be a double, convert to
   * double and cast to long.
   * 
   * @param field
   * @return
   */
  public static final long parseLong(String field) {
    // If it's an int, parse it normally
    if (INT_PARSE_PATTERN.matcher(field).matches()) {
      return Long.parseLong(field);
    } else {
      // Convert to double and parse it.
      return (long) parseDouble(field);
    }
  }

  /**
   * More robust number extraction that copes with text in the field. If the field
   * appears to be a number, parse that, else look for a number substring in the
   * text and convert that to a double, failing that return NaN. This
   *
   * @param field the field
   * @return A double A double value or NaN if the field is either unparsable or
   *         NaN itself.
   */
  public static final double parseDouble(String field) {
    // Remove commas
    field = field.replace(",", EMPTY_STRING);

    Matcher matcher = NUMBER_PATTERN.matcher(field);

    // System.err.println(field + " " + NUMBER_PATTERN + " " + matcher.find());

    // Whole field is a number so just parse it
    if (matcher.matches()) {
      return Double.parseDouble(field);
    } else {
      // Attempt to find a number in the string.

      matcher.reset();

      if (matcher.find()) {
        return Double.parseDouble(matcher.group(1));
      } else {
        return Double.NaN;
      }
    }

    // return Double.parseDouble(field.replace(",", EMPTY_STRING));
  }

  /**
   * Extracts a number from a string.
   *
   * @param field the field
   * @return the string
   */
  public static final String extractNumber(String field) {
    Matcher matcher = NUMBER_PATTERN.matcher(field);

    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return null;
    }
  }

  /**
   * Returns true if the field contains an extractable number.
   *
   * @param field the field
   * @return true, if successful
   */
  public static final boolean containsNumber(String field) {
    Matcher matcher = NUMBER_PATTERN.matcher(field);

    return matcher.find();
  }

  /**
   * Remove quotes from strings.
   *
   * @param text the text
   * @return the string
   */
  public static final String removeQuotes(String text) {
    return text.replaceAll("\\\"", EMPTY_STRING).replaceAll("'", EMPTY_STRING);
  }

  /**
   * Remove quotation marks if they enclose text.
   *
   * @param text the text
   * @return the string
   */
  public static final String unquote(String text) {
    if (text.charAt(0) == '"') {
      return text.substring(1, text.length() - 1);
    } else {
      return text;
    }
  }

  /**
   * Removes the trailing whitespace.
   *
   * @param text the text
   * @return the string
   */
  public static final String removeTrailingWhitespace(String text) {
    return text.replaceAll("^\\s+", "").replaceAll("\\s+$", "");
  }

  /**
   * Convert to int.
   *
   * @param values the values
   * @return the list
   * @throws ParseException the parse exception
   */
  public static final List<Integer> convertToInt(List<String> values) throws ParseException {
    List<Integer> ret = new ArrayList<>();

    for (String value : values) {
      ret.add(parseInt(value));
    }

    return ret;
  }

  /**
   * Truncates a string to a specified length and adds ellipses to indicate it has
   * been truncated.
   *
   * @param text   the text
   * @param length the length
   * @return the string
   */
  public static final String truncate(String text, int length) {
    if (text == null) {
      return null;
    }

    if (text.length() <= length) {
      return text;
    } else {
      // exceeds length
      int l = length - 3;

      return text.substring(0, l) + ELLIPSIS;
    }
  }

  public static final String truncateCenter(String text) {
    return truncateCenter(text, 50);
  }

  /**
   * Truncates the center of a string to a specified length and placing an
   * ellipsis '...' in the middle of the string to indicate the middle portion has
   * been truncated.
   *
   * @param text   the text
   * @param length the length
   * @return the string
   */
  public static final String truncateCenter(String text, int length) {
    if (text.length() <= length) {
      return text;
    }

    int l = (length - 3) / 2;

    return text.substring(0, l) + ELLIPSIS + text.substring(text.length() - l - 1);
  }

  /**
   * Search a list for a string and return the index if it is found.
   *
   * @param list the list
   * @param text the text
   * @return the index of the item or -1 if not found.
   */
  public static int searchList(List<String> list, String text) {
    String s = text.toLowerCase();

    for (int i = 0; i < list.size(); ++i) {
      // System.out.println("match " + text + " " + list.get(i));

      if (list.get(i).toLowerCase().contains(s)) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Search list exact.
   *
   * @param list the list
   * @param text the text
   * @return the int
   */
  public static int searchListExact(List<String> list, String text) {
    for (int i = 0; i < list.size(); ++i) {
      // System.out.println("match " + text + " " + list.get(i));

      if (list.get(i).equals(text)) {
        return i;
      }
    }

    return -1;
  }

  /**
   * Guesses a delimiter for a string.
   *
   * @param line the line
   * @return the delimiter
   */
  public static String getDelimiter(String line) {
    if (line.indexOf(',') != -1) {
      return COMMA_DELIMITER;
    } else {
      return TAB_DELIMITER;
    }
  }

  /**
   * Repeat a string multiple times.
   *
   * @param text    the text
   * @param repeats the repeats
   * @return the string
   */
  public static String repeat(String text, int repeats) {
    return repeat(text, EMPTY_STRING, repeats);
  }

  /**
   * Repeat a value with a given delimiter inbetween values.
   *
   * @param text      the text
   * @param delimiter the delimiter
   * @param repeats   the repeats
   * @return the string
   */
  public static String repeat(Object text, String delimiter, int repeats) {
    List<String> values = new ArrayList<>();

    for (int i = 0; i < repeats; ++i) {
      values.add(text.toString());
    }

    return join(values, delimiter);
  }

  /**
   * Creates the blank row.
   *
   * @param headings the headings
   * @return the list
   */
  public static List<String> createBlankRow(List<String> headings) {
    return createBlankRow(headings.size());
  }

  /**
   * Creates the blank row.
   *
   * @param size the size
   * @return the list
   */
  public static List<String> createBlankRow(int size) {
    List<String> row = new ArrayList<>();

    for (int i = 0; i < size; ++i) {
      row.add(EMPTY_STRING);
    }

    return row;
  }

  /**
   * Join a collection of objects as strings using a delimiter.
   *
   * @param delimiter the delimiter
   * @param values    the values
   * @return the string
   */
  public static final String join(String delimiter, Object... values) {
    StringBuilder builder = new StringBuilder(values[0].toString());

    for (int i = 1; i < values.length; ++i) {
      builder.append(delimiter).append(values[i]);
    }

    return builder.toString();
  }

  /**
   * Join.
   *
   * @param delimiter the delimiter
   * @param values    the values
   * @return the string
   */
  public static final String join(String delimiter, String... values) {
    StringBuilder builder = new StringBuilder(values[0]);

    for (int i = 1; i < values.length; ++i) {
      builder.append(delimiter).append(values[i]);
    }

    return builder.toString();
  }

  /**
   * Concatenates a list of items into a single string where each item is
   * separated by a delimiter.
   *
   * @param list      the list
   * @param delimiter the delimiter
   * @return the string
   */
  public static final String join(Collection<?> list, String delimiter) {
    StringBuilder buffer = new StringBuilder();

    join(list, delimiter, buffer);

    return buffer.toString();
  }

  /**
   * Concatenates a list of items into a single string where each item is
   * separated by a delimiter.
   * 
   * @param list
   * @param delimiter
   * @param buffer
   */
  public static final void join(Collection<?> list, String delimiter, Appendable buffer) {
    if (CollectionUtils.isNullOrEmpty(list)) {
      return;
    }

    Iterator<?> iter = list.iterator();

    try {
      buffer.append(toString(iter.next()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    while (iter.hasNext()) {
      try {
        buffer.append(delimiter).append(toString(iter.next()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * To string.
   *
   * @param o the o
   * @return the string
   */
  public static String toString(Object o) {
    if (o != null) {
      return o.toString();
    } else {
      return EMPTY_STRING;
    }
  }

  /**
   * Concatenates a set of items into a single string where each item is separated
   * by a delimiter.
   *
   * @param <T>       the generic type
   * @param s         the s
   * @param delimiter the delimiter
   * @return the string
   */
  public static final <T> String join(Set<?> s, String delimiter) {
    if (CollectionUtils.isNullOrEmpty(s)) {
      return EMPTY_STRING;
    }

    Iterator<?> iter = s.iterator();
    StringBuilder builder = new StringBuilder(iter.next().toString());

    while (iter.hasNext()) {
      builder.append(delimiter).append(iter.next());
    }

    return builder.toString();
  }

  /**
   * Concatenates a list of items into a single string where each item is
   * separated by a delimiter.
   *
   * @param <T>       the generic type
   * @param s         the s
   * @param delimiter the delimiter
   * @return the string
   */
  public static final <T> String join(T[] s, String delimiter) {
    if (CollectionUtils.isNullOrEmpty(s)) {
      return EMPTY_STRING;
    }

    StringBuilder builder = new StringBuilder(s[0].toString());

    for (int i = 1; i < s.length; ++i) {
      builder.append(delimiter).append(s[i]);
    }

    return builder.toString();
  }

  /**
   * Return a list of items as a comma separated list adding a final and between
   * the second to last and last item (also including an oxford comma).
   *
   * @param list the list
   * @return the string
   */
  public static String listAsSentence(List<?> list) {
    if (list.isEmpty()) {
      return "";
    }

    if (list.size() == 1) {
      return list.get(0).toString();
    }

    String text = join(CollectionUtils.subList(list, 0, list.size() - 1), TextUtils.COMMA_DELIMITER);

    text += ", and " + list.get(list.size() - 1).toString();

    return text;
  }

  /**
   * Repeat a delimited string of empty values. Useful for creating blank
   * rows/columns in a table output.
   *
   * @param size the size
   * @return the string
   */
  public static String emptyCells(int size) {
    return emptyCells(size, TAB_DELIMITER);
  }

  /**
   * Empty cells.
   *
   * @param size      the size
   * @param delimiter the delimiter
   * @return the string
   */
  public static String emptyCells(int size, String delimiter) {
    StringBuilder buffer = new StringBuilder();

    for (int i = 0; i < size - 1; ++i) {
      buffer.append(delimiter);
    }

    return buffer.toString();
  }

  /**
   * Attempts to split words based on them being separated by spaces, hyphens and
   * other characters considered to indicate a word boundary. Word break
   * characters are removed.
   *
   * @param sentence the sentence
   * @return the sets the
   */
  public static Set<String> keywords(String sentence) {
    // Replace characters indicating a word break with a space and then
    // replace all multiple spaces with a single space so we have a
    // cleaned up sentence we can split into words
    String s = sentence.replaceAll("[\\;\\,\\.\\'\\:\\\"\\?\\(\\)\\[\\]\\{\\}\\_\\+\\-\\=]", TextUtils.SPACE_DELIMITER)
        .replaceAll("\\s+", TextUtils.SPACE_DELIMITER).trim();

    return CollectionUtils.unique(TextUtils.fastSplit(s, TextUtils.SPACE_DELIMITER));
  }

  /**
   * Generates a lookup map for finding strings in a list.
   *
   * @param <T>  the generic type
   * @param list the list
   * @return the map
   */
  public static <T> Map<T, Integer> valueIndexMap(List<T> list) {
    Map<T, Integer> map = new HashMap<>();

    for (int i = 0; i < list.size(); ++i) {
      map.put(list.get(i), i);
    }

    return map;
  }

  /**
   * Returns true if s is within a list of string, false otherwise.
   *
   * @param list the list
   * @param s    the s
   * @return true, if successful
   */
  public static boolean in(List<String> list, String s) {
    if (CollectionUtils.isNullOrEmpty(list) || isNullOrEmpty(s)) {
      return false;
    }

    String su = s.toLowerCase();

    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i).toLowerCase().contains(su)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns true if one of the strings in a list starts with s, false otherwise.
   *
   * @param list the list
   * @param s    the s
   * @return the list
   */
  public static List<Integer> startsWith(List<String> list, String s) {
    if (CollectionUtils.isNullOrEmpty(list) || isNullOrEmpty(s)) {
      return Collections.emptyList();
    }

    List<Integer> indices = new ArrayList<>();

    String su = s.toLowerCase();

    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i).toLowerCase().startsWith(su)) {
        indices.add(i);
      }
    }

    return indices;
  }

  /**
   * Find.
   *
   * @param list the list
   * @param s    the s
   * @return the list
   */
  public static List<Integer> find(String[] list, String s) {
    return find(Arrays.asList(list), s);
  }

  /**
   * Returns the first index of a string containing s or -1 if s is not found.
   *
   * @param list the list
   * @param s    the s
   * @return the int
   */
  public static int findFirst(List<String> list, String... s) {
    String[] ls = toLowerCase(s);

    for (int i = 0; i < list.size(); ++i) {
      for (String l : ls) {
        if (list.get(i).toLowerCase().contains(l)) {
          return i;
        }
      }
    }

    return -1;
  }

  public static int findFirst(String[] list, String... s) {
    String[] ls = toLowerCase(s);

    for (int i = 0; i < list.length; ++i) {
      for (String l : ls) {
        if (list[i].toLowerCase().contains(l)) {
          return i;
        }
      }
    }

    return -1;
  }

  public static int findFirst(String[] list, Collection<String> s) {
    List<String> ls = toLowerCase(s);

    for (int i = 0; i < list.length; ++i) {
      for (String l : ls) {
        if (list[i].toLowerCase().contains(l)) {
          return i;
        }
      }
    }

    return -1;
  }

  /**
   * Find first.
   *
   * @param list the list
   * @param s    the s
   * @return the int
   */
  public static int findFirst(List<String> list, Collection<String> s) {
    List<String> ls = toLowerCase(s);

    for (int i = 0; i < list.size(); ++i) {
      for (String l : ls) {
        if (list.get(i).toLowerCase().contains(l)) {
          return i;
        }
      }
    }

    return -1;
  }

  /**
   * Returns the indices of items in a list that match a string.
   *
   * @param list the list
   * @param s    the s
   * @return the list
   */
  public static List<Integer> find(List<String> list, String s) {
    if (CollectionUtils.isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    List<Integer> indices = new ArrayList<>();

    for (int i = 0; i < list.size(); ++i) {
      if (isNullOrEmpty(s) || list.get(i).toLowerCase().contains(s)) {
        indices.add(i);
      }
    }

    return indices;
  }

  /**
   * Returns the indices of strings in a list of strings that match a regex.
   *
   * @param list  the list
   * @param regex the regex
   * @return the list
   */
  public static List<Integer> findByRegex(List<String> list, String regex) {
    return find(list, Pattern.compile(regex));
  }

  /**
   * Returns the indices of strings in a list that match a regex.
   *
   * @param list  the list
   * @param regex the regex
   * @return the list
   */
  public static List<Integer> find(List<String> list, Pattern regex) {
    if (CollectionUtils.isNullOrEmpty(list) || regex == null) {
      return Collections.emptyList();
    }

    List<Integer> indices = new ArrayList<>(list.size());

    for (int i = 0; i < list.size(); ++i) {
      Matcher matcher = regex.matcher(list.get(i));

      boolean found = matcher.find();

      if (found) {
        indices.add(i);
      }
    }

    return indices;
  }

  public static List<Integer> find(String[] list, Pattern regex) {
    if (CollectionUtils.isNullOrEmpty(list) || regex == null) {
      return Collections.emptyList();
    }

    List<Integer> indices = new ArrayList<Integer>(list.length);

    for (int i = 0; i < list.length; ++i) {
      if (find(list[i], regex)) {
        indices.add(i);
      }
    }

    return indices;
  }

  public static boolean find(String s, Pattern regex) {
    return regex.matcher(s).find();
  }

  /**
   * Match.
   *
   * @param text  the text
   * @param regex the regex
   * @return true, if successful
   */
  public static boolean match(String text, String regex) {
    return match(text, Pattern.compile(regex));
  }

  /**
   * Match.
   *
   * @param text  the text
   * @param regex the regex
   * @return true, if successful
   */
  public static boolean match(String text, Pattern regex) {
    Matcher matcher = regex.matcher(text);

    return matcher.matches();
  }

  /**
   * Returns a formatted version of an array.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the string
   */
  public static <T> String toString(List<T> items) {
    return OPEN_PARENTHESES + join(items, FORMATTED_LIST_DELIMITER) + CLOSE_PARENTHESES;
  }

  /**
   * Convert lines into a string with newlines to preserve formatting.
   *
   * @param lines the lines
   * @return the string
   */
  public static String linesToText(List<String> lines) {
    if (CollectionUtils.isNullOrEmpty(lines)) {
      return EMPTY_STRING;
    }

    StringBuilder buffer = new StringBuilder();

    for (String line : lines) {
      buffer.append(line).append(NEW_LINE);
    }

    return buffer.toString();
  }

  /**
   * Splits a string into a list of strings of equal length.
   *
   * @param text the text
   * @param size the size
   * @return the list
   */
  public static List<String> breakApart(String text, int size) {
    if (isNullOrEmpty(text) || size < 1) {
      return Collections.emptyList();
    }

    int l = text.length();

    List<String> ret = new ArrayList<>((l + size - 1) / size);

    for (int start = 0; start < l; start += size) {
      ret.add(text.substring(start, Math.min(l, start + size)));
    }

    return ret;
  }

  /**
   * Returns the max length of a list of strings.
   *
   * @param items the items
   * @return the int
   */
  public static int maxLength(Collection<String> items) {
    if (CollectionUtils.isNullOrEmpty(items)) {
      return -1;
    }

    int max = -1;

    for (String s : items) {
      max = Math.max(max, s.length());
    }

    return max;
  }

  public static int maxLength(String... items) {
    if (CollectionUtils.isNullOrEmpty(items)) {
      return -1;
    }

    int max = -1;

    for (String s : items) {
      max = Math.max(max, s.length());
    }

    return max;
  }

  /**
   * Returns the string with the max length.
   *
   * @param items the items
   * @return the string
   */
  public static String maxString(Collection<String> items) {
    if (CollectionUtils.isNullOrEmpty(items)) {
      return null;
    }

    String ret = EMPTY_STRING;

    for (String s : items) {
      if (s.length() > ret.length()) {
        ret = s;
      }
    }

    return ret;
  }

  /**
   * Returns a list of elements with empty tokens removed.
   *
   * @param iter the iter
   * @return the list
   */
  public static List<String> removeEmptyElements(Iterable<String> iter) {
    if (iter == null) {
      return Collections.emptyList();
    }

    List<String> ret = new ArrayList<>();

    for (String item : iter) {
      if (item == null || item.equals(TextUtils.EMPTY_STRING)) {
        continue;
      }

      ret.add(item);
    }

    return ret;
  }

  /**
   * Removes the na.
   *
   * @param iter the iter
   * @return the list
   */
  public static List<String> removeNA(Iterable<String> iter) {
    if (iter == null) {
      return Collections.emptyList();
    }

    List<String> ret = new ArrayList<>();

    for (String item : iter) {
      if (item != null && !caseInsensitiveMatch(item, NA)) {
        ret.add(item);
      }
    }

    return ret;
  }

  /**
   * Case insensitive match.
   *
   * @param s1 the s1
   * @param s2 the s2
   * @return true, if successful
   */
  public static boolean caseInsensitiveMatch(String s1, String s2) {
    if (isNullOrEmpty(s1) || isNullOrEmpty(s2)) {
      return false;
    } else {
      return s1.toLowerCase().equals(s2.toLowerCase());
    }
  }

  /**
   * Parses an array as ints.
   *
   * @param text      the text
   * @param delimiter the delimiter
   * @return the list
   */
  public static List<Integer> splitInts(String text, String delimiter) {
    if (isNullOrEmpty(text) || isNullOrEmpty(delimiter)) {
      return Collections.emptyList();
    }

    List<Integer> ret = new ArrayList<>();

    List<String> tokens = fastSplit(text, delimiter);

    for (String t : tokens) {
      ret.add(Integer.parseInt(t));
    }

    return ret;
  }

  /**
   * Split line on tabs. Equivalent to fastSplit(line, TextUtils.TAB_DELIMITER).
   *
   * @param line the line
   * @return the list
   */
  public static List<String> tabSplit(String line) {
    return fastSplit(line, TAB_DELIMITER);
  }

  /**
   * New line split.
   *
   * @param text the text
   * @return the list
   */
  public static List<String> newLineSplit(String text) {
    return fastSplit(text, NEW_LINE);
  }

  /**
   * Join strings with a tab separator.
   *
   * @param tokens the tokens
   * @return the string
   */
  public static String tabJoin(List<String> tokens) {
    return join(tokens, TAB_DELIMITER);
  }

  public static String tabJoin(String... tokens) {
    return join(tokens, TAB_DELIMITER);
  }

  /**
   * Join on semi-colon.
   *
   * @param <T>    the generic type
   * @param tokens the tokens
   * @return the string
   */
  public static <T> String scJoin(List<T> tokens) {
    return join(tokens, SEMI_COLON_DELIMITER);
  }

  /**
   * Semi colon split
   *
   * @param line the line
   * @return the list
   */
  public static List<String> scSplit(String line) {
    return fastSplit(line, SEMI_COLON_DELIMITER);
  }

  /**
   * Period split.
   *
   * @param line the line
   * @return the list
   */
  public static List<String> periodSplit(String line) {
    return fastSplit(line, PERIOD_DELIMITER);
  }

  /**
   * Comma join.
   *
   * @param <T>    the generic type
   * @param tokens the tokens
   * @return the string
   */
  public static <T> String commaJoin(List<T> tokens) {
    return join(tokens, COMMA_DELIMITER);
  }

  /**
   * Returns a comma separated list putting a space after each comma so it is more
   * human readable.
   *
   * @param list the list
   * @return the string
   */
  public static String formattedList(List<String> list) {
    if (CollectionUtils.isNullOrEmpty(list)) {
      return EMPTY_STRING;
    }

    StringBuilder builder = new StringBuilder(toString(list.get(0)));

    for (int i = 1; i < list.size() - 1; ++i) {
      builder.append(FORMATTED_LIST_DELIMITER).append(toString(list.get(i)));
    }

    if (list.size() > 1) {
      builder.append(", and ").append(toString(list.get(list.size() - 1)));
    }

    return builder.toString();
  }

  /**
   * Convert a string list of ints to a list of ints.
   *
   * @param items the items
   * @return the list
   */
  public static List<Integer> toInt(List<String> items) {
    if (CollectionUtils.isNullOrEmpty(items)) {
      return Collections.emptyList();
    }

    List<Integer> ret = new ArrayList<>(items.size());

    for (String item : items) {
      ret.add(Integer.parseInt(item));
    }

    return ret;
  }

  /**
   * Return the reverse of a string.
   *
   * @param text the text
   * @return the string
   */
  public static String reverse(String text) {
    return new StringBuilder(text).reverse().toString();
  }

  /**
   * Comma split.
   *
   * @param line the line
   * @return the list
   */
  public static List<String> commaSplit(String line) {
    if (isNullOrEmpty(line)) {
      return Collections.emptyList();
    }

    return fastSplit(line, COMMA_DELIMITER);
  }

  /**
   * Returns the tail substring of a string.
   *
   * @param text the text
   * @param n    the n
   * @return the string
   */
  public static String tail(String text, int n) {
    return text.substring(text.length() - n, text.length());
  }

  /**
   * Replaces spaces with underscores.
   *
   * @param text the text
   * @return the string
   */
  public static String replaceSpaces(String text) {
    return text.replaceAll("\\s+", UNDERSCORE_DELIMITER);
  }

  /**
   * Paste.
   *
   * @param objects the objects
   * @return the string
   */
  public static String cat(Object... objects) {
    StringBuilder buffer = new StringBuilder();

    for (Object o : objects) {
      buffer.append(o.toString());
    }

    return buffer.toString();
  }

  /**
   * Paste a number of strings together. Shorthand method for using a
   * StringBuilder and to save writing long concatenations involving '+'.
   *
   * @param strings an array of strings.
   * @return The strings concatenated together.
   */
  public static String cat(String... strings) {
    StringBuilder buffer = new StringBuilder();

    for (String s : strings) {
      buffer.append(s);
    }

    return buffer.toString();
  }

  /**
   * Convert a list of string regexes to a list of compiled regexes.
   *
   * @param regexes the regexes
   * @return the list
   */
  public static List<Pattern> compile(List<String> regexes) {
    return compile(regexes, false);
  }

  /**
   * Compile.
   *
   * @param regexes         the regexes
   * @param caseInsensitive the case insensitive
   * @return the list
   */
  public static List<Pattern> compile(List<String> regexes, boolean caseInsensitive) {
    if (CollectionUtils.isNullOrEmpty(regexes)) {
      return Collections.emptyList();
    }

    List<Pattern> ret = new ArrayList<>(regexes.size());

    for (String regex : regexes) {
      if (caseInsensitive) {
        ret.add(Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
      } else {
        ret.add(Pattern.compile(regex));
      }
    }

    return ret;
  }

  /**
   * To lower case.
   *
   * @param items the items
   * @return the list
   */
  public static List<String> toLowerCase(Collection<String> items) {
    if (CollectionUtils.isNullOrEmpty(items)) {
      return Collections.emptyList();
    }

    List<String> ret = new ArrayList<>(items.size());

    for (String id : items) {
      ret.add(id.toLowerCase());
    }

    return ret;
  }

  /**
   * Converts a set of strings to a set of lower case string.
   *
   * @param items the items
   * @return the sets the
   */
  public static Set<String> toLowerCase(Set<String> items) {
    if (CollectionUtils.isNullOrEmpty(items)) {
      return Collections.emptySet();
    }

    Set<String> ret = new HashSet<>(items.size());

    for (String id : items) {
      ret.add(id.toLowerCase());
    }

    return ret;
  }

  /**
   * To lower case.
   *
   * @param items the items
   * @return the list
   */
  public static String[] toLowerCase(String[] items) {
    if (CollectionUtils.isNullOrEmpty(items)) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    String[] ret = new String[items.length];

    for (int i = 0; i < items.length; ++i) {
      ret[i] = items[i].toLowerCase();
    }

    return ret;
  }

  /**
   * Returns true if text appears to be a number.
   *
   * @param value the value
   * @return true, if is number
   */
  public static boolean isNumber(String value) {
    if (value == null) {
      return false;
    }

    return NUMBER_PATTERN.matcher(value).matches();
  }

  public static boolean areNumbers(Collection<String> values) {
    if (CollectionUtils.isNullOrEmpty(values)) {
      return false;
    }

    return RegexUtils.matches(TextUtils.NUMBER_PATTERN, values);
  }

  /**
   * Returns true if text appears to be an integer.
   *
   * @param value the value
   * @return true, if is number
   */
  public static boolean isInt(String value) {
    if (value == null) {
      return false;
    }

    Matcher matcher = STRICT_INT_PATTERN.matcher(value);

    return matcher.matches();
  }

  public static boolean isDouble(String value) {
    if (value == null) {
      return false;
    }

    Matcher matcher = STRICT_DOUBLE_PATTERN.matcher(value);

    return matcher.matches();
  }

  /**
   * Tail tab split.
   *
   * @param line the line
   * @return the string
   */
  public static String tailTabSplit(String line) {
    return tailSplit(line, TAB_DELIMITER);
  }

  /**
   * Tail split.
   *
   * @param line      the line
   * @param delimiter the delimiter
   * @return the string
   */
  public static String tailSplit(String line, String delimiter) {
    return line.substring(line.indexOf(delimiter) + 1);
  }

  /**
   * Parse a string as boolean. True if lower case value matches "true", "t",
   * "yes", "y", or "1".
   *
   * @param value the value
   * @return true, if successful
   */
  public static boolean parseBool(String value) {
    String l = value.toLowerCase();

    return l.equals("true") || l.equals("t") || l.equals("yes") || l.equals("y") || l.equals("1");
  }

  /**
   * Returns the common prefix of multiple strings.
   *
   * @param values the values
   * @return the string
   */
  public static String commonPrefix(String... values) {
    int n = minLength(values);

    StringBuilder buffer = new StringBuilder();

    for (int i = 0; i < n; ++i) {
      boolean match = true;

      // Do the letter match?

      char c = values[0].charAt(i);

      for (int j = 1; j < values.length; ++j) {
        if (values[j].charAt(i) != c) {
          match = false;
          break;
        }
      }

      if (!match) {
        break;
      }

      buffer.append(values[0].charAt(i));
    }

    return buffer.toString();
  }

  /**
   * Min length.
   *
   * @param values the values
   * @return the int
   */
  public static int minLength(String... values) {
    int n = Integer.MAX_VALUE;

    for (String v : values) {
      n = Math.min(n, v.length());
    }

    return n;
  }

  /**
   * Removes ,="..." style quotes and separator used for some CSV files to force
   * Excel to render cells as text rather than guessing. This makes parsing text
   * files more difficult
   *
   * @param line the line
   * @return the string
   */
  public static String removeExcelQuotes(String line) {
    return removeQuotes(line.replaceAll(",=\"", COMMA_DELIMITER));
  }

  /**
   * Parses a CSV line taking into account quotations so that data in quotations
   * containing a comma is not erroneously separated.
   *
   * @param line the line
   * @return the list
   */
  public static List<String> parseCSVLine(final String line) {
    int n = line.length();

    List<String> ret = new ArrayList<>(n);

    boolean quoteMode = false;

    StringBuilder buffer = new StringBuilder(n);

    char[] chars = line.toCharArray();

    for (char c : chars) {
      if (c == '"') {
        quoteMode = !quoteMode;
      } else if (quoteMode) {
        buffer.append(c);
      } else if (c == ',') {
        ret.add(buffer.toString());
        buffer.setLength(0);
      } else if (c == '=') {
        // Ignore
      } else {
        buffer.append(c);
      }
    }

    if (buffer.length() > 0) {
      ret.add(buffer.toString());
    }

    return ret;
  }

  /**
   * The Class Parse.
   */
  public static class Parse {

    /**
     * To int.
     *
     * @param text the text
     * @return the int
     * @throws ParseException the parse exception
     */
    public int toInt(final String text) throws ParseException {
      return parseInt(text);
    }

    /**
     * To double.
     *
     * @param text the text
     * @return the double
     * @throws ParseException the parse exception
     */
    public double toDouble(final String text) throws ParseException {
      return parseDouble(text);
    }
  }

  /**
   * Parses the.
   *
   * @return the parses the
   */
  public static Parse parse() {
    return new Parse();
  }

  /**
   * Extracts text from between double quotation marks.
   *
   * @param text the text
   * @return the string
   */
  public static String extractFromQuotes(String text) {
    int s = text.indexOf('"');

    if (s == -1) {
      return text;
    }

    int e = text.indexOf('"', s + 1);

    if (e == -1) {
      return null;
    }

    return text.substring(s + 1, e);
  }

  /**
   * Non empty.
   *
   * @param values the values
   * @return the list
   */
  public static List<String> nonEmpty(Collection<String> values) {
    List<String> ret = new ArrayList<>(values.size());

    for (String value : values) {
      if (!isNullOrEmpty(value)) {
        ret.add(value);
      }
    }

    return ret;
  }

  /**
   * Converts a string to an array of single character strings.
   *
   * @param s the s
   * @return the string[]
   */
  public static String[] toStringArray(String s) {
    String[] ret = new String[s.length()];

    for (int i = 0; i < s.length(); ++i) {
      ret[i] = Character.toString(s.charAt(i));
    }

    return ret;
  }

  /**
   * Loads the first.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String[] firstColAsList(Path file, boolean skipHeader) throws IOException {
    return colAsList(file, skipHeader, 0);
  }

  /**
   * Parses a matrix file and extracts a column as a list.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @param col        the col
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String[] colAsList(Path file, boolean skipHeader, int col) throws IOException {
    // LOG.info("Load list from {}, {}...", file, skipHeader);

    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    List<String> rows = new ArrayList<>();

    Splitter splitter = Splitter.onTab();

    try {

      if (skipHeader) {
        reader.readLine();
      }

      while ((line = reader.readLine()) != null) {
        rows.add(splitter.text(line).get(col));
      }
    } finally {
      reader.close();
    }

    return rows.toArray(new String[0]);
  }

  /**
   * Formats a number as it would appear in a written order such as 1 becomes 1st,
   * 2 becomes 2nd, etc.
   *
   * @param order the order
   * @return the string
   */
  public static String formatOrder(int order) {
    switch (order) {
    case 1:
      return "1st";
    case 2:
      return "2nd";
    case 3:
      return "3rd";
    default:
      return Integer.toString(order) + "th";
    }
  }

  /**
   * Append a string to each string in a list of strings.
   *
   * @param tokens the tokens
   * @param append the append
   * @return the list
   */
  public static List<String> append(List<String> tokens, String append) {
    return append(tokens, append, 1);
  }

  /**
   * Append a string to each string in a list of strings, optionally skipping
   * elements.
   *
   * @param tokens A list of strings.
   * @param append The text to append to each element.
   * @param step   How many elements to skip, for example 2 will append
   *               {@code append} to every second element.
   * @return the list
   */
  public static List<String> append(List<String> tokens, String append, int step) {
    int n = tokens.size();

    List<String> ret = new ArrayList<>(n);

    for (int i = 0; i < n; ++i) {
      if (i % step == 0) {
        ret.add(tokens.get(i) + append);
      } else {
        ret.add(tokens.get(i));
      }
    }

    return ret;
  }

  /**
   * Replaces special strings within a string with predefined values.
   * 
   * ${year} - The current year.
   *
   * @param s the s
   * @return the string
   */
  public static String replaceVariables(String s) {
    if (isNullOrEmpty(s)) {
      return EMPTY_STRING;
    }

    if (VAR_YEAR_PATTERN.matcher(s).find()) {
      s = VAR_YEAR_PATTERN.matcher(s).replaceAll(DateUtils.year());
    }

    return s;
  }

  /**
   * Sentence case.
   *
   * @param o the o
   * @return the string
   */
  public static String sentenceCase(final Object o) {
    return sentenceCase(o.toString());
  }

  /**
   * Convert a string to sentence case.
   *
   * @param s the s
   * @return the string
   */
  public static String sentenceCase(final String s) {
    StringBuilder buffer = new StringBuilder(s.toLowerCase());
    Matcher matcher = SENTENCE_CASE_REGEX.matcher(buffer);

    // We replace the last character of the match (which is the start of
    // a word) with its upper case equivalent.
    while (matcher.find()) {
      buffer.replace(matcher.end() - 1, matcher.end(), matcher.group(2).toUpperCase());
    }

    return buffer.toString();
  }

  /**
   * Capitalize the first letter of each word.
   *
   * @param s the s
   * @return the string
   */
  public static String titleCase(final String s) {
    StringBuilder buffer = new StringBuilder(s.toLowerCase());
    Matcher matcher = TITLE_CASE_REGEX.matcher(buffer);

    // We replace the last character of the match (which is the start of
    // a word) with its upper case equivalent.
    while (matcher.find()) {
      buffer.replace(matcher.end() - 1, matcher.end(), matcher.group(2).toUpperCase());
    }

    return buffer.toString();
  }

  /**
   * Replace runs of spaces with a single space.
   *
   * @param text Text to fix.
   * @return the string
   */
  public static String fixSpaceRuns(String text) {
    return SPACE_RUN_REGEX.matcher(text).replaceAll(TextUtils.SPACE_DELIMITER);
  }

  /**
   * Replace all substrings in a buffer.
   * 
   * @param text        The text to find.
   * @param replacement The replacement text.
   * @param buffer      The buffer to scan.
   */
  public static void replace(final String text, final String replacement, StringBuilder buffer) {
    int index = buffer.indexOf(text);

    int n = text.length();
    int repn = replacement.length();

    while (index != -1) {
      buffer.replace(index, index + n, replacement);
      // Move to the next position past the end of the replacement.
      index = buffer.indexOf(text, index + repn);
    }
  }

  /**
   * Returns true if line starts with any of the matches.
   *
   * @param line    the line
   * @param matches the matches
   * @return true, if successful
   */
  public static boolean startsWith(String line, Collection<String> matches) {

    for (String match : matches) {
      if (line.startsWith(match)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns an empty string list.
   *
   * @return the list
   */
  public static List<String> emptyList() {
    return Collections.emptyList();
  }

  /**
   * Take a collection and create of a map of each string to its lowercase
   * equivalent.
   * 
   * @param values
   * @return
   */
  public static Map<String, String> toLowerCaseMap(final Collection<String> values) {
    Map<String, String> ret = new HashMap<>();

    for (String v : values) {
      ret.put(v, v.toLowerCase());
    }

    return ret;
  }

  /**
   * Ensures that a string is either has a value or is the empty string.
   * 
   * @param ret
   * @return
   */
  public static String nonNull(String ret) {
    return ret != null ? ret : EMPTY_STRING;
  }

  /**
   * Find the first whitespace character in a string and returns the substring
   * before it.
   * 
   * @param location
   * @return
   */
  public static String firstWhiteSpaceToken(String location) {
    int p = location.indexOf(' ');

    if (p != -1) {
      return location.substring(0, p);
    }

    p = location.indexOf('\t');

    if (p != -1) {
      return location.substring(0, p);
    }

    return location;
  }

  public static String prefix(String text, String prefix) {
    return prefix + text;
  }

  /**
   * Prefix a value onto each of a collection of strings.
   * 
   * @param values
   * @param prefix
   * @return
   */
  public static List<String> prefix(final Collection<String> values, String prefix) {
    List<String> ret = new ArrayList<>(values.size());

    for (String s : values) {
      ret.add(prefix(s, prefix));
    }

    return ret;
  }

  /**
   * Returns the first n characters of a string.
   * 
   * @param name
   * @param n
   * @return
   */
  public static String head(String name, int n) {
    if (name == null) {
      return TextUtils.EMPTY_STRING;
    }

    if (name.length() > n) {
      return name.substring(0, n);
    } else {
      return name;
    }
  }

  /**
   * Case insensitive search of some text for a selection of keywords. Returns
   * true if any one is found.
   * 
   * @param text
   * @param search
   * @param searches
   * @return
   */
  public static boolean find(String text, String search, String... searches) {
    String lt = text.toLowerCase();

    if (lt.contains(search.toLowerCase())) {
      return true;
    }

    for (String s : searches) {
      if (lt.contains(s.toLowerCase())) {
        return true;
      }
    }

    return false;
  }

  /**
   * Formats a string replacing '{}' with variables.This method uses
 concatenation to provide a faster format method than
  <code>String.format()</code>.
   * 
   * @param line
   * @param vars
   * @return
   */
  public static String format(String line, Object... vars) {
    if (vars.length == 0) {
      return line;
    }

    int n = line.length();

    int i = 0;
    int j = line.indexOf(FORMAT_DELIMITER);

    int c = 0;

    StringBuilder buffer = new StringBuilder();

    while (j != -1) {
      buffer.append(line.substring(i, j)).append(vars[c++]);

      // Skip to next token
      i = j + 2;
      j = line.indexOf(FORMAT_DELIMITER, i);
    }

    // Add whatever is remaining
    if (i < n) {
      buffer.append(line.substring(i));
    }

    return buffer.toString();
  }

  /**
   * Create a tab indented string.
   * 
   * @param s
   * @return
   */
  public static String tabIndent(String s) {
    return tabIndent(s, 1);
  }

  /**
   * Create a tab indented string.
   * 
   * @param s    String to indent.
   * @param tabs Number of tabs to indent by.
   * 
   * @return String s tab indented.
   */
  public static String tabIndent(String s, int tabs) {
    StringBuilder buffer = new StringBuilder();

    tabs(tabs, buffer);

    buffer.append(s);

    return buffer.toString();
  }

  public static void tabs(int tabs, StringBuilder buffer) {
    for (int i = 0; i < tabs; ++i) {
      buffer.append(TAB_DELIMITER);
    }
  }

  /**
   * Count occurences of string within string.
   * 
   * @param str
   * @param sub
   * @return
   */
  public static int countMatches(final String str, final String sub) {
    if (isNullOrEmpty(str)) {
      return 0;
    }

    int count = 0;
    int idx = 0;
    int l = sub.length();

    while ((idx = str.indexOf(sub, idx)) != -1) {
      ++count;
      idx += l;
    }

    return count;
  }

  /**
   * Count occurences of char in string.
   * 
   * @param str
   * @param c
   * @return
   */
  public static int countMatches(final String str, final char c) {
    if (isNullOrEmpty(str)) {
      return 0;
    }

    int count = 0;
    int idx = 0;

    while ((idx = str.indexOf(c, idx)) != -1) {
      ++count;
      ++idx;
    }

    return count;
  }

  /**
   * Pad a list of strings.
   * 
   * @param items
   * @param v
   * @param size
   * @return
   */
  public static <T> String[] pad(String[] items, String v, int size) {
    if (ArrayUtils.isNullOrEmpty(items)) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }

    String[] ret = new String[size];

    SysUtils.arraycopy(items, ret);

    for (int i = items.length; i < size; ++i) {
      ret[i] = v;
    }

    return ret;
  }
}
