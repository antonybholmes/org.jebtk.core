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
package org.jebtk.core.search;

import org.jebtk.core.event.Event;

/**
 * For generating search events.
 *
 * @author Antony Holmes
 */
public class SearchEvent extends Event {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constant SEARCH_MESSAGE.
   */
  public static final String SEARCH_MESSAGE = "search";

  /**
   * Instantiates a new search event.
   *
   * @param source the source
   */
  public SearchEvent(Object source) {
    this(source, SEARCH_MESSAGE);
  }

  /**
   * Instantiates a new search event.
   *
   * @param source  the source
   * @param message the message
   */
  public SearchEvent(Object source, String message) {
    super(source, message);
  }
}
