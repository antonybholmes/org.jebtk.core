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
import java.util.Collections;

/**
 * Provides a fixed size circular buffer that removes the oldest element once
 * the buffer is filled. The implementation means there is no array resizing or
 * copying.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class CircularArray<T> extends ArrayList<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member offset.
   */
  private int mOffset = 0;

  /**
   * The member capacity.
   */
  private int mCapacity = 0;

  /**
   * The member size.
   */
  private int mSize = 0;

  /**
   * Instantiates a new circular array.
   *
   * @param capacity the capacity
   */
  public CircularArray(int capacity) {
    super(Collections.nCopies(capacity, (T) null));

    mCapacity = capacity;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#add(java.lang.Object)
   */
  @Override
  public boolean add(T e) {
    set(mOffset, e);

    mSize = Math.min(mSize + 1, mCapacity);

    mOffset = (mOffset + 1) % mSize;

    return true;
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
   * @see java.util.ArrayList#remove(int)
   */
  @Override
  public T remove(int i) {
    return get(i);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#get(int)
   */
  @Override
  public T get(int index) {
    return super.get((index + mOffset) % mSize);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#size()
   */
  @Override
  public int size() {
    return mSize;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#clear()
   */
  @Override
  public void clear() {
    reset();
  }

  /**
   * Reset.
   */
  public void reset() {
    mOffset = 0;
    mSize = 0;
  }

  /**
   * Gets the capacity.
   *
   * @return the capacity
   */
  public int getCapacity() {
    return mCapacity;
  }

}
