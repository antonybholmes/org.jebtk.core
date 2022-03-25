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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The Class DateUtils.
 */
public class DateUtils {

  /** The Constant MMDDYYYY_FORMAT. */
  public static final String MMDDYYYY_FORMAT = "MM/dd/yyyy";

  /** The Constant YYYY_FORMAT. */
  public static final String YYYY_FORMAT = "yyyy";

  /**
   * Instantiates a new date utils.
   */
  private DateUtils() {
    // Do nothing
  }

  /**
   * Creates the american date format.
   *
   * @return the date format
   */
  public static DateFormat createAmericanDateFormat() {
    return createMMDDYYYYFormat();
  }

  /**
   * Creates the MMDDYYYY format.
   *
   * @return the date format
   */
  public static DateFormat createMMDDYYYYFormat() {
    return new SimpleDateFormat(DateUtils.MMDDYYYY_FORMAT);
  }

  /**
   * Format.
   *
   * @param dates the dates
   * @return the list
   */
  public static List<String> format(Collection<Date> dates) {
    return format(dates, createMMDDYYYYFormat());
  }

  /**
   * Format.
   *
   * @param dates  the dates
   * @param format the format
   * @return the list
   */
  public static List<String> format(Collection<Date> dates, DateFormat format) {
    List<String> ret = new ArrayList<>(dates.size());

    for (Date date : dates) {
      ret.add(format.format(date));
    }

    return ret;

  }

  /**
   * Gets the rev formatted date.
   *
   * @param date the date
   * @return the rev formatted date
   */
  public static final String getRevFormattedDate(Date date) {
    return new SimpleDateFormat("yyyy-dd-mm").format(date);
  }

  /**
   * Gets the american formatted date.
   *
   * @param date the date
   * @return the american formatted date
   */
  public static final String getAmericanFormattedDate(Date date) {
    return createAmericanDateFormat().format(date);
  }

  /**
   * Parses the rev formatted date.
   *
   * @param date the date
   * @return the date
   * @throws ParseException the parse exception
   */
  public static final Date parseRevFormattedDate(String date) throws ParseException {
    return new SimpleDateFormat("yyyy-mm-dd").parse(date);
  }

  /**
   * Returns the current year.
   *
   * @return the string
   */
  public static String year() {
    return new SimpleDateFormat(YYYY_FORMAT).format(Calendar.getInstance().getTime());
  }
}
