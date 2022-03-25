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

/**
 * Immutable list for lists that should not be changed once created. The
 * benefits of a vector without the ability to modify it.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class ImmutableSet<T> extends HashSet<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashSet#clear()
   */
  @Override
  public void clear() {
    // Do nothing
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.HashSet#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object o) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.AbstractSet#removeAll(java.util.Collection)
   */
  @Override
  public boolean removeAll(Collection<?> items) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.AbstractCollection#retainAll(java.util.Collection)
   */
  @Override
  public boolean retainAll(Collection<?> items) {
    return false;
  }
}
