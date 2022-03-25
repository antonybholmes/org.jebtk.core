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
 * Similar to system properties.
 * 
 * @author Antony Holmes
 *
 */
public class Computer {

  /**
   * The constant INSTANCE.
   */
  private static final Computer INSTANCE = new Computer();

  /**
   * Gets the single instance of Computer.
   *
   * @return single instance of Computer
   */
  public static final Computer getInstance() {
    return INSTANCE;
  }

  /**
   * The os.
   */
  private OperatingSystem os;

  /**
   * Instantiates a new computer.
   */
  private Computer() {
    if (System.getProperty("os.name").startsWith("Windows")) {
      os = OperatingSystem.WINDOWS;
    } else if (System.getProperty("os.name").startsWith("Mac")) {
      os = OperatingSystem.OSX;
    } else {
      os = OperatingSystem.LINUX;
    }
  }

  /**
   * Gets the operating system.
   *
   * @return the operating system
   */
  public OperatingSystem getOperatingSystem() {
    return os;
  }

}
