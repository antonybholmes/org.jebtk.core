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

/**
 * An immutable point.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class Position<T extends Number> {

  /**
   * The member x.
   */
  public final T mX;

  /**
   * The member y.
   */
  public final T mY;

  /**
   * Instantiates a new position.
   *
   * @param x the x
   * @param y the y
   */
  public Position(T x, T y) {
    mX = x;
    mY = y;
  }

  /**
   * Gets the x.
   *
   * @return the x
   */
  public T getX() {
    return mX;
  }

  /**
   * Gets the y.
   *
   * @return the y
   */
  public T getY() {
    return mY;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mX + " " + mY;
  }
}
