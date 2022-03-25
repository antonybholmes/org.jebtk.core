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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Represents a thread safe collection of objects that can be reused.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class ObjectPool<T> {

  /**
   * The pool.
   */
  protected List<T> pool = new ArrayList<T>();

  /**
   * The available.
   */
  protected Stack<T> available = new Stack<T>();

  /**
   * The used.
   */
  protected Set<T> used = new HashSet<T>();

  /**
   * The max size.
   */
  protected int maxSize;

  /**
   * The name.
   */
  private String name;

  /**
   * Instantiates a new object pool.
   *
   * @param name    the name
   * @param maxSize the max size
   */
  public ObjectPool(String name, int maxSize) {
    this.name = name;
    this.maxSize = maxSize;

    pool = new ArrayList<T>(maxSize);
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Adds the.
   *
   * @param object the object
   */
  public synchronized void add(T object) {
    if (pool.size() == maxSize) {
      return;
    }

    pool.add(object);
    available.push(object);
  }

  /**
   * Check out one of the objects from the pool.
   *
   * @return the t
   */
  public synchronized T checkOut() {
    if (available.size() > 0) {
      T object = available.pop();

      used.add(object);

      return object;
    }

    return null;
  }

  /**
   * Check an object in to the store. The object must have been checked out before
   * it can be checked in.
   *
   * @param object the object
   */
  public synchronized void checkIn(T object) {
    if (!used.contains(object)) {
      return;
    }

    // System.err.println("check in " + object.toString());

    used.remove(object);

    available.push(object);
  }

}
