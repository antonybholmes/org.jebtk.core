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
import java.io.FileWriter;
import java.io.IOException;

/**
 * The class XmlWriter.
 */
public class XmlWriter {

  /**
   * The constant XML_HEADER.
   */
  public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  /**
   * The writer.
   */
  private BufferedWriter writer;

  /**
   * Instantiates a new xml writer.
   *
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public XmlWriter(File file) throws IOException {
    writer = new BufferedWriter(new FileWriter(file));
    Xml.writeXmlHeader(writer);
  }

  /**
   * Write tag.
   *
   * @param tag the tag
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void writeTag(String tag) throws IOException {
    writer.write(Xml.startTag(tag));

  }

  /**
   * Write end tag.
   *
   * @param tag the tag
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void writeEndTag(String tag) throws IOException {
    writer.write(Xml.endTag(tag));
  }

  /**
   * Write open tag.
   *
   * @param tag the tag
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void writeOpenTag(String tag) throws IOException {
    writer.write("<" + tag);
  }

  /**
   * Write close tag.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void writeCloseTag() throws IOException {
    writer.write(">");
  }

  /**
   * End closed tag.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void endClosedTag() throws IOException {
    writer.write(" />");
  }

  /**
   * Adds the parameter.
   *
   * @param name  the name
   * @param value the value
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void addParameter(String name, String value) throws IOException {
    writer.write(" " + name + "=\"" + value + "\"");
  }

  /**
   * Close.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void close() throws IOException {
    writer.close();
  }

  /**
   * Write.
   *
   * @param text the text
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void write(String text) throws IOException {
    writer.write(text);
  }

  /**
   * Write tagged text.
   *
   * @param tag  the tag
   * @param text the text
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void writeTaggedText(String tag, String text) throws IOException {
    writeTag(tag);
    write(text);
    writeEndTag(tag);
    writer.newLine();
  }

  /**
   * New line.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void newLine() throws IOException {
    writer.newLine();
  }
}
