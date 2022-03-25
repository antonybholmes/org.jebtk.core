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

import org.jebtk.core.collections.CollectionUtils;

/**
 * Pair items together.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 * @param <V> the value type
 */
public class Pair<T, V> {

  /**
   * The item1.
   */
  T item1;

  /**
   * The item2.
   */
  V item2;

  /**
   * Instantiates a new pair.
   *
   * @param item1 the item1
   * @param item2 the item2
   */
  public Pair(T item1, V item2) {
    this.item1 = item1;
    this.item2 = item2;
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
  public static <T, V> List<Pair<T, V>> pair(List<T> l1, List<V> l2) {
    if (CollectionUtils.isNullOrEmpty(l1) || CollectionUtils.isNullOrEmpty(l2) || l1.size() != l2.size()) {
      return null;
    }

    List<Pair<T, V>> ret = new ArrayList<Pair<T, V>>();

    for (int i = 0; i < l1.size(); ++i) {
      ret.add(new Pair<T, V>(l1.get(i), l2.get(i)));
    }

    return ret;
  }
}
