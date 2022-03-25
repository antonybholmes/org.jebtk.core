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

import java.awt.Color;

/**
 * Represents a color.
 * 
 * @author Antony Holmes
 *
 */
public class ColorValue {

  /**
   * The member red.
   */
  public int mRed = 0;

  /**
   * The member green.
   */
  public int mGreen = 0;

  /**
   * The member blue.
   */
  public int mBlue = 0;

  /**
   * The member alpha.
   */
  public int mAlpha = 0;

  /**
   * Instantiates a new color value.
   */
  public ColorValue() {
    // TODO Auto-generated constructor stub
  }

  /**
   * Instantiates a new color value.
   *
   * @param red   the red
   * @param green the green
   * @param blue  the blue
   */
  public ColorValue(int red, int green, int blue) {
    this(red, green, blue, 255);
  }

  /**
   * Instantiates a new color value.
   *
   * @param red   the red
   * @param green the green
   * @param blue  the blue
   * @param alpha the alpha
   */
  public ColorValue(int red, int green, int blue, int alpha) {
    mRed = red;
    mGreen = green;
    mBlue = blue;
    mAlpha = alpha;
  }

  /**
   * Instantiates a new color value.
   *
   * @param color the color
   */
  public ColorValue(ColorValue color) {
    this(color.mRed, color.mGreen, color.mBlue, color.mAlpha);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return mRed + " " + mGreen + " " + mBlue + " " + mAlpha;
  }

  /**
   * Convert.
   *
   * @param color the color
   * @return the color value
   */
  public static ColorValue convert(Color color) {
    return new ColorValue(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
  }

  /**
   * Convert.
   *
   * @param color the color
   * @return the color
   */
  public static Color convert(ColorValue color) {
    return new Color(color.mRed, color.mGreen, color.mBlue, color.mAlpha);
  }
}
