/**
 * Copyright (C) 2016, Antony Holmes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of copyright holder nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jebtk.core;

import java.awt.Color;
import static org.jebtk.core.ColorUtils.HTML_COLOR_WHITE;
import static org.jebtk.core.ColorUtils.hexString;

import org.jebtk.core.json.Json;
import org.jebtk.core.json.JsonObject;
import org.jebtk.core.json.JsonRepresentation;
import org.jebtk.core.xml.XmlRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The class CSSColor represents a color with additional Props for use with HTML
 * like elements.
 */
public class CSSColor extends Color implements XmlRepresentation, JsonRepresentation {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;
  
  public static final CSSColor CSS_WHITE = new CSSColor(WHITE);
  
  public CSSColor(int v) {
    super(v);
  }
  
  public CSSColor(Color c) {
    super(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
  }

  /**
   * Instantiates a new color map color.
   *
   * @param r the r
   * @param g the g
   * @param b the b
   */
  public CSSColor(int r, int g, int b) {
    super(r, g, b);
  }

  /**
   * Instantiates a new color map color.
   *
   * @param r the r
   * @param g the g
   * @param b the b
   * @param a the a
   */
  public CSSColor(int r, int g, int b, int a) {
    super(r, g, b, a);
  }

  /**
   * Instantiates a new color map color.
   *
   * @param r the r
   * @param g the g
   * @param b the b
   */
  public CSSColor(float r, float g, float b) {
    super(r, g, b);
  }

  /**
   * Instantiates a new color map color.
   *
   * @param r the r
   * @param g the g
   * @param b the b
   * @param a the a
   */
  public CSSColor(float r, float g, float b, float a) {
    super(r, g, b, a);
  }
  
  @Override
  public String toString() {
    return toHtml(this);
  }
  
  public static String toHtml(CSSColor color) {
    if (color == null) {
      return HTML_COLOR_WHITE;
    }

    StringBuilder buffer = new StringBuilder();

    buffer.append("#");
    buffer.append(hexString(color.getRed()));
    buffer.append(hexString(color.getGreen()));
    buffer.append(hexString(color.getBlue()));
    buffer.append(hexString(color.getAlpha()));

    return buffer.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.xml.XmlRepresentation#toXml()
   */
  @Override
  public Element toXml(Document doc) {
    Element element = doc.createElement("color");

    element.setAttribute("r", Integer.toString(getRed()));
    element.setAttribute("g", Integer.toString(getGreen()));
    element.setAttribute("b", Integer.toString(getBlue()));
    element.setAttribute("a", Integer.toString(getAlpha()));

    return element;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.JsonRepresentation#toJson()
   */
  @Override
  public Json toJson() {
    Json element = new JsonObject();

    element.add("r", getRed());
    element.add("g", getGreen());
    element.add("b", getBlue());
    element.add("a", getAlpha());

    return element;
  }

  /**
   * Creates the.
   *
   * @param c the c
   * @return the color map color
   */
  public static CSSColor create(Color c) {
    return new CSSColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
  }
}
