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
package org.jebtk.core.json;

import java.awt.Color;
import java.io.IOException;

import org.jebtk.core.ColorUtils;

/**
 * The Class JsonDouble.
 *
 * @author Antony Holmes
 */
public class JsonColor extends Json {

  /**
   * The member value.
   */
  private Color mValue;

  /**
   * Instantiates a new json double.
   *
   * @param value the value
   */
  public JsonColor(Color value) {
    mValue = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#getColor()
   */
  @Override
  public Color getColor() {
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#getString()
   */
  @Override
  public String getString() {
    return ColorUtils.toHtml(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#formattedTxt(java.lang.StringBuilder)
   */
  @Override
  public void toJson(Appendable buffer) throws IOException {
    buffer.append(getString());
  }
}