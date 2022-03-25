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

import java.util.HashSet;
import java.util.Set;

/**
 * The Class HashSetCreator.
 *
 * @param <V> the value type
 */
public class HashSetCreator<V> implements SetCreator<V> {

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.collections.EntryCreator#create()
   */
  @Override
  public Set<V> newEntry() {
    return new HashSet<V>();
  }

  /**
   * Creates the.
   *
   * @param <V1> the generic type
   * @return the collection creator
   */
  public static <V1> SetCreator<V1> create() {
    return new HashSetCreator<V1>();
  }
}
