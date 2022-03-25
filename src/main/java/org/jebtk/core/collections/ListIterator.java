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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Provides an iterable wrapper around a list that only allows items to be added
 * and iterated over.
 *
 * @param <T> the generic type
 */
public class ListIterator<T> implements Iterable<T> {

  /** The m values. */
  private List<T> mValues = new ArrayList<T>();

  /**
   * Adds an item to the iterator.
   *
   * @param v the v
   */
  public void add(T v) {
    mValues.add(v);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<T> iterator() {
    return mValues.iterator();
  }
}
