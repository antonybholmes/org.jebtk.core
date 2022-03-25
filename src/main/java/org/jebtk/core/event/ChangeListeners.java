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

/**
 * Provides the ability to register and unregister ModernClickEventListeners for
 * controls and provides standard functions to interface with {
 * EventListenerList by taking care of casting etc.
 *
 * @author Antony Holmes
 *
 */
public class ChangeListeners extends EventProducer<ChangeListener> implements ChangeEventProducer {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.lib.event.ChangeEventProducer#addChangeListener(org.abh.lib.event.
   * ChangeListener)
   */
  public synchronized void addChangeListener(ChangeListener l) {
    mListeners.add(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.event.ChangeEventProducer#removeChangeListener(org.abh.lib.
   * event. ChangeListener)
   */
  public synchronized void removeChangeListener(ChangeListener l) {
    mListeners.remove(l);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.event.ChangeEventProducer#fireChanged(org.abh.lib.event.
   * ChangeEvent)
   */
  public synchronized void fireChanged(ChangeEvent e) {
    for (ChangeListener l : mListeners) {
      // System.err.println("change listener " + l);

      l.changed(e);
    }
  }

  /**
   * Fire changed.
   */
  public void fireChanged() {
    // System.err.println("fire changed " + this);

    fireChanged(new ChangeEvent(this));
  }
}
