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

import java.io.IOException;
import java.nio.file.Path;

import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;

/**
 * Provides global settings for an application such as the default directory to
 * save user files in. This is to allow settings etc to persist even if the
 * application changes.
 */
public class AppService implements NameGetter {

  /**
   * The Class AppServiceLoader.
   */
  private static class AppServiceLoader {

    /** The Constant INSTANCE. */
    private static final AppService INSTANCE = new AppService();
  }

  /**
   * Gets the single instance of SettingsService.
   *
   * @return single instance of SettingsService
   */
  public static AppService getInstance() {
    return AppServiceLoader.INSTANCE;
  }

  public static final Path APP_HOME = FileUtils.HOME.resolve("app_home");

  public static final Path RES_HOME = APP_HOME.resolve("res");

  /** Returns the shared module directory */
  public static final Path MOD_HOME = RES_HOME.resolve("modules");

  public static final Path RES_DIR = PathUtils.getPath("res");

  /**
   * Returns the module directory for an app. This is specific to the app's
   * location
   */
  public static final Path MOD_DIR = RES_DIR.resolve("modules");

  /** The m directory. */
  private Path mDirectory;

  /** The m name. */
  private String mName;

  private Path mInstModDir;

  private Path mModDir;

  /**
   * Instantiates a new app service.
   */
  private AppService() {
    setAppInfo("default");
  }

  /**
   * Set the application name and create a home directory.
   *
   * @param name the new app info
   */
  public void setAppInfo(String name) {
    mName = name.toLowerCase();

    mDirectory = create(name);

    try {
      // Make the directory if it does not exist.
      FileUtils.mkdir(mDirectory);
    } catch (IOException e) {
      e.printStackTrace();
    }

    mModDir = MOD_HOME.resolve(mName);

    mInstModDir = MOD_DIR.resolve(mName);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.NameProperty#getName()
   */
  @Override
  public String getName() {
    return mName;
  }

  /**
   * Returns the application directory for storing settings etc.
   *
   * @return the app dir
   */
  public Path getAppDir() {
    return mDirectory;
  }

  public Path getModDir() {
    return mModDir;
  }

  public Path getInstanceModDir() {
    return mInstModDir;
  }

  /**
   * Creates a file name in the application directory by prefixing .
   *
   * @param name the name
   * @return the file
   * @{code appname.name} onto the application directory
   */
  public Path getFile(String name) {
    return getAppDir().resolve(name);
  }

  public Path getFile(Path file) {
    return getAppDir().resolve(file);
  }

  /**
   * Creates the the application directory path.
   *
   * @param name the name
   * @return the path
   */
  public static Path create(String name) {
    return APP_HOME.resolve(name.toLowerCase());
  }
}
