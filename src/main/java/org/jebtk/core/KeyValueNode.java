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
package org.jebtk.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a node in a tree hierarchy. Each node consists of a string name
 * (key) which can be coupled with an object value. Each node may have children,
 * though each child must have a distinct name. Unlike Category node, node names
 * are not required to be standardized so the user must keep track of any naming
 * conventions used. This node is designed for textual tree structures without
 * repeating children (nodes with the same name). For a more ordered tree where
 * branch order can be inferred, try TreeNode
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class KeyValueNode<T> implements Iterable<KeyValueNode<T>>, Comparable<KeyValueNode<T>>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The children.
   */
  private Map<String, KeyValueNode<T>> children = new HashMap<String, KeyValueNode<T>>();

  /**
   * The name.
   */
  protected String name;

  /**
   * The value.
   */
  private T value = null;

  /**
   * Instantiates a new key value node.
   *
   * @param name the name
   */
  public KeyValueNode(String name) {
    this.name = name;
  }

  /**
   * Instantiates a new key value node.
   *
   * @param name  the name
   * @param value the value
   */
  public KeyValueNode(String name, T value) {
    this.name = name;

    this.value = value;
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
   * Gets the value.
   *
   * @return the value
   */
  public T getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param value the new value
   */
  public void setValue(T value) {
    this.value = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return name;
  }

  /**
   * Adds the child.
   *
   * @param child the child
   */
  public void addChild(KeyValueNode<T> child) {
    children.put(child.getName(), child);
  }

  /**
   * Returns a sub category. Non-existant nodes are automatically created.
   *
   * @param name the name
   * @return the child
   */
  public KeyValueNode<T> getChild(String name) {
    if (name == null) {
      return null;
    }

    if (!children.containsKey(name)) {
      addChild(new KeyValueNode<T>(name));
    }

    return children.get(name);
  }

  /**
   * Gets the child count.
   *
   * @return the child count
   */
  public int getChildCount() {
    return children.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  public Iterator<KeyValueNode<T>> iterator() {
    return children.values().iterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(KeyValueNode<T> n) {
    return name.compareTo(n.getName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object o) {
    if (!(o instanceof KeyValueNode)) {
      return false;
    }

    return name.equals(((KeyValueNode<?>) o).getName());
  }

  /**
   * Clear.
   */
  public void clear() {
    children.clear();
  }
}
