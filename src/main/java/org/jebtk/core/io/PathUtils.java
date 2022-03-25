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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jebtk.core.text.TextUtils;

/**
 * The Class PathUtils.
 */
public class PathUtils {

  /**
   * Instantiates a new path utils.
   */
  private PathUtils() {
    // Do nothing
  }

  /**
   * Convert.
   *
   * @param files the files
   * @return the list
   */
  public static List<Path> toList(File[] files) {
    List<Path> ret = new ArrayList<Path>(files.length);

    for (File file : files) {
      ret.add(file.toPath());
    }

    return ret;
  }

  /**
   * Gets the file ext.
   *
   * @param file the file
   * @return the file ext
   */
  public static String getFileExt(final Path file) {
    return Io.getFileExt(getName(file));
  }

  /**
   * Returns the portion of the path name after the first period is encountered.
   *
   * @param file the file
   * @return the file ext long
   */
  public static String getFileExtLong(final Path file) {
    return Io.getFileExtLong(getName(file));
  }

  /**
   * Returns the absolute path of a file as a string.
   *
   * @param file the file
   * @return the string
   */
  public static String toString(final Path file) {
    if (file != null) {
      return file.toAbsolutePath().toString();
    } else {
      return TextUtils.EMPTY_STRING;
    }
  }

  public static List<String> toString(final Collection<Path> files) {
    List<String> ret = new ArrayList<String>(files.size());

    for (Path f : files) {
      ret.add(toString(f));
    }

    return ret;
  }

  /**
   * Return just the names of a collection of files (without the full path).
   * 
   * @param files
   * @return
   */
  public static List<String> names(final Collection<Path> files) {
    List<String> ret = new ArrayList<String>(files.size());

    for (Path f : files) {
      ret.add(getName(f));
    }

    return ret;
  }

  /**
   * Gets the name.
   *
   * @param file the file
   * @return the name
   */
  public static String getName(Path file) {
    file = file.getFileName();

    if (file != null) {
      return file.toString();
    } else {
      return TextUtils.EMPTY_STRING;
    }
  }

  /**
   * Gets the name of a file excluding the parent path and the file extension (the
   * last period up until the end of the file name).
   *
   * @param file the file
   * @return the name no ext
   */
  public static String getNameNoExt(final Path file) {
    String name = getName(file);

    int i = name.lastIndexOf(".");

    if (i != -1) {
      return name.substring(0, i);
    } else {
      return name;
    }
  }

  /**
   * Returns the file name up until the first period (.) is encountered.
   *
   * @param file the file
   * @return the string
   */
  public static String namePrefix(final Path file) {
    return namePrefix(file, ".");
  }

  /**
   * Returns the name of the file removing everything after a break.
   *
   * @param file the file
   * @param s    the s
   * @return the string
   */
  public static String namePrefix(final Path file, String s) {
    String name = getName(file);

    int i = name.indexOf(s);

    if (i != -1) {
      return name.substring(0, i);
    } else {
      return name;
    }
  }

  /**
   * Adds a file extension to a file name. This method will check to ensure it
   * does not create duplicate endings such as .txt.txt, but it will allow
   * .csv.txt for example.
   *
   * @param file      the file
   * @param extension the extension
   * @return the file
   */
  public static final Path addExtension(final Path file, final String extension) {
    String s = toString(file);

    if (!s.toLowerCase().endsWith("." + extension)) {
      s += "." + extension;
    }

    return getPath(s);
  }

  /**
   * Gets the path.
   *
   * @param first the first
   * @param rest  the rest
   * @return the path
   */
  public static Path getPath(String first, String... rest) {
    if (first != null) {
      return Paths.get(first, rest);
    } else {
      return null;
    }
  }

  /**
   * Gets the path.
   *
   * @param first the first
   * @return the path
   */
  public static Path getPath(String first) {
    if (first != null) {
      return Paths.get(first);
    } else {
      return null;
    }
  }

  /**
   * Gets the pwd.
   *
   * @return the pwd
   */
  public static Path getPwd() {
    return Paths.get(".").toAbsolutePath().normalize();
  }

  //
  // Functional
  //

  /**
   * The Class Ext.
   */
  public static class Ext {

    /**
     * Txt.
     *
     * @return the ext test
     */
    public ExtTest txt() {
      return type("txt");
    }

    /**
     * Csv.
     *
     * @return the ext test
     */
    public ExtTest csv() {
      return type("csv");
    }

    /**
     * Gz.
     *
     * @return the ext test
     */
    public ExtTest gz() {
      return type("gz");
    }

    /**
     * Json.
     *
     * @return the ext test
     */
    public ExtTest json() {
      return type("json");
    }

    /**
     * Xml.
     *
     * @return the ext test
     */
    public ExtTest xml() {
      return type("xml");
    }

    /**
     * Type.
     *
     * @param ext the ext
     * @return the ext test
     */
    public ExtTest type(String ext) {
      return new ExtTest(ext);
    }
  }

  /**
   * Ext.
   *
   * @return the ext
   */
  public static Ext ext() {
    return new Ext();
  }

  /**
   * Ensures a path is in relative form by stripping leading slashes.
   *
   * @param path the path
   * @return the path
   */
  public static Path relative(Path path) {
    return relative(toString(path));
  }

  /**
   * Ensures a path is in relative form by stripping leading slashes.
   *
   * @param path the path
   * @return the path
   */
  public static Path relative(String path) {
    if (path != null) {
      return getPath(path.replaceFirst("^\\/", TextUtils.EMPTY_STRING));
    } else {
      return null;
    }
  }

  /**
   * Returns the directory of a file.
   * 
   * @param file
   * @return
   */
  public static Path getDir(Path file) {
    return file.toAbsolutePath().getParent();
  }

}
