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
package org.jebtk.core.log;

import java.util.Date;

import org.jebtk.core.event.Event;

/**
 * The class LogEvent.
 */
public class LogEvent extends Event {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The date.
   */
  private Date date;

  /**
   * The formatted date.
   */
  private String formattedDate;

  /**
   * The type.
   */
  private LogEventType type;

  /**
   * Instantiates a new log event.
   *
   * @param source  the source
   * @param message the message
   * @param type    the type
   */
  public LogEvent(Object source, String message, LogEventType type) {
    super(source, message);

    this.type = type;

    this.date = new Date();
    this.formattedDate = Log.DATE_FORMAT.format(date.getTime());
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public LogEventType getType() {
    return type;
  }

  /**
   * Gets the date.
   *
   * @return the date
   */
  public final Date getDate() {
    return date;
  }

  /**
   * Gets the formatted date.
   *
   * @return the formatted date
   */
  public final String getFormattedDate() {
    return formattedDate;
  }
}
