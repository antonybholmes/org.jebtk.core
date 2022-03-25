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

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class Parser.
 */
public class Parser {

  /** The Constant NAN. */
  public static final String NAN = "NaN";

  /** The Constant NUMBER_PATTERN. */
  public static final Pattern NUMBER_PATTERN = Pattern.compile("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)");

  /** The Constant ENDS_WITH_NUMBER_PATTERN. */
  public static final Pattern ENDS_WITH_NUMBER_PATTERN = Pattern.compile("([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)$");

  /** The Constant STRICT_NUMBER_PATTERN. */
  public static final Pattern STRICT_NUMBER_PATTERN = Pattern.compile("^([-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?)$");

  /**
   * Instantiates a new parser.
   */
  private Parser() {
    // Do nothing
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
    int v;

    try {
      v = toInt(field);
    } catch (ParseException e) {
      v = defaultValue;
    }

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
    double v;

    try {
      v = toDouble(field);
    } catch (ParseException e) {
      v = defaultValue;
    }

    return v;
  }

  /**
   * Parses an integer field as a double and then converts back to int to provide
   * more robust handling of ints written as floating point numbers in files.
   *
   * @param field the field
   * @return the int
   * @throws ParseException the parse exception
   */
  public static final int toInt(String field) throws ParseException {
    return (int) toDouble(field);
  }

  /**
   * More robust number extraction that copes with text in the field. It attempts
   * to extract the longest run of digits (with optional period) { possible from a
   * string and convert it to a number.
   *
   * @param field the field
   * @return A double
   * @throws ParseException the parse exception
   */
  public static final double toDouble(String field) throws ParseException {
    if (TextUtils.isNullOrEmpty(field)) {
      throw new ParseException(TextUtils.STRING_EMPTY_NULL_WARNING, 0);
    }

    if (field.equals(NAN)) {
      return Double.NaN;
    }

    String f = extractNumber(field);

    if (TextUtils.isNullOrEmpty(f)) {
      throw new ParseException(f + " does not contain a number", 0);
    }

    try {
      return Double.parseDouble(f);
    } catch (NumberFormatException e) {
      throw new ParseException(f + " is not a valid number", 0);
    }
  }

  /**
   * Extracts the numerical portion of a string and returns it as a double or
   * Double.NaN if no number if found.
   *
   * @param field the field
   * @return the double
   */
  public static final double d(String field) {
    if (TextUtils.isNullOrEmpty(field)) {
      return Double.NaN;
    }

    if (field.equals(NAN)) {
      return Double.NaN;
    }

    String f = extractNumber(field);

    if (TextUtils.isNullOrEmpty(f)) {
      return Double.NaN;
    }

    try {
      return Double.parseDouble(f);
    } catch (NumberFormatException e) {
      return Double.NaN;
    }
  }

  /**
   * I.
   *
   * @param field the field
   * @return the int
   */
  public static final int i(String field) {
    if (TextUtils.isNullOrEmpty(field)) {
      return Integer.MIN_VALUE;
    }

    if (field.equals(NAN)) {
      return Integer.MIN_VALUE;
    }

    String f = extractNumberFromText(field);

    if (TextUtils.isNullOrEmpty(f)) {
      return Integer.MIN_VALUE;
    }

    try {
      return Integer.parseInt(f);
    } catch (NumberFormatException e) {
      return Integer.MIN_VALUE;
    }
  }

  /**
   * Extracts a number from a string.
   *
   * @param field the field
   * @return the string
   */
  public static final String extractNumber(String field) {
    Matcher matcher = STRICT_NUMBER_PATTERN.matcher(field.replaceAll(",", TextUtils.EMPTY_STRING));

    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return null;
    }
  }

  /**
   * Extract number from text.
   *
   * @param field the field
   * @return the string
   */
  public static final String extractNumberFromText(String field) {
    Matcher matcher = ENDS_WITH_NUMBER_PATTERN.matcher(field.replaceAll(",", TextUtils.EMPTY_STRING));

    if (matcher.find()) {
      return matcher.group(1);
    } else {
      return null;
    }
  }
}
