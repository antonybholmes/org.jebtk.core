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

import java.io.IOException;

import org.jebtk.core.text.TextUtils;

/**
 * Stores a raw JSON string without modifying it.
 */
public class JsonRaw extends Json {

  /**
   * The member value.
   */
  protected String mValue;

  /**
   * Instantiates a new json raw.
   *
   * @param value the value
   */
  public JsonRaw(String value) {
    if (value != null) {
      mValue = value;
    } else {
      mValue = TextUtils.EMPTY_STRING;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#toString()
   */
  @Override
  public String toString() {
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#getString()
   */
  @Override
  public String getString() {
    return toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#getChar()
   */
  @Override
  public char getChar() {
    return mValue.charAt(0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#formattedTxt(java.lang.StringBuilder)
   */
  @Override
  public void toJson(Appendable buffer) throws IOException {
    buffer.append(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#formattedJson(java.lang.Appendable, int)
   */
  @Override
  public void prettyJson(Appendable buffer, int level) throws IOException {
    // buffer.append(indentation(level));

    toJson(buffer);
  }
}