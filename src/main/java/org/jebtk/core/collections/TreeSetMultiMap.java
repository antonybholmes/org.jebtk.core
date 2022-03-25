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

import static org.jebtk.core.collections.MultiMap.DEFAULT_INITIAL_CAPACITY;

/**
 * Creates a map of lists where the each list is initialized with an default
 * value to a given size.
 * 
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class TreeSetMultiMap<K, V> extends SetMultiMap<K, V> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new default list multi map.
   *
   * @param initialCapacity the initial capacity
   */
  public TreeSetMultiMap(int initialCapacity) {
    super(initialCapacity, new TreeSetCreator<V>());
  }

  /**
   * Creates the.
   *
   * @param <V1> the generic type
   * @param <K1> the generic type
   * @return the multi map
   */
  public static <V1, K1> SetMultiMap<K1, V1> create() {
    return create(DEFAULT_INITIAL_CAPACITY);
  }

  /**
   * Creates the.
   *
   * @param <V1>            the generic type
   * @param <K1>            the generic type
   * @param initialCapacity the initial capacity
   * @return the multi map
   */
  public static <V1, K1> SetMultiMap<K1, V1> create(int initialCapacity) {
    return new TreeSetMultiMap<>(initialCapacity);
  }
}
