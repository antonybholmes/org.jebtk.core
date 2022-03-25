/**
 * Copyright 2017 Antony Holmes
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
package org.jebtk.core.path;

/**
 * Represents a path that always starts from the root.
 * 
 * @author Antony Holmes
 *
 */
public class RootPath extends Path {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new root path.
   */
  public RootPath() {
    super(true);
  }

  /**
   * Instantiates a new root path.
   *
   * @param level  the level
   * @param levels the levels
   */
  public RootPath(Object level, Object... levels) {
    super(true, level, levels);
  }
}
