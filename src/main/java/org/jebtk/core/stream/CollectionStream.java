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
package org.jebtk.core.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Class BaseStream encapulates an initial collection with the intent of
 * apply further streams to process the list.
 *
 * @param <T> the generic type
 */
public class CollectionStream<T> extends IteratorStream<T> {

  /** The m items. */
  private Collection<T> mItems;

  /**
   * Instantiates a new base stream.
   *
   * @param items the items
   */
  public CollectionStream(Collection<T> items) {
    super(items.iterator());

    mItems = items;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.stream.Stream#toList()
   */
  @Override
  public List<T> toList() {
    return new ArrayList<T>(mItems);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.stream.Stream#size()
   */
  @Override
  public int size() {
    return mItems.size();
  }
}
