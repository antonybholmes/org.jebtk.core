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

import java.awt.Point;
import java.awt.Rectangle;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jebtk.core.collections.UniqueArrayList;
import org.jebtk.core.geom.IntPos2D;

/**
 * Extended mathematical functions not provided by the standard Java Math
 * library.
 * 
 * @author Antony Holmes
 *
 */
public class Mathematics {

  /**
   * The constant LOG2_DENOMINATOR.
   */
  private static final double LOG2_DENOMINATOR = Math.log(2);

  /**
   * The constant LOG10_DENOMINATOR.
   */
  private static final double LOG10_DENOMINATOR = Math.log(10);

  /**
   * The constant QUARTER_PI.
   */
  public static final double QUARTER_PI = Math.PI / 4.0;

  /**
   * The constant LOG_MIN.
   */
  private static final double LOG_MIN = 1E-10;

  /**
   * The constant HALF_PI.
   */
  public static final double HALF_PI = Math.PI / 2;

  /**
   * The constant GOLDEN_RATIO.
   */
  public static final double GOLDEN_RATIO = 1.61803398875;

  /**
   * The constant RATIO_43.
   */
  public static final double RATIO_43 = 1.33333333;

  /**
   * The constant TWO_PI.
   */
  public static final double TWO_PI = 2 * Math.PI;

  /**
   * The constant PI_32.
   */
  public static final double PI_32 = Math.PI * 1.5;

  /**
   * The constant PI.
   */
  public static final double PI = Math.PI;

  public static final float PI_FLOAT = (float) PI;

  /**
   * Instantiates a new mathematics.
   */
  private Mathematics() {
    // do nothing
  }

  /**
   * Log2.
   *
   * @param x the x
   * @return the double
   */
  public static final double log2(double x) {
    return Math.log(Math.max(LOG_MIN, x)) / LOG2_DENOMINATOR;
  }

  /**
   * Log10.
   *
   * @param x the x
   * @return the double
   */
  public static final double log10(double x) {
    return Math.log(Math.max(LOG_MIN, x)) / LOG10_DENOMINATOR;
  }

  /**
   * Return the log of a number in a given base.
   *
   * @param x    the x
   * @param base the base
   * @return the double
   */
  public static final double log(double x, int base) {
    switch (base) {
    case 2:
      return log2(x);
    case 10:
      return log10(x);
    default:
      return Math.log(x) / Math.log(base);
    }
  }

  /**
   * Inverse log2.
   *
   * @param x the x
   * @return the double
   */
  public static final double inverseLog2(double x) {
    return inverseLog(2, x);
  }

  /**
   * Inverse log.
   *
   * @param base the base
   * @param x    the x
   * @return the double
   */
  public static final double inverseLog(int base, double x) {
    return Math.pow(base, x);
  }

  /**
   * Min.
   *
   * @param values the values
   * @return the double
   */
  public static final double min(List<Double> values) {
    double min = Double.MAX_VALUE;

    for (double value : values) {
      min = Math.min(min, value);
    }

    return min;
  }

  /**
   * Min.
   *
   * @param values the values
   * @return the double
   */
  public static final double min(double[] values) {
    double min = Double.MAX_VALUE;

    for (double value : values) {
      min = Math.min(min, value);
    }

    return min;
  }

  /**
   * Returns the maximum value in a list of numbers.
   *
   * @param values the values
   * @return the double
   */
  public static final double max(List<Double> values) {
    double max = Double.MIN_VALUE;

    for (double value : values) {
      max = Math.max(max, value);
    }

    return max;
  }

  /**
   * Returns the max value in an array of integers.
   *
   * @param values the values
   * @return the int
   */
  public static final int max(int[] values) {
    int max = Integer.MIN_VALUE;

    for (int value : values) {
      max = Math.max(max, value);
    }

    return max;
  }

  /**
   * Find the maximum value in a collection of integers.
   *
   * @param values the values
   * @return the int
   */
  public static final int maxInt(Collection<Integer> values) {
    int max = Integer.MIN_VALUE;

    for (int value : values) {
      max = Math.max(max, value);
    }

    return max;
  }

