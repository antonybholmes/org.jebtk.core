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
package org.jebtk.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides a unique id to a class (not guaranteed to persist between JVM or
 * application restarts).
 * 
 * @author Antony Holmes
 *
 */
public abstract class IdObject implements IdProperty, Comparable<IdObject> {

  /**
   * The constant NEXT_ID.
   */
  private static final AtomicInteger NEXT_ID = new AtomicInteger();

  /**
   * The member id.
   */
  protected int mId = -1;

  /**
   * Instantiates a new uid object.
   */
  public IdObject() {
    this(NEXT_ID.getAndIncrement());
  }

  /**
   * Instantiates a new uid object.
   *
   * @param id the id
   */
  public IdObject(int id) {
    mId = id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.UidProperty#getUid()
   */
  @Override
  public int getId() {
    return mId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof IdObject) {
      return compareTo((IdObject) o) == 0;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(IdObject c) {
    if (mId > c.mId) {
      return 1;
    } else if (mId < c.mId) {
      return -1;
    } else {
      return 0;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return mId;
  }
}
