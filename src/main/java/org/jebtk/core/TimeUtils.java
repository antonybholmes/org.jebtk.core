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
package org.jebtk.core;

/**
 * The Class TimeUtils.
 */
public class TimeUtils {

  /**
   * Instantiates a new time utils.
   */
  private TimeUtils() {
    // Do nothing
  }

  /**
   * Return the current time in milliseconds.
   *
   * @return the current time ms
   */
  public static long getCurrentTimeMs() {
    return System.currentTimeMillis();
  }

  /**
   * Gets the current time S.
   *
   * @return the current time S
   */
  public static long getCurrentTimeS() {
    return getCurrentTimeMs() / 1000;
  }

}
