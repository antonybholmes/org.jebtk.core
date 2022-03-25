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

import java.util.Iterator;
import java.util.List;

import org.jebtk.core.collections.CollectionUtils;

/**
 * The Class BaseStream encapulates an initial collection with the intent of
 * apply further streams to process the list.
 *
 * @param <T> the generic type
 */
public class IteratorStream<T> extends Stream<T> {

  /** The m iter. */
  private Iterator<T> mIter;

  /** The m current. */
  private T mCurrent = null;

  /**
   * Instantiates a new base stream.
   *
   * @param iter the iter
   */
  public IteratorStream(Iterator<T> iter) {
    mIter = iter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.stream.Stream#toList()
   */
  @Override
  public List<T> toList() {
    return CollectionUtils.toList(mIter);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    return mIter.hasNext();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public T next() {
    mCurrent = mIter.next();

    return mCurrent;
  }
}
