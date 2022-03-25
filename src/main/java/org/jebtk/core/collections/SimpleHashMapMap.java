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
 * Enforces that both keys must be of the same type.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class SimpleHashMapMap<K, V> extends DefaultHashMapMap<K, K, V> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new simple map map.
   */
  public SimpleHashMapMap() {
    this(INITIAL_CAPACITY);
  }

  /**
   * Instantiates a new simple hash map map.
   *
   * @param initialCapacity the initial capacity
   */
  public SimpleHashMapMap(int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Creates the.
   *
   * @param <KK> the generic type
   * @param <VV> the generic type
   * @return the multi map
   */
  public static <KK, VV> MapMap<KK, KK, VV> create() {
    return create(INITIAL_CAPACITY);
  }

  /**
   * Creates the.
   *
   * @param <KK>            the generic type
   * @param <VV>            the generic type
   * @param initialCapacity the initial capacity
   * @return the multi map
   */
  public static <KK, VV> MapMap<KK, KK, VV> create(int initialCapacity) {
    return new SimpleHashMapMap<KK, VV>(initialCapacity);
  }
}
