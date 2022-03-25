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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jebtk.core.path.Path;
import org.jebtk.core.path.StrictPath;

/**
 * Represents a node in a radix tree. Names are stored as sequences of
 * characters which can be associated with one or more objects.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class PathObjectNode<T> implements Comparable<PathObjectNode<T>>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member objects.
   */
  private final Set<T> mObjects = new TreeSet<>();

  /**
   * The member children.
   */
  private final Map<String, PathObjectNode<T>> mChildren = new TreeMap<>();

  /**
   * The member level.
   */
  private final String mLevel;

  /**
   * The member prefix.
   */
  private final Path mPrefix;

  /**
   * Instantiates a new path object node.
   *
   * @param prefix the prefix
   * @param level  the level
   */
  public PathObjectNode(Path prefix, String level) {
    mPrefix = prefix;
    mLevel = level;
  }

  /**
   * Gets the level.
   *
   * @return the level
   */
  public String getLevel() {
    return mLevel;
  }

  /**
   * Gets the objects.
   *
   * @return the objects
   */
  public Set<T> getObjects() {
    return new HashSet<T>(mObjects);
  }

  /**
   * Returns the node associated with a given prefix.
   *
   * @param path the path
   * @return the child
   */
  public PathObjectNode<T> getChild(Path path) {
    PathObjectNode<T> traverse = this;

    for (String level : path) {

      traverse = traverse.getChild(level);

      if (traverse == null) {
        return null;
      }
    }

    return traverse;
  }

  /**
   * Returns a child node. Will return null if the child does not exist.
   *
   * @param level the level
   * @return the child
   */
  private PathObjectNode<T> getChild(String level) {
    return mChildren.get(level);
  }

  /**
   * Returns a child node and auto creates the child if it does not already exist.
   *
   * @param level the level
   * @return the path object node
   */
  private PathObjectNode<T> createChild(String level) {
    PathObjectNode<T> child = getChild(level);

    // If the child does not exist, it is
    // automatically created
    if (child == null) {
      child = new PathObjectNode<T>(new StrictPath(mPrefix, level), level);

      mChildren.put(level, child);
    }

    return child;
  }

  /**
   * Parse a string into prefixs and build a sub tree under the current node to
   * represent that string.
   *
   * @param path   the path
   * @param object the object
   */
  public void addObject(StrictPath path, T object) {

    // Clearly the object belongs to the root/current node
    mObjects.add(object);

    // Add the object to every subtree to which it
    // belongs

    PathObjectNode<T> traverse = this;

    for (String level : path) {
      traverse = traverse.createChild(level);
      traverse.mObjects.add(object);
    }
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
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(PathObjectNode<T> n) {
    return mPrefix.compareTo(n.mPrefix);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof PathObjectNode)) {
      return false;
    }

    return compareTo((PathObjectNode<T>) o) == 0;
  }

  /**
   * Clear.
   */
  public void clear() {
    mChildren.clear();

    mObjects.clear();
  }

  /**
   * Returns the prefix associated with the node.
   *
   * @return the prefix
   */
  public Path getPrefix() {
    return mPrefix;
  }
}
