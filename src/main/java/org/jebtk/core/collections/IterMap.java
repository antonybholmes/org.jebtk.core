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

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Iter maps allow the keys to be iterated over to reduce coding.
 * 
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public interface IterMap<K, V> extends Map<K, V>, Iterable<Entry<K, V>>, Serializable {
  /**
   * Returns the first key in the map. The notion of first depends on the map
   * implementation.
   * 
   * @return
   */
  public Entry<K, V> first();

}
