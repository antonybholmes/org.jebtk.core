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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jebtk.core.text.TextUtils;

/**
 * The Class JsonArray.
 *
 * @author Antony Holmes
 */
public class JsonArray extends JsonContainer {

  /**
   * The member elements.
   */
  private final List<Json> mElements = new ArrayList<>();

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#add(java.lang.String,
   * org.abh.lib.json.JsonValue)
   */
  @Override
  public Json add(String name, Json value) {
    return add(value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#add(org.abh.lib.json.JsonValue)
   */
  @Override
  public Json add(Json value) {
    mElements.add(value);

    mJson = null;

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.JsonContainer#insert(org.abh.common.json.JsonRaw)
   */
  @Override
  public Json insert(JsonRaw json) {
    add(json);

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#get(int)
   */
  @Override
  public Json get(int index) {
    if (index < 0 || index > mElements.size() - 1) {
      return null;
    }

    return mElements.get(index);
  }

  @Override
  public Json get(String name) {
    return get(0).get(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#size()
   */
  @Override
  public int size() {
    return mElements.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#formattedTxt(java.lang.StringBuilder)
   */
  @Override
  public void toJson(Appendable buffer) throws IOException {
    if (mJson != null) {
      buffer.append(mJson);

      return;
    }

    buffer.append(JsonBuilder.JSON_ARRAY_START);

    for (int i = 0; i < mElements.size(); ++i) {
      Json v = mElements.get(i);

      if (v == null) {
        buffer.append(TextUtils.NULL);
      } else {
        v.toJson(buffer);
      }

      if (i < mElements.size() - 1) {
        buffer.append(JsonBuilder.JSON_ARRAY_DELIMITER);
      }
    }

    buffer.append(JsonBuilder.JSON_ARRAY_END);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#formattedJson(java.lang.Appendable, int)
   */
  @Override
  public void prettyJson(Appendable buffer, int level) throws IOException {
    String indentation = indentation(level);
    String indentation2 = indentation(level + 1);

    buffer.append(JsonBuilder.JSON_ARRAY_START);
    buffer.append(TextUtils.NEW_LINE);

    int c = 0;

    for (Json v : mElements) {
      buffer.append(indentation2);

      if (v == null) {
        buffer.append(TextUtils.NULL);
      } else {
        v.prettyJson(buffer, level + 1);
      }

      if (c < mElements.size() - 1) {
        buffer.append(JsonBuilder.JSON_ARRAY_DELIMITER);
      }

      buffer.append(TextUtils.NEW_LINE);

      c += 1;
    }

    buffer.append(indentation);
    buffer.append(JsonBuilder.JSON_ARRAY_END);
    // buffer.append(TextUtils.NEW_LINE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#iterator()
   */
  @Override
  public Iterator<Json> iterator() {
    return mElements.iterator();
  }
}