  /**
   * Max.
   *
   * @param values the values
   * @return the double
   */
  public static final double max(double[] values) {
    double max = Double.MIN_VALUE;

    for (double value : values) {
      max = Math.max(max, value);
    }

    return max;
  }

  /**
   * Max.
   *
   * @param values the values
   * @param start  the start
   * @return the double
   */
  public static final double max(double[] values, int start) {
    return max(values, start, values.length - start);
  }

  /**
   * Max.
   *
   * @param values the values
   * @param start  the start
   * @param length the length
   * @return the double
   */
  public static final double max(double[] values, int start, int length) {
    double max = Double.MIN_VALUE;

    int end = start + length;

    for (int i = start; i < end; ++i) {
      max = Math.max(max, values[i]);
    }

    return max;
  }

  /**
   * Returns the sum of a list of numbers.
   *
   * @param l the l
   * @return the double
   */
  public static double sum(List<Double> l) {
    double sum = 0;

    for (double v : l) {
      sum += v;
    }

    return sum;
  }

  /**
   * Returns the sum of a sequence of numbers from 0 to x inclusive using Gauss
   * sumation.
   *
   * @param x the x
   * @return the int
   */
  public static int sum(int x) {
    if (x < 10000) {
      return x * (x + 1) / 2;
    } else {
      // Log version of gauss sumation
      return (int) (Math.round(Math.exp(Math.log(x) + Math.log(x + 1) - Math.log(2))));
    }
  }

  /**
   * Zeros.
   *
   * @param size the size
   * @return the list
   */
  public static List<Double> zeros(int size) {
    return repeat(0, size);
  }

  /**
   * Int zeros.
   *
   * @param size the size
   * @return the list
   */
  public static List<Integer> intZeros(int size) {
    return intRepeat(0, size);
  }

  /**
   * Create a prefilled double array of ones.
   *
   * @param size the size
   * @return the list
   */
  public static List<Double> ones(int size) {
    return repeat(1, size);
  }

  /**
   * Create a double array of a repeating value.
   *
   * @param v    the repeated value.
   * @param size the number of elements in the array.
   * @return the list
   */
  public static List<Double> repeat(double v, int size) {
    List<Double> l = new ArrayList<Double>();

    for (int i = 0; i < size; ++i) {
      l.add(v);
    }

    return l;
  }

  /**
   * Int repeat.
   *
   * @param v    the v
   * @param size the size
   * @return the list
   */
  public static List<Integer> intRepeat(int v, int size) {
    List<Integer> l = new ArrayList<Integer>();

    for (int i = 0; i < size; ++i) {
      l.add(v);
    }

    return l;
  }

  /**
   * Zeros int array.
   *
   * @param size the size
   * @return the int[]
   */
  public static int[] zerosIntArray(int size) {
    return repeatIntArray(0, size);
  }

  /**
   * Ones int array.
   *
   * @param size the size
   * @return the int[]
   */
  public static int[] onesIntArray(int size) {
    return repeatIntArray(1, size);
  }

  /**
   * Repeat int array.
   *
   * @param v    the v
   * @param size the size
   * @return the int[]
   */
  public static int[] repeatIntArray(int v, int size) {
    int[] l = new int[size];

    for (int i = 0; i < size; ++i) {
      l[i] = v;
    }

    return l;
  }

  /**
   * Zeros byte array.
   *
   * @param size the size
   * @return the byte[]
   */
  public static byte[] zerosByteArray(int size) {
    return repeatByteArray((byte) 0, size);
  }

  /**
   * Repeat byte array.
   *
   * @param v    the v
   * @param size the size
   * @return the byte[]
   */
  public static byte[] repeatByteArray(byte v, int size) {
    byte[] l = new byte[size];

    for (int i = 0; i < size; ++i) {
      l[i] = v;
    }

    return l;
  }

  /**
   * Zeros array.
   *
   * @param size the size
   * @return the double[]
   */
  public static double[] zerosArray(int size) {
    return repeatArray(0, size);
  }

  /**
   * Create a prefilled double array of ones.
   *
   * @param size the size
   * @return the double[]
   */
  public static double[] onesArray(int size) {
    return repeatArray(1, size);
  }

