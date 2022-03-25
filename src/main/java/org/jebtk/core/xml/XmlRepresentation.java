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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Should be implemented by elements that can create an XML representation of
 * themselves.
 * 
 * @author Antony Holmes
 *
 */
public interface XmlRepresentation {

  /**
   * Should return an XML element encapsulating the object's data so that it can
   * be streamed to a file and used to reconstruct the object's state at a later
   * date.
   *
   * @param doc the doc
   * @return the xml element
   */
  public Element toXml(Document doc);
}
