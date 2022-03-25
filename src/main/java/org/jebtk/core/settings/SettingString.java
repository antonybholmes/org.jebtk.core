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
import org.jebtk.core.http.URLPath;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.path.Path;
import org.jebtk.core.text.TextUtils;

/**
 * Represents a string stored as a setting.
 * 
 * @author Antony Holmes
 *
 */
public class SettingString extends Setting {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The m value. */
  private final String mValue;

  /**
   * Instantiates a new setting string.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   */
  public SettingString(Path path, String value, String description, boolean locked) {
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
    return mValue;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getInt()
   */
  @Override
  public int getInt() {
    return TextUtils.scanInt(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getDouble()
   */
  @Override
  public double getDouble() {
    return TextUtils.scanDouble(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getBool()
   */
  @Override
  public boolean getBool() {
    return mValue.toLowerCase().equals(TextUtils.TRUE);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getColor()
   */
  @Override
  public CSSColor getColor() {
    return ColorUtils.decodeHtmlColor(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getFile()
   */
  @Override
  public java.nio.file.Path getFile() {
    return PathUtils.getPath(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#getUrlBuilder()
   */
  @Override
  public URLPath getUrlBuilder() {
    return URLPath.fromUrl(mValue);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Setting#toString()
   */
  @Override
  public String toString() {
    return new StringBuilder("string_setting:").append(mPath.toString()).append(TextUtils.EQUALS_DELIMITER)
        .append(mValue).toString();
  }
}
