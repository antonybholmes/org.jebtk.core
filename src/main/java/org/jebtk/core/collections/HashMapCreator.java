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
 * The Class HashMapCreator.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class HashMapCreator<K, V> implements IterMapCreator<K, V> {

  /** The Constant INITIAL_CAPACITY. */
  public static final int INITIAL_CAPACITY = 32;

  /** The m init cap. */
  private int mInitCap = 0;

  /**
   * Instantiates a new hash map creator.
   */
  public HashMapCreator() {
    this(INITIAL_CAPACITY);
  }

  /**
   * Instantiates a new hash map creator.
   *
   * @param initCap the init cap
   */
  public HashMapCreator(int initCap) {
    mInitCap = initCap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.collections.EntryCreator#create()
   */
  @Override
  public IterMap<K, V> newEntry() {
    return new IterHashMap<K, V>(mInitCap);
  }
}
