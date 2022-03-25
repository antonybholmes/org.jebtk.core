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
 * Immutable doubleeger dimension.
 * 
 * @author Antony Holmes
 *
 */
public class DoubleDim implements Comparable<DoubleDim> {

  /**
   * The member w.
   */
  public final double mW;

  /**
   * The member h.
   */
  public final double mH;

  public DoubleDim(double w) {
    this(w, w);
  }

  /**
   * Instantiates a new double dim.
   *
   * @param w the w
   * @param h the h
   */
  public DoubleDim(double w, double h) {
    mW = w;
    mH = h;
  }

  /**
   * Instantiates a new double dim.
   *
   * @param dim the dim
   */
  public DoubleDim(Dimension dim) {
    this(dim.width, dim.height);
  }

  /**
   * Gets the w.
   *
   * @return the w
   */
  public double getW() {
    return mW;
  }

  /**
   * Gets the h.
   *
   * @return the h
   */
  public double getH() {
    return mH;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mW + " " + mH;
  }

  /**
   * To dimension.
   *
   * @param size the size
   * @return the dimension
   */
  public static Dimension toDimension(DoubleDim size) {
    return new Dimension((int) size.getW(), (int) size.getH());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof DoubleDim) {
      return compareTo((DoubleDim) o) == 0;
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
  public int compareTo(DoubleDim d) {
    if (mW > d.mW) {
      if (mH > d.mH) {
        return 1;
      } else {
        return -1;
      }
    } else if (mW < d.mW) {
      if (mH > d.mH) {
        return 1;
      } else {
        return -1;
      }
    } else {
      // Same width so just consider height

      if (mH > d.mH) {
        return 1;
      } else if (mH < d.mH) {
        return -1;
      } else {
        return 0;
      }
    }
  }
}
