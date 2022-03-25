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

/**
 * Specialized root node for a tree. This node cannot contain data nor may its
 * name be changed.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class TreeRootNode<T> extends TreeNode<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new tree root node.
   */
  public TreeRootNode() {
    super("root");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.tree.TreeNode#setName(java.lang.String)
   */
  @Override
  public final void setName(String name) {
    // do nothing
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.tree.TreeNode#setValue(java.lang.Object)
   */
  @Override
  public final void setValue(T value) {
    // do nothing
  }

}
