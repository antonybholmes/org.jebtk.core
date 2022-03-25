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
import java.util.ListIterator;

/**
 * The Class TailListIterator.
 *
 * @param <T> the generic type
 */
public class TailListIterator<T> implements ListIterator<T> {

  /** The m list. */
  private final List<T> mList;

  /** The m start. */
  private final int mStart;

  /** The m I. */
  private int mI;

  /**
   * Instantiates a new tail list iterator.
   *
   * @param list  the list
   * @param start the start
   */
  TailListIterator(List<T> list, int start) {
    mList = list;
    mStart = start;
    mI = start;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#add(java.lang.Object)
   */
  @Override
  public void add(T arg0) {
    // Do nothing
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    return mI < mList.size() - 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#hasPrevious()
   */
  @Override
  public boolean hasPrevious() {
    return mI > mStart;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#next()
   */
  @Override
  public T next() {
    return mList.get(mI++);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#nextIndex()
   */
  @Override
  public int nextIndex() {
    return mI;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#previous()
   */
  @Override
  public T previous() {
    return mList.get(--mI);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#previousIndex()
   */
  @Override
  public int previousIndex() {
    return mI - 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#remove()
   */
  @Override
  public void remove() {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ListIterator#set(java.lang.Object)
   */
  @Override
  public void set(T arg0) {
    // TODO Auto-generated method stub

  }

}
