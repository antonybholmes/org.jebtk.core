/**
 * Copyright 2017 Antony Holmes
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
package org.jebtk.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The Class Range provides an iterable range object similar to python for
 * compactly iterating a variable over a number range instead of writing for
 * (int i = 0; i < x; ++i)... etc.
 */
public class Range implements Iterable<Integer> {

  /** The m start. */
  private final int mStart;

  /** The m length. */
  private final int mEnd;

  private final int mSkip;

  /**
   * Instantiates a new range.
   *
   * @param length the length
   */
  public Range(int length) {
    this(0, length);
  }

  public Range(int start, int end) {
    this(start, end, 1);
  }

  /**
   * Instantiates a new range.
   *
   * @param start  the start
   * @param length the length
   */
  public Range(int start, int end, int skip) {
    mStart = start;
    mEnd = end;
    mSkip = skip;

  }

  /*
   * @Override public boolean hasNext() { return mStart < mLength; }
   * 
   * @Override public Integer next() { if (hasNext()) { return mStart++; } else {
   * throw new NoSuchElementException("Range ended"); } }
   * 
   * @Override public void remove() { throw new
   * UnsupportedOperationException("Cannot remove values from a Range"); }
   */

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Integer> iterator() {
    return new Iterator<Integer>() {
      private int mCurrent = mStart;

      @Override
      public boolean hasNext() {
        return mCurrent < mEnd;
      }

      @Override
      public Integer next() {
        if (hasNext()) {
          int ret = mCurrent;
          mCurrent += mSkip;
          return ret;
        } else {
          throw new NoSuchElementException("Range ended");
        }
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("Cannot remove values from a Range");
      }
    };
  }

  public void forEach(ForEach l) {
    forEach(this, l);
  }

  public static void forEach(Range r, ForEach l) {
    for (int i : r) {
      l.loop(i);
    }
  }

  /**
   * Creates a new Range.
   *
   * @param length The max value (exclusive) of the range, thus 5 will generate
   *               0,1,2,3,4.
   * @return A range object.
   */
  public static Range create(int length) {
    return create(0, length);
  }

  /**
   * Creates the.
   *
   * @param start  the start
   * @param length the length
   * @return the range
   */
  public static Range create(int start, int end) {
    return create(start, end, 1);
  }

  public static Range create(int start, int end, int skip) {
    return new Range(start, end, skip);
  }
}