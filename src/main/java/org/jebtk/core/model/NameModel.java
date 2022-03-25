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
import java.util.List;

import org.jebtk.core.event.ChangeListeners;

/**
 * Generic model for sharing named items.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public abstract class NameModel<T> extends ChangeListeners implements Iterable<String> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Adds the.
   *
   * @param name the name
   * @param item the item
   */
  public void add(String name, T item) {
    update(name, item);

    fireChanged();
  }

  /**
   * Update.
   *
   * @param name the name
   * @param item the item
   */
  public abstract void update(String name, T item);

  /**
   * Removes the.
   *
   * @param name the name
   */
  public abstract void remove(String name);

  /**
   * Gets the.
   *
   * @param name the name
   * @return the t
   */
  public abstract T get(String name);

  /**
   * Contains.
   *
   * @param name the name
   * @return true, if successful
   */
  public abstract boolean contains(String name);

  /**
   * Clear.
   */
  public abstract void clear();

  /**
   * Size.
   *
   * @return the int
   */
  public abstract int size();

  /**
   * Returns the items in the model as a list. This list is a copy of items in the
   * model so altering the list will not alter the model.
   *
   * @return the list
   */
  public List<T> toList() {
    List<T> ret = new ArrayList<T>();

    for (String name : this) {
      ret.add(get(name));
    }

    return ret;
  }
}