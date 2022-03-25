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
package org.jebtk.core.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Immutable list for lists that should not be changed once created. The
 * benefits of a vector without the ability to modify it.
 *
 * @author Antony Holmes
 *
 */
public class Array {

  /**
   * The items.
   */
  protected int[] items;

  /**
   * Instantiates a new array.
   *
   * @param size the size
   */
  public Array(int size) {
    items = new int[size];
  }

  /**
   * Return the number of samples associated with the experiment.
   *
   * @return the int
   */
  public int size() {
    return items.length;
  }

  /**
   * Sets the.
   *
   * @param index the index
   * @param value the value
   */
  public void set(int index, int value) {
    items[index] = value;
  }

  /**
   * Gets the.
   *
   * @param index the index
   * @return the int
   */
  public int get(int index) {
    return items[index];
  }

  /**
   * Set all values in the list to a particular value.
   *
   * @param v the new all
   */
  public void setAll(int v) {
    for (int i = 0; i < items.length; ++i) {
      items[i] = v;
    }
  }

  /**
   * Creates the int array.
   *
   * @param size the size
   * @param v    the v
   * @return the int[]
   */
  public static int[] createIntArray(int size, int v) {
    int[] a = new int[size];

    setIntArray(a, v);

    return a;
  }

  /**
   * Set all values in an array to a particular value.
   *
   * @param array the array
   * @param v     the v
   */
  public static void setIntArray(int[] array, int v) {
    for (int i = 0; i < array.length; ++i) {
      array[i] = v;
    }
  }

  /**
   * Creates the double array.
   *
   * @param size the size
   * @param v    the v
   * @return the double[]
   */
  public static double[] createDoubleArray(int size, double v) {
    double[] a = new double[size];

    setDoubleArray(a, v);

    return a;
  }

  /**
   * Sets the double array.
   *
   * @param array the array
   * @param v     the v
   */
  public static void setDoubleArray(double[] array, double v) {
    for (int i = 0; i < array.length; ++i) {
      array[i] = v;
    }
  }

  /**
   * Create an array of size n so all n elements can be addressed out of order.
   *
   * @param <T>  the generic type
   * @param size the size
   * @return the list
   */
  public static <T> List<T> init(int size) {
    List<T> list = new ArrayList<T>(size);

    for (int i = 0; i < size; ++i) {
      list.add(null);
    }

    return list;
  }
}
