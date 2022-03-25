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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.jebtk.core.Function;
import org.jebtk.core.Indexed;
import org.jebtk.core.Mathematics;
import org.jebtk.core.NumConvertable;
import org.jebtk.core.sys.SysUtils;
import org.jebtk.core.text.TextUtils;

/**
 * Functions for manipulating lists and sets.
 *
 * @author Antony Holmes
 *
 */
public class CollectionUtils {

  /**
   * The constant EMPTY_INT_ARRAY.
   */
  public static final int[] EMPTY_INT_ARRAY = {};

  /**
   * The constant EMPTY_DOUBLE_ARRAY.
   */
  public static final double[] EMPTY_DOUBLE_ARRAY = {};

  /** The Constant EMPTY_BYTE_ARRAY. */
  public static final byte[] EMPTY_BYTE_ARRAY = {};

  /**
   * The class SortIgnoreCase.
   *
   * @param <X> the generic type
   */
  private static class SortIgnoreCase<X> implements Comparator<X> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(X o1, X o2) {
      String s1 = o1.toString().toLowerCase();
      String s2 = o2.toString().toLowerCase();

      return s1.toLowerCase().compareTo(s2.toLowerCase());
    }
  }

  /**
   * Instantiates a new array utils.
   */
  private CollectionUtils() {
    // do nothing
  }

  /**
   * Returns true if the collection is null or empty.
   *
   * @param <T> the generic type
   * @param c   the c
   * @return true, if is null or empty
   */
  public static <T> boolean isNullOrEmpty(Collection<T> c) {
    return !isNotNullOrEmpty(c);
  }

  /**
   * Returns true if the collection contains elements and is not null.
   *
   * @param <T> the generic type
   * @param c   the c
   * @return true, if is not null or empty
   */
  public static <T> boolean isNotNullOrEmpty(Collection<T> c) {
    return c != null && c.size() > 0;
  }

  /**
   * Checks if is null or empty.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param c   the c
   * @return true, if is null or empty
   */
  public static <K, V> boolean isNullOrEmpty(Map<K, V> c) {
    return c == null || c.size() == 0;
  }

  /**
   * Checks if is null or empty.
   *
   * @param <T> the generic type
   * @param c   the c
   * @return true, if is null or empty
   */
  public static <T> boolean isNullOrEmpty(int[] c) {
    return c == null || c.length == 0;
  }

  /**
   * Checks if is null or empty.
   *
   * @param <T> the generic type
   * @param c   the c
   * @return true, if is null or empty
   */
  public static <T> boolean isNullOrEmpty(double[] c) {
    return c == null || c.length == 0;
  }

  /**
   * Checks if is null or empty.
   *
   * @param <T> the generic type
   * @param c   the c
   * @return true, if is null or empty
   */
  public static <T> boolean isNullOrEmpty(boolean[] c) {
    return c == null || c.length == 0;
  }

  /**
   * Checks if is null or empty.
   *
   * @param <T> the generic type
   * @param c   the c
   * @return true, if is null or empty
   */
  public static <T> boolean isNullOrEmpty(T[] c) {
    return c == null || c.length == 0;
  }

  /**
   * Reorder a list. The list will be truncated to the size of indices if the list
   * of indices is shorter than the input list. Items will also be duplicated if
   * indices contains repeats.
   *
   * @param <T>     the generic type
   * @param s       the s
   * @param indices the indices
   * @return the list
   */
  public static final <T> List<T> reorder(final List<T> s, final List<Integer> indices) {
    List<T> ret = new ArrayList<T>(s.size());

    for (int i : indices) {
      ret.add(s.get(i));
    }

    return ret;
  }

  /**
   * Sort.
   *
   * @param <T>        the generic type
   * @param collection the collection
   * @param ascending  the ascending
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> sort(Collection<T> collection, boolean ascending) {
    List<T> ret = sort(collection);

    if (!ascending) {
      Collections.reverse(ret);
    }

    return ret;
  }

  public static <T extends Comparable<? super T>> List<T> sort(Collection<T> collection, Comparator<T> comparator,
      boolean ascending) {
    List<T> ret = sort(collection, comparator);

    if (!ascending) {
      Collections.reverse(ret);
    }

    return ret;
  }

  /**
   * Sort.
   *
   * @param <T>        the generic type
   * @param collection the collection
   * @param ascending  the ascending
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> sort(Iterable<T> collection, boolean ascending) {
    List<T> ret = sort(collection);

    if (!ascending) {
      Collections.reverse(ret);
    }

    return ret;
  }

  /**
   * Sort a list of items.
   *
   * @param <T>        the generic type
   * @param collection the collection
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> sort(Collection<T> collection) {
    List<T> ret = new ArrayList<T>(collection);

    Collections.sort(ret);

    return ret;
  }

  /**
   * Sort.
   *
   * @param <T>        the generic type
   * @param collection the collection
   * @param comparator the comparator
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> sort(Collection<T> collection, Comparator<T> comparator) {
    List<T> ret = new ArrayList<T>(collection);

    Collections.sort(ret, comparator);

    return ret;
  }

  /**
   * Sort case insensitive.
   *
   * @param <T>        the generic type
   * @param collection the collection
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> sortCaseInsensitive(Collection<T> collection) {
    List<T> ret = new ArrayList<T>(collection);

    Collections.sort(ret, new SortIgnoreCase<T>());

    return ret;
  }

  /**
   * Sort.
   *
   * @param <T>  the generic type
   * @param iter the iter
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> sort(Iterable<T> iter) {
    List<T> ret = toList(iter);

    Collections.sort(ret);

    return ret;
  }

  /**
   * Sort.
   *
   * @param <T>        the generic type
   * @param iter       the iter
   * @param comparator the comparator
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> sort(Iterable<T> iter, Comparator<T> comparator) {
    List<T> ret = toList(iter);

    Collections.sort(ret, comparator);

    return ret;
  }

  /**
   * Returns a reversed copy of a list.
   *
   * @param <T>  the generic type
   * @param iter the iter
   * @return the list
   */
  public static <T> List<T> reverse(Iterable<T> iter) {
    List<T> ret = toList(iter);

    Collections.reverse(ret);

    return ret;
  }

  /**
   * Reverse.
   *
   * @param <T>  the generic type
   * @param list the list
   * @return the list
   */
  public static <T> List<T> reverse(List<T> list) {
    List<T> ret = new ArrayList<T>(list);

    Collections.reverse(ret);
    // reverseList(ret);

    return ret;
  }

  /**
   * Reverses the elements in a list.
   *
   * @param <T>  the generic type
   * @param list the list
   */
  public static <T> void reverseList(List<T> list) {
    int m = list.size() / 2;

    int s = 0;
    int e = list.size() - 1;

    T t;

    for (int i = 0; i < m; ++i) {
      if (s == e) {
        break;
      }

      t = list.get(s);

      list.set(s, list.get(e));
      list.set(e, t);

      ++s;
      --e;
    }

  }

  /**
   * Reverse sort a list. This method returns a sorted list and does not sort the
   * original.
   *
   * @param <T>    the generic type
   * @param values the values
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> reverseSort(final Collection<T> values) {
    return reverse(sort(values));
  }

  /**
   * Returns a sorted list of an array.
   *
   * @param <X>   the generic type
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  public static final <X extends Comparable<? super X>, T extends Iterable<X>> List<X> sort(X[] items) {
    List<X> ret = new ArrayList<X>();

    for (X item : items) {
      ret.add(item);
    }

    Collections.sort(ret);

    // Let's use my quicksort implementation
    // quickSort(ret);

    return ret;
  }

  /**
   * Sort keys.
   *
   * @param <T> the generic type
   * @param <X> the generic type
   * @param map the map
   * @return the list
   */
  public static <T extends Comparable<? super T>, X> List<T> sortKeys(Map<T, X> map) {
    return sortKeys(map, true);
  }

  /**
   * Sort the keys in a map.
   *
   * @param <T>       the generic type
   * @param map       the map
   * @param ascending the ascending
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<T> sortKeys(Map<T, ?> map, boolean ascending) {
    return sort(map.keySet(), ascending);
  }

  public static <T extends Comparable<? super T>> List<T> sortKeys(Map<T, ?> map, Comparator<T> comparator,
      boolean ascending) {
    return sort(map.keySet(), comparator, ascending);
  }

  /**
   * Returns the values of a map sorted by their key.
   *
   * @param <T> the generic type
   * @param <X> the generic type
   * @param map the map
   * @return the list
   */
  public static <T extends Comparable<? super T>, X> List<X> sort(Map<T, X> map) {
    List<T> keys = sortKeys(map);

    List<X> ret = new ArrayList<X>();

    for (T key : keys) {
      ret.add(map.get(key));
    }

    return ret;
  }

  /**
   * Sort list map.
   *
   * @param <T> the generic type
   * @param <X> the generic type
   * @param map the map
   * @return the list
   */
  public static <T extends Comparable<? super T>, X> List<X> sortListMap(Map<T, List<X>> map) {
    List<T> keys = sortKeys(map);

    List<X> ret = new ArrayList<X>();

    for (T key : keys) {
      for (X item : map.get(key)) {
        ret.add(item);
      }
    }

    return ret;
  }

  /**
   * Returns the head of a list.
   *
   * @param <T>  the generic type
   * @param s    the s
   * @param size the size
   * @return the list
   */
  public static final <T> List<T> head(final List<T> s, int size) {
    return subList(s, 0, size);
  }

  /**
   * Return the head of a list.
   * 
   * @param ret
   * @return
   */
  public static <T> T head(final List<T> ret) {
    if (ret.size() > 0) {
      return ret.get(0);
    } else {
      return null;
    }
  }

  /**
   * Default tail function returning the last element of a list.
   *
   * @param <T>  the generic type
   * @param list the list
   * @return the t
   */
  public static final <T> T end(List<T> list) {
    return end(list, 1).get(0);
  }

  /**
   * Returns a list without its head element.
   *
   * @param <T>  the generic type
   * @param list the list
   * @return the list
   */
  public static final <T> List<T> tail(List<T> list) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    return tail(list, 1);
  }

  public static <T> List<T> tail(List<T> list, int start) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    return subList(list, start);
  }

  /**
   * Return the last n elements of a list.
   *
   * @param <T>  the generic type
   * @param list the list
   * @param n    the n
   * @return the list
   */
  public static final <T> List<T> end(List<T> list, int n) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    return subList(list, list.size() - n, n);
  }

  /**
   * Sub list.
   *
   * @param <T>   the generic type
   * @param list  the list
   * @param start the start
   * @return the list
   */
  public static <T> List<T> subList(List<T> list, int start) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    return subList(list, start, list.size() - start);
  }

  /**
   * Return part of a list.
   *
   * @param <T>    the generic type
   * @param list   the list
   * @param start  the start
   * @param length the length
   * @return the list
   */
  public static final <T> List<T> subList(List<T> list, int start, int length) {
    if (isNullOrEmpty(list) || start < 0 || start >= list.size() || length < 1) {
      return Collections.emptyList();
    }

    int s = start;
    int l = Math.min(s + length, list.size());

    List<T> ret = new ArrayList<T>(l);

    while (s < l) {
      ret.add(list.get(s));

      ++s;
    }

    return ret;
  }

  public static final <T> Object[] subList(Object[] list, int start, int length) {
    if (isNullOrEmpty(list) || start < 0 || start >= list.length || length < 1) {
      return null;
    }

    int s = start;
    int l = Math.min(s + length, list.length);

    Object[] ret = new Object[l];

    SysUtils.arraycopy(list, s, ret, l);

    return ret;
  }

  /**
   * Returns a subset of the items in a list in the order the indices appear in
   * the collection. Duplicate indices will result in duplicate values.
   *
   * @param <T>     the generic type
   * @param list    the list
   * @param indices the indices
   * @return the list
   */
  public static final <T> List<T> subList(List<T> list, Collection<Integer> indices) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    List<T> subset = new ArrayList<T>();

    for (int i : indices) {
      subset.add(list.get(i));
    }

    return subset;
  }

  /**
   * Sub list indexed.
   *
   * @param <T>     the generic type
   * @param <V>     the value type
   * @param list    the list
   * @param indices the indices
   * @return the list
   */
  public static final <T, V extends Comparable<? super V>> List<T> subListIndexed(List<T> list,
      final Collection<Indexed<Integer, V>> indices) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    List<T> subset = new ArrayList<T>();

    for (Indexed<Integer, ?> i : indices) {
      subset.add(list.get(i.getIndex()));
    }

    return subset;
  }

  /**
   * Sub list indexed.
   *
   * @param <V>     the value type
   * @param list    the list
   * @param indices the indices
   * @return the double[]
   */
  public static final <V extends Comparable<? super V>> double[] subListIndexed(double[] list,
      final Collection<Indexed<Integer, V>> indices) {
    if (isNullOrEmpty(list)) {
      return null;
    }

    double[] subset = new double[indices.size()];

    int c = 0;

    for (Indexed<Integer, ?> i : indices) {
      subset[c++] = list[i.getIndex()];
    }

    return subset;
  }

  public static final <V extends Comparable<? super V>> int[] subListIndexed(int[] list,
      final Collection<Indexed<Integer, V>> indices) {
    if (isNullOrEmpty(list)) {
      return null;
    }

    int[] subset = new int[indices.size()];

    int c = 0;

    for (Indexed<Integer, ?> i : indices) {
      subset[c++] = list[i.getIndex()];
    }

    return subset;
  }

  public static final <V extends Comparable<? super V>> Object[] subListIndexed(Object[] list,
      final Collection<Indexed<Integer, V>> indices) {
    if (isNullOrEmpty(list)) {
      return null;
    }

    Object[] subset = new Object[indices.size()];

    int c = 0;

    for (Indexed<Integer, ?> i : indices) {
      subset[c++] = list[i.getIndex()];
    }

    return subset;
  }

  public static final <V extends Comparable<? super V>> String[] subListIndexed(String[] list,
      final Collection<Indexed<Integer, V>> indices) {
    if (isNullOrEmpty(list)) {
      return null;
    }

    String[] subset = new String[indices.size()];

    int c = 0;

    for (Indexed<Integer, ?> i : indices) {
      subset[c++] = list[i.getIndex()];
    }

    return subset;
  }

  /**
   * Sub list.
   *
   * @param <T>     the generic type
   * @param list    the list
   * @param indices the indices
   * @return the double[]
   */
  public static final double[] subList(double[] list, final Collection<Integer> indices) {
    double[] subset = new double[indices.size()];

    int c = 0;

    for (int i : indices) {
      subset[c++] = list[i];
    }

    return subset;
  }

  public static final String[] subList(String[] list, final Collection<Integer> indices) {
    String[] subset = new String[indices.size()];

    int c = 0;

    for (int i : indices) {
      subset[c++] = list[i];
    }

    return subset;
  }

  public static final Object[] subList(Object[] list, final Collection<Integer> indices) {
    Object[] subset = new Object[indices.size()];

    int c = 0;

    for (int i : indices) {
      subset[c++] = list[i];
    }

    return subset;
  }

  /**
   * Head.
   *
   * @param list the list
   * @param size the size
   * @return the list
   */
  public static final List<Double> head(final double[] list, int size) {
    return subList(list, 0, size);
  }

  /**
   * Tail.
   *
   * @param list the list
   * @param size the size
   * @return the list
   */
  public static final List<Double> end(final double[] list, int size) {
    return subList(list, list.length - size, size);
  }

  /**
   * Tail.
   *
   * @param list the list
   * @return the list
   */
  public static final List<Double> tail(final double[] list) {
    return subList(list, 1);
  }

  /**
   * Sub list.
   *
   * @param list  the list
   * @param start the start
   * @return the list
   */
  public static final List<Double> subList(final double[] list, int start) {
    return subList(list, start, list.length - start);
  }

  /**
   * Sub list.
   *
   * @param list   the list
   * @param start  the start
   * @param length the length
   * @return the list
   */
  public static final List<Double> subList(final double[] list, int start, int length) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    List<Double> ret = new ArrayList<Double>();

    int s = Math.max(start, 0);

    for (int i = 0; i < length; ++i) {
      if (s + i == list.length) {
        break;
      }

      ret.add(list[s + i]);
    }

    return ret;
  }

  /**
   * Given an item and a list, return the indices where the item occurs.
   *
   * @param <T>  the generic type
   * @param list the list
   * @param item the item
   * @return the list
   */
  public static final <T> List<Integer> indices(List<T> list, T item) {
    if (isNullOrEmpty(list) || item == null) {
      return Collections.emptyList();
    }

    List<Integer> indices = new ArrayList<Integer>();

    for (int i = 0; i < list.size(); ++i) {
      if (list.get(i).equals(item)) {
        indices.add(i);
      }
    }

    return indices;
  }

  /**
   * Returns a list containing the rank of the elements in the input list. The
   * input list is unaltered.
   *
   * @param list the list
   * @return the list
   */
  public static final List<Integer> rank(final List<Double> list) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyList();
    }

    List<Double> list2 = new ArrayList<Double>(list);

    List<Integer> rank = new ArrayList<Integer>(list.size());

    Map<Double, Integer> position = new HashMap<Double, Integer>();

    Collections.sort(list2);

    for (int i = 0; i < list2.size(); ++i) {
      position.put(list2.get(i), i);
    }

    for (int i = 0; i < list.size(); ++i) {
      rank.add(position.get(list.get(i)));
    }

    return rank;
  }

  /**
   * Adds the.
   *
   * @param l1 the l1
   * @param l2 the l2
   * @return the list
   */
  public static final List<Double> add(List<Double> l1, List<Double> l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l1) || l1.size() != l2.size()) {
      return Collections.emptyList();
    }

    List<Double> sum = new ArrayList<Double>();

    for (int i = 0; i < l1.size(); ++i) {
      sum.add(l1.get(i) + l2.get(i));
    }

    return sum;
  }

  /**
   * Adds the int list.
   *
   * @param l1 the l1
   * @param l2 the l2
   * @return the list
   */
  public static final List<Integer> addIntList(List<Integer> l1, List<Integer> l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l1) || l1.size() != l2.size()) {
      return Collections.emptyList();
    }

    List<Integer> sum = new ArrayList<Integer>();

    for (int i = 0; i < l1.size(); ++i) {
      sum.add(l1.get(i) + l2.get(i));
    }

    return sum;
  }

  /**
   * Finds the smallest element in a list. If the list is null, it will return
   * Integer.MIN_VALUE
   *
   * @param list the list
   * @return the int
   */
  public static final int min(List<Integer> list) {
    if (isNullOrEmpty(list)) {
      return Integer.MIN_VALUE;
    }

    int min = list.get(0);

    for (int i : list) {
      min = Math.min(min, i);
    }

    return min;
  }

  /**
   * Returns.
   *
   * @param <T>     the generic type
   * @param map     the map
   * @param indices the indices
   * @return the map
   */
  public static final <T> Map<Integer, T> subset(Map<Integer, T> map, Collection<Integer> indices) {
    if (isNullOrEmpty(map) || isNullOrEmpty(indices)) {
      return Collections.emptyMap();
    }

    Map<Integer, T> subset = new HashMap<Integer, T>();

    for (int i : indices) {
      subset.put(i, map.get(i));
    }

    return subset;
  }

  /**
   * /** Calculates the intersection of two collections adding only those elements
   * that occur in both sets. Tries to preserve order
   *
   * @param <T>         the generic type
   * @param collections the collections
   * @return the int
   */

  /**
   * Counts the total number of elements in a number of collections.
   * 
   * @param collections
   * @return
   */
  @SafeVarargs
  public static <T> int countElements(Collection<T>... collections) {
    int sum = 0;

    for (Collection<T> c : collections) {
      sum += c.size();
    }

    return sum;
  }

  /**
   * Intersect.
   *
   * @param <T> the generic type
   * @param m1  the m 1
   * @param m2  the m 2
   * @return the list
   */
  public static final <T> List<T> intersect(Map<T, ?> m1, Map<T, ?> m2) {
    return intersect(m1.keySet(), m2.keySet());
  }

  /**
   * Intersect.
   *
   * @param <T>    the generic type
   * @param items1 the items1
   * @param items2 the items2
   * @return the list
   */
  public static <T> List<T> iterIntersect(Iterable<T> items1, Iterable<T> items2) {
    return intersect(toSetIter(items1), toSetIter(items2));
  }

  /**
   * Intersect.
   *
   * @param <T>    the generic type
   * @param l1     the l 1
   * @param l2     the l 2
   * @param others the others
   * @return the list
   */
  @SafeVarargs
  public static final <T> List<T> intersect(Collection<T> l1, Collection<T> l2, Collection<T>... others) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l2)) {
      return Collections.emptyList();
    }

    if (others.length > 0) {
      List<T> s = new ArrayList<T>(l1.size() + l2.size() + countElements(others));

      // Lets create sets of l2 + others to check l1 in

      List<Set<T>> checks = new ArrayList<Set<T>>(2 + others.length);

      checks.add(toSet(l2));

      for (Collection<T> c : others) {
        checks.add(toSet(c));
      }

      for (T e : uniquePreserveOrder(l1)) {
        boolean add = true;

        // Must be in every set
        for (Set<T> set : checks) {
          if (!set.contains(e)) {
            add = false;
            break;
          }
        }

        if (add) {
          s.add(e);
        }
      }

      return s;
    } else {
      // The two collection case, we don't need the more complex checking

      List<T> s = new ArrayList<T>(l1.size() + l2.size());

      Set<T> checkSet = toSet(l2);

      for (T e : uniquePreserveOrder(l1)) {
        if (checkSet.contains(e)) {
          s.add(e);
        }
      }

      return s;
    }
  }

  /**
   * Returns the set of things in l1 that are not in l2.
   *
   * @param <T> the generic type
   * @param l1  the l1
   * @param l2  the l2
   * @return the list
   */
  public static final <T> List<T> compliment(Collection<T> l1, Collection<T> l2) {
    // If l1 is empty // no point continuing
    if (isNullOrEmpty(l1)) {
      return Collections.emptyList();
    }

    // if l2 is empty then clearly everything in l1 is valid.
    if (isNullOrEmpty(l2)) {
      return new ArrayList<T>(l1);
    }

    List<T> s = new ArrayList<T>(l1.size());

    for (T e : uniquePreserveOrder(l1)) {
      if (!l2.contains(e)) {
        s.add(e);
      }
    }

    return s;
  }

  /**
   * Returns a set of the items in l1 that are not in l2.
   *
   * @param <T> the generic type
   * @param l1  the l 1
   * @param l2  the l 2
   * @return the collection
   */
  public static final <T> Collection<T> notIn(Collection<T> l1, Collection<T> l2) {
    // If l1 is empty // no point continuing
    if (isNullOrEmpty(l1)) {
      return Collections.emptySet();
    }

    // if l2 is empty then clearly everything in l1 is valid.
    if (isNullOrEmpty(l2)) {
      return l1;
    }

    Set<T> s = new HashSet<T>(l1.size());

    for (T e : l1) {
      if (!l2.contains(e)) {
        s.add(e);
      }
    }

    return s;
  }

  /**
   * Compliment iter.
   *
   * @param <T> the generic type
   * @param l1  the l1
   * @param l2  the l2
   * @return the list
   */
  public static final <T> List<T> iterCompliment(Iterable<T> l1, Iterable<T> l2) {
    return compliment(iterToList(l1), iterToList(l2));
  }

  /**
   * Updates l1 so it contains only those elements also present in l2.
   *
   * @param <T> the generic type
   * @param l1  the l1
   * @param l2  the l2
   */
  public static final <T> void intersectionUpdate(Collection<T> l1, Collection<T> l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l2)) {
      return;
    }

    Set<T> s = new HashSet<T>();

    if (l1.size() == 0) {
      // add everything from l2 to l1

      for (T e : l2) {
        l1.add(e);
      }
    } else {
      for (T e : l1) {
        if (!l2.contains(e)) {
          s.add(e);
        }
      }

      for (T e : s) {
        l1.remove(e);
      }
    }
  }

  /**
   * /** Calculates the union of two lists, i.e all of the elements present in
   * both sets, but without duplicates. This method will try to preserve order if
   * the collections are ordered.
   *
   * @param <T>    the generic type
   * @param l1     the l 1
   * @param l2     the l 2
   * @param others the others
   * @return the list
   */
  @SafeVarargs
  public static final <T> List<T> union(final Collection<T> l1, final Collection<T> l2, final Collection<T>... others) {
    List<T> ret = new UniqueArrayList<T>();

    for (T e : l1) {
      ret.add(e);
    }

    for (T e : l2) {
      ret.add(e);
    }

    for (Collection<T> l : others) {
      for (T e : l) {
        ret.add(e);
      }
    }

    return ret;
  }

  /**
   * Add the elements of l2 to l1 if l1 does not contain the element.
   *
   * @param <T> the generic type
   * @param l1  the l1
   * @param l2  the l2
   */
  public static final <T> void unionUpdate(Collection<T> l1, Collection<T> l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l2)) {
      return;
    }

    for (T e : l2) {
      if (l1.contains(e)) {
        continue;
      }

      l1.add(e);
    }
  }

  /**
   * Calculates the exclusive or of two collections. Returns a list that is
   * guaranteed to contain unique items. This allows order to be preserved.
   *
   * @param <T> the generic type
   * @param l1  the l1
   * @param l2  the l2
   * @return the list
   */
  public static final <T> List<T> xor(Collection<T> l1, Collection<T> l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l2)) {
      return Collections.emptyList();
    }

    List<T> s = new ArrayList<T>(l1.size());

    for (T e : l1) {
      if (!l2.contains(e)) {
        s.add(e);
      }
    }

    for (T e : l2) {
      if (!l1.contains(e)) {
        s.add(e);
      }
    }

    return uniquePreserveOrder(s);
  }

  /**
   * Converts a primitive array into an object array.
   *
   * @param <T>   the generic type
   * @param array the array
   * @return the list
   */
  public static final <T> List<T> convertToArray(T[] array) {
    if (isNullOrEmpty(array)) {
      return Collections.emptyList();
    }

    List<T> vector = new ArrayList<T>(array.length);

    for (int i = 0; i < array.length; ++i) {
      vector.add(array[i]);
    }

    return vector;
  }

  /**
   * Creates a map view of a table indexed on a column.
   *
   * @param <T>       the generic type
   * @param list      the list
   * @param keyColumn the key column
   * @return the map
   */
  public static final <T> Map<T, List<T[]>> convertToMap(List<T[]> list, int keyColumn) {
    if (isNullOrEmpty(list)) {
      return Collections.emptyMap();
    }

    Map<T, List<T[]>> hashmap = new HashMap<T, List<T[]>>();

    for (T[] tokens : list) {
      if (!hashmap.containsKey(tokens[keyColumn])) {
        ArrayList<T[]> t = new ArrayList<T[]>();

        hashmap.put(tokens[keyColumn], t);
      }

      // system.out.println(tokens[keyColumn] + ":col");

      hashmap.get(tokens[keyColumn]).add(tokens);
    }

    return hashmap;
  }

  /**
   * Convert to hash map.
   *
   * @param <T>       the generic type
   * @param array     the array
   * @param keyColumn the key column
   * @return the map
   */
  public static final <T> Map<T, T[]> convertToHashMap(List<T[]> array, int keyColumn) {
    if (isNullOrEmpty(array)) {
      return Collections.emptyMap();
    }

    Map<T, T[]> hashmap = new HashMap<T, T[]>();

    for (T[] tokens : array) {
      hashmap.put(tokens[keyColumn], tokens);
    }

    return hashmap;
  }

  /**
   * Converts a set to a list.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  public static final <T> List<T> toList(final Set<T> items) {
    if (isNullOrEmpty(items)) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList<T>(items.size());

    for (T item : items) {
      ret.add(item);
    }

    return ret;
  }

  /**
   * To sorted list.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  public static final <T extends Comparable<? super T>> List<T> toSortedList(final Set<T> items) {
    return sort(toList(items));
  }

  /**
   * Create a list from a single item.
   *
   * @param <T>   the generic type
   * @param item  the item
   * @param items the items
   * @return the list
   */
  @SafeVarargs
  public static final <T> List<T> asList(T item, T... items) {
    List<T> ret = new ArrayList<T>(1 + items.length);

    ret.add(item);

    for (T i : items) {
      ret.add(i);
    }

    return ret;
  }

  /**
   * Converts a iterator into a list.
   *
   * @param <T>  the generic type
   * @param iter the iter
   * @return the list
   */
  public static <T> List<T> toList(Iterable<T> iter) {
    if (iter == null) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList<T>();

    for (T item : iter) {
      ret.add(item);
    }

    return ret;
  }

  /**
   * Convert a stack to a list by popping items.
   * 
   * @param stack
   * @return
   */
  public static <T> List<T> toList(Deque<T> stack) {
    if (stack == null) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList<T>(stack.size());

    while (!stack.isEmpty()) {
      ret.add(stack.pop());
    }

    return ret;
  }

  /**
   * To list.
   *
   * @param <T>  the generic type
   * @param iter the iter
   * @return the list
   */
  public static <T> List<T> toList(Iterator<T> iter) {
    if (iter == null) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList<T>();

    while (iter.hasNext()) {
      ret.add(iter.next());
    }

    return ret;
  }

  /**
   * Turn an item into a list.
   *
   * @param <T>  the generic type
   * @param item the item
   * @return the list
   */
  public static <T> List<T> toList(T item) {
    return Collections.singletonList(item);

    /*
     * List<T> ret = new ArrayList<T>(1);
     * 
     * ret.add(item);
     * 
     * return ret;
     */
  }

  public static <T> List<T> asList(T item) {
    return Collections.singletonList(item);
  }

  /**
   * Convert a map to a list of strings formatted as "key=value".
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param map the map
   * @return the list
   */
  public static <K extends Comparable<? super K>, V> List<String> toList(Map<K, V> map) {
    List<String> ret = new ArrayList<String>(map.size());

    for (K k : sortKeys(map)) {
      ret.add(k + "=" + map.get(k));
    }

    return ret;
  }

  /**
   * To list.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  @SafeVarargs
  public static <T> List<T> toList(T value, T... values) {
    List<T> ret = new ArrayList<T>(1 + values.length);

    ret.add(value);

    for (T v : values) {
      ret.add(v);
    }

    return ret;
  }

  /**
   * To list.
   *
   * @param items the items
   * @return the list
   */
  @SafeVarargs
  public static List<Integer> toList(int... items) {
    List<Integer> ret = new ArrayList<Integer>();

    for (int item : items) {
      ret.add(item);
    }

    return ret;
  }

  /**
   * Create a set from a single item or returns an empty list if the item is null.
   *
   * @param <T>  the generic type
   * @param item the item
   * @return the sets the
   */
  public static final <T> Set<T> toSet(T item) {
    if (item != null) {
      return Collections.singleton(item);
    } else {
      return Collections.emptySet();
    }

    /*
     * Set<T> set = new HashSet<T>();
     * 
     * set.add(item);
     * 
     * return set;
     */
  }

  /**
   * Converts an item, or multiple items into a set. Useful for one line
   * instantiation and population of a set.
   *
   * @param <T>   the generic type
   * @param item  The item to create a set from.
   * @param items Optional extra items to add.
   * @return the sets the
   */
  @SafeVarargs
  public static <T> Set<T> asSet(T item, T... items) {
    if (item == null) {
      return Collections.emptySet();
    }

    Set<T> ret = new HashSet<T>(1 + items.length);

    ret.add(item);

    for (T i : items) {
      ret.add(i);
    }

    return ret;
  }

  /**
   * Append the items of list2 to list1.
   *
   * @param <T> the generic type
   * @param l1  the l1
   * @param l2  the l2
   * @return the list
   */
  public static <T> List<T> append(List<T> l1, List<T> l2) {
    if (isNullOrEmpty(l1) && isNullOrEmpty(l2)) {
      return Collections.emptyList();
    }

    if (isNullOrEmpty(l1)) {
      return new ArrayList<T>(l2);
    }

    if (isNullOrEmpty(l2)) {
      return new ArrayList<T>(l1);
    }

    List<T> list = new ArrayList<T>();

    for (T item : l1) {
      list.add(item);
    }

    for (T item : l2) {
      list.add(item);
    }

    return list;
  }

  @SafeVarargs
  public static <T> List<T> cat(List<T> l1, List<T>... lists) {
    if (isNullOrEmpty(l1)) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList<T>(l1.size());

    ret.addAll(l1);

    for (List<T> l : lists) {
      ret.addAll(l);
    }

    return ret;
  }

  /**
   * Returns the unique items in a set.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the sets the
   */
  public static <T> Set<T> unique(Collection<T> items) {
    if (isNullOrEmpty(items)) {
      return Collections.emptySet();
    }

    // Use a tree set so items can be iterated in order by default
    Set<T> set = new TreeSet<T>();

    for (T item : items) {
      set.add(item);
    }

    return set;
  }

  /**
   * Returns the unique items in an array.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the sets the
   */
  public static <T> Set<T> unique(T[] items) {
    if (isNullOrEmpty(items)) {
      return Collections.emptySet();
    }

    Set<T> set = new TreeSet<T>();

    for (T item : items) {
      set.add(item);
    }

    return set;
  }

  /**
   * Returns the unique items in a collection in the order they appear in the
   * collection.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  public static <T> List<T> uniquePreserveOrder(Collection<T> items) {
    return UniqueArrayList.create(items);
  }

  /**
   * Returns a unique version of items which preserves the order of the items.
   *
   * @param <T>  the generic type
   * @param iter the iter
   * @return the list
   */
  public static <T> List<T> uniquePreserveOrder(Iterable<T> iter) {
    if (iter == null) {
      return Collections.emptyList();
    }

    Set<T> used = new HashSet<T>();
    List<T> list = new ArrayList<T>();

    for (T item : iter) {
      if (!used.contains(item)) {
        list.add(item);
        used.add(item);
      }
    }

    return list;
  }

  /**
   * Copy the values from one map to another.
   *
   * @param <X>  the generic type
   * @param <Y>  the generic type
   * @param from the from
   * @param to   the to
   */
  public static <X, Y> void copy(Map<X, Y> from, Map<X, Y> to) {
    if (isNullOrEmpty(from) || isNullOrEmpty(to)) {
      return;
    }

    for (Entry<X, Y> v : from.entrySet()) {
      to.put(v.getKey(), v.getValue());
    }
  }

  /**
   * Create a list of a specific size of a repeated value.
   *
   * @param <T>   the generic type
   * @param value the value
   * @param size  the size
   * @return the list
   */
  public static <T> List<T> replicate(T value, int size) {
    List<T> values = new ArrayList<T>(size);

    while (values.size() < size) {
      values.add(value);
    }

    return values;
  }

  /**
   * Repeat each value in a collection, n times.
   *
   * @param <T>    the generic type
   * @param values the values
   * @param n      the n
   * @return the list
   */
  public static <T> List<T> replicate(Collection<T> values, int n) {
    int t = values.size() * n;

    List<T> ret = new ArrayList<T>(t);

    for (T value : values) {
      for (int i = 0; i < n; ++i) {
        ret.add(value);
      }
    }

    return ret;
  }

  /**
   * Quick sort a list.
   *
   * @param <T>  the generic type
   * @param list the list
   */
  public static <T extends Comparable<? super T>> void quickSort(List<T> list) {
    if (isNullOrEmpty(list)) {
      return;
    }

    // Allow for 2^64 length lists (assuming 2 entries per partition)
    // The custom stack is designed for speed so it is essentially
    // a program counter keeping track of what has been used in
    // the array.
    int[] stack = new int[128];

    int top = -1;
    int start;
    int end;

    stack[++top] = 0;
    stack[++top] = list.size() - 1;

    while (top > -1) {
      end = stack[top--];
      start = stack[top--];

      // Partition

      T pivot = list.get(end);

      int storeIndex = start;

      for (int i = start; i < end; ++i) {
        if (list.get(i).compareTo(pivot) <= 0) {
          T t = list.get(i);

          list.set(i, list.get(storeIndex));
          list.set(storeIndex, t);

          ++storeIndex;
        }
      }

      // swap the pivot with the store index
      // so that the it is correctly placed
      // between all those values less than
      // it and all those greater than it
      pivot = list.get(storeIndex);

      list.set(storeIndex, list.get(end));
      list.set(end, pivot);

      //
      // If the pivot is not at the extremes
      // of the list, we can partition the
      // upper and lower halves until the
      // list is eventually sorted.
      //

      if (storeIndex - 1 > start) {
        stack[++top] = start;
        stack[++top] = storeIndex - 1;
      }

      if (storeIndex + 1 < end) {
        stack[++top] = storeIndex + 1;
        stack[++top] = end;
      }
    }
  }

  /**
   * Convert a list into a set.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the sets the
   */
  public static <T> Set<T> toSet(Collection<T> items) {
    if (isNullOrEmpty(items)) {
      return Collections.emptySet();
    }

    if (items instanceof Set) {
      return (Set<T>) items;
    }

    Set<T> map = new HashSet<T>();

    for (T item : items) {
      map.add(item);
    }

    return map;
  }

  /**
   * To set iter.
   *
   * @param <T>  the generic type
   * @param iter the iterable object.
   * @return the sets the
   */
  public static <T> Set<T> toSetIter(Iterable<T> iter) {
    if (iter == null) {
      return Collections.emptySet();
    }

    Set<T> s1 = new HashSet<T>();

    for (T item : iter) {
      s1.add(item);
    }

    return s1;
  }

  /**
   * Create a list from an iterator.
   *
   * @param <T>  the generic type
   * @param iter the iter
   * @return the list
   */
  public static <T> List<T> iterToList(Iterable<T> iter) {
    if (iter == null) {
      return Collections.emptyList();
    }

    List<T> s1 = new ArrayList<T>();

    for (T item : iter) {
      s1.add(item);
    }

    return s1;
  }

  /**
   * Converts a double object list into a primitive array.
   * 
   * @param <T>
   *
   * @param values the values
   * @return the double[]
   */
  public static <T extends Number> double[] toDoublePrimitive(List<T> values) {
    if (isNullOrEmpty(values)) {
      return EMPTY_DOUBLE_ARRAY;
    }

    double[] ret = new double[values.size()];

    for (int i = 0; i < values.size(); ++i) {
      ret[i] = values.get(i).doubleValue();
    }

    return ret;
  }

  /**
   * Converts a integer object list into a primitive array.
   *
   * @param values the values
   * @return the int[]
   */
  public static int[] toIntPrimitive(List<Integer> values) {
    if (isNullOrEmpty(values)) {
      return EMPTY_INT_ARRAY;
    }

    int[] ret = new int[values.size()];

    for (int i = 0; i < values.size(); ++i) {
      ret[i] = values.get(i);
    }

    return ret;
  }

  /**
   * Convert a string list into a list of integers with the assumption that every
   * entry is a string representation of a number.
   *
   * @param values the values
   * @return the list
   * @throws ParseException the parse exception
   */
  public static List<Integer> toInt(List<?> values) throws ParseException {
    if (isNullOrEmpty(values)) {
      return Collections.emptyList();
    }

    List<Integer> ret = new ArrayList<Integer>(values.size());

    for (Object value : values) {
      ret.add(TextUtils.parseInt(value.toString()));
    }

    return ret;
  }

  /**
   * Convert to int.
   *
   * @param values the values
   * @return the list
   * @throws ParseException the parse exception
   */
  public static List<Integer> convertToInt(Collection<? extends NumConvertable> values) throws ParseException {
    if (isNullOrEmpty(values)) {
      return Collections.emptyList();
    }

    List<Integer> ret = new ArrayList<Integer>(values.size());

    for (NumConvertable value : values) {
      ret.add(value.getInt());
    }

    return ret;
  }

  /**
   * Convert to double.
   *
   * @param values the values
   * @return the list
   * @throws ParseException the parse exception
   */
  public static List<Double> convertToDouble(Collection<? extends NumConvertable> values) throws ParseException {
    if (isNullOrEmpty(values)) {
      return Collections.emptyList();
    }

    List<Double> ret = new ArrayList<Double>(values.size());

    for (NumConvertable value : values) {
      ret.add(value.getDouble());
    }

    return ret;
  }

  /**
   * Convert a string list into a list of integers with the assumption that every
   * entry is a string representation of a number. An empty list will be returned
   * if any string appears to be an invalid representation of a number.
   *
   * @param values the values
   * @return the list
   * @throws ParseException the parse exception
   */
  public static List<Double> toDouble(List<?> values) throws ParseException {
    if (isNullOrEmpty(values)) {
      return Collections.emptyList();
    }

    List<Double> ret = new ArrayList<Double>(values.size());

    for (Object value : values) {
      ret.add(TextUtils.parseDouble(value.toString()));
    }

    return ret;
  }

  /**
   * Concatenate an arbitrary list of paramaters into an array.
   * 
   * @param id
   * @param ids
   * @return
   */
  public static Object[] concatenate(Object id, Object... ids) {
    Object[] values = new Object[1 + ids.length];
    values[0] = id;
    System.arraycopy(ids, 0, values, 1, ids.length);

    return values;
  }

  /**
   * Concatenate two lists together.
   *
   * @param <T> the generic type
   * @param l1  the l1
   * @param l2  the l2
   * @return the list
   */
  public static <T> List<T> concatenate(List<T> l1, List<T> l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l2)) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList<T>();

    if (l1 != null) {
      ret.addAll(l1);
    }

    if (l2 != null) {
      ret.addAll(l2);
    }

    return ret;
  }

  /**
   * Adds a rank to an ordered set of value using standard competition ranking
   * (i.e. sames values get same rank).
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the map
   */
  public static <T extends Comparable<? super T>> Map<T, Integer> competitionRank(List<T> items) {
    if (isNullOrEmpty(items)) {
      return Collections.emptyMap();
    }

    // ensure items are sorted in order
    List<T> sorted = CollectionUtils.sort(items);

    Map<T, Integer> rankMap = new HashMap<T, Integer>();

    int rank = 0;

    rankMap.put(sorted.get(0), 0);

    for (int i = 1; i < sorted.size(); ++i) {
      // increase the rank if this item is greater otherwise
      // keep the rank the same
      if (sorted.get(i).compareTo(sorted.get(i - 1)) > 0) {
        ++rank;
      }

      rankMap.put(sorted.get(i), rank);
    }

    return rankMap;
  }

  /**
   * Competition rank2.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the map
   */
  public static <T extends Comparable<? super T>> Map<Integer, T> competitionRank2(List<T> items) {
    if (isNullOrEmpty(items)) {
      return null;
    }

    // ensure items are sorted in order
    List<T> sorted = CollectionUtils.sort(items);

    Map<Integer, T> rankMap = new HashMap<Integer, T>();

    int rank = 0;

    rankMap.put(0, sorted.get(0));

    for (int i = 1; i < sorted.size(); ++i) {
      // increase the rank if this item is greater otherwise
      // keep the rank the same
      if (sorted.get(i).compareTo(sorted.get(i - 1)) > 0) {
        ++rank;
      }

      rankMap.put(rank, sorted.get(i));
    }

    return rankMap;
  }

  /**
   * Creates a map from two lists where the keys are from l1 and the values l2.
   *
   * @param <T> the generic type
   * @param <V> the value type
   * @param l1  the l1
   * @param l2  the l2
   * @return the map
   */
  public static <T, V> Map<T, V> createMap(List<T> l1, List<V> l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l2)) {
      return Collections.emptyMap();
    }

    Map<T, V> map = new HashMap<T, V>();

    for (int i = 0; i < l1.size(); ++i) {
      map.put(l1.get(i), l2.get(i));
    }

    return map;
  }

  /**
   * Create a map to set of values in two lists. It is assumed l1 as the key will
   * include repeats to different values in l2.
   *
   * @param <T> the generic type
   * @param <V> the value type
   * @param l1  the l1
   * @param l2  the l2
   * @return the map
   */
  public static <T, V> Map<T, Set<V>> createMapSet(List<T> l1, List<V> l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l2)) {
      return Collections.emptyMap();
    }

    Map<T, Set<V>> map = new HashMap<T, Set<V>>();

    for (int i = 0; i < l1.size(); ++i) {
      if (!map.containsKey(l1.get(i))) {
        map.put(l1.get(i), new HashSet<V>());
      }

      map.get(l1.get(i)).add(l2.get(i));
    }

    return map;
  }

  public static Map<String, Set<String>> createMapSet(String[] l1, String[] l2) {
    if (isNullOrEmpty(l1) || isNullOrEmpty(l2)) {
      return Collections.emptyMap();
    }

    Map<String, Set<String>> map = DefaultHashMap.create(new HashSetCreator<String>());

    for (int i = 0; i < l1.length; ++i) {
      map.get(l1[i]).add(l2[i]);
    }

    return map;
  }

  /**
   * Sort a map's keys by their values.
   *
   * @param <T> the generic type
   * @param <X> the generic type
   * @param map the map
   * @return the list
   */
  public static <T extends Comparable<? super T>, X extends Comparable<? super X>> List<T> sortKeysByValues(
      Map<T, X> map) {
    if (isNullOrEmpty(map)) {
      return Collections.emptyList();
    }

    Map<X, Set<T>> valuesKeys = mapValuesToKeys(map);

    List<T> ret = new ArrayList<T>();

    for (X value : sort(valuesKeys.keySet())) {
      for (T key : sort(valuesKeys.get(value))) {
        ret.add(key);
      }
    }

    return ret;
  }

  /**
   * Takes a map and returns a new map using the values of the input map as keys
   * and the keys as values. Allowance is made for multiple keys mapping to the
   * same object.
   *
   * @param <T> the generic type
   * @param <X> the generic type
   * @param map the map
   * @return the map
   */
  public static <T, X> Map<X, Set<T>> mapValuesToKeys(Map<T, X> map) {
    if (isNullOrEmpty(map)) {
      return Collections.emptyMap();
    }

    Map<X, Set<T>> ret = new HashMap<X, Set<T>>();

    for (Entry<T, X> keyValue : map.entrySet()) {
      if (!ret.containsKey(keyValue.getValue())) {
        ret.put(keyValue.getValue(), new HashSet<T>());
      }

      ret.get(keyValue.getValue()).add(keyValue.getKey());
    }

    return ret;
  }

  /**
   * Converts a list of objects to a list of string names. Nulls are converted to
   * empty strings.
   *
   * @param <T>    the generic type
   * @param values the values
   * @return the list
   */
  public static <T> List<String> toString(Collection<T> values) {
    if (isNullOrEmpty(values)) {
      return Collections.emptyList();
    }

    List<String> ret = new ArrayList<String>(values.size());

    for (Object o : values) {
      if (o != null) {
        ret.add(o.toString());
      } else {
        ret.add(TextUtils.EMPTY_STRING);
      }
    }

    return ret;
  }

  /**
   * To string.
   *
   * @param <T>  the generic type
   * @param iter the iter
   * @return the list
   */
  public static <T> List<String> toString(Iterable<T> iter) {
    if (iter == null) {
      return Collections.emptyList();
    }

    List<String> ret = new ArrayList<String>();

    for (Object o : iter) {
      ret.add(o.toString());
    }

    return ret;
  }

  /**
   * To string.
   *
   * @param objects the objects
   * @return the list
   */
  public static List<String> toString(Object... objects) {
    if (isNullOrEmpty(objects)) {
      return Collections.emptyList();
    }

    List<String> ret = new ArrayList<String>(objects.length);

    for (Object o : objects) {
      ret.add(o.toString());
    }

    return ret;
  }

  /**
   * Create a generic object version of an array.
   *
   * @param <T>    the generic type
   * @param values the values
   * @return the list
   */
  public static <T> List<Object> toObjects(List<T> values) {
    if (isNullOrEmpty(values)) {
      return Collections.emptyList();
    }

    ArrayList<Object> ret = new ArrayList<Object>();

    for (T v : values) {
      ret.add(v);
    }

    return ret;
  }

  /**
   * Extract a set of items from a map in order.
   *
   * @param <T>   the generic type
   * @param <X>   the generic type
   * @param map   the map
   * @param items the items
   * @return the list
   */
  public static <T, X> List<X> extract(Map<T, X> map, List<T> items) {
    if (isNullOrEmpty(map) || isNullOrEmpty(items)) {
      return Collections.emptyList();
    }

    List<X> ret = new ArrayList<X>();

    for (T item : items) {
      if (map.containsKey(item)) {
        ret.add(map.get(item));
      }
    }

    return ret;
  }

  /**
   * Extract.
   *
   * @param <T>  the generic type
   * @param <X>  the generic type
   * @param map  the map
   * @param item the item
   * @return the x
   */
  public static <T, X> X extract(Map<T, X> map, T item) {
    return map.get(item);
  }

  /**
   * Reverse.
   *
   * @param items the items
   */
  public static void reverse(double[] items) {
    reverse(items, 0, items.length);
  }

  /**
   * Reverse part of a buffer. Useful if the buffer is larger than what we want to
   * search.
   *
   * @param items the items
   * @param start the start
   * @param n     the n
   */
  public static void reverse(double[] items, int start, int n) {
    if (isNullOrEmpty(items)) {
      return;
    }

    int n2 = n / 2;
    int ni = start + n - 1;

    int si = start;
    int ei;

    double temp;

    for (int i = 0; i < n2; ++i) {
      ei = ni - i;

      temp = items[si];

      items[si] = items[ei];

      items[ei] = temp;

      ++si;
    }
  }

  public static void reverse(int[] items) {
    reverse(items, 0, items.length);
  }

  public static void reverse(int[] items, int start, int n) {
    if (isNullOrEmpty(items)) {
      return;
    }

    int n2 = n / 2;
    int ni = start + n - 1;

    int si = start;
    int ei;

    int temp;

    for (int i = 0; i < n2; ++i) {
      ei = ni - i;

      temp = items[si];

      items[si] = items[ei];

      items[ei] = temp;

      ++si;
    }
  }

  /**
   * Reverse copy.
   *
   * @param items the items
   * @return the double[]
   */
  public static double[] reverseCopy(double[] items) {
    if (isNullOrEmpty(items)) {
      return EMPTY_DOUBLE_ARRAY;
    }

    double[] ret = new double[items.length];

    int n = items.length;

    int ni = n - 1;

    for (int i = 0; i < n; ++i) {
      ret[i] = items[ni - i];
    }

    return ret;
  }

  /**
   * Copy a value into all elements of an array.
   *
   * @param <T>    the generic type
   * @param value  the value
   * @param values the values
   */
  public static <T> void copyValue(T value, T[] values) {
    if (isNullOrEmpty(values)) {
      return;
    }

    for (int i = 0; i < values.length; ++i) {
      values[i] = value;
    }
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param values the values
   */
  public static void copyValue(boolean value, boolean[] values) {
    copyValue(value, values, values.length);
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param values the values
   * @param n      the n
   */
  public static void copyValue(boolean value, boolean[] values, int n) {
    copyValue(value, values, n, 0);
  }

  /**
   * Copy a value into an byte array n times from a given offset.
   *
   * @param value  the value
   * @param values the values
   * @param n      the n
   * @param offset the offset
   */
  public static void copyValue(boolean value, boolean[] values, int n, int offset) {
    if (isNullOrEmpty(values)) {
      return;
    }

    for (int i = 0; i < n; ++i) {
      int io = i + offset;

      if (io >= values.length) {
        break;
      }

      values[io] = value;
    }
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param values the values
   */
  public static void copyValue(double value, double[] values) {
    copyValue(value, values.length, values);
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param n      the n
   * @param values the values
   */
  public static void copyValue(double value, int n, double[] values) {
    copyValue(value, n, 0, values);
  }

  /**
   * Copy value.
   *
   * @param value  the value
   * @param n      the n
   * @param offset the offset
   * @param values the values
   */
  public static void copyValue(double value, int n, int offset, double[] values) {
    if (isNullOrEmpty(values)) {
      return;
    }

    for (int i = 0; i < n; ++i) {
      int io = i + offset;

      if (io >= values.length) {
        break;
      }

      values[io] = value;
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
   * Copy value.
   *
   * @param value  the value
   * @param values the values
   * @param n      the n
   * @param offset the offset
   */
  public static void copyValue(int value, int[] values, int n, int offset) {
    if (isNullOrEmpty(values)) {
      return;
    }

    for (int i = 0; i < n; ++i) {
      int io = i + offset;

      if (io >= values.length) {
        break;
      }

      values[i + offset] = value;
    }
  }

  /**
   * Pad a list with a set value until it reaches a minimum size.
   *
   * @param <T>   the generic type
   * @param items the items
   * @param v     the v
   * @param size  the size
   * @return the list
   */
  public static <T> List<T> pad(Collection<T> items, T v, int size) {
    if (isNullOrEmpty(items)) {
      return Collections.emptyList();
    }

    List<T> ret = new ArrayList<T>(size);

    ret.addAll(items);

    if (ret.size() < size) {
      while (ret.size() < size) {
        ret.add(v);
      }
    }

    return ret;
  }

  /**
   * Returns the indices of null entries.
   *
   * @param values the values
   * @return the list
   */
  public static List<Integer> nulls(List<?> values) {
    List<Integer> ret = new ArrayList<Integer>();

    for (int i = 0; i < values.size(); ++i) {
      if (values.get(i) == null) {
        ret.add(i);
      }
    }

    return ret;
  }

  /**
   * Null or zero length.
   *
   * @param values the values
   * @return the list
   */
  public static List<Integer> nullOrZeroLength(List<?> values) {
    List<Integer> ret = new ArrayList<Integer>();

    for (int i = 0; i < values.size(); ++i) {
      Object v = values.get(i);

      if (values.get(i) == null || v.toString().length() == 0) {
        ret.add(i);
      }
    }

    return ret;
  }

  /**
   * Initialize a list with values.
   *
   * @param <T>   the generic type
   * @param size  the size
   * @param value the value
   * @return the list
   */
  public static <T> List<T> initList(int size, T value) {
    List<T> ret = new ArrayList<T>(size);

    while (ret.size() < size) {
      ret.add(value);
    }

    return ret;
  }

  /**
   * If the list is null, return an empty list, otherwise return the list.
   *
   * @param <T>    the generic type
   * @param values the values
   * @return the list
   */
  public static <T> List<T> makeEmpty(List<T> values) {
    if (values == null) {
      return Collections.emptyList();
    } else {
      return values;
    }
  }

  /**
   * Unique.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param m1  the m 1
   * @param m2  the m 2
   * @return the list
   */
  public static <K, V> List<K> unique(final Map<K, V> m1, final Map<K, V> m2) {
    return unique(m1.keySet(), m2.keySet());
  }

  /**
   * Finds the items unique to c1 that are not in c2.
   *
   * @param <K> the key type
   * @param c1  the c 1
   * @param c2  the c 2
   * @return the list
   */
  public static <K> List<K> unique(final Collection<K> c1, final Set<K> c2) {
    List<K> ret = new ArrayList<K>(c1.size());

    for (K c : c1) {
      if (!c2.contains(c)) {
        ret.add(c);
      }
    }

    return ret;
  }

  /**
   * Pick a sub set of items across a set of values.
   *
   * @param <T>    the generic type
   * @param values the values
   * @param size   the size
   * @return the list
   */
  public static <T> List<T> subSample(List<T> values, int size) {
    int skip = Math.max(1, values.size() / size);

    int i = 0;

    List<T> ret = new ArrayList<T>(size);

    while (ret.size() < size && i < values.size()) {
      ret.add(values.get(i));

      i += skip;
    }

    return ret;
  }

  /**
   * Takes a collection of keys and a map and returns the values associated with
   * the keys. Values are returned in the order keys are iterated from the keys
   * collection.
   *
   * @param <K>  the key type
   * @param <V>  the value type
   * @param keys the keys
   * @param map  the map
   * @return the list
   */
  public static <K, V> List<V> map(final Collection<K> keys, Map<K, V> map) {
    List<V> ret = new ArrayList<V>();

    for (K key : keys) {
      ret.add(map.get(key));
    }

    return ret;
  }

  /**
   * Swap the keys and values in a map.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param map the map
   * @return the map
   */
  public static <K, V> Map<V, Set<K>> invertMap(Map<K, V> map) {
    SetMultiMap<V, K> ret = HashSetMultiMap.create();

    for (K key : map.keySet()) {
      ret.get(map.get(key)).add(key);
    }

    return ret;
  }

  /**
   * Counts the number of keys that map to a given value.
   *
   * @param <K>  the key type
   * @param <V>  the value type
   * @param map  the map
   * @param type the type
   * @return the int
   */
  public static <K, V> int countKeys(final Map<K, V> map, final V type) {
    int ret = 0;

    for (K k : map.keySet()) {
      if (map.get(k).equals(type)) {
        ++ret;
      }
    }

    return ret;
  }

  /**
   * Returns a list of the keys whose value matches a given value.
   *
   * @param <K>  the key type
   * @param <V>  the value type
   * @param map  the map
   * @param type the type
   * @return the list
   */
  public static <K, V> List<K> keys(final Map<K, V> map, final V type) {
    List<K> ret = new ArrayList<K>();

    for (K k : map.keySet()) {
      if (map.get(k).equals(type)) {
        ret.add(k);
      }
    }

    return ret;
  }

  /**
   * Sub map.
   *
   * @param <K>  the key type
   * @param <V>  the value type
   * @param map  the map
   * @param keys the keys
   * @return the map
   */
  public static <K, V> Map<K, V> subMap(Map<K, V> map, List<K> keys) {
    if (isNullOrEmpty(map) || isNullOrEmpty(keys)) {
      return Collections.emptyMap();
    }

    Map<K, V> ret = new HashMap<K, V>();

    for (K key : keys) {
      if (map.containsKey(key)) {
        ret.put(key, map.get(key));
      }
    }

    return ret;
  }

  /**
   * Casts a double list to an int list.
   *
   * @param values the values
   * @return the list
   */
  public static List<Integer> double2Int(List<Double> values) {
    List<Integer> ret = new ArrayList<Integer>(values.size());

    for (double d : values) {
      ret.add((int) d);
    }

    return ret;
  }

  /**
   * Int 2 double.
   *
   * @param values the values
   * @return the list
   */
  public static List<Double> int2Double(List<Integer> values) {
    List<Double> ret = new ArrayList<Double>(values.size());

    for (int v : values) {
      ret.add((double) v);
    }

    return ret;
  }

  /**
   * Takes a list and creates a mapping from each item to its index in the list.
   *
   * @param <V>    the value type
   * @param values the values
   * @return the map
   */
  public static <V> Map<V, Integer> toIndexMap(List<V> values) {
    Map<V, Integer> ret = new HashMap<V, Integer>();

    for (int i = 0; i < values.size(); ++i) {
      ret.put(values.get(i), i);
    }

    return ret;
  }

  public static Map<String, Integer> toIndexMap(String[] values) {
    Map<String, Integer> ret = new HashMap<String, Integer>();

    for (int i = 0; i < values.length; ++i) {
      ret.put(values[i], i);
    }

    return ret;
  }

  /**
   * Index a list of numbers to track their positions.
   *
   * @param items the items
   * @return the list
   */
  public static List<Indexed<Integer, Double>> index(List<Double> items) {
    List<Indexed<Integer, Double>> ret = new ArrayList<Indexed<Integer, Double>>(items.size());

    for (int i = 0; i < items.size(); ++i) {
      ret.add(new Indexed<Integer, Double>(i, items.get(i)));
    }

    return ret;
  }

  /**
   * Index.
   *
   * @param items the items
   * @return the list
   */
  public static List<Indexed<Integer, Double>> index(double[] items) {
    int n = items.length;

    List<Indexed<Integer, Double>> ret = new ArrayList<Indexed<Integer, Double>>(n);

    for (int i = 0; i < n; ++i) {
      ret.add(new Indexed<Integer, Double>(i, items[i]));
    }

    return ret;
  }

  /**
   * Returns the minimum size of a set of collections.
   *
   * @param collections the collections
   * @return the int
   */
  public static int minSize(Collection<?>... collections) {
    int min = Integer.MAX_VALUE;

    for (Collection<?> l : collections) {
      if (l.size() < min) {
        min = l.size();
      }
    }

    return min;
  }

  /**
   * Given a set of lists, extracts the ith element of each and returns these
   * values in a list.
   *
   * @param i     the i
   * @param lists the lists
   * @return the list
   */
  public static List<Object> asList(int i, List<?>... lists) {
    List<Object> ret = new ArrayList<Object>(lists.length);

    for (List<?> l : lists) {
      ret.add(l.get(i));
    }

    return ret;
  }

  /**
   * Flatten a collection of collections into a list of items.
   *
   * @param <V>    the value type
   * @param values the values
   * @return the list
   */
  public static <V> List<V> flatten(List<List<V>> values) {
    List<V> ret = new ArrayList<V>(values.size());

    for (Collection<V> l : values) {
      ret.addAll(l);
    }

    return ret;
  }

  public static void fill(String[] array, String v) {
    Arrays.fill(array, v);
  }

  /**
   * Set an array to have the same value in all elements.
   *
   * @param array the array.
   * @param v     the v
   */
  public static void fill(double[] array, double v) {
    Arrays.fill(array, v);
  }

  /**
   * Fill.
   *
   * @param array the array
   * @param v     the v
   * @param n     the n
   */
  public static void fill(double[] array, int n, double v) {
    fill(array, 0, n, v);
  }

  /**
   * Fill an array with v starting at position s and finishing at e (exclusive).
   *
   * @param array the array.
   * @param v     the value to copy.
   * @param s     the starting position.
   * @param e     the ending position (exclusive).
   */
  public static void fill(double[] array, int s, int e, double v) {
    Arrays.fill(array, s, e, v);
  }

  //

  public static void fill(int[] array, int v) {
    Arrays.fill(array, v);
  }

  /**
   * Fill.
   *
   * @param array the array
   * @param v     the v
   * @param n     the n
   */
  public static void fill(int[] array, int n, int v) {
    fill(array, 0, n, v);
  }

  /**
   * Fill an array with v starting at position s and finishing at e (exclusive).
   *
   * @param array the array.
   * @param v     the value to copy.
   * @param s     the starting position.
   * @param e     the ending position (exclusive).
   */
  public static void fill(int[] array, int s, int e, int v) {
    Arrays.fill(array, s, e, v);
  }

  /**
   * Copy a value into an array, but keep the maximum between the array and the
   * value at a given position to guarantee the maximum value is kept.
   * 
   * @param array
   * @param v
   * @param s
   * @param e
   */
  public static void fillMax(double[] array, double v, int s, int e) {
    for (int i = s; i < e; ++i) {
      if (v > array[i]) {
        // Record the maximum score found
        array[i] = v;
      }
    }
  }

  /**
   * Convert a double list to an array.
   *
   * @param values the values
   * @return the double[]
   */
  public static double[] toArray(List<Double> values) {
    double[] ret = new double[values.size()];

    for (int i = 0; i < values.size(); ++i) {
      ret[i] = values.get(i);
    }

    return ret;
  }

  /**
   * To list.
   *
   * @param values the values
   * @return the list
   */
  public static List<Double> toList(final double[] values) {
    List<Double> ret = new ArrayList<Double>(values.length);

    for (int i = 0; i < values.length; ++i) {
      ret.add(values[i]);
    }

    return ret;
  }

  /**
   * Adds all the items from source to target.
   *
   * @param <V>    the value type
   * @param source the source
   * @param target the target
   */
  public static <V> void addAll(final Collection<V> source, Collection<V> target) {
    for (V v : source) {
      target.add(v);
    }
  }

  /**
   * Adds all the items from source to target.
   *
   * @param <V>    the value type
   * @param source the source
   * @param target the target
   */
  public static <V> void addAll(final Iterable<V> source, Collection<V> target) {
    for (V v : source) {
      target.add(v);
    }
  }

  /**
   * Create a copy of a list.
   *
   * @param <V>    the value type
   * @param values the values
   * @return the list
   */
  public static <V> List<V> copy(final List<V> values) {
    List<V> ret = new ArrayList<V>(values.size());

    for (V v : values) {
      ret.add(v);
    }

    return ret;
  }

  /**
   * Returns true if the index is within the bounds of the array.
   *
   * @param <V>    the value type
   * @param index  the index
   * @param values the values
   * @return true, if successful
   */
  public static boolean inBounds(int index, List<?> values) {
    return Mathematics.inBound(index, 0, values.size());
  }

  /**
   * Returns a cyclic index that allows you to address indices between 0 and (size
   * - 1). If the index is less than 0, it is wrapped around the size. For example
   * -1 maps to (size - 1) and -2 maps to (size - 2)
   *
   * @param index the index
   * @param size  the size
   * @return the int
   */
  public static int cyclicIndex(int index, int size) {
    if (size == 0) {
      return index;
    }

    if (index < 0) {
      return size - 1 - (-(1 + index) % size);
    } else {
      return index % size;
    }
  }

  /**
   * Tests whether a map contains a key from a list of possible keys. The first
   * key that the map contains is returned or null if none of the keys are in the
   * map.
   *
   * @param <K>  the key type
   * @param <V>  the value type
   * @param map  the map
   * @param keys the keys
   * @return the k
   */
  @SafeVarargs
  public static <K, V> K contains(Map<K, V> map, K... keys) {
    for (K key : keys) {
      if (map.containsKey(key)) {
        return key;
      }
    }

    return null;
  }

  /**
   * Return the values in a map as as list.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param map the map
   * @return the list
   */
  public static <K, V> List<V> values(Map<K, V> map) {
    List<V> ret = new ArrayList<V>(map.size());

    for (K key : map.keySet()) {
      ret.add(map.get(key));
    }

    return ret;
  }

  /**
   * Returns true if an element of c1 is contained within c2.
   *
   * @param <K> the key type
   * @param c1  the c 1
   * @param c2  the c 2
   * @return true, if successful
   */
  public static <K> boolean contains(final Collection<K> c1, final Collection<K> c2) {
    for (K c : c1) {
      if (c2.contains(c)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Apply a function to an array.
   * 
   * @param in
   * @param out
   * @param f
   */
  public static <T, V> void apply(T[] in, V[] out, Function<T, V> f) {
    int l = Math.min(in.length, out.length);

    for (int i = 0; i < l; ++i) {
      out[i] = f.apply(in[i]);
    }
  }

  /**
   * Finds the index of the next position in the array with a value different to a
   * given value. This can be used to find runs of the same value in an array.
   * Returns the last index of the array if all the array values are the same.
   * 
   * @param values The array to search
   * @param f      The current value.
   * @param i      The starting index.
   * @return
   */
  public static int nextDiffValIdx(final double[] values, double f, int start) {
    int ret = values.length - 1;

    for (int i = start; i < values.length; ++i) {
      if (values[i] != f) {
        ret = i;
        break;
      }
    }

    return ret;
  }

  /**
   * Finds the next zero entry in an array.
   * 
   * @param values
   * @param start
   * @return
   */
  public static int nextZero(final double[] values, int start) {
    int ret = values.length - 1;

    for (int i = start; i < values.length; ++i) {
      if (values[i] == 0) {
        ret = i;
        break;
      }
    }

    return ret;
  }

}
