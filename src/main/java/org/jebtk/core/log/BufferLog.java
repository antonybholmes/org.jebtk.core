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

import org.jebtk.core.collections.CircularArray;

/**
 * Stores a fixed number of log events.
 * 
 * @author Antony Holmes
 *
 */
public class BufferLog extends CircularArray<LogEvent> implements LogEventListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * The constant DEFAULT_SIZE.
   */
  private static final int DEFAULT_SIZE = 1000;

  /**
   * Instantiates a new buffer log.
   */
  public BufferLog() {
    this(DEFAULT_SIZE);
  }

  /**
   * Instantiates a new buffer log.
   *
   * @param maxSize the max size
   */
  public BufferLog(int maxSize) {
    super(maxSize);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.log.LogEventListener#logEvent(org.abh.lib.log.LogEvent)
   */
  @Override
  public void logEvent(LogEvent e) {
    add(e);
  }

}
