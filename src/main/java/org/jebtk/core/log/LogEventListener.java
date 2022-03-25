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

import java.util.EventListener;

/**
 * The listener interface for receiving logEvent events. The class that is
 * interested in processing a logEvent event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addLogEventListener<code> method. When the logEvent event
 * occurs, that object's appropriate method is invoked.
 *
 * @see LogEventEvent
 */
public interface LogEventListener extends EventListener {

  /**
   * Log event.
   *
   * @param e the e
   */
  public void logEvent(LogEvent e);

  // public void logErrorEvent(LogEvent e);
}
