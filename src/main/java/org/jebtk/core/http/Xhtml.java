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
package org.jebtk.core.http;

import org.jebtk.core.Attribute;
import org.jebtk.core.text.TextUtils;

/**
 * Creates XML text elements for writing XML.
 *
 * @author Antony Holmes
 *
 */
public class Xhtml {

  /**
   * Instantiates a new xhtml.
   */
  private Xhtml() {
    // do nothing
  }

  /**
   * Returns the default XML header for writing xml files.
   *
   * @return the string
   */
  public static final String header() {
    return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
  }

  /**
   * Start tag.
   *
   * @param text the text
   * @return the string
   */
  public static final String startTag(String text) {
    return openTag(text) + closeTag();
  }

  /**
   * Open tag.
   *
   * @param text the text
   * @return the string
   */
  public static final String openTag(String text) {
    return openTag() + text;
  }

  /**
   * Open tag.
   *
   * @return the string
   */
  public static final String openTag() {
    return "<";
  }

  /**
   * Close tag.
   *
   * @return the string
   */
  public static final String closeTag() {
    return ">";
  }

  /**
   * Closed tag.
   *
   * @return the string
   */
  public static final String closedTag() {
    return "/>";
  }

  /**
   * End tag.
   *
   * @param text the text
   * @return the string
   */
  public static final String endTag(String text) {
    return "</" + text + closeTag();
  }

  /**
   * Closed tag.
   *
   * @param text the text
   * @return the string
   */
  public static final String closedTag(String text) {
    return openTag() + text + closedTag();
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
    return " " + name + "=\"" + value + "\"";
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
   * Cdata.
   *
   * @param data the data
   * @return the string
   */
  public static String cdata(String data) {
    return "<![CDATA[" + data + "]]>";
  }
}
