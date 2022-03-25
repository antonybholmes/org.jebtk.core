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

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.jebtk.core.io.FileUtils;
import org.jebtk.core.text.TextUtils;

/**
 * Represents an XML document.
 *
 * @author Antony Holmes
 *
 */
public class XmlDocument extends XmlElement {

  /**
   * Instantiates a new xml document.
   */
  public XmlDocument() {
    super("document");
  }

  /**
   * Instantiates a new xml document.
   *
   * @param element the element
   */
  public XmlDocument(XmlElement element) {
    super("document");

    appendChild(element);
  }

  /**
   * Write the document to the file in a nicely formatted, tab indented fashion.
   *
   * @param doc  the doc
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void writeXml(XmlDocument doc, File file) throws IOException {
    writeXml(doc, file.toPath());
  }

  /**
   * Write xml.
   *
   * @param doc  the doc
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void writeXml(XmlDocument doc, Path file) throws IOException {
    BufferedWriter writer = FileUtils.newBufferedWriter(file);

    try {
      writer.append(Xml.xmlHeader());
      writer.append(TextUtils.NEW_LINE);

      for (XmlElement children : doc) {
        children.formattedXml(writer, 0);
      }
    } finally {
      writer.close();
    }
  }
}
