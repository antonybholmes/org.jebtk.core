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
package org.jebtk.core.collections;

/**
 * Fast stack implementation for Integers. Note that at the moment, no bound
 * checks are performed for speed so it will ungraciously throw exceptions if
 * misused.
 *
 * @author Antony Holmes
 */
public abstract class AbstractStack {

  /**
   * The constant DEFAULT_SIZE.
   */
  public static final int DEFAULT_SIZE = 16;

  /** The m pc. */
  protected int mPc = -1;

  /**
   * Reset.
   */
  public void reset() {
    mPc = -1;
  }

  /**
   * Size.
   *
   * @return the int
   */
  public int size() {
    return mPc + 1;
  }

  /**
   * Gets the pc.
   *
   * @return the pc
   */
  public int getPc() {
    return mPc;
  }

  /**
   * Checks if is empty.
   *
   * @return true, if is empty
   */
  public boolean isEmpty() {
    return mPc == -1;
  }
}
