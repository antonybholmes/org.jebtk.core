/**
 * Copyright 2017 Antony Holmes
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
package org.jebtk.core;

/**
 * Interface for classes that have a get/set method combination.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public interface GetterSetter<T> {
  /**
   * Store an object.
   * 
   * @param o An object to be stored.
   */
  void set(T o);

  /**
   * Return the stored object.
   * 
   * @return The stored object.
   */
  T get();
}
