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

/**
 * A specialized DefaultHashMap for storing the number of times a key is added.
 *
 * @param <K> the key type
 */
public class DoubleCountMap<K> extends DefaultHashMap<K, Double> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;
  private double mMaxC = Double.MIN_VALUE;
  private K mMaxK = null;

  /**
   * Instantiates a new count map to keep track of doubleeger counts.
   */
  public DoubleCountMap() {
    this(0);
  }

  /**
   * Instantiates a new count map.
   *
   * @param defaultValue the default value
   */
  public DoubleCountMap(double defaultValue) {
    super(defaultValue);
  }

  /**
   * Removal has no effect.
   *
   * @param key the key
   * @return the doubleeger
   */
  @Override
  public Double remove(Object key) {
    /*
     * doubleeger ret = super.get(key);
     * 
     * dec((K) key);
     * 
     * return ret;
     */

    return super.get(key);
  }

  /**
   * Put all of the values from an iterator doubleo the map.
   *
   * @param iter the iter
   */
  public void putAll(Iterable<K> iter) {
    for (K v : iter) {
      put(v);
    }
  }

  /**
   * Adds the key to the map and increments it if it does not exist.
   *
   * @param key the key
   */
  public void put(K key) {
    inc(key);
  }

  /**
   * Increment the key count by 1 (default).
   *
   * @param key the key
   */
  public void inc(K key) {
    inc(key, 1);
  }

  /**
   * Inits the.
   *
   * @param key the key
   */
  public void init(K key) {
    put(key, 0.0);
  }

  /**
   * Increment the key count by ({@code inc} each time a key is added.
   *
   * @param key the key
   * @param inc the inc
   */
  public void inc(K key, double inc) {
    double c = (double) get(key) + inc;

    put(key, c);

    if (c > mMaxC) {
      mMaxC = c;
      mMaxK = key;
    }
  }

  public double getMaxC() {
    return mMaxC;
  }

  public K getMaxK() {
    return mMaxK;
  }

  /**
   * Decrement the key by a given amount.
   *
   * @param key the key
   * @param dec Should be a positive doubleeger indicating how much to reduce the
   *            count of the key by
   */
  public void dec(K key, double dec) {
    inc(key, -dec);
  }

  /**
   * Increment the values in a collection.
   *
   * @param values the values
   */
  public void inc(Collection<K> values) {
    for (K value : values) {
      inc(value);
    }
  }

  /**
   * Creates the.
   *
   * @param <K1> the generic type
   * @return the count map
   */
  public static <K1> DoubleCountMap<K1> create() {
    return create(0);
  }

  /**
   * Creates the.
   *
   * @param <K1>         the generic type
   * @param defaultValue the default value
   * @return the count map
   */
  public static <K1> DoubleCountMap<K1> create(double defaultValue) {
    return new DoubleCountMap<K1>(defaultValue);
  }
}
