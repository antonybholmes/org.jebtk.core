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
package org.jebtk.core.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.core.text.TextUtils;

/**
 * The Class DirFile.
 */
public class DirFile extends File {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new dir file.
   *
   * @param path the path
   */
  private DirFile(String path) {
    super(path);
  }

  /**
   * Instantiates a new dir file.
   *
   * @param path the path
   */
  private DirFile(File path) {
    this(path.getAbsolutePath());
  }

  /**
   * Creates the.
   *
   * @param more the more
   * @return the dir file
   * @throws FileIsNotADirException the file is not A dir exception
   */
  public static DirFile create(String... more) throws FileIsNotADirException {
    return create(new File(TextUtils.join(File.separator, more)));
  }

  /**
   * Creates the.
   *
   * @param path the path
   * @return the dir file
   * @throws FileIsNotADirException the file is not A dir exception
   */
  public static DirFile create(File path) throws FileIsNotADirException {
    if (path.isDirectory()) {
      return new DirFile(path);
    } else {
      throw new FileIsNotADirException(path);
    }
  }

  /**
   * Convert.
   *
   * @param files the files
   * @return the list
   * @throws FileIsNotADirException the file is not A dir exception
   */
  public static List<DirFile> convert(File[] files) throws FileIsNotADirException {
    List<DirFile> ret = new ArrayList<DirFile>(files.length);

    for (File file : files) {
      ret.add(create(file));
    }

    return ret;
  }
}
