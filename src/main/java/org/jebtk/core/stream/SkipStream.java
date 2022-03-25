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
 * Skip the first n items in a stream.
 *
 * @param <T> the generic type
 */
public class SkipStream<T> extends ContainerStream<T> {

  /** The m N. */
  private int mN;

  /** The m skip mode. */
  private boolean mSkipMode = true;

  /**
   * Instantiates a new filter stream.
   *
   * @param stream the stream
   * @param n      the n
   */
  public SkipStream(Stream<T> stream, int n) {
    super(stream);

    mN = n;
  }

  /**
   * Ignore the first n items.
   */
  private void skip() {
    if (mSkipMode) {
      for (int i = 0; i < mN; ++i) {
        mStream.next();
      }

      mSkipMode = false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#hasNext()
   */
  @Override
  public boolean hasNext() {
    skip();

    return super.hasNext();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public T next() {
    skip();

    return super.next();
  }
}
