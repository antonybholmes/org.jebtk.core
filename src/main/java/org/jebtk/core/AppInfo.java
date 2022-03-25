/**
 * Copyright (C) 2016, Antony Holmes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of copyright holder nor the names of its contributors 
 *     may be used to endorse or promote products derived from this software 
 *     without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jebtk.core;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.settings.Setting;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.core.text.TextUtils;
import org.xml.sax.SAXException;

/**
 * Describes a product.
 * 
 * @author Antony Holmes
 *
 */
public class AppInfo {

  /**
   * The member version.
   */
  private AppVersion mVersion;

  /**
   * Provides a copyright placeholder string.
   */
  private String mCopyright;

  /**
   * The member name.
   */
  private String mName;

  /**
   * The member help name.
   */
  private String mHelpName;

  /**
   * The member description.
   */
  private String mDescription;

  /**
   * Instantiates a new application information.
   *
   * @param name      the name
   * @param version   the version
   * @param copyright the copyright
   */
  public AppInfo(String name, AppVersion version, String copyright) {
    this(name, version, copyright, name.toLowerCase().replaceAll("\\s", ""), TextUtils.EMPTY_STRING);
  }

  /**
   * Instantiates a new application information.
   *
   * @param name        the name
   * @param version     the version
   * @param copyright   the copyright
   * @param description the description
   */
  public AppInfo(String name, AppVersion version, String copyright, String description) {
    this(name, version, copyright, name.toLowerCase().replaceAll("[\\.\\s]", ""), description);
  }

  /**
   * Instantiates a new application information.
   *
   * @param name        the name
   * @param version     the version
   * @param copyright   the copyright
   * @param helpName    the help name
   * @param description the description
   */
  public AppInfo(String name, AppVersion version, String copyright, String helpName, String description) {
    mName = name;
    mCopyright = TextUtils.replaceVariables(copyright);
    mHelpName = helpName;
    mDescription = description;

    mVersion = version; // loadVersion();
                        // //SettingsService.getInstance().getString(helpName
                        // +
                        // ".version");
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return mName;
  }

  /**
   * Gets the version.
   *
   * @return the version
   */
  public AppVersion getVersion() {
    return mVersion;
  }

  /**
   * Gets the copyright.
   *
   * @return the copyright
   */
  public String getCopyright() {
    return mCopyright;
  }

  /**
   * Gets the help name.
   *
   * @return the help name
   */
  public String getHelpName() {
    return mHelpName;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return mDescription;
  }

  /**
   * Gets the setting.
   *
   * @param setting the setting
   * @return the setting
   */
  public Setting getSetting(String setting) {
    return SettingsService.getInstance().getSetting(getHelpName() + "." + setting);
  }

  /**
   * Load settings based on the application name. The name is converted to
   * lowercase and spaces are replaced by underscore. A name.settings.xml file
   * must exist in the res folder inside the application jar file.
   *
   * @param appInfo the app info
   * @throws SAXException                 the SAX exception
   * @throws IOException                  Signals that an I/O exception has
   *                                      occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  public static void loadSettings(AppInfo appInfo) throws SAXException, IOException, ParserConfigurationException {
    SettingsService.getInstance().loadLibSettings(appInfo.getHelpName());
  }

  /**
   * Load the application version from settings.
   *
   * @return the app version
   */
  public AppVersion loadVersion() {
    return new AppVersion(getSetting("version.major").getInt());
  }
}
