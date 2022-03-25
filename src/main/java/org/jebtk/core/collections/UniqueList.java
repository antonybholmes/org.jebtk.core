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

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class UniqueList provides a wrapper on a list to ensure items are added
 * uniquely so that the order is preserved, but only one of a given item may
 * appear in the list.
 *
 * @param <T> the generic type
 */
public class UniqueList<T> extends ListContainer<T> {

  /** The m used. */
  private final Set<T> mUsed = new HashSet<T>();

  /**
   * Instantiates a new unique array list.A unique array list is an array list
 that preserves the order of items as they are added, but discards subsequent
 duplicates.
   * @param list
   */
  public UniqueList(List<T> list) {
    super(list);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#add(java.lang.Object)
   */
  @Override
  public boolean add(T item) {
    if (!mUsed.contains(item)) {
      mUsed.add(item);

      super.add(item);
    }

    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.collections.ReadOnlyListContainer#contains(java.lang.Object)
   */
  @Override
  public boolean contains(Object item) {
    return mUsed.contains(item);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends T> items) {
    for (T item : items) {
      add(item);
    }

    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ListContainer#clear()
   */
  @Override
  public void clear() {
    mUsed.clear();

    super.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ListContainer#remove(int)
   */
  @Override
  public T remove(int i) {
    mUsed.remove(get(i));

    return super.remove(i);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ListContainer#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object o) {
    mUsed.remove(o);

    return super.remove(o);
  }
}
