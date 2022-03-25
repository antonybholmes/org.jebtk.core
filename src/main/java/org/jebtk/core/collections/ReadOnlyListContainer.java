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
package org.jebtk.core.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A list container that allows addition of elements and iteration, but does not
 * allow items to be removed.
 *
 * @param <T> the generic type
 */
public class ReadOnlyListContainer<T> implements List<T> {

  /** The m list. */
  protected List<T> mList;

  /**
   * Instantiates a new read only list container.
   *
   * @param list the list
   */
  public ReadOnlyListContainer(List<T> list) {
    mList = list;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#add(java.lang.Object)
   */
  @Override
  public boolean add(T item) {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends T> items) {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#add(int, java.lang.Object)
   */
  @Override
  public void add(int arg0, T arg1) {
    // Do nothing
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#addAll(int, java.util.Collection)
   */
  @Override
  public boolean addAll(int arg0, Collection<? extends T> arg1) {
    return mList.addAll(arg0, arg1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#clear()
   */
  @Override
  public void clear() {
    // Do nothing
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#contains(java.lang.Object)
   */
  @Override
  public boolean contains(Object arg0) {
    return mList.contains(arg0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#containsAll(java.util.Collection)
   */
  @Override
  public boolean containsAll(Collection<?> c) {
    return mList.containsAll(c);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#get(int)
   */
  @Override
  public T get(int index) {
    return mList.get(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Override
  public int indexOf(Object o) {
    return mList.indexOf(o);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#isEmpty()
   */
  @Override
  public boolean isEmpty() {
    return mList.isEmpty();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#iterator()
   */
  @Override
  public Iterator<T> iterator() {
    return mList.iterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#lastIndexOf(java.lang.Object)
   */
  @Override
  public int lastIndexOf(Object o) {
    return mList.lastIndexOf(o);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#listIterator()
   */
  @Override
  public ListIterator<T> listIterator() {
    return mList.listIterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#listIterator(int)
   */
  @Override
  public ListIterator<T> listIterator(int index) {
    return mList.listIterator(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object o) {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#remove(int)
   */
  @Override
  public T remove(int index) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#removeAll(java.util.Collection)
   */
  @Override
  public boolean removeAll(Collection<?> c) {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#retainAll(java.util.Collection)
   */
  @Override
  public boolean retainAll(Collection<?> c) {
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#set(int, java.lang.Object)
   */
  @Override
  public T set(int index, T element) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#size()
   */
  @Override
  public int size() {
    return mList.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#subList(int, int)
   */
  @Override
  public List<T> subList(int fromIndex, int toIndex) {
    return mList.subList(fromIndex, toIndex);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#toArray()
   */
  @Override
  public Object[] toArray() {
    return mList.toArray();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#toArray(java.lang.Object[])
   */
  @SuppressWarnings("hiding")
  @Override
  public <T> T[] toArray(T[] a) {
    return mList.toArray(a);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mList.toString();
  }
}
