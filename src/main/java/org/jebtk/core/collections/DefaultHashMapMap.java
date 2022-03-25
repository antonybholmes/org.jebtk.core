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
 * Creates an auto populating hash map of hash maps.
 *
 * @author Antony Holmes
 * @param <K1> the generic type
 * @param <K2> the generic type
 * @param <V>  the value type
 */
public class DefaultHashMapMap<K1, K2, V> extends DefaultHashMap<K1, IterMap<K2, V>> implements MapMap<K1, K2, V> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new default hash map map.
   */
  public DefaultHashMapMap() {
    this(INITIAL_CAPACITY);
  }

  /**
   * Create a hash map of hash maps.
   *
   * @param capacity1 the capacity 1
   */
  public DefaultHashMapMap(int capacity1) {
    this(capacity1, new HashMapCreator<K2, V>());
  }

  /**
   * Instantiates a new default map map.
   *
   * @param capacity1  the capacity1
   * @param mapCreator Create a map
   */
  public DefaultHashMapMap(int capacity1, IterMapCreator<K2, V> mapCreator) {
    super(capacity1, mapCreator);
  }

  /**
   * Instantiates a new hash map map.
   *
   * @param defaultValue the default value
   */
  public DefaultHashMapMap(V defaultValue) {
    this(INITIAL_CAPACITY, defaultValue);
  }

  /**
   * Instantiates a new hash map map.
   *
   * @param initialCapacity the initial capacity
   * @param defaultValue    the default value
   */
  public DefaultHashMapMap(int initialCapacity, V defaultValue) {
    this(initialCapacity, initialCapacity, defaultValue);
  }

  /**
   * Instantiates a new default map map.
   *
   * @param capacity1    the capacity1
   * @param capacity2    the capacity2
   * @param defaultValue the default value
   */
  public DefaultHashMapMap(int capacity1, int capacity2, V defaultValue) {
    this(capacity1, capacity2, new ValueCreator<V>(defaultValue));
  }

  /**
   * Instantiates a new default hash map map.
   *
   * @param capacity1    the capacity 1
   * @param capacity2    the capacity 2
   * @param defaultValue the default value
   */
  public DefaultHashMapMap(int capacity1, int capacity2, EntryCreator<V> defaultValue) {
    this(capacity1, new DefaultHashMapCreator<K2, V>(capacity2, defaultValue));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.MapMap#get(java.lang.Object,
   * java.lang.Object)
   */
  @Override
  public V get(K1 k1, K2 k2) {
    return get(k1).get(k2);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.MapMap#put(java.lang.Object,
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public Map<K2, V> put(K1 key1, K2 key2, V value) {
    get(key1).put(key2, value);

    return get(key1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.MapMap#containsKeys(java.lang.Object,
   * java.lang.Object)
   */
  @Override
  public boolean containsKeys(K1 key1, K2 key2) {
    return containsKey(key1) && get(key1).containsKey(key2);
  }

  /**
   * Creates the.
   *
   * @param <KK1>     the generic type
   * @param <KK2>     the generic type
   * @param <VV>      the generic type
   * @param capacity1 the capacity 1
   * @param capacity2 the capacity 2
   * @return the map map
   */
  public static <KK1, KK2, VV> MapMap<KK1, KK2, VV> create(int capacity1, int capacity2) {
    return new DefaultHashMapMap<KK1, KK2, VV>(capacity1, new HashMapCreator<KK2, VV>(capacity2));
  }

  /**
   * Creates the.
   *
   * @param <KK1>        the generic type
   * @param <KK2>        the generic type
   * @param <VV>         the generic type
   * @param capacity1    the capacity 1
   * @param capacity2    the capacity 2
   * @param defaultValue the default value
   * @return the map map
   */
  public static <KK1, KK2, VV> MapMap<KK1, KK2, VV> create(int capacity1, int capacity2, VV defaultValue) {
    return create(capacity1, capacity2, new ValueCreator<VV>(defaultValue));
  }

  /**
   * Creates the.
   *
   * @param <KK1>        the generic type
   * @param <KK2>        the generic type
   * @param <VV>         the generic type
   * @param capacity1    the capacity 1
   * @param capacity2    the capacity 2
   * @param defaultValue the default value
   * @return the map map
   */
  public static <KK1, KK2, VV> MapMap<KK1, KK2, VV> create(int capacity1, int capacity2,
      EntryCreator<VV> defaultValue) {
    return create(capacity1, new DefaultHashMapCreator<KK2, VV>(capacity2, defaultValue));
  }

  /**
   * Creates the.
   *
   * @param <KK1>        the generic type
   * @param <KK2>        the generic type
   * @param <VV>         the generic type
   * @param capacity1    the capacity 1
   * @param defaultValue the default value
   * @return the multi map
   */
  public static <KK1, KK2, VV> MapMap<KK1, KK2, VV> create(int capacity1, IterMapCreator<KK2, VV> defaultValue) {
    return new DefaultHashMapMap<KK1, KK2, VV>(capacity1, defaultValue);
  }
}
