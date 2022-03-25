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
 * See the License for the specific language governing permissions andgetAlpha
 * limitations under the License.
 */
package org.jebtk.core;

import java.awt.Color;
import java.awt.GradientPaint;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jebtk.core.collections.CollectionUtils;

/**
 * The class ColorUtils.
 */
public class ColorUtils {

  /**
   * The constant HTML_COLOR_WHITE.
   */
  public static final String HTML_COLOR_WHITE = "#ffffff";

  /** The Constant COLOR_PATTERN. */
  public static final Pattern COLOR_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})");

  /** Constant representing a transparent color */
  public static final CSSColor TRANS = new CSSColor(0, 0, 0, 0);
  
  /**
   * Instantiates a new color utils.
   */
  private ColorUtils() {
    // Do nothing
  }

  /**
   * Decode a HTML color string like '#F567BA;' into a {@link CSSColor}.
   *
   * @param colorString The string to decode.
   * @return The decoded color
   */
  public static CSSColor decodeHtmlColor(String colorString) {
    if (colorString == null) {
      return null;
    }

    CSSColor color = null;

    if (colorString.startsWith("#")) {
      colorString = colorString.substring(1);
    }

    if (colorString.endsWith(";")) {
      colorString = colorString.substring(0, colorString.length() - 1);
    }

    int red;
    int green;
    int blue;
    int opacity;

    switch (colorString.length()) {
    case 8:
      // opacity
      red = Integer.parseInt(colorString.substring(0, 2), 16);
      green = Integer.parseInt(colorString.substring(2, 4), 16);
      blue = Integer.parseInt(colorString.substring(4, 6), 16);
      opacity = Integer.parseInt(colorString.substring(6, 8), 16);
      color = new CSSColor(red, green, blue, opacity);
      break;
    case 6:
      red = Integer.parseInt(colorString.substring(0, 2), 16);
      green = Integer.parseInt(colorString.substring(2, 4), 16);
      blue = Integer.parseInt(colorString.substring(4, 6), 16);

      // System.err.println(colorString + " " + red + " " + green + " " + blue);

      color = new CSSColor(red, green, blue);
      break;
    case 3:
      red = Integer.parseInt(colorString.substring(0, 1), 16);
      green = Integer.parseInt(colorString.substring(1, 2), 16);
      blue = Integer.parseInt(colorString.substring(2, 3), 16);
      color = new CSSColor(red, green, blue);
      break;
    case 1:
      red = Integer.parseInt(colorString.substring(0, 1), 16);
      green = red;
      blue = red;

      color = new CSSColor(red, green, blue);
      break;
    default:
      color = CSSColor.CSS_WHITE;
      break;
    }

    return color;
  }

  /**
   * Converts a color to html hex notation.
   *
   * @param color the color
   * @return the string
   */
  public static String toHtml(Color color) {
    if (color == null) {
      return HTML_COLOR_WHITE;
    }

    StringBuilder buffer = new StringBuilder();

    buffer.append("#");
    buffer.append(hexString(color.getRed()));
    buffer.append(hexString(color.getGreen()));
    buffer.append(hexString(color.getBlue()));
    buffer.append(hexString(color.getAlpha()));

    return buffer.toString();
  }

  /**
   * Hex string.
   *
   * @param value the value
   * @return the string
   */
  public static String hexString(int value) {
    String hexString = Integer.toHexString(value);

    if (hexString.length() < 2) {
      hexString = "0" + hexString;
    }

    return hexString;
  }

  /**
   * Random color.
   *
   * @return the color
   */
  public static CSSColor randomColor() {
    Random rand = new Random();

    return new CSSColor(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
  }

  /**
   * Adjust the alpha on a color.
   *
   * @param color the color
   * @param alpha the alpha
   * @return the transparent color
   */
  public static CSSColor getColor(Color color, double alpha) {
    if (color == null) {
      return null;
    }

    return new CSSColor(color.getRed(), color.getGreen(), color.getBlue(), (int) (Mathematics.bound(alpha, 0, 1) * 255));
  }

  public static CSSColor getTransparentColor(Color color, double alpha) {
    if (color == null) {
      return null;
    }

    return new CSSColor(color.getRed(), color.getGreen(), color.getBlue(),
        Mathematics.bound((int) ((1 - alpha) * 255), 0, 255));
  }

  public static double getTrans(Color color) {
    return Mathematics.bound((255 - color.getAlpha()) / 255.0, 0, 1);
  }

  /**
   * Get the alpha value of a color scaled between 0 and 1.
   * 
   * @param color
   * @return
   */
  public static double getAlpha(Color color) {
    return color.getAlpha() / 255.0;
  }

  /**
   * Gets the transparent color 100.
   *
   * @param color the color
   * @return the transparent color 100
   */
  public static CSSColor getTransparentColor100(Color color) {
    return getTransparentColor(color, 1);
  }

  /**
   * Gets the transparent color90.
   *
   * @param color the color
   * @return the transparent color90
   */
  public static CSSColor getTransparentColor90(Color color) {
    return getTransparentColor(color, 0.9);
  }

  /**
   * Return a copy of a color that is 95% transparent.
   *
   * @param color the color
   * @return the transparent color 95
   */
  public static CSSColor getTransparentColor95(Color color) {
    return getTransparentColor(color, 0.95);
  }

  /**
   * Gets the transparent color80.
   *
   * @param color the color
   * @return the transparent color80
   */
  public static CSSColor getTransparentColor85(Color color) {
    return getTransparentColor(color, 0.85);
  }

  /**
   * Gets the transparent color80.
   *
   * @param color the color
   * @return the transparent color80
   */
  public static CSSColor getTransparentColor80(Color color) {
    return getTransparentColor(color, 0.8);
  }

  /**
   * Returns a transparent version of a color.
   *
   * @param color the color
   * @return the transparent color75
   */
  public static CSSColor getTransparentColor75(Color color) {
    return getTransparentColor(color, 0.75);
  }

  /**
   * Returns a copy of the color that is 70% transparent.
   *
   * @param color the color
   * @return the transparent color70
   */
  public static CSSColor getTransparentColor70(Color color) {
    return getTransparentColor(color, 0.7);
  }

  /**
   * Gets the transparent color60.
   *
   * @param color the color
   * @return the transparent color60
   */
  public static CSSColor getTransparentColor60(Color color) {
    return getTransparentColor(color, 0.6);
  }

  /**
   * Gets the transparent color50.
   *
   * @param color the color
   * @return the transparent color50
   */
  public static CSSColor getTransparentColor50(Color color) {
    return getTransparentColor(color, 0.5);
  }

  /**
   * Gets the transparent color40.
   *
   * @param color the color
   * @return the transparent color40
   */
  public static CSSColor getTransparentColor40(Color color) {
    return getTransparentColor(color, 0.4);
  }

  /**
   * Gets the transparent color 30.
   *
   * @param color the color
   * @return the transparent color 30
   */
  public static CSSColor getTransparentColor30(Color color) {
    return getTransparentColor(color, 0.3);
  }

  /**
   * Gets the transparent color25.
   *
   * @param color the color
   * @return the transparent color25
   */
  public static CSSColor getTransparentColor25(Color color) {
    return getTransparentColor(color, 0.25);
  }

  /**
   * Gets the transparent color 20.
   *
   * @param color the color
   * @return the transparent color 20
   */
  public static CSSColor getTransparentColor20(Color color) {
    return getTransparentColor(color, 0.2);
  }

  /**
   * Gets the transparent color10.
   *
   * @param color the color
   * @return the transparent color10
   */
  public static CSSColor getTransparentColor10(Color color) {
    return getTransparentColor(color, 0.1);
  }

  /**
   * Gets the transparent color 0.
   *
   * @param color the color
   * @return the transparent color 0
   */
  public static CSSColor getTransparentColor0(Color color) {
    return getTransparentColor(color, 0);
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    CSSColor color = ColorUtils.decodeHtmlColor("#2c5aa0");

    System.err.println(color.getRed() + " " + color.getBlue() + " " + color.getGreen());
  }

  /**
   * Return a grayscale color where 1 = 100% gray (i.e black) and 0 = 0% gray i.e.
   * white.
   *
   * @param d the d
   * @return the gray scale
   */
  public static CSSColor getGrayScale(double d) {
    int c = (int) (Math.max(0, Math.min(d, 1.0 - d)) * 255);

    return new CSSColor(c, c, c);
  }

  /**
   * Lighten.
   *
   * @param color the color
   * @param d     the d
   * @return the color
   */
  public static CSSColor lighten(CSSColor color, double d) {
    float[] v = CSSColor.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

    // Adjust the saturation
    v[2] = (float) Mathematics.bound(d, 0, 1); // (float)Mathematics.bound((1 -
                                               // v[2]) * (1 + d), 0, 1);
                                               // //(float)Math.min(1, v[1] * (1
                                               // + Math.max(0, Math.min(1,
                                               // d))));

    return new CSSColor(Color.HSBtoRGB(v[0], v[1], v[2]));
  }

  /**
   * Saturation.
   *
   * @param color the color
   * @param d     the d
   * @return the color
   */
  public static CSSColor saturation(Color color, double d) {
    float[] v = CSSColor.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

    // Adjust the saturation
    v[1] = (float) Mathematics.bound(v[1] + ((1 - v[1]) * (1 + d)), 0, 1); // (float)Math.min(1,
                                                                           // v[1]
                                                                           // *
                                                                           // (1
                                                                           // +
                                                                           // Math.max(0,
                                                                           // Math.min(1,
                                                                           // d))));

    return new CSSColor(Color.HSBtoRGB(v[0], v[1], v[2]));
  }

  /**
   * Tint a color by adding white to it.
   * 
   * @param color
   * @param d
   * @return
   */
  public static CSSColor tint(Color color, double d) {
    int r = color.getRed();
    r = Mathematics.bound(r + (int) ((255 - r) * d), 0, 255);
    int g = color.getGreen();
    g = Mathematics.bound(g + (int) ((255 - g) * d), 0, 255);
    int b = color.getBlue();
    b = Mathematics.bound(b + (int) ((255 - b) * d), 0, 255);

    return new CSSColor(r, g, b);
  }

  /**
   * Red.
   *
   * @param p the p
   * @return the color
   */
  public static CSSColor red(float p) {
    return makeColor(1.0f, 1.0f - p, 1.0f - p, 1);
  }

  /**
   * Blue.
   *
   * @param p the p
   * @return the color
   */
  public static CSSColor blue(float p) {
    return makeColor(1.0f - p, 1.0f - p, 1.0f, 1);
  }

  /**
   * Green.
   *
   * @param p the p
   * @return the color
   */
  public static CSSColor green(float p) {
    return makeColor(1.0f - p, 1.0f, 1.0f - p, 1);
  }

  /**
   * Make color.
   *
   * @param r the r
   * @param g the g
   * @param b the b
   * @param a the a
   * @return the color
   */
  public static CSSColor makeColor(float r, float g, float b, float a) {
    return new CSSColor(Mathematics.bound(r, 0, 1), Mathematics.bound(g, 0, 1), Mathematics.bound(b, 0, 1),
        Mathematics.bound(a, 0, 1));
  }

  /**
   * Checks if is html color.
   *
   * @param value the value
   * @return true, if is html color
   */
  public static boolean isHtmlColor(String value) {
    if (value == null) {
      return false;
    }

    Matcher matcher = COLOR_PATTERN.matcher(value);

    return matcher.find();
  }

  /**
   * Create a gradient color.
   *
   * @param y1     the y 1
   * @param y2     the y 2
   * @param color1 the color 1
   * @param color2 the color 2
   * @return the v gradient
   */
  public static GradientPaint getVGradient(int y1, int y2, Color color1, Color color2) {
    return new GradientPaint(0, y1, color1, 0, y2, color2, false);
  }

  public static CSSColor[] trans(Color[] colors, final double trans) {
    CSSColor[] ret = new CSSColor[colors.length];

    CollectionUtils.apply(colors, ret, (Color color) -> getTransparentColor(color, trans));

    return ret;
  }

  public static CSSColor[] alpha(Color[] colors, final double alpha) {
    CSSColor[] ret = new CSSColor[colors.length];

    CollectionUtils.apply(colors, ret, (Color color) -> getColor(color, alpha));

    return ret;
  }
}