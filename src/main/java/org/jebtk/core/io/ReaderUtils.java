/**
 * Copyright 2017 Antony Holmes
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
package org.jebtk.core.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import org.jebtk.core.text.TextUtils;

/**
 * The Class ReaderUtils.
 */
public class ReaderUtils {

  /** The Constant COMMON_FILE_HEADERS. */
  public static final Collection<String> COMMON_FILE_HEADERS = new ArrayList<String>();

  static {
    COMMON_FILE_HEADERS.add("#");
    COMMON_FILE_HEADERS.add("%");
    COMMON_FILE_HEADERS.add("@");
  }

  /**
   * Instantiates a new reader utils.
   */
  private ReaderUtils() {
    // Do nothing
  }

  /**
   * Count header lines.
   *
   * @param file the file
   * @return the int
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static int countHeaderLines(Path file) throws IOException {
    return countHeaderLines(file, COMMON_FILE_HEADERS);
  }

  /**
   * Count the number of header lines in a file where a header line begins with a
   * given string. Common examples are # or %.
   *
   * @param file    the file
   * @param matches the matches
   * @return the int
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static int countHeaderLines(Path file, Collection<String> matches) throws IOException {
    int ret = 0;

    BufferedReader reader = FileUtils.newBufferedReader(file);

    try {
      String line;

      while ((line = reader.readLine()) != null) {
        // Assuming heading lines at beginning of file
        if (!TextUtils.startsWith(line, matches)) {
          break;
        }

        ++ret;
      }
    } finally {
      reader.close();
    }

    return ret;
  }

  /**
   * Skip lines.
   *
   * @param reader the reader
   * @param lines  the lines
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void skipLines(BufferedReader reader, int lines) throws IOException {
    for (int i = 0; i < lines; ++i) {
      reader.readLine();
    }
  }
}
