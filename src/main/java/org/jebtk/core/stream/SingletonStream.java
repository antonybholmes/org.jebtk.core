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
package org.jebtk.core.stream;

import java.util.List;

import org.jebtk.core.collections.CollectionUtils;

/**
 * Can be used to encapsulate a single item in a stream.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class SingletonStream<T> extends Stream<T> {

  /** The m item. */
  private T mItem;

  private boolean mNext = true;

  /**
   * Instantiates a new base stream.
   *
   * @param item the item
   */
  public SingletonStream(T item) {
    mItem = item;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.stream.Stream#toList()
   */
  @Override
  public List<T> toList() {
    return CollectionUtils.asList(mItem);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.stream.Stream#size()
   */
  @Override
  public int size() {
    return 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    return mNext;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public T next() {
    mNext = false;
    return mItem;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mItem.toString();
  }
}
