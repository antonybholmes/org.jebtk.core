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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeEventProducer;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.event.ChangeListeners;

/**
 * Generic model for sharing items and receiving notification when the items
 * change.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class SetModel<T extends Comparable<? super T>> extends HashSet<T> implements ChangeEventProducer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The listeners.
   */
  private final ChangeListeners mListeners = new ChangeListeners();

  /**
   * Sets the.
   *
   * @param item the item
   */
  public void set(T item) {
    set(CollectionUtils.asList(item));
  }

  /**
   * Sets the.
   *
   * @param items the items
   */
  public void set(Collection<T> items) {
    addAll(items);

    fireChanged();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashSet#add(java.lang.Object)
   */
  @Override
  public boolean add(T item) {
    boolean ret = super.add(item);

    fireChanged();

    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashSet#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object item) {
    boolean ret = super.remove(item);

    fireChanged();

    return ret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashSet#clear()
   */
  public void clear() {
    clear();

    fireChanged();
  }

  /**
   * Gets the item count.
   *
   * @return the item count
   */
  public int getItemCount() {
    return size();
  }

  /**
   * To list.
   *
   * @return the list
   */
  public List<T> toList() {
    List<T> ret = new ArrayList<T>();

    for (T item : this) {
      ret.add(item);
    }

    return ret;
  }

  /**
   * Returns the items sorted.
   *
   * @return the sorted
   */
  public List<T> getSorted() {
    return CollectionUtils.sort(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.lib.event.ChangeEventProducer#addChangeListener(org.abh.lib.event.
   * ChangeListener)
   */
  @Override
  public void addChangeListener(ChangeListener l) {
    mListeners.addChangeListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.event.ChangeEventProducer#removeChangeListener(org.abh.lib.
   * event. ChangeListener)
   */
  @Override
  public void removeChangeListener(ChangeListener l) {
    mListeners.removeChangeListener(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.event.ChangeEventProducer#fireChanged(org.abh.lib.event.
   * ChangeEvent)
   */
  @Override
  public void fireChanged(ChangeEvent e) {
    mListeners.fireChanged(e);
  }

  /**
   * Fire changed.
   */
  public void fireChanged() {
    mListeners.fireChanged();
  }
}