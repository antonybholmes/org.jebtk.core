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
import java.util.Map;

/**
 * The interface MultiMap for maps that map keys to collections of values. The
 * keys are automatically created if they do not exist.
 *
 * @param <K> the key type
 * @param <V> the value type
 * @param <T> the value type
 */
public interface MultiMap<K, V, T extends Collection<V>> extends Map<K, T> {

  /** The Constant DEFAULT_INITIAL_CAPACITY. */
  public static final int DEFAULT_INITIAL_CAPACITY = 100;
}
