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

/**
 * The Class JsonInteger.
 *
 * @author Antony Holmes
 */
public class JsonLong extends Json {

  /**
   * The member value.
   */
  private long mValue;

  /**
   * Instantiates a new json integer.
   *
   * @param value the value
   */
  public JsonLong(long value) {
    mValue = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#getDouble()
   */
  @Override
  public double getDouble() {
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#getInt()
   */
  @Override
  public int getInt() {
    return (int) mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#getLong()
   */
  @Override
  public long getLong() {
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#getString()
   */
  @Override
  public String getString() {
    return Long.toString(mValue);
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