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

import java.util.Comparator;

/**
 * Hashmap that automatically adds a default value if a key does not exist.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DefaultTreeMap<K, V> extends IterTreeMap<K, V> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member default value.
   */
  private EntryCreator<V> mDefaultValue;

  private boolean mAutoCreate = true;

  /**
   * Instantiates a new auto hash map.
   *
   * @param defaultValue the default value
   */
  public DefaultTreeMap(V defaultValue) {
    this(new ValueCreator<V>(defaultValue));
  }

  /**
   * Instantiates a new default map.
   *
   * @param defaultValue the default value
   */
  public DefaultTreeMap(EntryCreator<V> defaultValue) {
    mDefaultValue = defaultValue;
  }

  public DefaultTreeMap(Comparator<K> c, EntryCreator<V> defaultValue) {
    super(c);

    mDefaultValue = defaultValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashMap#get(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public V get(Object key) {
    return getValue((K) key);
  }

  /**
   * Gets the value.
   *
   * @param key the key
   * @return the value
   */
  public V getValue(K key) {
    if (mAutoCreate && !containsKey(key)) {
      put(key, mDefaultValue.newEntry());
    }

    return super.get(key);
  }

  /**
   * Set whether entries are automatically created or not. Useful for locking map
   * so that new entries must be explicity added.
   * 
   * @param autoCreate
   */
  public void setAutoCreate(boolean autoCreate) {
    mAutoCreate = autoCreate;
  }

  /**
   * Creates a new Default Tree Map.
   *
   * @param <KK>         the generic type
   * @param <VV>         the generic type
   * @param defaultValue the default value
   * @return the map
   */
  public static <KK, VV> DefaultTreeMap<KK, VV> create(VV defaultValue) {
    return create(new ValueCreator<VV>(defaultValue));
  }

  /**
   * Creates the.
   *
   * @param <KK>         the generic type
   * @param <VV>         the generic type
   * @param defaultValue the default value
   * @return the map
   */
  public static <KK, VV> DefaultTreeMap<KK, VV> create(EntryCreator<VV> defaultValue) {
    return new DefaultTreeMap<KK, VV>(defaultValue);
  }
}
