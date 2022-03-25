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

import java.io.Serializable;

/**
 * Represents the level on a path.
 * 
 * @author Antony Holmes
 *
 */
public class PathLevel implements Comparable<PathLevel>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member level.
   */
  private final String mLevel;

  /**
   * Instantiates a new path level.
   *
   * @param level the level
   */
  public PathLevel(String level) {
    mLevel = sanitize(level);
  }

  /**
   * Length.
   *
   * @return the int
   */
  public int length() {
    return mLevel.length();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mLevel;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(PathLevel p) {
    return mLevel.compareTo(p.mLevel);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof StrictPath)) {
      return false;
    }

    return compareTo((PathLevel) o) == 0;
  }

  /**
   * Sanitize.
   *
   * @param level the level
   * @return the string
   */
  public static String sanitize(String level) {
    String s = level; // level.toLowerCase();

    // Cannot contain slashes
    s = s.replaceAll("[\\/\\\\]", "");

    return s;
  }
}
