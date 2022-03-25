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
package org.jebtk.core.pool;

/**
 * Represents a thread safe collection of objects that can be reused.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class DynamicObjectPool<T> extends ObjectPool<T> {

  /**
   * The constant DEFAULT_SIZE.
   */
  private static final int DEFAULT_SIZE = 100;

  /**
   * The creator.
   */
  private ObjectCreator<T> creator = null;

  /**
   * Instantiates a new dynamic object pool.
   *
   * @param name    the name
   * @param creator the creator
   * @param maxSize the max size
   */
  public DynamicObjectPool(String name, ObjectCreator<T> creator, int maxSize) {
    super(name, maxSize);

    this.creator = creator;
  }

  /**
   * Instantiates a new dynamic object pool.
   *
   * @param name    the name
   * @param creator the creator
   */
  public DynamicObjectPool(String name, ObjectCreator<T> creator) {
    super(name, DEFAULT_SIZE);

    this.creator = creator;
  }

  /**
   * Check out one of the objects from the pool.
   *
   * @return the t
   */
  public synchronized T checkOut() {
    T object = super.checkOut();

    if (object != null) {
      return object;
    }

    // attempt to increase stack size
    add(creator.create());

    object = super.checkOut();

    if (object != null) {
      return object;
    }

    return null;
  }
}
