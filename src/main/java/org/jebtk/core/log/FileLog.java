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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.text.TextUtils;

/**
 * The class FileLog.
 */
public class FileLog implements LogEventListener {

  /**
   * The constant DEFAULT_LOG_FILE.
   */
  public static final Path DEFAULT_LOG_FILE = PathUtils.getPath("log.txt");

  /**
   * Instantiates a new file log.
   */
  public FileLog() {
    this(DEFAULT_LOG_FILE);
  }

  /**
   * Instantiates a new file log.
   *
   * @param file the file
   */
  public FileLog(Path file) {
    Logger.getRootLogger().getLoggerRepository().resetConfiguration();

    ConsoleAppender console = new ConsoleAppender(); // create appender
    // configure the appender
    // String PATTERN = "%d [%p|%c|%C{1}] %m%n";
    // console.setLayout(new PatternLayout(PATTERN));
    // console.setThreshold(Level.FATAL);
    console.activateOptions();

    // add appender to any Logger (here is root)
    Logger.getRootLogger().addAppender(console);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.log.LogEventListener#logEvent(org.abh.lib.log.LogEvent)
   */
  @Override
  public void logEvent(LogEvent e) {

    BufferedWriter writer = null;

    try {
      // writer = new BufferedWriter(new FileWriter(mFile, mFile.exists()));

      try {
        writer.write(e.getType().toString());
        writer.write(TextUtils.TAB_DELIMITER);
        writer.write(e.getFormattedDate());
        writer.write(TextUtils.TAB_DELIMITER);
        writer.write(e.getSource().toString());
        writer.write(TextUtils.TAB_DELIMITER);
        writer.write(e.getMessage());
        writer.write(TextUtils.TAB_DELIMITER);
        writer.write(Integer.toString(e.getId()));

        writer.newLine();
      } finally {
        writer.close();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
