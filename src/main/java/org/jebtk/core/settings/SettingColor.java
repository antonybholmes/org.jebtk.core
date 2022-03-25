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


import org.jebtk.core.CSSColor;
import org.jebtk.core.ColorUtils;
import org.jebtk.core.path.Path;
import org.jebtk.core.text.TextUtils;

/**
 * The Class SettingColor.
 */
public class SettingColor extends Setting {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m value. */
  private final CSSColor mValue;

  /**
   * Instantiates a new setting color.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   */
  public SettingColor(Path path, CSSColor value, String description, boolean locked) {
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
    return ColorUtils.toHtml(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getColor()
   */
  @Override
  public CSSColor getColor() {
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#toString()
   */
  @Override
  public String toString() {
    return new StringBuilder("color_setting:").append(TextUtils.EQUALS_DELIMITER).append(mPath.toString())
        .append(TextUtils.EQUALS_DELIMITER).append(mValue).toString();
  }
}
