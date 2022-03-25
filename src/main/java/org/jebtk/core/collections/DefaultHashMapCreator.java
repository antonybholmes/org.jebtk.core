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
 * The Class DefaultHashMapCreator.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DefaultHashMapCreator<K, V> implements IterMapCreator<K, V> {

  /** The m default value. */
  private EntryCreator<V> mDefaultValue;

  /** The m initial capacity. */
  private int mInitialCapacity;

  /**
   * Instantiates a new default hash map creator.
   *
   * @param defaultValue the default value
   */
  public DefaultHashMapCreator(V defaultValue) {
    this(0, defaultValue);
  }

  /**
   * Instantiates a new auto hash map.
   *
   * @param initialCapacity the initial capacity
   * @param defaultValue    the default value
   */
  public DefaultHashMapCreator(int initialCapacity, V defaultValue) {
    this(initialCapacity, new ValueCreator<V>(defaultValue));
  }

  /**
   * Instantiates a new default map.
   *
   * @param creator the creator
   */
  public DefaultHashMapCreator(EntryCreator<V> creator) {
    this(0, creator);
  }

  /**
   * Instantiates a new default hash map creator.
   *
   * @param initialCapacity the initial capacity
   * @param creator         the creator
   */
  public DefaultHashMapCreator(int initialCapacity, EntryCreator<V> creator) {
    mInitialCapacity = initialCapacity;
    mDefaultValue = creator;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.EntryCreator#newEntry()
   */
  @Override
  public IterMap<K, V> newEntry() {
    return new DefaultHashMap<K, V>(mInitialCapacity, mDefaultValue);
  }

  public static <KK, VV> IterMapCreator<KK, VV> create(EntryCreator<VV> defaultValue) {
    return new DefaultHashMapCreator<KK, VV>(defaultValue);
  }
}
