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

import java.io.IOException;
import java.nio.file.Path;

import org.jebtk.core.AppService;

/**
 * The Class SettingsReaderUserHomeJson.
 */
public class SettingsReaderUserHomeJson implements ISettingsReader {

  /** The Constant USER_JSON_FILE. */
  public static final String USER_JSON_FILE = "user.settings.json";

  /** The Constant APP_HOME. */
  public static final String APP_HOME = "app_home";

  /** The m file. */
  private final Path mFile;

  /**
   * Instantiates a new settings reader user home json.
   */
  public SettingsReaderUserHomeJson() {
    mFile = create();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.SettingsReader#load(org.abh.common.settings.
   * Settings)
   */
  @Override
  public void load(Settings settings) {
    LOG.info("Loading user JSON settings from {}...", mFile);

    // Load any per user settings. We flag these as being updated so
    // that on the next write cycle, they will be written back to the
    // settings file.
    try {
      settings.loadJson(mFile, false);
    } catch (IOException e) {
      e.printStackTrace();
    }

    LOG.info("Finished loading settings...");
  }

  /**
   * Creates the.
   *
   * @return the path
   */
  public static Path create() {
    return AppService.getInstance().getAppDir().resolve(USER_JSON_FILE);
  }
}
