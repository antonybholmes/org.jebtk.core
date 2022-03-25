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
package org.jebtk.core.path;

/**
 * Represents the level on a path.
 * 
 * @author Antony Holmes
 *
 */
public class StrictPathLevel extends PathLevel {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new strict path level.
   *
   * @param level the level
   */
  public StrictPathLevel(String level) {
    super(sanitize(level));
  }

  /**
   * Return the path level in a standardized way. Path elements consist only of of
   * letters, numbers dashes and underscores. All other characters are considered
   * illegal and stripped out. Brackets are converted to underscores.
   *
   * @param level the level
   * @return the string
   */
  public static String sanitize(String level) {
    String s = level; // level.toLowerCase();

    // Cannot contain slashes
    s = s.replaceAll("[\\/\\\\]", "");

    // Brackets become spaces
    s = s.replaceAll("[\\[\\]\\(\\)\\{\\}]", " ");

    // System.err.println("s1 " + s);

    // Convert spaces to underscores
    s = s.replaceAll("\\s+", "_");

    // Strip leading and trailing underscores
    s = s.replaceAll("^_+", "").replaceAll("_+$", "");

    // Remove all other illegal characters
    s = s.replaceAll("[^a-zA-Z0-9\\-_\\.]", "");

    // Remove underscore repeats
    s = s.replaceAll("_+", "_");

    return s;
  }
}
