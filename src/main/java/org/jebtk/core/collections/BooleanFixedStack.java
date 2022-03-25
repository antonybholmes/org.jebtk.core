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

/**
 * Fast stack implementation for Integers. Note that at the moment, no bound
 * checks are performed for speed so it will ungraciously throw exceptions if
 * misused.
 *
 * @author Antony Holmes
 */
public class BooleanFixedStack extends AbstractStack {

  /**
   * The elements.
   */
  private boolean[] elements;

  /**
   * Instantiates a new int fixed stack.
   */
  public BooleanFixedStack() {
    this(DEFAULT_SIZE);
  }

  /**
   * Instantiates a new int fixed stack.
   *
   * @param size the size
   */
  public BooleanFixedStack(int size) {
    elements = new boolean[size];
  }

  /**
   * Push.
   *
   * @param element the element
   */
  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.AbstractFixedStack#push(java.lang.Object)
   */
  public final void push(boolean element) {
    elements[++mPc] = element;
  }

  /**
   * Pop.
   *
   * @return true, if successful
   */
  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.AbstractFixedStack#pop()
   */
  public final boolean pop() {
    return elements[mPc--];
  }

  /**
   * Peek.
   *
   * @return true, if successful
   */
  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.AbstractFixedStack#peek()
   */
  public final boolean peek() {
    return elements[mPc];
  }

  /**
   * Gets the.
   *
   * @param i the i
   * @return true, if successful
   */
  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.AbstractFixedStack#get(int)
   */
  public boolean get(int i) {
    return elements[i];
  }
}
