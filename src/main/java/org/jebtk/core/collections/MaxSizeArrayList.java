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
import java.util.Collection;

/**
 * An array list whose size has a fixed upper bound.
 *
 * @param <T> the generic type
 */
public class MaxSizeArrayList<T> extends ArrayList<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /** The m max size. */
  private int mMaxSize = -1;

  /**
   * Creates a new array that cannot grow to beyond max size elements.
   * 
   * @param maxSize The maximum size of the array. If set to -1 array behaves like
   *                a normal array list.
   */
  public MaxSizeArrayList(int maxSize) {
    mMaxSize = maxSize;
  }

  /**
   * Instantiates a new unique list.
   *
   * @param size    the size
   * @param maxSize the max size
   */
  public MaxSizeArrayList(int size, int maxSize) {
    super(size);

    mMaxSize = maxSize;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#add(java.lang.Object)
   */
  @Override
  public boolean add(T item) {
    if (mMaxSize == -1 || size() < mMaxSize) {
      super.add(item);
    }

    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#add(int, java.lang.Object)
   */
  @Override
  public void add(int index, T item) {
    if (mMaxSize == -1 || size() < mMaxSize) {
      super.add(index, item);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends T> items) {
    for (T item : items) {
      add(item);
    }

    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#addAll(int, java.util.Collection)
   */
  @Override
  public boolean addAll(int index, Collection<? extends T> items) {
    if (mMaxSize == -1 || items.size() + size() <= mMaxSize) {
      super.addAll(index, items);
    }

    return true;
  }
}
