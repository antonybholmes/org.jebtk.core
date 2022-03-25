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

import java.util.Arrays;
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
public class IntArrayListView implements List<Integer> {

  private int[] mList;

  public IntArrayListView(int[] list) {
    mList = list;
  }

  @Override
  public int size() {
    return mList.length;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean contains(Object o) {
    return false;
  }

  @Override
  public Iterator<Integer> iterator() {
    return new IntArrayListIterator(mList);
  }

  @Override
  public Object[] toArray() {
    return null;
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return null;
  }

  @Override
  public boolean add(Integer e) {
    return false;
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends Integer> c) {
    return false;
  }

  @Override
  public boolean addAll(int index, Collection<? extends Integer> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {
    // Do nothing
  }

  @Override
  public Integer get(int index) {
    return mList[index];
  }

  @Override
  public Integer set(int index, Integer element) {
    return null;
  }

  @Override
  public void add(int index, Integer element) {
    // Do nothing
  }

  @Override
  public Integer remove(int index) {
    return null;
  }

  @Override
  public int indexOf(Object o) {
    int s = (int) o;

    for (int i = 0; i < mList.length; ++i) {
      if (mList[i] == s) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public int lastIndexOf(Object o) {
    return 0;
  }

  @Override
  public ListIterator<Integer> listIterator() {
    return null;
  }

  @Override
  public ListIterator<Integer> listIterator(int index) {
    return null;
  }

  @Override
  public List<Integer> subList(int fromIndex, int toIndex) {
    return new IntArrayListView(Arrays.copyOfRange(mList, fromIndex, toIndex));
  }

}
