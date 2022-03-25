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
package org.jebtk.core.xml;

import java.io.IOException;

/**
 * Elements that can return a xml text representation of themselves should
 * implement this.
 * 
 * @author Antony Holmes
 *
 */
public interface IFormattedXml {

  /**
   * Formatted xml.
   *
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void formattedXml(Appendable buffer) throws IOException;

  /**
   * Formatted xml.
   *
   * @param buffer the buffer
   * @param level  the level
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void formattedXml(Appendable buffer, int level) throws IOException;
}
