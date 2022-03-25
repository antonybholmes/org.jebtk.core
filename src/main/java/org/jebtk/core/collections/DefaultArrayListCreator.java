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

import java.util.List;

/**
 * The class DefaultListCreator.
 *
 * @param <V> the value type
 */
public class DefaultArrayListCreator<V> implements ListCreator<V> {

  /**
   * The member default value.
   */
  private EntryCreator<V> mDefaultValue;

  public DefaultArrayListCreator() {
    this(new NullCreator<V>());
  }

  /**
   * Instantiates a new default list creator.
   *
   * @param listSize     the list size
   * @param defaultValue the default value
   */
  public DefaultArrayListCreator(V defaultValue) {
    this(new ValueCreator<V>(defaultValue));
  }

  /**
   * Instantiates a new default list creator.
   *
   * @param listSize     the list size
   * @param defaultValue the default value
   */
  public DefaultArrayListCreator(EntryCreator<V> defaultValue) {
    mDefaultValue = defaultValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.collections.EntryCreator#create()
   */
  @Override
  public List<V> newEntry() {
    return DefaultArrayList.create(mDefaultValue);
  }
}
