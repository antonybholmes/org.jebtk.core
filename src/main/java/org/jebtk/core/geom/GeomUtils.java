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

import java.awt.Rectangle;

/**
 * The Class GeomUtils.
 */
public class GeomUtils {

  /** The Constant DIM_ZERO. */
  public static final IntDim DIM_ZERO = IntDim.DIM_ZERO;

  /** The Constant INT_POINT_ZERO. */
  public static final IntPos2D INT_POINT_ZERO = new IntPos2D(0, 0);

  public static final DoublePos2D DOUBLE_POINT_ZERO = new DoublePos2D(0, 0);

  /**
   * Instantiates a new geom utils.
   */
  private GeomUtils() {
    // Do nothing
  }

  /**
   * Returns a clone of a rectangle where the height has been adjusted.
   *
   * @param rect   the rect
   * @param height the height
   * @return the rectangle
   */
  public static Rectangle setHeight(Rectangle rect, int height) {
    return new Rectangle(rect.x, rect.y, rect.width, height);
  }
}
