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

import java.util.EventObject;
import java.util.concurrent.atomic.AtomicInteger;

import org.jebtk.core.IdProperty;

/**
 * The class Event.
 */
public class Event extends EventObject implements IdProperty {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The constant NEXT_ID.
   */
  private static final AtomicInteger NEXT_ID = new AtomicInteger(0);

  /**
   * The member message.
   */
  private String mMessage = null;

  /**
   * The id.
   */
  private final int id = NEXT_ID.getAndIncrement();

  /**
   * Instantiates a new event.
   *
   * @param source  the source
   * @param message the message
   */
  public Event(Object source, String message) {
    super(source);

    mMessage = message;
  }

  /**
   * Gets the message.
   *
   * @return the message
   */
  public String getMessage() {
    return mMessage;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.UidProperty#getUid()
   */
  @Override
  public int getId() {
    return id;
  }
}
