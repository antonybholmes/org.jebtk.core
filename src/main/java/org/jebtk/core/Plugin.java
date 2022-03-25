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
package org.jebtk.core;

/**
 * Container for a Class object that can be sorted { and indexed.
 *
 * @author Antony Holmes
 *
 */
public class Plugin implements Comparable<Plugin> {

  /**
   * The member class.
   */
  private Class<?> mClass;

  /**
   * Instantiates a new plugin.
   *
   * @param c the c
   */
  public Plugin(Class<?> c) {
    mClass = c;
  }

  /**
   * Returns the plugin class object that can { be instanciated into a new
   * instance.
   *
   * @return the plugin class
   */
  public Class<?> getPluginClass() {
    return mClass;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  public final String toString() {
    return mClass.getCanonicalName();
  }

  /**
   * Returns a standardized name of the plugin based on the class name.
   *
   * @return the name
   */
  public final String getName() {
    return mClass.getSimpleName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Plugin e) {
    return getName().compareTo(e.getName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Plugin) {
      return compareTo((Plugin) o) == 0;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return getName().hashCode();
  }

}
