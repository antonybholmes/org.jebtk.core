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
 * Creates a searchable database of objects tagged by categories.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class TextObjectDb<T> implements Iterable<TextObjectNode<T>>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The root.
   */
  private final TextObjectNode<T> mRoot = new TextObjectNode<>("root");

  /**
   * The member path map.
   */
  private final Map<Path, TextObjectNode<T>> mPathMap = new HashMap<>();

  /**
   * Gets the child by path.
   *
   * @param path the path
   * @return the child by path
   */
  public TextObjectNode<T> getChildByPath(String path) {
    return getChildByPath(new Path(path));
  }

  /**
   * Gets the child by path.
   *
   * @param path the path
   * @return the child by path
   */
  public TextObjectNode<T> getChildByPath(Path path) {
    TextObjectNode<T> child = mPathMap.get(path);

    if (child != null) {
      return child;
    }

    child = mRoot.getChildByPath(path);

    mPathMap.put(path, child);

    return child;
  }

  /**
   * Clear.
   */
  public void clear() {
    mRoot.clear();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<TextObjectNode<T>> iterator() {
    return mRoot.iterator();
  }
}
