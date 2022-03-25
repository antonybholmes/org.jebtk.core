/**
 * Copyright 2018 Antony Holmes
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
package org.jebtk.core.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;

import org.jebtk.core.text.Join;
import org.jebtk.core.text.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create temporary file.
 * 
 * @author Antony Holmes
 *
 */
public class TmpService {

  private static final int MAX_TRIES = 100;

  private static class TempServiceLoader {

    /** The Constant INSTANCE. */
    private static final TmpService INSTANCE = new TmpService();
  }

  /**
   * Gets the single instance of SettingsService.
   *
   * @return single instance of SettingsService
   */
  public static TmpService getInstance() {
    return TempServiceLoader.INSTANCE;
  }

  private static final SecureRandom RANDOM = new SecureRandom();

  private static final String RND_TMP_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

  /**
   * The constant TEMP_DIRECTORY.
   */
  public static final Path TMP_DIR = PathUtils.getPath("tmp");

  /**
   * The constant LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(TmpService.class);

  private Path mTmpDir;

  private TmpService() {
    mTmpDir = TMP_DIR;
  }

  public Path getTmpDir() {
    return mTmpDir;
  }

  /**
   * Creates a temp directory if it does not exist.
   *
   * @return true, if successful
   */
  public boolean createTmpDir() {
    if (FileUtils.exists(mTmpDir)) {
      return false;
    }

    LOG.info("Creating tmp directory at {}...", mTmpDir);

    try {
      Files.createDirectory(mTmpDir);

      return true;
    } catch (IOException e) {
      e.printStackTrace();

      return false;
    }
  }

  /**
   * Generates a tmp file name with a unique id for this session. Ids are not
   * guaranteed to be unique if the program or VM are restarted.
   *
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Path newTmpFile() throws IOException {
    return newTmpFile("tmp");
  }

  /**
   * Generate a temp file with the given extension.
   *
   * @param ext the ext
   * @return the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Path newTmpFile(String ext) throws IOException {
    return newTmpFile(TextUtils.EMPTY_STRING, ext);
  }

  public Path newTmpFile(String prefix, String ext) throws IOException {
    createTmpDir();

    Path ret = null;

    Join join = Join.onPeriod().ignoreEmptyStrings();

    for (int i = 0; i < MAX_TRIES; ++i) {
      ret = mTmpDir.resolve(join.toString(prefix, newTmpString(), ext));

      if (!FileUtils.exists(ret)) {
        break;
      }
    }

    return ret;
  }

  /**
   * Delete files from the temporary directory.
   */
  public void deleteTempFiles() {
    deleteTempFiles(null);
  }

  /**
   * Delete temp files.
   *
   * @param name the name
   */
  public void deleteTempFiles(String name) {
    if (!FileUtils.exists(mTmpDir)) {
      return;
    }

    LOG.info("Deleting temp files from {}...", mTmpDir);

    try {
      for (Path file : FileUtils.ls(mTmpDir)) {
        if (name != null && !name.equals("") && !PathUtils.getName(file).contains(name)) {
          continue;
        }

        Files.delete(file);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static String newTmpString() {
    return newTmpString(6);
  }

  private static String newTmpString(int l) {
    StringBuilder buffer = new StringBuilder();

    for (int i = 0; i < l; ++i) {
      buffer.append(RND_TMP_LETTERS.charAt(RANDOM.nextInt(RND_TMP_LETTERS.length())));
    }

    return buffer.toString();
  }

}
