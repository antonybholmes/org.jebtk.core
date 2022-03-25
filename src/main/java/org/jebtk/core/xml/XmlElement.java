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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jebtk.core.Attribute;
import org.jebtk.core.text.TextUtils;

/**
 * The class XmlElement.
 */
public class XmlElement implements Iterable<XmlElement>, IFormattedXml {

  /**
   * The member attributes.
   */
  private List<Attribute> mAttributes = new ArrayList<Attribute>();

  /**
   * The member elements.
   */
  private List<XmlElement> mElements = new ArrayList<XmlElement>();

  /**
   * The member name.
   */
  private String mName;

  /**
   * Instantiates a new xml element.
   *
   * @param name the name
   */
  public XmlElement(String name) {
    mName = name;
  }

  /**
   * Instantiates a new xml element.
   *
   * @param name the name
   * @param text the text
   */
  public XmlElement(String name, String text) {
    this(name);

    appendChild(new XmlDataElement(text));
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return mName;
  }

  /**
   * Sets the attribute.
   *
   * @param name  the name
   * @param value the value
   */
  public void setAttribute(String name, String value) {
    mAttributes.add(new Attribute(name, value));
  }

  /**
   * Sets the attribute.
   *
   * @param name  the name
   * @param value the value
   */
  public final void setAttribute(String name, int value) {
    mAttributes.add(new Attribute(name, Integer.toString(value)));
  }

  /**
   * Sets the attribute.
   *
   * @param name  the name
   * @param value the value
   */
  public final void setAttribute(String name, double value) {
    mAttributes.add(new Attribute(name, Double.toString(value)));
  }

  /**
   * Append child.
   *
   * @param element the element
   */
  public final void appendChild(XmlElement element) {
    mElements.add(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<XmlElement> iterator() {
    return mElements.iterator();
  }

  /**
   * Gets the elements by name.
   *
   * @param name the name
   * @return the elements by name
   */
  public List<XmlElement> getElementsByName(String name) {
    List<XmlElement> elementsByName = new ArrayList<XmlElement>();

    for (XmlElement element : mElements) {
      if (element.getName().equals(name)) {
        elementsByName.add(element);
      }
    }

    return elementsByName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    String out = null;

    try {
      out = formattedXml();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return out;
  }

  /**
   * Formatted xml.
   *
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public String formattedXml() throws IOException {
    StringBuilder buffer = new StringBuilder();

    formattedXml(buffer);

    return buffer.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.xml.FormattedXml#formattedXml(java.lang.Appendable)
   */
  @Override
  public void formattedXml(Appendable buffer) throws IOException {
    formattedXml(buffer, 0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.xml.FormattedXml#formattedXml(java.lang.Appendable, int)
   */
  @Override
  public void formattedXml(Appendable buffer, int level) throws IOException {
    String indentation = Xml.indentation(level);

    buffer.append(Xml.indentation(level));
    buffer.append(Xml.openTag(mName));

    for (Attribute attribute : mAttributes) {
      buffer.append(Xml.attribute(attribute));
    }

    if (mElements.size() > 0) {
      buffer.append(Xml.closeTag());
      buffer.append(TextUtils.UNIX_NEW_LINE);

      for (XmlElement element : mElements) {
        element.formattedXml(buffer, level + 1);
      }

      buffer.append(indentation + Xml.endTag(mName));
    } else {
      // self closing tag
      buffer.append(Xml.closedTag());
    }

    buffer.append(TextUtils.UNIX_NEW_LINE);
  }
}
