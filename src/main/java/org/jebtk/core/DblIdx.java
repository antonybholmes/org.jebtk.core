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
package org.jebtk.core;

/**
 * Generic index object that allows one object to be associated with some form
 * of index.
 *
 * @author Antony Holmes
 * @param <K> the generic type
 * @param <V> the value type
 */
public class DblIdx implements Comparable<DblIdx> {

  /**
   * The member index.
   */
  private int mIndex;

  /**
   * The member value.
   */
  private double mValue;

  /**
   * Instantiates a new indexed value.
   *
   * @param index the index
   * @param item  the item
   */
  public DblIdx(int index, double item) {
    mValue = item;
    mIndex = index;

    // mHash = mValue.toString() + index;
  }

  /**
   * Gets the index.
   *
   * @return the index
   */
  public int getIndex() {
    return mIndex;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public double getValue() {
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "[" + mIndex + ", " + mValue + "]";
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(DblIdx l) {
    return Mathematics.compareTo(mValue, l.mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof DblIdx) {
      return compareTo((DblIdx) o) == 0;
    } else {
      return false;
    }
  }

  /**
   * Return the items indexed.
   *
   * @param items the items
   * @return the list
   */
  public static DblIdx[] index(double[] items) {
    DblIdx[] ret = new DblIdx[items.length];

    int c = 0;

    for (double item : items) {
      ret[c++] = new DblIdx(c, item);
    }

    return ret;
  }
}
