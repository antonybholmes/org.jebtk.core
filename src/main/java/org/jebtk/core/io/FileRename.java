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
package org.jebtk.core.io;

import java.nio.file.Path;

/**
 * The class FileRename.
 */
public class FileRename {

  /**
   * The original.
   */
  private Path original;

  /**
   * The regulated.
   */
  private Path regulated;

  /**
   * Instantiates a new file rename.
   *
   * @param original  the original
   * @param regulated the regulated
   */
  public FileRename(Path original, Path regulated) {
    this.original = original;
    this.regulated = regulated;
  }

  /**
   * Original.
   *
   * @return the file
   */
  public Path original() {
    return original;
  }

  /**
   * Regulated.
   *
   * @return the file
   */
  public Path regulated() {
    return regulated;
  }
}
