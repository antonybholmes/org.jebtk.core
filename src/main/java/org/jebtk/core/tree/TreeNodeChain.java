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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Holds a list of nodes as they appear in a tree and represents the nodes in a
 * path.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class TreeNodeChain<T> implements Iterable<TreeNode<T>> {

  /**
   * The nodes.
   */
  private final List<TreeNode<T>> nodes = new ArrayList<TreeNode<T>>();

  /**
   * Adds the.
   *
   * @param node the node
   */
  public void add(TreeNode<T> node) {
    nodes.add(node);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<TreeNode<T>> iterator() {
    return nodes.iterator();
  }

}
