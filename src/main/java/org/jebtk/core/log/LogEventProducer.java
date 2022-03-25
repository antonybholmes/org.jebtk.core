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

/**
 * For classes that generate ModernClickEvents.
 *
 * @author Antony Holmes
 */
public interface LogEventProducer {

  /**
   * Adds the log listener.
   *
   * @param l the l
   */
  public void addLogListener(LogEventListener l);

  /**
   * Removes the log listener.
   *
   * @param l the l
   */
  public void removeLogListener(LogEventListener l);

  /**
   * Fire log event.
   *
   * @param e the e
   */
  public void fireLogEvent(LogEvent e);
}
