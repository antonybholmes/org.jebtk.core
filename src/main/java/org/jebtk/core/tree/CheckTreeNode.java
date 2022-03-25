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
package org.jebtk.core.tree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A Node that has a selected property.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class CheckTreeNode<T> extends TreeNode<T> {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m checked. */
  private boolean mChecked;

  /**
   * Instantiates a new check tree node.
   *
   * @param name  the name
   * @param value the value
   */
  public CheckTreeNode(String name, T value) {
    this(name, value, false);
  }

  /**
   * Instantiates a new check tree node.
   *
   * @param name the name
   */
  public CheckTreeNode(String name) {
    this(name, false);
  }

  /**
   * Instantiates a new check tree node.
   *
   * @param name     the name
   * @param selected the selected
   */
  public CheckTreeNode(String name, boolean selected) {
    this(name, null, selected);
  }

  /**
   * Instantiates a new check tree node.
   *
   * @param name     the name
   * @param value    the value
   * @param selected the selected
   */
  public CheckTreeNode(String name, T value, boolean selected) {
    super(name, value);

    setChecked(selected);
  }

  /**
   * Gets the checks if is checked.
   *
   * @return the checks if is checked
   */
  public boolean getIsChecked() {
    return mChecked;
  }

  /**
   * Updates the node to be selected and by default all of its children. Use
   * {@code setSelected(selected, updateChildren)} to control whether the children
   * are updated.
   *
   * @param checked the new checked
   */
  public void setChecked(boolean checked) {
    setChecked(checked, true);
  }

  /**
   * Sets the checked.
   *
   * @param checked   the checked
   * @param recursive the recursive
   */
  public void setChecked(boolean checked, boolean recursive) {
    updateChecked(checked, recursive);

    fireTreeNodeUpdated();
  }

  /**
   * Update checked.
   *
   * @param checked the checked
   */
  public void updateChecked(boolean checked) {
    updateChecked(checked, true);
  }

  /**
   * Update checked.
   *
   * @param checked   the checked
   * @param recursive the recursive
   */
  public void updateChecked(boolean checked, boolean recursive) {
    Deque<TreeNode<T>> stack = new ArrayDeque<>();

    stack.push(this);

    while (!stack.isEmpty()) {
      TreeNode<T> n = stack.pop();

      if (n instanceof CheckTreeNode) {
        CheckTreeNode<T> node = (CheckTreeNode<T>) n;

        node.mChecked = checked;

        if (recursive) {
          for (TreeNode<T> child : node) {
            stack.push(child);
          }
        }
      }
    }
  }
}
