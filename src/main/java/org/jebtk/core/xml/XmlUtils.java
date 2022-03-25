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

import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

/**
 * XmlUtils provides wrapper functions for simplifying XML generation.
 * 
 */
public class XmlUtils {

  /**
   * Instantiates a new xml utils.
   */
  private XmlUtils() {
    // Do nothing
  }

  /**
   * Creates an XML document.
   *
   * @return the document
   * @throws ParserConfigurationException the parser configuration exception
   */
  public static Document createDoc() throws ParserConfigurationException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

    // root elements
    Document doc = docBuilder.newDocument();

    return doc;
  }

  public static void writeXml(XmlRepresentation xml, Path file)
      throws TransformerException, ParserConfigurationException {
    Document doc = createDoc();

    doc.appendChild(xml.toXml(doc));

    writeXml(doc, file);
  }

  /**
   * Writes an xml document to file, and also pretty prints the output.
   *
   * @param doc  the doc.
   * @param file the file.
   * @throws TransformerException the transformer exception.
   */
  public static void writeXml(Document doc, Path file) throws TransformerException {
    // TODO Auto-generated method stub

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(file.toFile());

    // Output to console for testing
    // StreamResult result = new StreamResult(System.out);

    transformer.transform(source, result);

  }
}
