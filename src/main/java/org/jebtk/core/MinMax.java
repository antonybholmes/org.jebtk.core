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
 * Immutable integer dimension.
 * 
 * @author Antony Holmes
 *
 */
public class MinMax implements Comparable<MinMax> {
  /**
   * The member w.
   */
  private double mMax;

  /**
   * The member h.
   */
  private double mMin;

  /**
   * Instantiates a new int dim.
   *
   * @param w the w
   * @param h the h
   */
  public MinMax(double min, double max) {
    mMin = min;
    mMax = max;
  }

  public double getMax() {
    return mMax;
  }

  public double getMin() {
    return mMin;
  }

  public void setMin(double min) {
    mMin = min;
  }

  public void setMax(double max) {
    mMax = max;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mMin + " " + mMax;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof MinMax) {
      return compareTo((MinMax) o) == 0;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(MinMax d) {
    if (mMin > d.mMin) {
      if (mMax > d.mMax) {
        return 1;
      } else {
        return -1;
      }
    } else if (mMin < d.mMin) {
      if (mMax > d.mMax) {
        return 1;
      } else {
        return -1;
      }
    } else {
      // Same width so just consider height

      if (mMax > d.mMax) {
        return 1;
      } else if (mMax < d.mMax) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  /**
   * Creates a new IntDim.
   *
   * @param x the x
   * @param y the y
   * @return the int dim
   */
  public static MinMax create(int x, int y) {
    return new MinMax(x, y);
  }

  /**
   * Creates the.
   *
   * @param x the x
   * @param y the y
   * @return the int dim
   */
  public static MinMax create(long x, long y) {
    return new MinMax(x, y);
  }

  /**
   * Creates the.
   *
   * @param x the x
   * @param y the y
   * @return the int dim
   */
  public static MinMax create(double x, double y) {
    return new MinMax(x, y);
  }

}
