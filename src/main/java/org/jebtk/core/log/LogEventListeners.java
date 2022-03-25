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

import org.jebtk.core.event.EventProducer;

/**
 * The basis for model controls in a model view controller setup.
 * 
 * @author Antony Holmes
 *
 */
public class LogEventListeners extends EventProducer<LogEventListener> implements LogEventProducer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.log.LogEventProducer#addLogListener(org.abh.lib.log.
   * LogEventListener)
   */
  public void addLogListener(LogEventListener l) {
    mListeners.add(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.log.LogEventProducer#removeLogListener(org.abh.lib.log.
   * LogEventListener)
   */
  public void removeLogListener(LogEventListener l) {
    mListeners.remove(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.log.LogEventProducer#fireLogEvent(org.abh.lib.log.LogEvent)
   */
  public void fireLogEvent(LogEvent e) {
    for (LogEventListener l : mListeners) {
      l.logEvent(e);
    }
  }
}