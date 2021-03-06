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

import java.util.EventListener;

import org.jebtk.core.event.ChangeEvent;

/**
 * The listener interface for receiving treeNodeEvent events. The class that is
 * interested in processing a treeNodeEvent event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addTreeNodeEventListener<code> method. When the
 * treeNodeEvent event occurs, that object's appropriate method is invoked.
 *
 * @see TreeNodeEventEvent
 */
public interface ITreeNodeEventListener extends EventListener {

  /**
   * Node changed.
   *
   * @param e the e
   */
  public void nodeChanged(ChangeEvent e);

  /**
   * Invoked when node update occurs.
   *
   * @param e the e
   */
  public void nodeUpdated(ChangeEvent e);

}
