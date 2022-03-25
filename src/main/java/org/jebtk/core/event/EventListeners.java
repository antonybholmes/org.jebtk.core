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
package org.jebtk.core.event;

import java.util.Collections;
import java.util.EventListener;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides the ability to register and unregister ModernClickEventListeners for
 * controls and provides standard functions to interface with {
 * EventListenerList by taking care of casting etc.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class EventListeners<T extends EventListener> implements Iterable<T> {

  private Set<T> mListeners = Collections.newSetFromMap(new ConcurrentHashMap<T, Boolean>());

  /**
   * Instantiates a new event listeners.
   */
  public EventListeners() {

  }

  /**
   * Instantiates a new event listeners.
   *
   * @param l the l
   */
  public EventListeners(EventListeners<T> l) {
    addAll(l);
  }

  public void add(T listener) {
    mListeners.add(listener);
  }

  public void remove(T listener) {
    mListeners.remove(listener);
  }

  public void addAll(EventListeners<T> listeners) {
    for (T l : listeners) {
      add(l);
    }
  }

  @Override
  public Iterator<T> iterator() {
    return mListeners.iterator();
  }
}