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
package org.jebtk.core.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jebtk.core.collections.UniqueArrayList;

/**
 * Model to store items indexed by name. Items are stored in the order they are
 * entered and are retrivable either by name or index.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class NameListModel<T> extends NameModel<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member items.
   */
  protected Map<String, T> mItemMap = new HashMap<String, T>();

  /** The m items. */
  protected List<String> mItems = new UniqueArrayList<String>();

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#add(java.lang.String, java.lang.Object)
   */
  @Override
  public void add(String name, T item) {
    update(name, item);

    fireChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#update(java.lang.String,
   * java.lang.Object)
   */
  @Override
  public void update(String name, T item) {
    mItems.add(name);
    mItemMap.put(name, item);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#remove(java.lang.String)
   */
  @Override
  public void remove(String name) {
    mItems.remove(name);
    mItemMap.remove(name);

    fireChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#get(java.lang.String)
   */
  @Override
  public T get(String name) {
    return mItemMap.get(name);
  }

  /**
   * Gets the.
   *
   * @param index the index
   * @return the t
   */
  public T get(int index) {
    return mItemMap.get(mItems.get(index));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#contains(java.lang.String)
   */
  @Override
  public boolean contains(String name) {
    return mItemMap.containsKey(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#clear()
   */
  @Override
  public void clear() {
    mItemMap.clear();
    mItems.clear();

    fireChanged();
  }

  /**
   * Size.
   *
   * @return the int
   */
  @Override
  public int size() {
    return mItemMap.size();
  }

  /**
   * Returns a sorted iterator of the names in this model.
   *
   * @return the iterator
   */
  @Override
  public Iterator<String> iterator() {
    return mItems.iterator();
  }
}