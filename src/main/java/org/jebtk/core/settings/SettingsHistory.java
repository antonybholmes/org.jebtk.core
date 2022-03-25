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

import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.event.ChangeListeners;

/**
 * The Class SettingsHistory.
 */
public class SettingsHistory extends ChangeListeners {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /** The m settings. */
  private final List<Setting> mSettings = new ArrayList<>(2);

  /**
   * Adds the.
   *
   * @param setting the setting
   * @param updated the updated
   */
  public void add(Setting setting, boolean updated) {
    if (!updated) {
      // If the setting is not updated, we assume it is a default,
      // internal setting, in which case, it should appear first in the
      // list.
      mSettings.clear();
    }
    
    if (mSettings.size() < 2) {
      mSettings.add(setting);
    } else {
      mSettings.set(1, setting);
    }
    
    fireChanged();
  }

  /**
   * Size.
   *
   * @return the int
   */
  public int size() {
    return mSettings.size();
  }
  
  /**
   * First.
   *
   * @return the setting
   */
  public Setting getDefaultSetting() {
    return mSettings.get(0);
  }


  /**
   * Gets the current setting value.
   *
   * @return the setting
   */
  public Setting current() {
    return mSettings.get(mSettings.size() - 1);
  }

  /**
   * Changes the setting to its default value.
   */
  public void resetToDefault() {
    if (mSettings.size() > 1) {
      mSettings.subList(1, mSettings.size()).clear();
      fireChanged();
    }
  }
}
