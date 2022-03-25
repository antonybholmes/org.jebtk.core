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
package org.jebtk.core.model;

import org.jebtk.core.event.ChangeListeners;

/**
 * Generic model for sharing a changable item of fixed type.
 *
 * @author Antony Holmes
 */
public class DoubleModel extends ChangeListeners {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member item.
   */
  protected double mItem = Double.NaN;

  /** The m previous item. */
  private double mPreviousItem = Double.NaN;

  /**
   * Instantiates a new double model.
   */
  public DoubleModel() {
    clear();
  }

  /**
   * Sets the.
   *
   * @param item the item
   */
  public void set(double item) {
    update(item);

    fireChanged();
  }

  /**
   * Update.
   *
   * @param item the item
   */
  public void update(double item) {
    mPreviousItem = mItem;
    mItem = item;
  }

  /**
   * Gets the.
   *
   * @return the t
   */
  public double get() {
    return mItem;
  }

  /**
   * Gets the previous.
   *
   * @return the previous
   */
  public double getPrevious() {
    return mPreviousItem;
  }

  /**
   * Clear.
   */
  public void clear() {
    mItem = Double.NaN;
    mPreviousItem = Double.NaN;

    fireChanged();
  }
}