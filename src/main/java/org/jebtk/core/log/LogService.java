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

import org.jebtk.core.model.NameMapModel;

/**
 * Allows multiple logs to be agglomerated so a message can be fire to multiple
 * logs.
 *
 * @author Antony Holmes
 *
 */
public class LogService extends NameMapModel<Log> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The Class LogServiceLoader.
   */
  private static class LogServiceLoader {

    /** The Constant INSTANCE. */
    private static final LogService INSTANCE = new LogService();
  }

  /**
   * Gets the single instance of SettingsService.
   *
   * @return single instance of SettingsService
   */
  public static LogService getInstance() {
    return LogServiceLoader.INSTANCE;
  }

  /**
   * Instantiates a new log service.
   */
  private LogService() {
    // Do nothing
  }

  /**
   * Returns a logger with a specific name. If the log does not exist, it is
   * created.
   *
   * @param name the name
   * @return the log
   */
  public final Log getLog(String name) {
    if (contains(name)) {
      return get(name);
    }

    add(name, new Log(name));

    return get(name);
  }

  /**
   * Adds the file log.
   *
   * @param name the name
   */
  public void addFileLog(String name) {
    getLog(name).addLogListener(new FileLog());
  }

  /**
   * Adds the console log.
   *
   * @param name the name
   */
  public void addConsoleLog(String name) {
    getLog(name).addLogListener(new ConsoleLog());
  }

  /**
   * Adds the std err log.
   *
   * @param name the name
   */
  public void addStdErrLog(String name) {
    getLog(name).addLogListener(new StdErrLog());
  }
}
