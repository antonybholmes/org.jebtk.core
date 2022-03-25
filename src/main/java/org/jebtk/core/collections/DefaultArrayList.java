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

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList that auto fills with default values so that it is initialized to a
 * given size.
 *
 * @param <T> the value type
 */
public class DefaultArrayList<T> extends ArrayList<T> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /** The Constant DEFAULT_SIZE. */
  private static final int DEFAULT_SIZE = 100;

  /**
   * The member default value.
   */
  private EntryCreator<T> mDefaultValue;

  public DefaultArrayList() {
    this(DEFAULT_SIZE, new NullCreator<T>());
  }

  /**
   * Instantiates a new default array list.
   *
   * @param defaultValue the default value
   */
  public DefaultArrayList(T defaultValue) {
    this(DEFAULT_SIZE, new ValueCreator<T>(defaultValue));
  }

  /**
   * Instantiates a new auto hash map.
   *
   * @param size         the size
   * @param defaultValue the default value
   */
  public DefaultArrayList(int size, T defaultValue) {
    this(size, new ValueCreator<T>(defaultValue));
  }

  /**
   * Instantiates a new default list.
   *
   * @param size         the size
   * @param defaultValue the default value
   */
  public DefaultArrayList(int size, EntryCreator<T> defaultValue) {
    super(size);

    mDefaultValue = defaultValue;

    // autoCreate(size);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.ArrayList#get(int)
   */
  @Override
  public T get(int index) {
    autoCreate(index + 1);

    return super.get(index);
  }

  @Override
  public T set(int index, T v) {
    autoCreate(index + 1);

    return super.set(index, v);
  }

  /**
   * Auto create.
   *
   * @param size the size
   */
  private void autoCreate(int size) {
    while (size() < size) {
      add(mDefaultValue.newEntry());
    }
  }

  //
  // Static methods
  //

  /**
   * Creates the.
   *
   * @param <T1>         the generic type
   * @param defaultValue the default value
   * @return the list
   */
  public static <T1> List<T1> create(T1 defaultValue) {
    return create(DEFAULT_SIZE, new ValueCreator<T1>(defaultValue));
  }

  /**
   * Creates the.
   *
   * @param <T1>         the generic type
   * @param size         the size
   * @param defaultValue the default value
   * @return the list
   */
  public static <T1> List<T1> create(int size, T1 defaultValue) {
    return create(size, new ValueCreator<T1>(defaultValue));
  }

  /**
   * Creates a new {@code DefaultArrayList}.
   *
   * @param <T1>         the generic type
   * @param defaultValue the default value
   * @return the list
   */
  public static <T1> List<T1> create(EntryCreator<T1> defaultValue) {
    return create(DEFAULT_SIZE, defaultValue);
  }

  /**
   * Creates a new {@code DefaultArrayList}.
   *
   * @param <T1>         the generic type
   * @param size         the size
   * @param defaultValue the default value
   * @return the list
   */
  public static <T1> List<T1> create(int size, EntryCreator<T1> defaultValue) {
    return new DefaultArrayList<T1>(size, defaultValue);
  }
}
