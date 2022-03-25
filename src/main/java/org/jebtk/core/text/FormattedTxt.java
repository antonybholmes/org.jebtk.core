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
package org.jebtk.core.text;

import java.io.IOException;

/**
 * For classes that can output a text representation of themselves. This should
 * be used
 * 
 * @author Antony Holmes
 *
 */
public interface FormattedTxt {

  // String formattedTxt();

  /**
   * Like toString(), should create a text representation of an object but write
   * it to a string buffer rather than creating a new string.
   *
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  void formattedTxt(Appendable buffer) throws IOException;
}
