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
 * Specify an inclusive number limit.
 * 
 * @author Antony Holmes
 *
 */
public class Limits {

  /**
   * The min.
   */
  public double min = 0;

  /**
   * The max.
   */
  public double max = 0;

  /**
   * Instantiates a new limits.
   *
   * @param min the min
   * @param max the max
   */
  public Limits(double min, double max) {
    this.min = min;
    this.max = max;
  }
}
