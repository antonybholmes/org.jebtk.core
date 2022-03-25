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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jebtk.core.collections.IterHashMap;
import org.jebtk.core.collections.IterMap;
import org.jebtk.core.collections.UniqueArrayList;
import org.jebtk.core.text.TextUtils;

/**
 * Represents a json object containing key value pairs.
 * 
 * @author Antony Holmes
 *
 */
public class JsonObject extends JsonContainer {
  // Keep the members sorted alphabetically

  /**
   * The member member names.
   */
  private List<String> mMemberNames = new UniqueArrayList<String>();

  /**
   * The member member map.
   */
  private IterMap<String, Json> mMemberMap = new IterHashMap<String, Json>();

  /**
   * Instantiates a new json object.
   */
  public JsonObject() {
    // Do nothing
  }

  /**
   * Instantiates a new json object.
   *
   * @param name the name
   */
  public JsonObject(String name) {
    add("name", name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#get(int)
   */
  @Override
  public Json get(int i) {
    return get(mMemberNames.get(i));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#get(java.lang.String)
   */
  @Override
  public Json get(String name) {
    return mMemberMap.get(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#get(java.lang.String, boolean)
   */
  @Override
  public Json get(String name, boolean defaultValue) {
    if (!containsKey(name)) {
      add(name, defaultValue);
    }

    return get(name);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#add(java.lang.String,
   * org.abh.lib.json.JsonValue)
   */
  @Override
  public Json add(String name, Json value) {
    mMemberNames.add(name);

    mMemberMap.put(name, value);

    mJson = null;

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.JsonContainer#insert(java.lang.String,
   * org.abh.common.json.JsonRaw)
   */
  @Override
  public Json insert(String name, JsonRaw json) {
    add(name, json);

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#containsKey(java.lang.String)
   */
  @Override
  public boolean containsKey(String key) {
    return mMemberMap.containsKey(key);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#getKeys()
   */
  @Override
  public Collection<String> getKeys() {
    return Collections.unmodifiableList(mMemberNames);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#size()
   */
  @Override
  public int size() {
    return mMemberMap.size();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#formattedTxt(java.lang.StringBuilder)
   */
  @Override
  public void toJson(Appendable buffer) throws IOException {
    buffer.append(JsonBuilder.JSON_OBJECT_START);

    int c = 0;

    Json v = null;

    for (String name : mMemberNames) {
      v = mMemberMap.get(name);

      buffer.append(TextUtils.quote(JsonString.escape(name))).append(JsonBuilder.JSON_VALUE_DELIMITER);

      if (v == null) {
        buffer.append(JsonBuilder.JSON_NULL);
      } else {
        v.toJson(buffer);
      }

      if (c < mMemberNames.size() - 1) {
        buffer.append(JsonBuilder.JSON_ARRAY_DELIMITER);
      }

      c += 1;
    }

    buffer.append(JsonBuilder.JSON_OBJECT_END);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#formattedJson(java.lang.Appendable, int)
   */
  @Override
  public void prettyJson(Appendable buffer, int level) throws IOException {
    if (mJson != null) {
      buffer.append(mJson);

      return;
    }

    String indentation = indentation(level);
    String indentation2 = indentation(level + 1);

    // buffer.append(indentation);
    buffer.append(JsonBuilder.JSON_OBJECT_START);
    buffer.append(TextUtils.NEW_LINE);

    int c = 0;

    Json v = null;

    for (String name : mMemberNames) {
      v = mMemberMap.get(name);

      buffer.append(indentation2);
      JsonBuilder.quote(JsonString.escape(name), buffer);
      // buffer.append(JsonBuilder.quote(JsonString.escape(name)));
      buffer.append(JsonBuilder.JSON_VALUE_DELIMITER);
      // buffer.append(TextUtils.SPACE_DELIMITER);

      if (v == null) {
        buffer.append(JsonBuilder.JSON_NULL);
      } else {
        v.prettyJson(buffer, level + 1);
      }

      if (c < mMemberNames.size() - 1) {
        buffer.append(TextUtils.FORMATTED_COMMA_DELIMITER);
      }

      buffer.append(TextUtils.NEW_LINE);

      c += 1;
    }

    buffer.append(indentation);
    buffer.append(JsonBuilder.JSON_OBJECT_END);
    // buffer.append(TextUtils.NEW_LINE);
  }

  /**
   * Creates the.
   *
   * @param key   the key
   * @param value the value
   * @return the json
   */
  public static Json create(String key, String value) {
    JsonObject json = create();

    json.add(key, value);

    return json;
  }

  /**
   * Create a new JSONObject for storing key value pairs.
   *
   * @return the json object
   */
  public static JsonObject create() {
    return new JsonObject();
  }

  @Override
  public String toString() {
    return mMemberNames.toString();
  }
}