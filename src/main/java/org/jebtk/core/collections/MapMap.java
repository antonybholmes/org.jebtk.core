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

import java.util.Map;

/**
 * Interface for a Map of Maps.
 *
 * @param <K1> the generic type
 * @param <K2> the generic type
 * @param <V>  the value type
 */
public interface MapMap<K1, K2, V> extends IterMap<K1, IterMap<K2, V>> {

  /** The Constant INITIAL_CAPACITY. */
  public static final int INITIAL_CAPACITY = 32;

  /**
   * Gets the.
   *
   * @param k1 the k1
   * @param k2 the k2
   * @return the v
   */
  public V get(K1 k1, K2 k2);

  /**
   * Put.
   *
   * @param key1  the key1
   * @param key2  the key2
   * @param value the value
   * @return the map
   */
  public Map<K2, V> put(K1 key1, K2 key2, V value);

  /**
   * Contains keys.
   *
   * @param key1 the key1
   * @param key2 the key2
   * @return true, if successful
   */
  public boolean containsKeys(K1 key1, K2 key2);
}
