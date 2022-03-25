package org.jebtk.core.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jebtk.core.Indexed;
import org.jebtk.core.IndexedInt;

public class ArrayUtils {

  public static final int[] EMPTY_INT_ARRAY = {};
  public static final double[] EMPTY_DOUBLE_ARRAY = {};
  public static final String[] EMPTY_STRING_ARRAY = {};

  private ArrayUtils() {
    // Do nothing
  }

  public static int[] array(int start, int end) {
    return array(start, end, 1);
  }

  /**
   * Create an array from start to end (inclusive if steps coincide with end
   * otherwise first step closest to, but less than end).
   *
   * @param start
   * @param end
   * @param inc
   * @return
   */
  public static int[] array(int start, int end, int inc) {
    int n = (end - start) / inc + 1;

    // n += n % inc;
    int[] ret = new int[n];

    for (int i = 0; i < n; ++i) {
      ret[i] = start;
      start += inc;
    }

    return ret;
  }
  
  public static <T> T[] asArraySingle(T item) {
    return asArray(item);
  }

  public static <T> T[] asArray(T... items) {
    return items;
  }

  public static <T> List<T> toList(T[] items) {
    return toList(items, items.length);
  }

  /**
   * Convert an array to a list.
   *
   * @param items An array.
   * @param n How many items to copy from the array.
   * @return
   */
  public static <T> List<T> toList(T[] items, int n) {
    if (isNullOrEmpty(items) || n < 1) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList(n);

    for (int i = 0; i < n; ++i) {
      ret.add(items[i]);
    }

    return ret;
  }

  public static <T> List<T> toUniqueList(T[] items) {
    return toUniqueList(items, items.length);
  }

  /**
   * Convert array to list and remove duplicates.
   *
   * @param items
   * @param n
   * @return
   */
  public static <T> List<T> toUniqueList(T[] items, int n) {
    if (isNullOrEmpty(items) || n < 1) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList<>(n);
    Set<T> used = new HashSet<>(n);

    for (int i = 0; i < n; ++i) {
      if (!used.contains(items[i])) {
        ret.add(items[i]);
        used.add(items[i]);
      }
    }

    return ret;
  }

  /**
   * Returns true if the array is null or empty.
   *
   * @param items
   * @return
   */
  public static boolean isNullOrEmpty(Object[] items) {
    return items == null || items.length == 0;
  }

  public static int[] doubleToInt(double[] values) {
    int[] ret = new int[values.length];

    for (int i = 0; i < values.length; ++i) {
      ret[i] = (int) values[i];
    }

    return ret;
  }

  /**
   * Convert byte array to primitive list.
   *
   * @param values
   * @return
   */
  public static byte[] mapToByte(List<Byte> values) {
    byte[] ret = new byte[values.size()];

    for (int i = 0; i < values.size(); ++i) {
      ret[i] = values.get(i);
    }

    return ret;
  }

  /**
   * Convert int array to primitive list.
   *
   * @param list
   * @return
   */
  public static int[] mapToInt(List<Integer> list) {
    return list.stream().mapToInt(Integer::intValue).toArray();
  }

  /**
   * Returns a copy of an array from a given offset of a given length. This
   * supplements {@code Arrays.copyOfRange} by offering the more conventional
   * start length subset annotation rather than the start end.
   *
   * @param list
   * @param start
   * @param length
   * @return
   */
  public static int[] copyOf(int[] list, int start, int length) {
    return Arrays.copyOfRange(list, start, start + length);
  }

  /**
   * Convert an int list to a primitive int array.
   *
   * @param values
   * @return
   */
  public static int[] toInt(Collection<Integer> values) {
    int[] ret = new int[values.size()];

    int i = 0;

    for (int v : values) {
      ret[i++] = v;
    }

    return ret;
  }

  public static int[] argsort(String[] values) {
    return argsort(values, false);
  }

  /**
   * Returns the indices of the sorted items.
   *
   * original index in the list so that
   *
   * @param values
   * @return
   */
  public static int[] argsort(String[] values, boolean reverse) {
    List<Indexed<Integer, String>> items = new ArrayList<Indexed<Integer, String>>(values.length);

    for (int i = 0; i < values.length; ++i) {
      items.add(new IndexedInt<String>(i, values[i]));
    }

    Collections.sort(items);

    if (reverse) {
      Collections.reverse(items);
    }

    int[] ret = new int[values.length];

    for (int i = 0; i < values.length; ++i) {
      ret[i] = items.get(i).mIndex;
    }

    return ret;
  }

  /**
   * Return the indices of the items when sorted smallest to largest.
   *
   * @param values
   * @return
   */
  public static int[] argsort(double[] values) {
    return argsort(values, false);
  }

  /**
   * Returns the indices of the sorted items.
   *
   * original index in the list so that
   *
   * @param values
   * @return
   */
  public static int[] argsort(double[] values, boolean reverse) {
    List<Indexed<Integer, Double>> items = new ArrayList<Indexed<Integer, Double>>(values.length);

    for (int i = 0; i < values.length; ++i) {
      items.add(new IndexedInt<Double>(i, values[i]));
    }

    Collections.sort(items);

    if (reverse) {
      Collections.reverse(items);
    }

    int[] ret = new int[values.length];

    for (int i = 0; i < values.length; ++i) {
      ret[i] = items.get(i).mIndex;
    }

    return ret;
  }

  /**
   * Return the indices of the items when sorted smallest to largest.
   *
   * @param values
   * @return
   */
  public static int[] argsort(int[] values) {
    return argsort(values, false);
  }

  /**
   * Returns the indices of the sorted items.
   *
   * original index in the list so that
   *
   * @param values
   * @return
   */
  public static int[] argsort(int[] values, boolean reverse) {
    List<Indexed<Integer, Integer>> items = new ArrayList<Indexed<Integer, Integer>>(values.length);

    for (int i = 0; i < values.length; ++i) {
      items.add(new Indexed<Integer, Integer>(i, values[i]));
    }

    Collections.sort(items);

    if (reverse) {
      Collections.reverse(items);
    }

    int[] ret = new int[values.length];

    for (int i = 0; i < values.length; ++i) {
      ret[i] = items.get(i).mIndex;
    }

    return ret;
  }

  public static final double[] subList(double[] values, final int... indices) {
    double[] subset = new double[indices.length];

    for (int i = 0; i < values.length; ++i) {
      subset[i] = values[indices[i]];
    }

    return subset;
  }

  public static final int[] subList(int[] values, final int... indices) {
    int[] subset = new int[indices.length];

    for (int i = 0; i < values.length; ++i) {
      subset[i] = values[indices[i]];
    }

    return subset;
  }

  public static final String[] subList(String[] values, final int... indices) {
    String[] subset = new String[indices.length];

    for (int i = 0; i < values.length; ++i) {
      subset[i] = values[indices[i]];
    }

    return subset;
  }

  public static final Object[] subList(Object[] values, final int... indices) {
    Object[] subset = new Object[indices.length];

    for (int i = 0; i < values.length; ++i) {
      subset[i] = values[indices[i]];
    }

    return subset;
  }

  /**
   * Reverse an array in place.
   *
   * @param array
   */
  public static void reverse(Object[] array) {
    Object temp;

    for (int i = 0; i < array.length / 2; i++) {
      temp = array[i];
      array[i] = array[array.length - i - 1];
      array[array.length - i - 1] = temp;
    }
  }
}
