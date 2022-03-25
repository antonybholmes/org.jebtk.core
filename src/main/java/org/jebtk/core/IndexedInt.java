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
import java.util.List;

/**
 * Wraps an item with an associated index such as its position in a list.
 * Compliments IndexValue
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class IndexedInt<T extends Comparable<? super T>> extends Indexed<Integer, T> {

  /**
   * Instantiates a new indexed value int.
   *
   * @param index the index
   * @param item  the item
   */
  public IndexedInt(int index, T item) {
    super(index, item);
  }

  /**
   * Adds an index to items so we can keep track of where they occur in a list if
   * we sort them etc.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<Indexed<Integer, T>> index(Collection<T> items) {
    List<Indexed<Integer, T>> ret = new ArrayList<Indexed<Integer, T>>(items.size());

    int c = 0;

    for (T item : items) {
      ret.add(new IndexedInt<T>(c, item));

      ++c;
    }

    return ret;
  }

  /**
   * Index.
   *
   * @param <T>   the generic type
   * @param items the items
   * @return the list
   */
  public static <T extends Comparable<? super T>> List<Indexed<Integer, T>> index(T[] items) {
    List<Indexed<Integer, T>> ret = new ArrayList<Indexed<Integer, T>>(items.length);

    for (int i = 0; i < items.length; ++i) {
      ret.add(new IndexedInt<T>(i, items[i]));
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
    List<Indexed<Integer, Double>> ret = new ArrayList<Indexed<Integer, Double>>(items.length);

    for (int i = 0; i < items.length; ++i) {
      ret.add(new IndexedInt<Double>(i, items[i]));
    }

    return ret;
  }
}
