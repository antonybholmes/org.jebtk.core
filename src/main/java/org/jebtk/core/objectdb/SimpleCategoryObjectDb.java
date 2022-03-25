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

import org.jebtk.core.path.StrictPath;

/**
 * Category Object nodes store data in radix trees for fast searching.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class SimpleCategoryObjectDb<T> implements Iterable<StrictPath>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member children.
   */
  private Map<StrictPath, RadixObjectDb<T>> mChildren = new HashMap<StrictPath, RadixObjectDb<T>>();

  /**
   * The member all db.
   */
  private RadixObjectDb<T> mAllDb = null;

  /**
   * Instantiates a new simple category object db.
   */
  public SimpleCategoryObjectDb() {
    clear();
  }

  /**
   * Gets the category.
   *
   * @param args the args
   * @return the category
   */
  public RadixObjectDb<T> getCategory(Object... args) {
    return getCategory(new StrictPath(args));
  }

  /**
   * Gets the category.
   *
   * @param path the path
   * @return the category
   */
  public RadixObjectDb<T> getCategory(String path) {
    return getCategory(new StrictPath(path));
  }

  /**
   * Gets the category.
   *
   * @param path the path
   * @return the category
   */
  public RadixObjectDb<T> getCategory(StrictPath path) {
    if (mChildren.containsKey(path)) {
      return mChildren.get(path);
    }

    // Create the category if it does not exist.

    System.err.println("adding simple " + path);

    RadixObjectDb<T> db = new RadixObjectDb<T>();

    mChildren.put(path, db);

    return db;
  }

  /**
   * Gets the all categories.
   *
   * @return the all categories
   */
  public RadixObjectDb<T> getAllCategories() {
    return mAllDb;
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
  public Iterator<StrictPath> iterator() {
    return mChildren.keySet().iterator();
  }

  /**
   * Clear.
   */
  public void clear() {
    mChildren.clear();

    // Register a category for everything
    mAllDb = getCategory(CategoryObjectDb.ALL_CATEGORIES);
  }

}
