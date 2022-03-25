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

/**
 * Stores objects by categories in a searchable radix tree.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class CategoryObjectDb<T> implements Iterable<CategoryObjectNode<T>>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constant ALL_CATEGORIES.
   */
  public static final Path ALL_CATEGORIES = new Path("all_categories");

  /**
   * The member root.
   */
  private final CategoryObjectNode<T> mRoot = new CategoryObjectNode<T>("root");

  // private PrefixObjectDb<T> global = new PrefixObjectDb<T>();

  /**
   * The member path map.
   */
  private final Map<Path, CategoryObjectNode<T>> mPathMap = new HashMap<>();

  /**
   * Gets the child by path.
   *
   * @param path the path
   * @return the child by path
   */
  public CategoryObjectNode<T> getChildByPath(String path) {
    return getChildByPath(new Path(path));
  }

  /**
   * Gets the child by path.
   *
   * @param path the path
   * @return the child by path
   */
  public CategoryObjectNode<T> getChildByPath(Path path) {
    CategoryObjectNode<T> child = mPathMap.get(path);

    if (child != null) {
      return child;
    }

    child = (CategoryObjectNode<T>) mRoot.getChildByPath(path);

    mPathMap.put(path, child);

    return child;
  }

  /**
   * Clear.
   */
  public void clear() {
    mRoot.clear();
    // global.clear();
  }

  /**
   * Returns a global category that can be used for storing all categories. This
   * can be used to easily search all fields for example.
   *
   * @return the all categories
   */
  public RadixObjectDb<T> getAllCategories() {
    return getChildByPath(ALL_CATEGORIES).getPrefixTree();
  }

  /**
   * Gets the root.
   *
   * @return the root
   */
  public CategoryObjectNode<T> getRoot() {
    return mRoot;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<CategoryObjectNode<T>> iterator() {
    return getRoot().iterator();
  }
}
