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

/**
 * Immutable list for lists that should not be changed once created. The
 * benefits of a vector without the ability to modify it.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class ImmutableList<T> extends ArrayList<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The locked.
   */
  private boolean locked = false;

  /**
   * Lock the list to prevent futher entries being added.
   */
  public final void lock() {
    locked = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#add(java.lang.Object)
   */
  public boolean add(T item) {
    if (locked) {
      return false;
    }

    return super.add(item);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#clear()
   */
  public void clear() {
    // Prevent items from being removed.
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#remove(int)
   */
  public T remove(int i) {
    // Prevent items from being removed.

    return null;
  }
}
