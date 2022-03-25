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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Immutable list for lists that should not be changed once created. The
 * benefits of a vector without the ability to modify it.
 *
 * @author Antony Holmes
 * @param <T1> the generic type
 * @param <T2> the generic type
 */
public class ImmutableMap<T1, T2> extends HashMap<T1, T2> implements Iterable<Entry<T1, T2>> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The locked.
   */
  private boolean locked = false;

  /**
   * Lock the map to prevent futher entries being added.
   */
  public final void lock() {
    locked = true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
   */
  public T2 put(T1 key, T2 value) {
    if (locked) {
      return null;
    }

    return super.put(key, value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashMap#clear()
   */
  public void clear() {
    // Do nothing
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashMap#remove(java.lang.Object)
   */
  public T2 remove(Object o) {
    // Prevent items from being removed.

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  public Iterator<Entry<T1, T2>> iterator() {
    return entrySet().iterator();
  }
}
