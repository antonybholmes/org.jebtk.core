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
import java.util.List;

/**
 * The class IndexedValueDouble.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class IndexedDouble<T extends Comparable<? super T>> extends Indexed<Double, T> {

  /**
   * Instantiates a new indexed value double.
   *
   * @param index the index
   * @param item  the item
   */
  public IndexedDouble(double index, T item) {
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
  public static <T extends Comparable<? super T>> List<IndexedDouble<T>> index(List<T> items) {
    List<IndexedDouble<T>> ret = new ArrayList<IndexedDouble<T>>(items.size());

    for (int i = 0; i < items.size(); ++i) {
      ret.add(new IndexedDouble<T>(i, items.get(i)));
    }

    return ret;
  }
}
