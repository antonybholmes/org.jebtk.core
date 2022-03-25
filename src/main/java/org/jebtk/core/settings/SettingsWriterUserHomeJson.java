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

import org.jebtk.core.io.FileUtils;

/**
 * The Class SettingsWriterUserHomeJson.
 */
public class SettingsWriterUserHomeJson implements SettingsWriter {

  /** The m file. */
  private final Path mFile;

  /**
   * Instantiates a new settings writer user home json.
   */
  public SettingsWriterUserHomeJson() {
    mFile = SettingsReaderUserHomeJson.create();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.SettingsWriter#save(org.abh.common.settings.
   * Settings)
   */
  @Override
  public void save(Settings settings) {
    LOG.info("Writing JSON settings to {}...", mFile);

    try {
      // Ensure directory exists.
      FileUtils.mkdir(mFile.getParent());
      settings.writeJson(mFile);
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  }

}
