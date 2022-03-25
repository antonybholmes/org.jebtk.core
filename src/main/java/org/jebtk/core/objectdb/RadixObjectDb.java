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

import org.jebtk.core.text.TextUtils;

/**
 * Creates a search radix tree of objects tagged by their name.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class RadixObjectDb<T> extends RadixObjectNode<T> implements Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new radix object db.
   */
  public RadixObjectDb() {
    super('r', TextUtils.EMPTY_STRING);
  }

  /**
   * Creates the.
   * 
   * @param <TT>
   *
   * @return the radix object db
   */
  public static <TT> RadixObjectDb<TT> create() {
    return new RadixObjectDb<>();
  }
}
