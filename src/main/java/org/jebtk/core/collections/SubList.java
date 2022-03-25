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
 * Can be used to create a sub view of a list without creating a new list
 * instance each time. Since this is a view, the internal list cannot be edited
 * and calls to subList() and toObject() will be slower since it is assumed that
 * these will not be called on a view, despite being valid.
 *
 * @param <E> the element type
 */
public class SubList<E> implements List<E> {

  /**
   * The class SubListIterator.
   *
   * @param <T> the generic type
   */
  private class SubListIterator<T> implements Iterator<T> {

    /**
     * The member list.
     */
    private final List<T> mList;

    /**
     * The member s.
     */
    private int mS = -1;

    /**
     * The member end.
     */
    private int mEnd = -1;

    /**
     * Instantiates a new sub list iterator.
     *
     * @param list   the list
     * @param start  the start
     * @param length the length
     */
    public SubListIterator(List<T> list, int start, int length) {
      mList = list;
      mEnd = Math.min(list.size(), start + length) - 1;
      mS = start - 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext() {
      return mS < mEnd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#next()
     */
    @Override
    public T next() {
      return mList.get(++mS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove() {
      // Do nothing
    }
  }

  /**
   * The class SubListListIterator.
   *
   * @param <T> the generic type
   */
  private class SubListListIterator<T> implements ListIterator<T> {

    /**
     * The member list.
     */
    private final List<T> mList;

    /**
     * The member s.
     */
    private int mS = -1;

    /**
     * The member start.
     */
    private int mStart = -1;

    /**
     * The member end.
     */
    private int mEnd = -1;

    /**
     * Instantiates a new sub list list iterator.
     *
     * @param list   the list
     * @param start  the start
     * @param length the length
     */
    public SubListListIterator(List<T> list, int start, int length) {
      mList = list;
      mStart = start;
      mEnd = Math.min(list.size(), start + length);
      mS = start - 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#add(java.lang.Object)
     */
    @Override
    public void add(T e) {
      // Do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#hasNext()
     */
    @Override
    public boolean hasNext() {
      return mS < mEnd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#hasPrevious()
     */
    @Override
    public boolean hasPrevious() {
      return mS > mStart;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#next()
     */
    @Override
    public T next() {
      return mList.get(++mS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#nextIndex()
     */
    @Override
    public int nextIndex() {
      return mS - mStart + 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#previous()
     */
    @Override
    public T previous() {
      return mList.get(--mS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#previousIndex()
     */
    @Override
    public int previousIndex() {
      return mS - mStart - 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#remove()
     */
    @Override
    public void remove() {
      // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.ListIterator#set(java.lang.Object)
     */
    @Override
    public void set(T e) {
      // TODO Auto-generated method stub

    }

  }

  /**
   * The member list.
   */
  private List<E> mList = null;

  /**
   * The member start.
   */
  private int mStart = -1;

  /**
   * The member length.
   */
  private int mSize = -1;

  /**
   * The member end.
   */
  private int mEnd;

  /**
   * Instantiates a new sub list view.
   *
   * @param list   the list
   * @param start  the start
   * @param length the length
   */
  public SubList(List<E> list, int start, int length) {
    mList = list;
    mStart = start;
    mEnd = start + length - 1;
    mSize = Math.min(mList.size(), length);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#size()
   */
  @Override
  public int size() {
    return mSize;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#get(int)
   */
  @Override
  public E get(int index) {
    return mList.get(mStart + index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#add(java.lang.Object)
   */
  @Override
  public boolean add(E e) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#add(int, java.lang.Object)
   */
  @Override
  public void add(int index, E element) {
    // Items cannot be added to a view.
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#addAll(java.util.Collection)
   */
  @Override
  public boolean addAll(Collection<? extends E> c) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#addAll(int, java.util.Collection)
   */
  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#clear()
   */
  @Override
  public void clear() {
    // A view cannot be cleared.
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#contains(java.lang.Object)
   */
  @Override
  public boolean contains(Object o) {
    int index = mList.indexOf(o);

    return index >= mStart && index < mStart + mSize;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#containsAll(java.util.Collection)
   */
  @Override
  public boolean containsAll(Collection<?> c) {
    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }

    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#indexOf(java.lang.Object)
   */
  @Override
  public int indexOf(Object o) {
    int index = mList.indexOf(o);

    if (index >= mStart && index <= mEnd) {
      return index;
    } else {
      return -1;
    }
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
  public Iterator<E> iterator() {
    return new SubListIterator<E>(mList, mStart, mSize);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#lastIndexOf(java.lang.Object)
   */
  @Override
  public int lastIndexOf(Object o) {
    int index = mList.lastIndexOf(o);

    if (index >= mStart && index <= mEnd) {
      return index;
    } else {
      return -1;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#listIterator()
   */
  @Override
  public ListIterator<E> listIterator() {
    return listIterator(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#listIterator(int)
   */
  @Override
  public ListIterator<E> listIterator(int index) {
    return new SubListListIterator<E>(mList, mStart + index, mSize);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object o) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#remove(int)
   */
  @Override
  public E remove(int index) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#removeAll(java.util.Collection)
   */
  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#retainAll(java.util.Collection)
   */
  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#set(int, java.lang.Object)
   */
  @Override
  public E set(int index, E element) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#subList(int, int)
   */
  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    return mList.subList(fromIndex + mStart, toIndex + mStart);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#toArray()
   */
  @Override
  public Object[] toArray() {
    return subList(0, mSize - 1).toArray();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.List#toArray(java.lang.Object[])
   */
  @Override
  public <T> T[] toArray(T[] a) {
    return subList(0, mSize - 1).toArray(a);
  }
}
