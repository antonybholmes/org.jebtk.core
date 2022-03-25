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
package org.jebtk.core.objectdb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jebtk.core.path.Path;
import org.jebtk.core.path.PathLevel;
import org.jebtk.core.path.StrictPath;
import org.jebtk.core.settings.KeyNode;

/**
 * Represents a node in a tree hierarchy. A node may contain a data object as
 * well as child nodes.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class TextObjectNode<T> implements Iterable<TextObjectNode<T>>, Comparable<TextObjectNode<T>>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member value.
   */
  protected T mValue = null;

  /**
   * The member children.
   */
  protected Map<String, TextObjectNode<T>> mChildren = new HashMap<>();

  /**
   * The member name.
   */
  private final String mName;

  /**
   * Instantiates a new text object node.
   *
   * @param name the name
   */
  public TextObjectNode(String name) {
    mName = KeyNode.standardize(name);
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return mName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mName;
  }

  /**
   * Sets the value.
   *
   * @param value the new value
   */
  public void setValue(T value) {
    this.mValue = value;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public T getValue() {
    return mValue;
  }

  /**
   * Gets the child.
   *
   * @param level the level
   * @return the child
   */
  public TextObjectNode<T> getChild(PathLevel level) {
    return getChild(level.toString());
  }

  /**
   * Gets the child.
   *
   * @param name the name
   * @return the child
   */
  public TextObjectNode<T> getChild(String name) {
    if (mChildren.containsKey(name)) {
      return mChildren.get(name);
    }

    TextObjectNode<T> node = new TextObjectNode<>(name);

    mChildren.put(name, node);

    return node;
  }

  /**
   * Gets the child by path.
   *
   * @param path the path
   * @return the child by path
   */
  public TextObjectNode<T> getChildByPath(String path) {
    return getChildByPath(new StrictPath(path));
  }

  /**
   * Traverse a Props tree using a path expression to find a particular property.
   * Returns null if the property is not found.
   *
   * @param path the path
   * @return the child by path
   */
  public TextObjectNode<T> getChildByPath(Path path) {
    // System.err.println("Category path " + path);

    TextObjectNode<T> node = this;

    for (String level : path) {
      // System.err.println("level " + level);

      node = node.getChild(level);
    }

    return node;
  }

  /**
   * Gets the child count.
   *
   * @return the child count
   */
  public int getChildCount() {
    return mChildren.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<TextObjectNode<T>> iterator() {
    return mChildren.values().iterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(TextObjectNode<T> n) {
    return mName.compareTo(n.mName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TextObjectNode)) {
      return false;
    }

    return compareTo((TextObjectNode<T>) o) == 0;
  }

  /**
   * Clear.
   */
  public void clear() {
    mChildren.clear();
    mValue = null;
  }
}
