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

import java.util.Collection;

/**
 * The class DefaultMultiMap creates a multi-map with keys of type K and values
 * being a collection of V. This is the generic form of the multi-map.
 * ArrayListMultiMap allows you to create a map of array lists for example, if
 * you need specific types. This is to provide more versatility than the Guava
 * MultiMap since this is a drop in replacement for a Map and allows for
 * specific types in the values rather than having to deal with a generic
 * collection (and possibly having to cast it to a concrete collection type).
 *
 * @param <K> the key type
 * @param <V> the value type
 * @param <T> the generic type
 */
public abstract class DefaultMultiMap<K, V, T extends Collection<V>> extends DefaultHashMap<K, T>
    implements MultiMap<K, V, T> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new multi map.
   *
   * @param defaultValue the default value
   */
  public DefaultMultiMap(CollectionCreator<V, T> defaultValue) {
    this(DEFAULT_INITIAL_CAPACITY, defaultValue);
  }

  /**
   * Instantiates a new abstract multi map.
   *
   * @param initialCapacity the initial capacity
   * @param defaultValue    the default value
   */
  public DefaultMultiMap(int initialCapacity, CollectionCreator<V, T> defaultValue) {
    super(initialCapacity, defaultValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#containsValue(java.lang.Object)
   */
  @Override
  public boolean containsValue(Object value) {
    for (K key : keySet()) {
      if (get(key).contains(value)) {
        return true;
      }
    }

    return false;
  }
}
