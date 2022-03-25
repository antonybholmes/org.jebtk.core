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

import org.jebtk.core.text.TextUtils;

/**
 * The class XmlDataElement.
 */
public class XmlDataElement extends XmlElement {

  /**
   * The member data.
   */
  private String mData;

  /**
   * Instantiates a new xml data element.
   *
   * @param data the data
   */
  public XmlDataElement(int data) {
    this(Integer.toString(data));
  }

  /**
   * Instantiates a new xml data element.
   *
   * @param data the data
   */
  public XmlDataElement(double data) {
    this(Double.toString(data));
  }

  /**
   * Instantiates a new xml data element.
   *
   * @param data the data
   */
  public XmlDataElement(String data) {
    super(null);

    mData = data;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.xml.XmlElement#formattedXml(java.lang.Appendable, int)
   */
  @Override
  public void formattedXml(Appendable buffer, int level) throws IOException {
    buffer.append(Xml.indentation(level));
    buffer.append(Xml.cdata(mData));
    buffer.append(TextUtils.UNIX_NEW_LINE);
  }
}
