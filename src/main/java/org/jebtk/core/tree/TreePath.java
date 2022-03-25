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

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jebtk.core.path.StrictPath;
import org.jebtk.core.text.TextUtils;

/**
 * Represents a path in a tree. Tree paths are defined by a chain of UIDs
 * specifying the nodes.
 * 
 * @author Antony Holmes
 *
 */
public class TreePath implements Iterable<Integer>, Serializable, Comparable<TreePath> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The path.
   */
  private StringBuilder path = new StringBuilder();

  /**
   * The tokens.
   */
  private List<Integer> tokens = new ArrayList<Integer>();

  /**
   * Instantiates a new tree path.
   */
  public TreePath() {
    // do nothing
  }

  /**
   * Instantiates a new tree path.
   *
   * @param <T>  the generic type
   * @param path the path
   * @throws ParseException the parse exception
   */
  public <T> TreePath(String path) throws ParseException {
    // System.err.println("parse path " + path);

    List<String> t = TextUtils.fastSplit(path.toString(), StrictPath.PATH_DELIMITER);

    for (String token : t) {
      add(token);
    }
  }

  /**
   * Clone constructor.
   *
   * @param path the path
   */
  public TreePath(TreePath path) {
    for (int i : path) {
      add(i);
    }
  }

  /**
   * Instantiates a new tree path.
   *
   * @param <T>  the generic type
   * @param node the node
   */
  public <T> TreePath(TreeNode<T> node) {
    add(node);
  }

  /**
   * Adds the.
   *
   * @param <T>  the generic type
   * @param node the node
   * @return the tree path
   */
  public <T> TreePath add(TreeNode<T> node) {
    return add(node.getId());
  }

  /**
   * Adds the.
   *
   * @param element the element
   * @return the tree path
   * @throws ParseException the parse exception
   */
  private TreePath add(String element) throws ParseException {
    return add(TextUtils.parseInt(element));
  }

  /**
   * Adds the.
   *
   * @param level the level
   * @return the tree path
   */
  private TreePath add(int level) {
    if (tokens.size() > 0) {
      path.append(StrictPath.PATH_DELIMITER);
    }

    path.append(level);
    tokens.add(level);

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return path.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Integer> iterator() {
    return tokens.iterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(TreePath p) {
    return toString().compareTo(p.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof TreePath)) {
      return false;
    }

    return compareTo((TreePath) o) == 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Returns the number of path levels in the path.
   *
   * @return the int
   */
  public int size() {
    return tokens.size();
  }
}