  /**
   * Create a prefilled double array of a single value.
   *
   * @param v    the v
   * @param size the size
   * @return the double[]
   */
  public static double[] repeatArray(double v, int size) {
    double[] l = new double[size];

    for (int i = 0; i < size; ++i) {
      l[i] = v;
    }

    return l;
  }

  /**
   * Create a filled float array with a repeated value.
   * 
   * @param v
   * @param size
   * @return
   */
  public static float[] floatRepeat(float v, int size) {
    float[] l = new float[size];

    for (int i = 0; i < size; ++i) {
      l[i] = v;
    }

    return l;
  }

  /**
   * Ones.
   *
   * @param rows    the rows
   * @param columns the columns
   * @return the double[][]
   */
  public static double[][] ones(int rows, int columns) {
    // TODO Auto-generated method stub
    return repeat(1, rows, columns);
  }

  /**
   * Zeros.
   *
   * @param rows    the rows
   * @param columns the columns
   * @return the double[][]
   */
  public static double[][] zeros(int rows, int columns) {
    return repeat(0, rows, columns);
  }

  /**
   * Create a prefilled double array of a single value.
   *
   * @param v       the v
   * @param rows    the rows
   * @param columns the columns
   * @return the double[][]
   */
  public static double[][] repeat(double v, int rows, int columns) {
    double[][] l = new double[rows][columns];

    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < columns; ++j) {
        l[i][j] = v;
      }
    }

    return l;
  }

  /**
   * Generate a sequence of numbers.
   *
   * @param start the start
   * @param end   the end
   * @return the list
   */
  public static List<Integer> sequence(int start, int end) {
    return sequence(start, end, 1);
  }

  /**
   * Generate a sequence of numbers with a fixed increment.
   *
   * @param start     the start
   * @param end       the end
   * @param increment the increment
   * @return the list
   */
  public static List<Integer> sequence(int start, int end, int increment) {
    List<Integer> ret = new ArrayList<Integer>();

    for (int i = start; i <= end; i += increment) {
      ret.add(i);
    }

    return ret;
  }

  public static int[] seq(int start, int end) {
    int[] ret = new int[end - start + 1];

    int i = 0;

    for (int v = start; v <= end; ++v) {
      ret[i++] = v;
    }

    return ret;
  }

  /**
   * Generate a sequence of numbers with a fixed increment.
   *
   * @param start     the start
   * @param end       the end
   * @param increment the increment
   * @return the list
   */
  public static List<Integer> seq(int start, int end, int increment) {
    List<Integer> ret = new ArrayList<Integer>();

    for (int i = start; i <= end; i += increment) {
      ret.add(i);
    }

    return ret;
  }

  /**
   * Linear interpolation of function specified by points x y to evaluate y' of
   * values.
   *
   * @param x      the x
   * @param y      the y
   * @param values the values
   * @return the list
   */
  public static List<Double> linearInterpolation(List<Double> x, List<Double> y, List<Double> values) {

    System.err.println("interpolating");

    List<Double> ret = new ArrayList<Double>(values.size());

    // pair and sort the x y values
    // index on y since we want to sort by x
    List<Indexed<Double, Double>> indexed = Indexed.pair(y, x);

    // sort by x
    Collections.sort(indexed);

    boolean found = false;
    double yd;
    double xd;
    double vi;
    double v;

    for (int i = 0; i < values.size(); ++i) {
      v = values.get(i);

      found = false;

      /*
       * // Modified binary search to find points to // interpolate between. Assumes
       * values are // ordered smallest to largest.
       * 
       * mins = 0; // skip last element maxs = indexed.size() - 2;
       * 
       * while(maxs >= mins) { mids = mins + (maxs - mins) / 2;
       * 
       * //System.err.println("khsdfhsd " + mids);
       * 
       * if (indexed.get(mids).value <= v && indexed.get(mids + 1).value > v) { //
       * found the correct bin
       * 
       * yd = indexed.get(mids + 1).index - indexed.get(mids).index;
       * 
       * xd = (v - indexed.get(mids).value) / (indexed.get(mids + 1).value -
       * indexed.get(mids).value);
       * 
       * vi = indexed.get(mids).index + yd * xd;
       * 
       * ret.add(vi);
       * 
       * found = true;
       * 
       * break; } else if (indexed.get(mids).value == indexed.get(mids + 1).value) {
       * // In regions where there are multiple bins with the same // value, keep
       * skipping forward one until we find a bin // boundary. This portion of the
       * algorithm is searching // linearly, but for the most part, if the values are
       * // reasonably unique, the algorithm will perform in N(log 2) ++mins; } else
       * if (indexed.get(mids).value > v) { maxs = mids - 1; } else if
       * (indexed.get(mids).value < v) { mins = mids + 1; } else { break; } }
       */

      for (int j = 0; j < indexed.size() - 1; ++j) {
        // find where this v lies between

        if (indexed.get(j).getValue() <= v && indexed.get(j + 1).getValue() > v) {
          // interpolate

          yd = indexed.get(j + 1).getIndex() - indexed.get(j).getIndex();

          xd = (v - indexed.get(j).getValue()) / (indexed.get(j + 1).getValue() - indexed.get(j).getValue());

          vi = indexed.get(j).getIndex() + yd * xd;

          ret.add(vi);

          found = true;

          break;
        }
      }

      if (!found) {
        // the value lies outside the function range
        // so just use the max value
        ret.add(indexed.get(indexed.size() - 1).getIndex());
      }
    }

    return ret;
  }

  /**
   * Linear interpolation.
   *
   * @param x      the x
   * @param y      the y
   * @param values the values
   * @return the double[]
   */
  public static double[] linearInterpolation(List<Double> x, List<Double> y, double[] values) {

    int n = values.length;

    double[] ret = new double[n];

    // pair and sort the x y values
    // index on y since we want to sort by x
    List<Indexed<Double, Double>> indexed = Indexed.pair(y, x);

    // sort by x
    Collections.sort(indexed);

    boolean found = false;
    double yd;
    double xd;
    double vi;
    double v;

    double max = indexed.get(indexed.size() - 1).getIndex();

    for (int i = 0; i < n; ++i) {
      v = values[i];

      found = false;

      for (int j = 0; j < indexed.size() - 1; ++j) {
        // find where this v lies between

        if (indexed.get(j).getValue() <= v && indexed.get(j + 1).getValue() > v) {
          // interpolate

          yd = indexed.get(j + 1).getIndex() - indexed.get(j).getIndex();

          xd = (v - indexed.get(j).getValue()) / (indexed.get(j + 1).getValue() - indexed.get(j).getValue());

          vi = indexed.get(j).getIndex() + yd * xd;

          ret[i] = vi;

          found = true;

          break;
        }
      }

      if (!found) {
        // the value lies outside the function range
        // so just use the max value
        ret[i] = max; // indexed.get(indexed.size() - 1).getIndex();
      }
    }

    return ret;
  }

  /**
   * Returns v bounded between min and max inclusive.
   *
   * @param v   the v
   * @param min the min
   * @param max the max
   * @return the int
   */
  public static int bound(int v, int min, int max) {
    return Math.min(max, Math.max(min, v));
  }

  /**
   * Bounds v and guarantees it lie between a number range. Chiefly for use for
   * ensuring that p-values etc do not exceed 1 or become less than zero.
   *
   * @param v   the v
   * @param min the min
   * @param max the max
   * @return the double
   */
  public static double bound(double v, double min, double max) {
    return Math.min(max, Math.max(min, v));
  }

  /**
   * Bound.
   *
   * @param v   the v
   * @param min the min
   * @param max the max
   * @return the float
   */
  public static float bound(float v, float min, float max) {
    return Math.min(max, Math.max(min, v));
  }

  /**
   * Will return v or min if v is less min.
   *
   * @param v   the v
   * @param min the min
   * @return the double
   */
  public static double boundMin(double v, double min) {
    return Math.max(min, v);
  }

  /**
   * To double.
   *
   * @param values the values
   * @return the list
   */
  public static List<Double> toDouble(List<Integer> values) {
    List<Double> ret = new ArrayList<Double>(values.size());

    for (int v : values) {
      ret.add((double) v);
    }

    return ret;
  }

  /**
   * Square.
   *
   * @param d the d
   * @return the double
   */
  public static double square(double d) {
    return d * d;
  }

  /**
   * Return a floored version of an array.
   *
   * @param values the values
   * @return the list
   */
  public static List<Double> floor(List<Double> values) {
    List<Double> ret = new ArrayList<Double>(values.size());

    for (double v : values) {
      ret.add(Math.floor(v));
    }

    return ret;
  }

  /**
   * Floor.
   *
   * @param values the values
   * @return the double[]
   */
  public static double[] floor(double[] values) {
    double[] ret = new double[values.length];

    for (int i = 0; i < values.length; ++i) {
      ret[i] = Math.floor(values[i]);
    }

    return ret;
  }

  /**
   * Returns a version of the double list with values greater than zero.
   *
   * @param values the values
   * @return the list
   */
  public static List<Double> gtZero(List<Double> values) {
    List<Double> ret = new ArrayList<Double>();

    for (double v : values) {
      if (v > 0) {
        ret.add(v);
      }
    }

    return ret;
  }

  /**
   * Gt zero.
   *
   * @param values the values
   * @return the list
   */
  public static List<Double> gtZero(double[] values) {
    List<Double> ret = new ArrayList<Double>();

    for (double v : values) {
      if (v > 0) {
        ret.add(v);
      }
    }

    return ret;
  }

  /**
   * Copy a value into all elements of an array.
   *
   * @param value  the value
   * @param values the values
   */
  public static void copyValue(double value, double[] values) {
    copyValue(value, values, values.length);
  }

  /**
   * Copy a value into all elements of an array.
   *
   * @param value  the value
   * @param values the values
   * @param n      copy up to n values
   */
  public static void copyValue(double value, double[] values, int n) {
    for (int i = 0; i < n; ++i) {
      values[i] = value;
    }
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param values the values
   */
  public static void copyValue(int value, int[] values) {
    copyValue(value, values, values.length);
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param values the values
   * @param n      the n
   */
  public static void copyValue(int value, int[] values, int n) {
    copyValue(value, values, n, 0);
  }

  /**
   * Copy a value into an int array n times from a given offset.
   *
   * @param value  the value
   * @param values the values
   * @param n      the n
   * @param offset the offset
   */
  public static void copyValue(int value, int[] values, int n, int offset) {
    for (int i = 0; i < n; ++i) {
      values[i + offset] = value;
    }
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param values the values
   */
  public static void copyValue(int value, byte[] values) {
    copyValue(value, values, values.length);
  }

  /**
   * Copy a value into an byte array n times from a given offset.
   *
   * @param value  the value
   * @param values the values
   * @param n      the n
   * @param offset the offset
   */
  public static void copyValue(int value, byte[] values, int n, int offset) {
    for (int i = 0; i < n; ++i) {
      values[i + offset] = (byte) value;
    }
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param values the values
   * @param n      the n
   */
  public static void copyValue(int value, byte[] values, int n) {
    copyValue(value, values, n, 0);
  }

  /**
   * Negate all the values in an array.
   *
   * @param values the values
   */
  public static void negate(double[] values) {
    for (int i = 0; i < values.length; ++i) {
      values[i] = -values[i];
    }
  }

  /**
   * Return the indices greater than zero.
   *
   * @param values the values
   * @return the list
   */
  public static List<Integer> nonZeroIndices(double[] values) {
    return gtIndices(values, 0);
  }

  /**
   * Return the indices where values exceed a threshold.
   *
   * @param values the values
   * @param min    the min
   * @return the list
   */
  public static List<Integer> gtIndices(double[] values, double min) {
    List<Integer> ret = new ArrayList<Integer>();

    for (int i = 0; i < values.length; ++i) {
      if (values[i] > min) {
        ret.add(i);
      }
    }

    return ret;
  }

  /**
   * Rounds uneven numbers down to make them even.
   *
   * @param x the x
   * @return the int
   */
  public static int makeMult2(double x) {
    return makeMult2((int) x);
  }

  /**
   * Rounds uneven numbers up to make them even.
   *
   * @param x the x
   * @return the int
   */
  public static int makeMult2(int x) {
    return x % 2 == 0 ? x : x - 1;
  }

  /**
   * Rounds uneven numbers up to make them even.
   *
   * @param x the x
   * @return the long
   */
  public static long makeMult2(long x) {
    return x % 2 == 0 ? x : x - 1;
  }

  /**
   * Round a list of doubles to a fixed number of places.
   *
   * @param values the values
   * @param places the places
   * @return the list
   */
  public static List<Double> round(Collection<Double> values, int places) {
    List<Double> ret = new ArrayList<Double>(values.size());

    for (double v : values) {
      ret.add(round(v, places));
    }

    return ret;
  }

  /**
   * Round a value to n places.
   *
   * @param value  the value
   * @param places the places
   * @return the double
   */
  public static double round(double value, int places) {
    return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
  }

  /**
   * Set the precision of a number.
   * 
   * @param x  A numner.
   * @param dp The number of decimal places to truncate the number to.
   * @return
   */
  public static double dp(double x, int dp) {
    if (x > 0) {
      return new BigDecimal(x).setScale(dp, BigDecimal.ROUND_FLOOR).doubleValue();
    } else {
      return new BigDecimal(x).setScale(dp, BigDecimal.ROUND_CEILING).doubleValue();
    }
  }

  /**
   * Returns the distance between two integers.
   *
   * @param start the start
   * @param end   the end
   * @return the length
   */
  public static int getLength(int start, int end) {
    return end - start + 1;
  }

  /**
   * Gets the euclidian d.
   *
   * @param p1 the p1
   * @param p2 the p2
   * @return the euclidian d
   */
  public static double getEuclidianD(Point p1, Point p2) {
    return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
  }

  /**
   * Gets the euclidian D.
   *
   * @param p1 the p 1
   * @param p2 the p 2
   * @return the euclidian D
   */
  public static double getEuclidianD(Point p1, IntPos2D p2) {
    return Math.sqrt(Math.pow(p1.x - p2.getX(), 2) + Math.pow(p1.y - p2.getY(), 2));
  }

  /**
   * Return a list of numbers multiplied by a factor.
   *
   * @param <T>    the generic type
   * @param values the values
   * @param x      the x
   * @return the list
   */
  public static <T extends Number> List<Double> multiply(List<T> values, double x) {
    List<Double> ret = new ArrayList<Double>(values.size());

    for (T v : values) {
      ret.add(v.doubleValue() * x);
    }

    return ret;
  }

  public static double[] multiply(int[] values, double x) {
    double[] ret = new double[values.length];

    for (int i = 0; i < values.length; ++i) {
      ret[i] = values[i] * x;
    }

    return ret;
  }

  /**
   * Returns the Euclidean distance between the centers of two rectangles.
   *
   * @param r1 the r1
   * @param r2 the r2
   * @return the double
   */
  public static double distance(Rectangle r1, Rectangle r2) {
    double x1 = r1.x + r1.width / 2.0;
    double y1 = r1.y + r1.height / 2.0;

    double x2 = r2.x + r2.width / 2.0;
    double y2 = r2.y + r2.height / 2.0;

    double xd = x1 - x2;
    double yd = y1 - y2;

    return Math.sqrt(xd * xd + yd * yd);
  }

  /**
   * Returns true if x is within (l, u].
   *
   * @param x the x
   * @param l the l
   * @param u the u
   * @return true, if successful
   */
  public static boolean inBound(int x, int l, int u) {
    return x >= l && x < u;
  }

  /**
   * Returns true if double appears to be an int.
   *
   * @param v the v
   * @return true, if is int
   */
  public static boolean isInt(double v) {
    return isValidNumber(v) && v == Math.rint(v);
  }

  /**
   * Returns true if float appears to be an int.
   *
   * @param v the v
   * @return true, if is int
   */
  public static boolean isInt(float v) {
    return v == Math.floor(v);
  }

  /**
   * Returns true if value is valid number, i.e. not NaN or infinite.
   *
   * @param v the v
   * @return true, if is valid number
   */
  public static boolean isValidNumber(double v) {
    return !Double.isNaN(v) && !Double.isInfinite(v);
  }

  /**
   * Checks if is valid number.
   *
   * @param v the v
   * @return true, if is valid number
   */
  public static boolean isValidNumber(float v) {
    return !Float.isNaN(v) && !Float.isInfinite(v);
  }

  /**
   * Checks if is valid number.
   *
   * @param v the v
   * @return true, if is valid number
   */
  public static boolean isValidNumber(int v) {
    return true;
  }

  /**
   * Returns true if the number is infinite or NaN.
   *
   * @param v the v
   * @return true, if is invalid number
   */
  public static boolean isInvalidNumber(double v) {
    return Double.isNaN(v) || Double.isInfinite(v);
  }

  /**
   * Checks if is invalid number.
   *
   * @param v the v
   * @return true, if is invalid number
   */
  public static boolean isInvalidNumber(float v) {
    return Float.isNaN(v) || Float.isInfinite(v);
  }

  /**
   * Checks if is invalid number.
   *
   * @param v the v
   * @return true, if is invalid number
   */
  public static boolean isInvalidNumber(int v) {
    return false;
  }

  /**
   * Rand subset with replacement.
   *
   * @param <T>  the generic type
   * @param l    the l
   * @param size the size
   * @return the list
   */
  public static <T> List<T> randSubsetWithReplacement(List<T> l, int size) {
    Random rand = new Random();

    List<T> ret = new ArrayList<T>(size);

    while (ret.size() < size) {
      ret.add(l.get(rand.nextInt(l.size())));
    }

    return ret;
  }

  /**
   * Rand subset with replacement.
   *
   * @param <T>  the generic type
   * @param l    the l
   * @param size the size
   * @return the double[]
   */
  public static <T> double[] randSubsetWithReplacement(double[] l, int size) {
    Random rand = new Random();

    double[] ret = new double[size];

    for (int i = 0; i < size; ++i) {
      ret[i] = l[rand.nextInt(size)];
    }

    return ret;
  }

  /**
   * Generate a random sequence of integers between 0 and max - 1 up to a given
   * size.
   *
   * @param <T>  the generic type
   * @param max  the max
   * @param size the size
   * @return the list
   */
  public static <T> List<Integer> randIntSeqWithRep(int max, int size) {
    Random rand = new Random();

    List<Integer> ret = new ArrayList<Integer>(size);

    while (ret.size() < size) {
      ret.add(rand.nextInt(max));
    }

    return ret;
  }

  /**
   * Return a random number between 0 and max (exclusive).
   * 
   * @param max
   * @return
   */
  public static int rand(int max) {
    Random rand = new Random();

    return rand.nextInt(max);
  }

  /**
   * Rand subset without replacement.
   *
   * @param <T>  the generic type
   * @param l    the l
   * @param size the size
   * @return the list
   */
  public static <T> List<T> randSubsetWithoutReplacement(List<T> l, int size) {
    Random rand = new Random();

    List<T> ret = new UniqueArrayList<T>(size);

    while (ret.size() < size) {
      ret.add(l.get(rand.nextInt(l.size())));
    }

    return ret;
  }

  /**
   * Create an array of size n.
   *
   * @param size the size
   * @return the list
   */
  public static List<Double> array(int size) {
    return new ArrayList<Double>(size);
  }

  /**
   * Subtract.
   *
   * @param values the values
   * @param x      the x
   * @return the list
   */
  public static List<Integer> subtract(List<Integer> values, int x) {
    List<Integer> ret = new ArrayList<Integer>(values.size());

    for (int v : values) {
      ret.add(v - x);
    }

    return ret;
  }

  /**
   * Sum.
   *
   * @param values the values
   * @return the double
   */
  public static double sum(final double[] values) {
    double ret = 0;

    for (double v : values) {
      ret += v;
    }

    return ret;
  }

  /**
   * Returns {@code b} to the {@code k}th power using integers.
   *
   * @param b the b
   * @param k the k
   * @return the int
   */
  public static int pow(int b, int k) {
    switch (b) {
    case 0:
      return (k == 0) ? 1 : 0;
    case 1:
      return 1;
    case -1:
      return ((k & 1) == 0) ? 1 : -1;
    case 2:
      return (k < Integer.SIZE) ? (1 << k) : 0;
    case -2:
      if (k < Integer.SIZE) {
        return ((k & 1) == 0) ? (1 << k) : -(1 << k);
      } else {
        return 0;
      }
    default:
      switch (k) {
      case 0:
        return 1;
      case 1:
        return b;
      default:
        for (int i = 1; i < k; ++i) {
          b *= b;
        }

        return b;
      }
    }
  }

  /**
   * Tied rank.
   *
   * @param values the values
   * @return the double[]
   */
  public static double[] tiedRank(double[] values) {
    Map<Double, Integer> countMap = new HashMap<Double, Integer>();

    for (double value : values) {
      if (!countMap.containsKey(value)) {
        countMap.put(value, 0);
      }

      countMap.put(value, countMap.get(value) + 1);
    }

    List<Indexed<Integer, Double>> sorted = IndexedInt.index(values);

    // Items are now rank sorted
    Collections.sort(sorted);

    double rank = 1.0;

    double currentValue = Double.MIN_VALUE;

    double v;

    Map<Integer, Double> ranks = new HashMap<Integer, Double>();

    // ranks.put(sorted.get(0).getIndex(), rank /
    // countMap.get(sorted.get(0).getValue()));

    for (int i = 0; i < sorted.size(); ++i) {
      v = sorted.get(i).getValue();

      if (v > currentValue) {
        // We have moved into a new block so calculate the fractional
        // rank for this block

        // Since we are using zero based indices, the rank is the
        // number of items in the group (since for each index we
        // must add 1 to get the one based index) plus the zero
        // based ranks of each group member.

        rank = countMap.get(v);

        for (int j = 0; j < countMap.get(v); ++j) {
          rank += i + j;
        }

        rank /= countMap.get(v);

        // increase the rank by the number of previous items
        // rank += i; //countMap.get(sorted.get(i - 1).getValue());
      }

      currentValue = v;

      ranks.put(sorted.get(i).getIndex(), rank);
    }

    int n = values.length;

    double[] ret = new double[n];

    for (int i = 0; i < n; ++i) {
      ret[i] = ranks.get(i);
    }

    return ret;
  }

  /**
   * Compare two doubles and return -1 if v1 < v2, 1 if v1 > v2, or 0 otherwise.
   * 
   * @param v1
   * @param v2
   * @return
   */
  public static int compareTo(double v1, double v2) {
    if (v1 < v2) {
      return -1;
    } else if (v1 > v2) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Compare two integers and return -1 if v1 < v2, 1 if v1 > v2, or 0 otherwise.
   * 
   * @param v1
   * @param v2
   * @return
   */
  public static int compareTo(int v1, int v2) {
    if (v1 < v2) {
      return -1;
    } else if (v1 > v2) {
      return 1;
    } else {
      return 0;
    }
  }

  /**
   * Returns the maximum of a list of doubles.
   * 
   * @param x
   * @param values
   * @return
   */
  public static double max(double x, double... values) {
    double max = x;

    for (double v : values) {
      if (v > max) {
        max = v;
      }
    }

    return max;
  }

  /**
   * Returns the multiplication of all values.
   * 
   * @param values
   * @return
   */
  public static double multiply(double[] values) {
    double ret = 1;

    for (double v : values) {
      ret *= v;
    }

    return ret;
  }

  /**
   * Returns the nth root of a value.
   * 
   * @param v
   * @param n
   * @return
   */
  public static double nthRoot(double v, int n) {
    return Math.pow(v, 1.0 / n);
  }

  /**
   * Signum functions for integers. Returns 1 if v > 0, -1 if v < 0 or 0 if v ==
   * 0.
   * 
   * @param v
   * @return
   */
  public static int signum(int v) {
    if (v > 0) {
      return 1;
    } else if (v < 0) {
      return -1;
    } else {
      return v;
    }
  }

  /**
   * Add x to an array.
   * 
   * @param x
   * @param s
   * @param e
   * @param values
   */
  public static void add(double x, int s, int e, double[] values) {
    for (int i = s; i < e; ++i) {
      values[i] += x;
    }
  }
}