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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides a way to automate giving unique string ids to objects.
 * 
 * @author Antony Holmes
 *
 */
public class StringId {

  /**
   * The next id.
   */
  private final AtomicInteger mNextId = new AtomicInteger(1);

  /**
   * The member prefix.
   */
  private final String mPrefix;

  /**
   * Instantiates a new string id.
   *
   * @param prefix the prefix
   */
  public StringId(String prefix) {
    mPrefix = prefix;
  }

  /**
   * Returns a new id using the given prefix.
   *
   * @return the next id
   */
  public synchronized String getNextId() {
    return mPrefix + " " + mNextId.getAndIncrement();
  }
}
