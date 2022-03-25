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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.collections.UniqueArrayList;
import org.jebtk.core.text.TextUtils;

/**
 * Represents a node in a radix tree. Names are stored as sequences of
 * characters which can be associated with one or more objects.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class RadixObjectNode<T> implements Comparable<RadixObjectNode<T>>, Serializable, Iterable<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member words.
   */
  private final Set<String> mWords = new HashSet<>();

  /**
   * The member objects.
   */
  private final List<T> mObjects = new UniqueArrayList<>();

  /**
   * The member children.
   */
  private final Map<Character, RadixObjectNode<T>> mChildren = new HashMap<>();

  /**
   * The member c.
   */
  private final char mC;

  /**
   * The member prefix.
   */
  private final String mPrefix;

  /**
   * Instantiates a new radix object node.
   *
   * @param c      the c
   * @param prefix the prefix
   */
  public RadixObjectNode(char c, String prefix) {
    mC = standardize(c);
    mPrefix = standardize(prefix);
  }

  /**
   * Gets the char.
   *
   * @return the char
   */
  public char getChar() {
    return mC;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return Character.toString(mC);
  }

  /**
   * Gets the objects.
   *
   * @return the objects
   */
  public List<T> getObjects() {
    return CollectionUtils.toList(mObjects);
  }

  /**
   * Returns the node associated with a given prefix.
   *
   * @param prefix the prefix
   * @return the child
   */
  public RadixObjectNode<T> getChild(String prefix) {
    return getChild(this, prefix);
  }

  public static <TT> RadixObjectNode<TT> getChild(RadixObjectNode<TT> root, String prefix) {
    RadixObjectNode<TT> ret = root;

    char[] chars = standardize(prefix).toCharArray();

    for (char c : chars) {
      RadixObjectNode<TT> node = ret.mChildren.get(c);

      if (node != null) {
        ret = node;
      } else {
        return null;
      }
    }

    return ret;
  }

  /**
   * Returns a child node. Will return null if the child does not exist.
   *
   * @param c the c
   * @return the child
   */
  private RadixObjectNode<T> getChild(char c) {
    return mChildren.get(standardize(c));
  }

  /**
   * Return the words associated with a prefix or null if the prefix does not
   * exist in this tree.
   *
   * @param prefix the prefix
   * @return the words
   */
  public Set<String> getWords(String prefix) {
    return getWords(this, prefix);
  }

  public static <TT> Set<String> getWords(RadixObjectNode<TT> root, String prefix) {
    RadixObjectNode<TT> ret = getChild(root, prefix);

    return Collections.unmodifiableSet(ret.mWords);
  }

  /**
   * Gets the words.
   *
   * @return the words
   */
  public Set<String> getWords() {
    return new HashSet<>(mWords);
  }

  /**
   * Returns a child node and auto creates the child if it does not already exist.
   *
   * @param c      the c
   * @param prefix the prefix
   * @param word   the word
   * @return the radix object node
   */
  private RadixObjectNode<T> createChild(char c, String prefix, String word) {
    c = standardize(c);

    RadixObjectNode<T> child = mChildren.get(c);

    // If the child does not exist, it is automatically created
    if (child == null) {
      child = new RadixObjectNode<>(c, prefix);

      mChildren.put(c, child);
    }

    child.mWords.add(word);

    return child;
  }

  /**
   * Parse a string into prefixs and build a sub tree under the current node to
   * represent that string.
   *
   * @param word   the word
   * @param object the object
   */
  public void addObject(String word, T object) {
    if (TextUtils.isNullOrEmpty(word) || object == null) {
      return;
    }

    // Clearly the object belongs to the root/current node
    mObjects.add(object);

    mWords.add(word);

    // Add the object to every subtree to which it
    // belongs

    char[] chars = word.toCharArray();

    RadixObjectNode<T> traverse = this;

    StringBuilder buffer = new StringBuilder(mPrefix);

    for (int i = 0; i < chars.length; ++i) {
      char c = chars[i];

      buffer.append(c);

      traverse = traverse.createChild(c, buffer.toString(), word);
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
  public int compareTo(RadixObjectNode<T> n) {
    if (mC == n.mC) {
      return 0;
    } else if (mC < n.mC) {
      return -1;
    } else {
      return 1;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof RadixObjectNode)) {
      return false;
    }

    return compareTo((RadixObjectNode<T>) o) == 0;
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
  public String getPrefix() {
    return mPrefix;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<T> iterator() {
    return mObjects.iterator();
  }

  /**
   * Ensure characters are consistent for searching purposes i.e case insensitive.
   *
   * @param name the name
   * @return the char
   */
  private static char standardize(char name) {
    return Character.toLowerCase(name);
  }

  /**
   * Standardize.
   *
   * @param name the name
   * @return the string
   */
  private static String standardize(String name) {
    return name.toLowerCase();
  }

}
