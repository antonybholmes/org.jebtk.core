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
public class IntDim implements Comparable<IntDim> {

  /** The Constant DIM_ZERO. */
  public static final IntDim DIM_ZERO = new IntDim(0, 0);

  /**
   * The member w.
   */
  public final int w;

  /**
   * The member h.
   */
  public final int h;

  /**
   * Instantiates a new int dim.
   *
   * @param w the w
   * @param h the h
   */
  public IntDim(int w, int h) {
    this.w = w;
    this.h = h;
  }

  /**
   * Instantiates a new int dim.
   *
   * @param dim the dim
   */
  public IntDim(Dimension dim) {
    this(dim.width, dim.height);
  }

  /**
   * Gets the w.
   *
   * @return the w
   */
  public int getW() {
    return w;
  }

  /**
   * Gets the h.
   *
   * @return the h
   */
  public int getH() {
    return h;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return w + " " + h;
  }

  /**
   * To dimension.
   *
   * @param size the size
   * @return the dimension
   */
  public static Dimension toDimension(IntDim size) {
    return new Dimension(size.getW(), size.getH());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof IntDim) {
      return compareTo((IntDim) o) == 0;
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
  public int compareTo(IntDim d) {
    if (w > d.w) {
      if (h > d.h) {
        return 1;
      } else {
        return -1;
      }
    } else if (w < d.w) {
      if (h > d.h) {
        return 1;
      } else {
        return -1;
      }
    } else {
      // Same width so just consider height

      if (h > d.h) {
        return 1;
      } else if (h < d.h) {
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
  public static IntDim create(int x, int y) {
    return new IntDim(x, y);
  }

  /**
   * Creates the.
   *
   * @param x the x
   * @param y the y
   * @return the int dim
   */
  public static IntDim create(long x, long y) {
    return new IntDim((int) x, (int) y);
  }

  /**
   * Creates the.
   *
   * @param x the x
   * @param y the y
   * @return the int dim
   */
  public static IntDim create(double x, double y) {
    return new IntDim((int) x, (int) y);
  }

  /**
   * Create a new IntDim from a Dimension.
   *
   * @param dim the dim
   * @return An IntDim
   */
  public static IntDim create(Dimension dim) {
    return new IntDim(dim);
  }
}
