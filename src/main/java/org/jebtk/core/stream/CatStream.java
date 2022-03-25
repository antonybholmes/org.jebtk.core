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
 * Concatenate two streams.
 *
 * @param <T> the generic type
 */
public class CatStream<T> extends Stream<T> {

  /** The m stream. */
  private Stream<T> mS1;

  /** The m S 2. */
  private Stream<T> mS2;

  /** The first. */
  private boolean first = true;

  /**
   * Instantiates a new map stream.
   *
   * @param s1 the s 1
   * @param s2 the s 2
   */
  public CatStream(Stream<T> s1, Stream<T> s2) {
    mS1 = s1;
    mS2 = s2;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    return mS2.hasNext();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public T next() {
    if (first) {
      T v = mS1.next();

      if (!mS1.hasNext()) {
        first = false;
      }

      return v;
    } else {
      return mS2.next();
    }
  }
}
