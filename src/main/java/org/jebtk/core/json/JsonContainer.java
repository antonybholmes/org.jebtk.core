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

/**
 * The base class for the json array and object which are containers of either
 * list or maps.
 *
 * @author Antony Holmes
 */
public abstract class JsonContainer extends Json {

  /** The m json. */
  protected String mJson = null;

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#add(org.abh.common.json.Json)
   */
  @Override
  public Json add(Json value) {
    mJson = null;

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#add(java.lang.String, org.abh.common.json.Json)
   */
  @Override
  public Json add(String name, Json value) {
    mJson = null;

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#insert(org.abh.common.json.JsonRaw)
   */
  @Override
  public Json insert(JsonRaw json) {
    mJson = null;

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#insert(java.lang.String,
   * org.abh.common.json.JsonRaw)
   */
  @Override
  public Json insert(String name, JsonRaw json) {
    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#setJson(java.lang.String)
   */
  @Override
  public void setJson(String json) {
    mJson = json;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.Json#toString()
   */
  @Override
  public String toString() {
    if (mJson == null) {
      mJson = super.toString();
    }

    return mJson;
  }
}