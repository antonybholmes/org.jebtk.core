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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jebtk.core.collections.ArrayListCreator;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.collections.DefaultHashMap;

/**
 * Generic index object that allows one object to be associated with some form
 * of index.
 *
 * @author Antony Holmes
 * @param <K> the generic type
 * @param <V> the value type
 */
public class Indexed<K extends Number, V extends Comparable<? super V>> implements Comparable<Indexed<K, V>> {

  /**
   * The member index.
   */
  public final K mIndex;

  /**
   * The member value.
   */
  public final V mValue;

  /**
   * Instantiates a new indexed value.
   *
   * @param index the index
   * @param item  the item
   */
  public Indexed(K index, V item) {
    mValue = item;
    mIndex = index;

    // mHash = mValue.toString() + index;
  }

  /**
   * Gets the index.
   *
   * @return the index
   */
  public K getIndex() {
    return mIndex;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public V getValue() {
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "[" + mIndex + ", " + mValue + "]";
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Indexed<K, V> l) {
    return mValue.compareTo(l.mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Indexed<?, ?>) {
      return compareTo((Indexed) o) == 0;
    } else {
      return false;
    }
  }

  /**
   * Return the indices from a list of indexed values.
   *
   * @param <K1>  the generic type
   * @param <V1>  the generic type
   * @param items the items
   * @return a list of indices.
   */
  public static <K1 extends Number, V1 extends Comparable<? super V1>> List<K1> indices(
      Collection<Indexed<K1, V1>> items) {
    List<K1> ret = new ArrayList<K1>(items.size());

    for (Indexed<K1, V1> index : items) {
      ret.add(index.mIndex);
    }

    return ret;
  }

  /**
   * Convert indexed values back to values.
   *
   * @param <K1>  the generic type
   * @param <V1>  the generic type
   * @param items the items
   * @return the list
   */
  public static <K1 extends Number, V1 extends Comparable<? super V1>> List<V1> values(List<Indexed<K1, V1>> items) {
    List<V1> ret = new ArrayList<V1>();

    for (Indexed<K1, V1> index : items) {
      ret.add(index.mValue);
    }

    return ret;
  }

  /**
   * Pair.
   *
   * @param <T> the generic type
   * @param <V> the value type
   * @param l1  the l1
   * @param l2  the l2
   * @return the list
   */
  public static <T extends Number, V extends Comparable<? super V>> List<Indexed<T, V>> pair(List<T> l1, List<V> l2) {
    if (CollectionUtils.isNullOrEmpty(l1) || CollectionUtils.isNullOrEmpty(l2) || l1.size() != l2.size()) {
      return Collections.emptyList();
    }

    List<Indexed<T, V>> ret = new ArrayList<Indexed<T, V>>();

    for (int i = 0; i < l1.size(); ++i) {
      ret.add(new Indexed<T, V>(l1.get(i), l2.get(i)));
    }

    return ret;
  }

  /**
   * Map values to index.
   *
   * @param <T>    the generic type
   * @param <V>    the value type
   * @param values the values
   * @return the map
   */
  public static <T extends Number, V extends Comparable<? super V>> Map<V, T> mapValuesToIndex(
      List<Indexed<T, V>> values) {
    if (CollectionUtils.isNullOrEmpty(values)) {
      return Collections.emptyMap();
    }

    Map<V, T> map = new HashMap<V, T>();

    for (Indexed<T, V> v : values) {
      map.put(v.mValue, v.mIndex);
    }

    return map;
  }

  /**
   * Creates the int.
   *
   * @param <V1>  the generic type
   * @param index the index
   * @param v     the v
   * @return the indexed
   */
  public static <V1 extends Comparable<? super V1>> Indexed<Integer, V1> createInt(int index, V1 v) {
    return new Indexed<Integer, V1>(index, v);
  }

  /**
   * Creates a map from the keys of the indexed values.
   *
   * @param <K1>    the generic type
   * @param <V1>    the generic type
   * @param indexed the indexed
   * @return the map
   */
  public static <K1 extends Number, V1 extends Comparable<? super V1>> Map<V1, List<Indexed<K1, V1>>> listToMap(
      List<Indexed<K1, V1>> indexed) {
    Map<V1, List<Indexed<K1, V1>>> ret = DefaultHashMap.create(new ArrayListCreator<Indexed<K1, V1>>());

    for (Indexed<K1, V1> item : indexed) {
      ret.get(item.getIndex()).add(item);
    }

    return ret;

  }

  /**
   * Index values using an integer index.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<Indexed<Integer, T>> intIndex(Collection<T> items) {
    List<Indexed<Integer, T>> ret = new ArrayList<Indexed<Integer, T>>(items.size());

    int c = 0;

    for (T item : items) {
      ret.add(new IndexedInt<T>(c, item));

      ++c;
    }

    return ret;
  }

  /**
   * Int index.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<Indexed<Integer, T>> intIndex(T[] items) {
    List<Indexed<Integer, T>> ret = new ArrayList<Indexed<Integer, T>>(items.length);

    int c = 0;

    for (T item : items) {
      ret.add(new IndexedInt<T>(c, item));

      ++c;
    }

    return ret;
  }

  /**
   * Int index.
   *
   * @param items the items
   * @return the list
   */
  public static List<Indexed<Integer, Double>> intIndex(double[] items) {
    List<Indexed<Integer, Double>> ret = new ArrayList<Indexed<Integer, Double>>(items.length);

    for (int i = 0; i < items.length; ++i) {
      ret.add(new IndexedInt<Double>(i, items[i]));
    }

    return ret;
  }
}
