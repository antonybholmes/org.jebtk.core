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
 * The Class ReplicateStream replicates each value in a given stream n times so
 * that the length of a stream can be expanded.
 *
 * @param <T> the generic type
 */
public class ReplicateStream<T> extends ContainerStream<T> {

  /** The m next. */
  private T mNext;

  /** The m N. */
  private final int mN;

  /** The m C. */
  private int mC = 0;

  /**
   * Instantiates a new filter stream.
   *
   * @param stream the stream
   * @param n      the n
   */
  public ReplicateStream(Stream<T> stream, int n) {
    super(stream);

    mN = n;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.stream.ContainerStream#hasNext()
   */
  @Override
  public boolean hasNext() {
    // If we are not on a real value (i.e a value from the parent stream
    // which we only encounter when the counter modulo n == 0) then we
    // are duplicating a value so always return true. Once we reach the
    // end of the parent stream and move through our duplicates, we will
    // eventually get to the next index % 2 == 0 at which point we
    // check the parent hasNext() which returns false.
    return mC % mN != 0 || mStream.hasNext();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Iterator#next()
   */
  @Override
  public T next() {
    if (mC++ % mN == 0) {
      mNext = super.next();
    }

    return mNext;
  }
}
