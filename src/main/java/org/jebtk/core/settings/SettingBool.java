/**
 * Copyright 2017 Antony Holmes
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
package org.jebtk.core.settings;

import org.jebtk.core.json.Json;
import org.jebtk.core.path.Path;
import org.jebtk.core.text.TextUtils;

/**
 * The Class SettingBool.
 */
public class SettingBool extends Setting {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m value. */
  private final boolean mValue;

  /**
   * Instantiates a new setting bool.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   */
  public SettingBool(Path path, boolean value, String description, boolean locked) {
    super(path, description, locked);

    mValue = value;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getString()
   */
  @Override
  public String getValue() {
    return Boolean.toString(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getBool()
   */
  @Override
  public boolean getBool() {
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#toString()
   */
  @Override
  public String toString() {
    return new StringBuilder("bool_setting:").append(mPath.toString()).append(TextUtils.EQUALS_DELIMITER).append(mValue)
        .toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.abh.common.settings.Setting#formatJsonValue(org.abh.common.json.Json)
   */
  @Override
  protected void formatJsonValue(Json o) {
    o.add("value", mValue);
  }
}
