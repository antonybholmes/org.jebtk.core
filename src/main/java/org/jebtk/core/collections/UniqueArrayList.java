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
import java.util.Collection;
import java.util.List;

/**
 * The class UniqueList.
 *
 * @param <T> the generic type
 */
public class UniqueArrayList<T> extends UniqueList<T> {

  /**
   * Instantiates a new unique array list. A unique array list is an array list
   * that preserves the order of items as they are added, but discards subsequent
   * duplicates.
   */
  public UniqueArrayList() {
    super(new ArrayList<T>());
  }

  public UniqueArrayList(Collection<T> list) {
    super(CollectionUtils.toList(list));
  }

  /**
   * Instantiates a new unique array list.
   *
   * @param size the size
   */
  public UniqueArrayList(int size) {
    super(new ArrayList<T>(size));
  }

  /**
   * Create a new unique array list.
   *
   * @param <TT> the generic type
   * @return the list
   */
  public static <TT> List<TT> create() {
    return new UniqueArrayList<>();
  }

  public static <TT> List<TT> create(Collection<TT> items) {
    return new UniqueArrayList<>(items);
  }
}
