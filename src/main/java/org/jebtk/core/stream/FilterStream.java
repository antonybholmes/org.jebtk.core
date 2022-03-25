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

/**
 * The Class FilterStream.
 *
 * @param <T> the generic type
 */
public class FilterStream<T> extends ContainerStream<T> {

  /** The m filter. */
  private Filter<T> mFilter;

  /**
   * Instantiates a new filter stream.
   *
   * @param stream the stream
   * @param filter the filter
   */
  public FilterStream(Stream<T> stream, Filter<T> filter) {
    super(stream);

    mFilter = filter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public T next() {
    return getNext();
  }

  /**
   * Returns the next item in the parent stream that matches the filter.
   *
   * @return the next
   */
  private T getNext() {
    T next = super.next();

    while (!mFilter.keep(next)) {
      next = super.next();

      if (next == null) {
        break;
      }
    }

    return next;
  }
}
