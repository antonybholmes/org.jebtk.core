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
 * @param <X> the generic type
 */
public class MapModel<T extends Comparable<? super T>, X> extends HashMap<T, X> implements ChangeEventProducer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The listeners.
   */
  private final ChangeListeners mListeners = new ChangeListeners();

  /**
   * Adds the.
   *
   * @param key  the key
   * @param item the item
   * @return the x
   */
  public X add(T key, X item) {
    X ret = update(key, item);

    fireChanged();

    return ret;
  }

  /**
   * Update.
   *
   * @param key  the key
   * @param item the item
   * @return the x
   */
  public X update(T key, X item) {
    return put(key, item);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashMap#clear()
   */
  @Override
  public void clear() {
    super.clear();

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