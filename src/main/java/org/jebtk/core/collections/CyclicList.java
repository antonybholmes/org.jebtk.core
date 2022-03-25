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

import java.util.List;

/**
 * Allows positive or negative indices and cycles around the list if the index
 * is outside the list boundaries. Thus for example, -1 will give you the last
 * element in the list since in a cycle, the list end is joined to the list
 * start so the element before the start, would be the last element in the list.
 * Unlike a {@code CircularArray}, the size of the cyclic list can change.
 *
 * @param <T> the generic type
 */
public class CyclicList<T> extends ListContainer<T> {

  /**
   * Instantiates a new cyclic list.
   *
   * @param list the list
   */
  public CyclicList(List<T> list) {
    super(list);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#get(int)
   */
  @Override
  public T get(int index) {
    // System.err.println("cyclic " + index + " " + size() + " " +
    // CollectionUtils.cyclicIndex(index, size()));

    return super.get(CollectionUtils.cyclicIndex(index, size()));
  }
}
