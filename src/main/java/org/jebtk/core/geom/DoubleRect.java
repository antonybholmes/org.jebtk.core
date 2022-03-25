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

import java.awt.Point;

/**
 * Immutable doubleeger rectangle.
 * 
 * @author Antony Holmes
 *
 */
public class DoubleRect extends DoublePos2D {

  /**
   * The member w.
   */
  public final double mW;

  /**
   * The member h.
   */
  public final double mH;

  /**
   * Instantiates a new double rect.
   *
   * @param x the x
   * @param y the y
   * @param w the w
   * @param h the h
   */
  public DoubleRect(double x, double y, double w, double h) {
    super(x, y);

    mW = w;
    mH = h;
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
   * @see org.abh.lib.doublePosition#toString()
   */
  @Override
  public String toString() {
    return mX + " " + mY + " " + mW + " " + mH;
  }

  /**
   * Contains.
   *
   * @param p the p
   * @return true, if successful
   */
  public boolean contains(Point p) {
    return contains(p, 0);
  }

  /**
   * Contains.
   *
   * @param p       the p
   * @param padding the padding
   * @return true, if successful
   */
  public boolean contains(Point p, double padding) {
    return contains(p.x, p.y, padding);
  }

  /**
   * Returns true if the podouble is within the bounds of the rectangle.
   *
   * @param x the x
   * @param y the y
   * @return true, if successful
   */
  public boolean contains(double x, double y) {
    return contains(x, y, 0);
  }

  /**
   * Returns true if the podouble x y is contained within the rectangle plus a
   * padding allowance.
   *
   * @param x       the x
   * @param y       the y
   * @param padding the padding
   * @return true, if successful
   */
  public boolean contains(double x, double y, double padding) {
    return x >= mX - padding && x <= mX + mW + padding && y >= mY - padding && y <= mY + mH + padding;
  }
}
