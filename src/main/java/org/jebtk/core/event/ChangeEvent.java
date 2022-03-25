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
package org.jebtk.core.event;

/**
 * Modern UI controls such as buttons should fire ModernClickEvents.
 * 
 * @author Antony Holmes
 *
 */
public class ChangeEvent extends Event {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constant EVENT_ITEM_CHANGED.
   */
  public static final String EVENT_ITEM_CHANGED = "item_changed";

  /**
   * Instantiates a new change event.
   *
   * @param source the source
   */
  public ChangeEvent(Object source) {
    this(source, EVENT_ITEM_CHANGED);
  }

  /**
   * Instantiates a new change event.
   *
   * @param source  the source
   * @param message the message
   */
  public ChangeEvent(Object source, String message) {
    super(source, message);
  }
}
