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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A Generic map container which can be used as a wrapper around different map
 * types (e.g. hash or tree etc). It's primary function is to provide the
 * boilerplate code for writing a class that has an internal map.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class MapContainer<K, V> implements IterMap<K, V> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * The member map.
   */
  protected final Map<K, V> mMap;

  /**
   * Instantiates a new map container.
   *
   * @param map the map
   */
  public MapContainer(Map<K, V> map) {
    mMap = map;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#clear()
   */
  @Override
  public void clear() {
    mMap.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#containsKey(java.lang.Object)
   */
  @Override
  public boolean containsKey(Object key) {
    return mMap.containsKey(key);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#containsValue(java.lang.Object)
   */
  @Override
  public boolean containsValue(Object value) {
    return mMap.containsValue(value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#entrySet()
   */
  @Override
  public Set<Entry<K, V>> entrySet() {
    return mMap.entrySet();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#get(java.lang.Object)
   */
  @Override
  public V get(Object key) {
    return mMap.get(key);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return mMap.isEmpty();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#keySet()
   */
  @Override
  public Set<K> keySet() {
    return mMap.keySet();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  @Override
  public V put(K key, V value) {
    return mMap.put(key, value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#putAll(java.util.Map)
   */
  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    for (K k : m.keySet()) {
      put(k, m.get(k));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#remove(java.lang.Object)
   */
  @Override
  public V remove(Object key) {
    return mMap.remove(key);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#size()
   */
  @Override
  public int size() {
    return mMap.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Map#values()
   */
  @Override
  public Collection<V> values() {
    return mMap.values();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Entry<K, V>> iterator() {
    return mMap.entrySet().iterator();
  }

  @Override
  public Entry<K, V> first() {
    return iterator().next();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mMap.toString();
  }
}
