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

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Generic model for sharing named items.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class NameMapModel<T> extends NameModel<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member items.
   */
  protected Map<String, T> mItems = new TreeMap<String, T>();

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
  public void update(String name, T item) {
    mItems.put(name, item);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#remove(java.lang.String)
   */
  @Override
  public void remove(String name) {
    mItems.remove(name);

    fireChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#get(java.lang.String)
   */
  @Override
  public T get(String name) {
    return mItems.get(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#contains(java.lang.String)
   */
  @Override
  public boolean contains(String name) {
    return mItems.containsKey(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#clear()
   */
  @Override
  public void clear() {
    mItems.clear();

    fireChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.model.NameModel#size()
   */
  @Override
  public int size() {
    return mItems.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<String> iterator() {
    return mItems.keySet().iterator();
  }
}