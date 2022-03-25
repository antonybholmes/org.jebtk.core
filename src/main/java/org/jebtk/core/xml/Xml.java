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
import java.io.IOException;

import org.jebtk.core.Attribute;
import org.jebtk.core.text.TextUtils;

/**
 * Creates XML strings for writing XML.
 *
 * @author Antony Holmes
 *
 */
public class Xml {

  /**
   * The constant HEADER.
   */
  private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  /**
   * Instantiates a new xml.
   */
  private Xml() {
    // do nothing
  }

  /**
   * Writes the default XML header to the writer.
   *
   * @param writer the writer
   */
  public static final void writeXmlHeader(BufferedWriter writer) {
    try {
      writer.write(xmlHeader());
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the default XML header for writing xml files.
   *
   * @return the string
   */
  public static final String xmlHeader() {
    // return "<?xml version=\"1.0\"?>";
    return HEADER;
  }

  /**
   * Start tag.
   *
   * @param text the text
   * @return the string
   */
  public static final String startTag(String text) {
    return new StringBuilder(openTag(text)).append(closeTag()).toString();
  }

  /**
   * Open tag.
   *
   * @param text the text
   * @return the string
   */
  public static final String openTag(String text) {
    return new StringBuilder(openTag()).append(text).toString();
  }

  /**
   * Returns the start tag angle bracket.
   *
   * @return the string
   */
  public static final String openTag() {
    return "<";
  }

  /**
   * Returns the end tag angle bracket.
   *
   * @return the string
   */
  public static final String closeTag() {
    return ">";
  }

  /**
   * Returns the slash + closeTag to indicate the element has no children.
   *
   * @return the string
   */
  public static final String closedTag() {
    return new StringBuilder("/").append(closeTag()).toString();
  }

  /**
   * Returns the named end tag to indicate a tag can be closed.
   *
   * @param text the text
   * @return the string
   */
  public static final String endTag(String text) {
    return new StringBuilder("</").append(text).append(closeTag()).toString();
  }

  /**
   * Returns a named closed tag.
   *
   * @param text the text
   * @return the string
   */
  public static final String closedTag(String text) {
    return new StringBuilder(openTag()).append(text).append(closedTag()).toString();
  }

  /**
   * Attribute.
   *
   * @param attribute the attribute
   * @return the string
   */
  public static String attribute(Attribute attribute) {
    return attribute(attribute.getName(), attribute.getValue());
  }

  /**
   * Attribute.
   *
   * @param name  the name
   * @param value the value
   * @return the string
   */
  public static final String attribute(String name, String value) {
    return new StringBuilder(" ").append(name).append("=\"").append(value).append("\"").toString();
  }

  /**
   * Creates an indentation space for writing aesthetically pleasing XML files
   * rather than one line monoliths.
   *
   * @param level the level
   * @return the string
   */
  public static String indentation(int level) {
    return TextUtils.repeat(TextUtils.TAB_DELIMITER, level);
  }

  /**
   * Surround a string with CDATA tags.
   *
   * @param data the data
   * @return the string
   */
  public static String cdata(String data) {
    return new StringBuilder("<![CDATA[").append(data).append("]]>").toString();
  }
}
