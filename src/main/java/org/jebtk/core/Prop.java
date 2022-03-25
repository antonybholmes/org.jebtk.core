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

/**
 * Generic Props object for sharing heterogenous properties.
 *
 * @author Antony Holmes
 */
public class Prop extends KeyValuePair<String, Object> implements NameGetter {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  public Prop(String name, Object item) {
    super(name, item);
  }

  @Override
  public String getName() {
    return getKey();
  }

  /**
   * Gets the property as int.
   *
   * @return the property as int
   */
  public int getInt() {
    return getInt(this);
  }

  /**
   * Gets the as bool.
   *
   * @return the as bool
   */
  public boolean getBool() {
    return getBool(this);
  }

  /**
   * Gets the as color.
   *
   * @return the as color
   */
  public Color getColor() {
    return getColor(this);
  }

  /**
   * Gets the property as double.
   *
   * @return the property as double
   */
  public double getDouble() {
    return getDouble(this);
  }

  public static double getDouble(Prop p) {
    return (Double) p.getValue();
  }

  public static int getInt(Prop p) {
    return (Integer) p.getValue();
  }

  public static boolean getBool(Prop p) {
    return (Boolean) p.getValue();
  }

  public static Color getColor(Prop p) {
    return (Color) p.getValue();
  }
}