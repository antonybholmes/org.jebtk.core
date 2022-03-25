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
package org.jebtk.core.cli;

import org.jebtk.core.text.TextUtils;

/**
 * Specifies an expected argument on the command line.
 * 
 * @author Antony Holmes
 *
 */
public class Arg {

  /**
   * The member short name.
   */
  private String mShortName;

  /**
   * The member long name.
   */
  private String mLongName;

  /**
   * The member has arg.
   */
  private boolean mHasValue;

  /**
   * The member description.
   */
  private String mDescription;

  private String mS;

  private String mDefaultValue;

  public Arg(String name) {
    this(name.charAt(0), name);
  }
  
  public Arg(char shortName) {
    this(shortName, false);
  }
  
  public Arg(char shortName, boolean hasArg) {
    this(shortName, Character.toString(shortName), hasArg, TextUtils.EMPTY_STRING);
  }
  
  /**
   * Instantiates a new command line option.
   *
   * @param shortName   the short name
   * @param hasArg      the has arg
   * @param description the description
   */
  public Arg(char shortName, boolean hasArg, String description) {
    this(shortName, Character.toString(shortName), hasArg, description);
  }
  
  public Arg(char shortName, String longName) {
    this(shortName, longName, false);
  }
  
  public Arg(char shortName, String longName, boolean hasArg) {
    this(shortName, longName, hasArg, TextUtils.EMPTY_STRING, TextUtils.EMPTY_STRING);
  }

  /**
   * Instantiates a new command line option.
   *
   * @param shortName   the short name
   * @param longName    the long name
   * @param hasArg      the has arg
   * @param description the description
   */
  public Arg(char shortName, String longName, boolean hasArg, String defaultValue) {
    this(shortName, longName, hasArg, defaultValue, TextUtils.EMPTY_STRING);
  }

  public Arg(char shortName, String longName, boolean hasArg, String defaultValue, String description) {
    mShortName = Character.toString(shortName);
    mLongName = parseArgName(longName);
    mHasValue = hasArg;
    mDefaultValue = defaultValue;
    mDescription = description;
  }

  /**
   * Gets the short name.
   *
   * @return the short name
   */
  public String getShortName() {
    return mShortName;
  }

  /**
   * Gets the long name.
   *
   * @return the long name
   */
  public String getLongName() {
    return mLongName;
  }

  /**
   * Returns true if this arg requires a value.
   *
   * @return true, if successful
   */
  public boolean hasValue() {
    return mHasValue;
  }

  public String getDefaultValue() {
    return mDefaultValue;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return mDescription;
  }

  @Override
  public String toString() {
    if (mS == null) {
      StringBuilder buffer = new StringBuilder(getShortName());

      if (hasValue()) {
        buffer.append(" VALUE");
      }

      buffer.append(", --" + getLongName());

      if (hasValue()) {
        buffer.append("=VALUE");
      }

      mS = buffer.toString();
    }

    return mS;
  }

  
  public static String parseArgName(String arg) {
    return arg.replaceFirst("^\\-+", TextUtils.EMPTY_STRING).replaceFirst("=.+", TextUtils.EMPTY_STRING);
  }
}
