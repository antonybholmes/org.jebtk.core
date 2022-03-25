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

import java.text.DateFormat;

/**
 * The class Log.
 */
public class Log extends LogEventListeners implements LogEventListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constant DATE_FORMAT.
   */
  public static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);

  /**
   * The member name.
   */
  private String mName;

  /**
   * Instantiates a new log.
   *
   * @param name the name
   */
  public Log(String name) {
    mName = name;

    addLogListener(this);
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return mName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.log.LogEventListener#logEvent(org.abh.lib.log.LogEvent)
   */
  @Override
  public void logEvent(LogEvent e) {
    fireLogEvent(e);
  }
}
