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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * The Class TailList.
 *
 * @param <T> the generic type
 */
public class TailList<T> extends ReadOnlyListContainer<T> {

  /** The m start. */
  private int mStart;

  /**
   * Instantiates a new tail list.
   *
   * @param list the list
   */
  public TailList(List<T> list) {
    this(list, 1);
  }

  /**
   * Instantiates a new tail list.
   *
   * @param list  the list
   * @param start the start
   */
  public TailList(List<T> list, int start) {
    super(list);

    mStart = start;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#get(int)
   */
  @Override
  public T get(int index) {
    return super.get(index + mStart);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#iterator()
   */
  @Override
  public Iterator<T> iterator() {
    return listIterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#listIterator()
   */
  @Override
  public ListIterator<T> listIterator() {
    return listIterator(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#listIterator(int)
   */
  @Override
  public ListIterator<T> listIterator(int index) {
    return new TailListIterator<>(mList, mStart + index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.collections.ReadOnlyListContainer#indexOf(java.lang.Object)
   */
  @Override
  public int indexOf(Object o) {
    return indexOf(o) - mStart;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return mStart == mList.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#size()
   */
  @Override
  public int size() {
    return super.size() - mStart;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#subList(int, int)
   */
  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return super.subList(fromIndex + mStart, toIndex + mStart);
  }
}
