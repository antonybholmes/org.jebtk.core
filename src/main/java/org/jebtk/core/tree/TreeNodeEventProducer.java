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
package org.jebtk.core.tree;

import org.jebtk.core.event.ChangeEvent;

/**
 * For classes that generate ModernClickEvents.
 *
 * @author Antony Holmes
 */
public interface TreeNodeEventProducer {

  /**
   * Add an action listener.
   *
   * @param l the l
   */
  public void addTreeNodeListener(ITreeNodeEventListener l);

  /**
   * Remove an action listener.
   *
   * @param l the l
   */
  public void removeTreeNodeListener(ITreeNodeEventListener l);

  /**
   * fire an event.
   *
   * @param e the e
   */
  public void fireTreeNodeChanged(ChangeEvent e);

  /**
   * Fire tree node updated.
   *
   * @param e the e
   */
  public void fireTreeNodeUpdated(ChangeEvent e);
}
