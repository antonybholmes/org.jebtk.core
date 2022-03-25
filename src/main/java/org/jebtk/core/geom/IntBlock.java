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
package org.jebtk.core.geom;

import java.awt.Dimension;

/**
 * Immutable integer dimension.
 * 
 * @author Antony Holmes
 *
 */
public class IntBlock implements Comparable<IntBlock> {

  /** The Constant DIM_ZERO. */
  public static final IntBlock DIM_ZERO = new IntBlock(0, 0);

  /**
   * The member w.
   */
  public final int mX;

  /**
   * The member h.
   */
  public final int mW;

  /**
   * Instantiates a new int dim.
   *
   * @param w the w
   * @param h the h
   */
  public IntBlock(int w, int h) {
    mX = w;
    mW = h;
  }

  /**
   * Gets the w.
   *
   * @return the w
   */
  public int getX() {
    return mX;
  }

  /**
   * Gets the h.
   *
   * @return the h
   */
  public int getW() {
    return mW;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mX + " " + mW;
  }

  /**
   * To dimension.
   *
   * @param size the size
   * @return the dimension
   */
  public static Dimension toDimension(IntBlock size) {
    return new Dimension(size.getX(), size.getW());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof IntBlock) {
      return compareTo((IntBlock) o) == 0;
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
  public int compareTo(IntBlock d) {
    if (mX > d.mX) {
      if (mW > d.mW) {
        return 1;
      } else {
        return -1;
      }
    } else if (mX < d.mX) {
      if (mW > d.mW) {
        return 1;
      } else {
        return -1;
      }
    } else {
      // Same width so just consider height

      if (mW > d.mW) {
        return 1;
      } else if (mW < d.mW) {
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
  public static IntBlock create(int x, int y) {
    return new IntBlock(x, y);
  }

  /**
   * Creates the.
   *
   * @param x the x
   * @param y the y
   * @return the int dim
   */
  public static IntBlock create(long x, long y) {
    return new IntBlock((int) x, (int) y);
  }

  /**
   * Creates the.
   *
   * @param x the x
   * @param y the y
   * @return the int dim
   */
  public static IntBlock create(double x, double y) {
    return new IntBlock((int) x, (int) y);
  }
}
