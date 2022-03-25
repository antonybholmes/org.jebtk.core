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

import org.jebtk.core.io.PathUtils;

/**
 * The Class SettingsReaderDefaultJson.
 */
public class SettingsReaderDefaultJson implements ISettingsReader {

  /** The Constant JSON_FILE. */
  public static final Path JSON_FILE = PathUtils.getPath("res/default.settings.json");

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.SettingsReader#load(org.abh.common.settings.
   * Settings)
   */
  @Override
  public void load(Settings settings) {
    LOG.info("Loading user JSON settings...");

    // Load any per user settings. We flag these as being updated so
    // that on the next write cycle, they will be written back to the
    // settings file.
    try {
      settings.loadJson(JSON_FILE, false);
    } catch (IOException e) {
      e.printStackTrace();
    }

    LOG.info("Finished loading settings...");
  }
}
