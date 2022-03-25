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

import java.util.HashMap;
import java.util.Map;

/**
 * A map of fixed size that begins to excise the oldest elements once the map
 * reaches a maximum size.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 * @param <X> the generic type
 */
public class AgeMap<T, X> extends HashMap<T, X> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member age map.
   */
  private Map<Integer, T> mAgeMap = new HashMap<Integer, T>();

  /**
   * Track the oldest id.
   */
  private int mOldest = 0;

  /**
   * The member max size.
   */
  private int mMaxSize = 0;

  /**
   * Instantiates a new age map.
   *
   * @param maxSize the max size
   */
  public AgeMap(int maxSize) {
    mMaxSize = maxSize;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public X put(T key, X value) {
    // First clear the cache of junk
    if (!containsKey(key)) {
      if (size() == mMaxSize) {
        remove(mAgeMap.get(mOldest));
        mAgeMap.remove(mOldest);
        ++mOldest;
      }

      mAgeMap.put(size(), key);
    }

    super.put(key, value);

    return value;
  }
}
