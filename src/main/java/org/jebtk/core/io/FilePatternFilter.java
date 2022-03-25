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
package org.jebtk.core.io;

import java.io.File;
import java.io.FileFilter;

/**
 * The class FilePatternFilter.
 */
public class FilePatternFilter implements FileFilter {

  /**
   * The pattern.
   */
  private String pattern;

  /**
   * Instantiates a new file pattern filter.
   *
   * @param pattern the pattern
   */
  public FilePatternFilter(String pattern) {
    // System.out.println("filter:" + filter);

    this.pattern = pattern;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.io.FileFilter#accept(java.io.File)
   */
  public boolean accept(File f) {
    // files should not begin with a period as this indicates hidden files
    return !f.isDirectory() && !f.getName().startsWith(".") && f.getName().matches(pattern);
  }
}
