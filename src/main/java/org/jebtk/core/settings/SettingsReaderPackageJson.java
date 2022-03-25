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
import java.io.InputStream;
import java.nio.file.Path;

import org.jebtk.core.Resources;

/**
 * The Class SettingsReaderPackageJson.
 */
public class SettingsReaderPackageJson implements ISettingsReader {

  /**
   * The Constant DEFAULT_JSON_FILE.
   */
  public static final Path DEFAULT_JSON_FILE
          = Resources.RES_DIR.resolve("default.settings.json");

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.SettingsReader#load(org.abh.common.settings.
   * Settings)
   */
  @Override
  public void load(Settings settings) {
    LOG.info("Loading JSON package settings...");

    for (String res : Resources.getInstance()) {
      if (!res.contains("settings.json")) {
        continue;
      }

      LOG.info("Loading settings from {}...", res);

      try ( InputStream is = Resources.getResInputStream(res)) {
        settings.loadJson(is, false);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // Load local settings that may overwrite internal settings.
    try {
      settings.loadJson(DEFAULT_JSON_FILE, false);
    } catch (IOException e) {
      e.printStackTrace();
    }

    LOG.info("Finished loading settings...");
  }
}
