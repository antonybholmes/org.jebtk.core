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

import java.awt.Color;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jebtk.core.collections.IterHashMap;
import org.jebtk.core.collections.IterMap;
import org.jebtk.core.event.ChangeEvent;
import org.jebtk.core.event.ChangeListener;
import org.jebtk.core.event.ChangeListeners;

/**
 * Generic Props object for sharing heterogenous properties.
 *
 * @author Antony Holmes
 */
public class Props extends ChangeListeners implements Iterable<Entry<String, Object>>, ChangeListener {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  // protected IterMap<String, Property> mPropertyMap = null;

  /**
   * The member items.
   */
  // protected IterMap<String, Object> mPropertyMap = new IterTreeMap<String,
  // Object>();
  protected IterMap<String, Object> mPropertyMap = new IterHashMap<>();

  public Props() {
    // Do nothing
  }

  public Props(Props parent) {
    set(parent);
  }

  public Props set(Props properties) {
    update(properties);

    fireChanged();

    return this;
  }

  public Props update(Props properties) {
    mPropertyMap.putAll(properties.mPropertyMap);

    return this;
  }

  /**
   * Update a property without triggering a change event.
   *
   * @param name the name
   * @param item the item
   * @return
   */
  public Props set(String name, Object value) {
    update(name, value);

    fireChanged();

    return this;
  }

  public Props update(String name, Object value) {
    mPropertyMap.put(name, value);

    return this;
  }

  /**
   * Gets the property as int.
   *
   * @param name the name
   * @return the property as int
   */
  public int getInt(String name) {
    return getInt(name, 0);
  }

  /**
   * Gets the as bool.
   *
   * @param name the name
   * @return the as bool
   */
  public boolean getBool(String name) {
    return (boolean) get(name, false);
  }

  /**
   * Gets the as color.
   *
   * @param name the name
   * @return the as color
   */
  public Color getColor(String name) {
    return getColor(name, ColorUtils.TRANS);
  }

  public Color getColor(String name, Color color) {
    return (Color) get(name, color);
  }

  /**
   * Gets the property as double.
   *
   * @param name the name
   * @return the property as double
   */
  public double getDouble(String name) {
    return (double) get(name, 0);
  }

  public String toString(String name) {
    return get(name).toString();
  }

  public Object get(String name) {
    return get(name, null);
  }

  /**
   * Returns a property or a default value if the property does not exist.
   * 
   * @param name
   * @param defaultValue
   * @return
   */
  public Object get(String name, Object defaultValue) {
    return mPropertyMap.getOrDefault(name, defaultValue);
  }

  public int getInt(String name, int defaultValue) {
    return (int) get(name, defaultValue);
  }

  /**
   * Returns true if the property exists.
   *
   * @param name the name
   * @return true, if successful
   */
  public boolean contains(String name) {
    return mPropertyMap.containsKey(name);
  }

  /**
   * Clear.
   */
  public void clear() {
    mPropertyMap.clear();

    fireChanged();
  }

  /**
   * Size.
   *
   * @return the int
   */
  public int size() {
    return mPropertyMap.size();
  }

  /**
   * Returns a sorted iterator of the names in this model.
   *
   * @return the iterator
   */
  @Override
  public Iterator<Entry<String, Object>> iterator() {
    return mPropertyMap.iterator();
  }

  @Override
  public void changed(ChangeEvent e) {
    fireChanged();
  }

}