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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple stack that can be reused. This is for cases where the stack structure
 * maintains results so that it can be re-used without having to rebuild it each
 * time.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class PseudoStack<T> extends AbstractStack implements Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The elements.
   */
  private List<T> elements = new ArrayList<T>(32);

  /**
   * Push.
   *
   * @param item the item
   */
  public final void push(T item) {
    elements.add(item);

    reset();
  }

  /**
   * Pop.
   *
   * @return the t
   */
  public final T pop() {
    // System.err.println("pc " + pc);

    if (mPc == -1) {
      return null;
    }

    T ret = elements.get(mPc);

    --mPc;

    return ret;
  }

  /**
   * Peek.
   *
   * @return the t
   */
  public final T peek() {
    if (mPc == -1) {
      return null;
    }

    return elements.get(mPc);
  }

  /**
   * Gets the.
   *
   * @param i the i
   * @return the t
   */
  public T get(int i) {
    return elements.get(i);
  }

  /**
   * Allow stack structure to be reused.
   */
  @Override
  public void reset() {
    mPc = elements.size() - 1;
  }
}
