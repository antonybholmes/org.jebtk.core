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
 * Implementation of a Map of Maps using a HashMap to store the primary key
 * values.
 *
 * @param <K1> the generic type
 * @param <K2> the generic type
 * @param <V>  the value type
 */
public class TreeMapMap<K1, K2, V> extends DefaultHashMapMap<K1, K2, V> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new default list multi map.
   *
   * @param initialCapacity the initial capacity
   */
  public TreeMapMap(int initialCapacity) {
    super(initialCapacity, new TreeMapCreator<K2, V>());
  }

  /**
   * Creates the.
   *
   * @param <KK1> the generic type
   * @param <KK2> the generic type
   * @param <VV>  the generic type
   * @return the multi map
   */
  public static <KK1, KK2, VV> MapMap<KK1, KK2, VV> create() {
    return create(INITIAL_CAPACITY);
  }

  /**
   * Creates the.
   *
   * @param <KK1>           the generic type
   * @param <KK2>           the generic type
   * @param <VV>            the generic type
   * @param initialCapacity the initial capacity
   * @return the multi map
   */
  public static <KK1, KK2, VV> MapMap<KK1, KK2, VV> create(int initialCapacity) {
    return new TreeMapMap<>(initialCapacity);
  }
}
