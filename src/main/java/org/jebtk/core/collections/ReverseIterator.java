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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ReverseIterator<T> implements Iterator<T>, Iterable<T> {
  private final ListIterator<T> mIt;

  public ReverseIterator(List<T> l) {
    mIt = l.listIterator(l.size());
  }

  @Override
  public boolean hasNext() {
    return mIt.hasPrevious();
  }

  @Override
  public T next() {
    return mIt.previous();
  }

  @Override
  public Iterator<T> iterator() {
    return this;
  }

  @Override
  public void remove() {
    // TODO Auto-generated method stub

  }

  public static <TT> ReverseIterator<TT> create(List<TT> l) {
    return new ReverseIterator<>(l);
  }
}

/**
 * ReverseIterator traverses a list in reverse order to negate having to sort
 * and or copy a list to reverse it.
 *
 * @param <T> the generic type
 */
// public class ReverseIterator<T> implements Iterator<T>, Iterable<T> {
//
// /** The m list. */
// private final List<T> mList;
//
// /** The m P. */
// private int mP;
//
// /**
// * Instantiates a new reverse iterator.
// *
// * @param list the list
// */
// public ReverseIterator(List<T> list) {
// mList = list;
// mP = list.size() - 1;
// }
//
// /* (non-Javadoc)
// * @see java.util.Iterator#hasNext()
// */
// @Override
// public boolean hasNext() {
// return mP >= 0;
// }
//
// /* (non-Javadoc)
// * @see java.util.Iterator#next()
// */
// @Override
// public T next() {
// return mList.get(mP--);
// }
//
// /* (non-Javadoc)
// * @see java.util.Iterator#remove()
// */
// @Override
// public void remove() {
// throw new UnsupportedOperationException();
// }
//
// /* (non-Javadoc)
// * @see java.lang.Iterable#iterator()
// */
// @Override
// public Iterator<T> iterator() {
// return this;
// }
// }
