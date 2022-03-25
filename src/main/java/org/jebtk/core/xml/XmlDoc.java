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

import java.io.File;
import java.nio.file.Path;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * The class XmlDoc.
 */
public class XmlDoc {

  /**
   * Write.
   *
   * @param doc  the doc
   * @param file the file
   * @throws TransformerException the transformer exception
   */
  public static void write(Document doc, File file) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(file.getAbsolutePath());

    // Output to console for testing
    // StreamResult result = new StreamResult(System.out);

    transformer.transform(source, result);
  }

  /**
   * Write.
   *
   * @param doc  the doc
   * @param file the file
   * @throws TransformerException the transformer exception
   */
  public static void write(Document doc, Path file) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(file.toFile());

    // Output to console for testing
    // StreamResult result = new StreamResult(System.out);

    transformer.transform(source, result);
  }
}
