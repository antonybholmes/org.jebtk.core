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
import java.util.List;

/**
 * A list container is a wrapper around a list to form the basis of components
 * that require a list interface, but want to easily change list functions such
 * as preventing items from being removed or added etc.
 *
 * @param <T> the generic type
 */
public class ListContainer<T> extends ReadOnlyListContainer<T> {

  /**
   * Instantiates a new list container.
   *
   * @param list the list
   */
  public ListContainer(Collection<T> list) {
    this(CollectionUtils.toList(list));
  }

  /**
   * Instantiates a new list container.
   *
   * @param list the list
   */
  public ListContainer(List<T> list) {
    super(list);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#add(java.lang.Object)
   */
  @Override
  public boolean add(T item) {
    return mList.add(item);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends T> items) {
    return mList.addAll(items);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#add(int,
   * java.lang.Object)
   */
  @Override
  public void add(int arg0, T arg1) {
    mList.add(arg0, arg1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#addAll(int,
   * java.util.Collection)
   */
  @Override
  public boolean addAll(int arg0, Collection<? extends T> arg1) {
    return mList.addAll(arg0, arg1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#clear()
   */
  @Override
  public void clear() {
    mList.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.collections.ReadOnlyListContainer#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object o) {
    return mList.remove(o);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#remove(int)
   */
  @Override
  public T remove(int index) {
    return mList.remove(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#removeAll(java.util.
   * Collection)
   */
  @Override
  public boolean removeAll(Collection<?> c) {
    return mList.removeAll(c);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#retainAll(java.util.
   * Collection)
   */
  @Override
  public boolean retainAll(Collection<?> c) {
    return mList.retainAll(c);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.collections.ReadOnlyListContainer#set(int,
   * java.lang.Object)
   */
  @Override
  public T set(int index, T element) {
    return mList.set(index, element);
  }
}
