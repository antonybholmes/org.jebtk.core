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
package org.jebtk.core.settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jebtk.core.tree.TreeNode;
import org.jebtk.core.xml.Xml;
import org.jebtk.core.xml.XmlWriter;
import org.xml.sax.SAXException;

/**
 * The class KeyService.
 */
public class KeyService extends KeyNode {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new key service.
   *
   * @param name the name
   */
  public KeyService(String name) {
    super(name);
  }

  /**
   * Load xml.
   *
   * @param file the file
   * @throws SAXException                 the SAX exception
   * @throws IOException                  Signals that an I/O exception has
   *                                      occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  public void loadXml(File file) throws SAXException, IOException, ParserConfigurationException {
    System.err.println("Loading settings from " + file + "...");

    InputStream stream = new FileInputStream(file);

    loadXml(stream);
  }

  /**
   * Load xml.
   *
   * @param is the is
   * @throws SAXException                 the SAX exception
   * @throws IOException                  Signals that an I/O exception has
   *                                      occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  public void loadXml(InputStream is) throws SAXException, IOException, ParserConfigurationException {
    if (is == null) {
      return;
    }

    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();

    KeyXmlHandler handler = new KeyXmlHandler(this);

    saxParser.parse(is, handler);
  }

  /**
   * Write xml.
   *
   * @param out the out
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected synchronized final void writeXml(File out) throws IOException {
    writeXml(out, this);
  }

  /**
   * Write the settings as an xml file.
   *
   * @param out  the out
   * @param node the node
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static final void writeXml(File out, TreeNode<String> node) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(out));

    try {
      writer.write(XmlWriter.XML_HEADER);
      writer.newLine();

      writer.write(Xml.startTag(node.getName()));
      writer.newLine();

      for (TreeNode<String> child : node) {
        write(writer, child, 1);
      }

      writer.write(Xml.endTag(node.getName()));
      writer.newLine();
    } finally {
      writer.close();
    }
  }

  /**
   * Writes out the given node at the given indentation level so that the XML file
   * remains formatted.
   *
   * @param writer the writer
   * @param node   the node
   * @param level  the level
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void write(BufferedWriter writer, TreeNode<String> node, int level) throws IOException {
    writer.write(Xml.indentation(level));
    writer.write(Xml.openTag("key"));
    writer.write(Xml.attribute("name", node.getName()));

    if (node.getValue() != null && node.getValue().length() > 0) {
      writer.write(Xml.attribute("value", node.getValue()));
    }

    if (node.isParent()) {
      writer.write(Xml.closeTag());
      writer.newLine();

      for (TreeNode<String> child : node) {
        write(writer, child, level + 1);
      }

      writer.write(Xml.indentation(level));
      writer.write(Xml.endTag("key"));
    } else {
      writer.write(Xml.closedTag());
    }

    writer.newLine();
  }
}
