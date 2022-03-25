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
package org.jebtk.core.path;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.text.Join;
import org.jebtk.core.text.Splitter;
import org.jebtk.core.text.TextUtils;

/**
 * Represents a path such as file path or tree structure.
 * 
 * @author Antony Holmes
 *
 */
public class Path implements Iterable<String>, Serializable, Comparable<Path> {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /** The Constant PATH_DELIMITER. */
  public static final String PATH_DELIMITER = "/";

  // private static final Pattern PATH_DELIM_REGEX =
  // Pattern.compile("[\\.\\/\\\\]");

  /** The Constant SPLITTER. */
  private static final Splitter SPLITTER = Splitter.on('.', '/').ignoreEmptyStrings();

  // private static final String START_PATH_DELIMITER = "^\\" + PATH_DELIMITER;
  // private static final String END_PATH_DELIMITER = "\\" + PATH_DELIMITER +
  // "$";

  /**
   * The member levels.
   */
  // private StringBuilder mPath = new StringBuilder();
  private final List<String> mLevels = new ArrayList<>();

  /**
   * The member path.
   */
  private String mPath = null;

  /** The m prefix. */
  private String mPrefix = TextUtils.EMPTY_STRING;

  /**
   * Instantiates a new path.
   *
   * @param <T>  the generic type
   * @param path the path
   */
  public <T> Path(String path) {
    this(path, false);
  }

  /**
   * Instantiates a new path.
   *
   * @param <T>    the generic type
   * @param path   the path
   * @param isRoot the is root
   */
  public <T> Path(String path, boolean isRoot) {
    if (isRoot || path.startsWith(PATH_DELIMITER)) {
      mPrefix = PATH_DELIMITER;
    }

    parse(path);
  }

  /**
   * Create a clone of a path.
   *
   * @param path the path
   */
  public Path(Path path) {
    mPrefix = path.mPrefix;

    for (String level : path) {
      parse(level);
    }
  }

  /**
   * Create a clone of a path and add some new levels to it.
   *
   * @param path   the path
   * @param levels the levels
   */
  public Path(Path path, Object... levels) {
    this(path);

    for (Object o : levels) {
      parse(o.toString());
    }
  }

  /**
   * Instantiates a new path.
   *
   * @param levels the levels
   */
  public Path(List<?> levels) {
    for (Object s : levels) {
      parse(s);
    }
  }

  public Path(boolean isRoot) {
    if (isRoot) {
      mPrefix = PATH_DELIMITER;
    }
  }

  /**
   * Instantiates a new path.
   *
   * @param isRoot the is root
   * @param level  the level
   * @param levels the levels
   */
  public Path(boolean isRoot, Object level, Object... levels) {
    this(isRoot);

    parse(level);

    for (Object l : levels) {
      parse(l);
    }
  }

  /**
   * Return a copy of the path with the new path appended to it.
   *
   * @param path the path
   * @return the path
   */
  public Path append(Path path) {
    Path ret = clone();

    for (String level : path) {
      ret.parse(level);
    }

    return path;
  }

  /**
   * Append.
   *
   * @param level the level
   * @return the path
   */
  public Path append(Object level) {
    return append(level.toString());
  }

  /**
   * Append levels to a path. A new path consisting of the current path with the
   * new level added will be returned to preserve path immutability.
   *
   * @param level the level
   * @return the path
   */
  public Path append(String level) {
    Path ret = clone();

    ret.parse(level);

    return ret;
  }

  /**
   * Creates a clone of this path.
   *
   * @return the path
   */
  @Override
  protected Path clone() {
    return new Path(this);
  }

  /**
   * Internal method for updating the path. Since paths are immutable, this is not
   * publicly accessible.
   *
   * @param level the level
   * @return the path
   */
  private void parse(Object level) {
    parse(level.toString());
  }

  /**
   * Internal method for updating the path. Since paths are immutable, this is not
   * publicly accessible.
   *
   * @param level the level
   * @return the path
   */
  private void parse(String level) {
    if (level == null) {
      return;
    }

    List<String> parts = SPLITTER.text(level); // PATH_DELIM_REGEX.split(level);

    for (String s : parts) {
      s = sanitize(s);

      if (s.length() > 0) {
        mLevels.add(s);
      }
    }
  }

  /**
   * Return the parent path of this path.
   *
   * @return the parent
   */
  public Path getParent() {
    if (mLevels.isEmpty()) {
      return null;
    }

    return new Path(CollectionUtils.head(mLevels, mLevels.size() - 1));
  }

  /**
   * Gets the name of the path (the last level excluding any separators).
   *
   * @return the name
   */
  public String getName() {
    return CollectionUtils.end(mLevels);
  }

  /**
   * Returns a string representation of the path.
   *
   * @return the path
   */
  public String getPath() {
    if (mPath == null) {
      mPath = mPrefix + Join.on(PATH_DELIMITER).values(mLevels).toString();
    }

    return mPath;
  }

  /**
   * Gets the path with period sep.
   *
   * @return the path with period sep
   */
  public String getPathWithPeriodSep() {
    return Join.on(TextUtils.PERIOD_DELIMITER).values(mLevels).toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getPath();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<String> iterator() {
    return mLevels.iterator();
  }

  /**
   * Sanitizes string.
   *
   * @param s the s
   * @return the string
   */
  protected String sanitize(String s) {
    return s;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Path p) {
    return toString().compareTo(p.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Path) {
      return compareTo((Path) o) == 0;
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
    return toString().hashCode();
  }

  /**
   * Level.
   *
   * @param i the i
   * @return the path level
   */
  public String level(int i) {
    return mLevels.get(i);
  }

  /**
   * Creates the.
   *
   * @param path the path
   * @return the path
   */
  public static Path create(String path) {
    return new Path(path);
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    Path p = new Path("Characteristics[GEP Based Classification]");
  }

  /**
   * Checks if is valid path.
   *
   * @param path the path
   * @return true, if is valid path
   */
  public static boolean isValidPath(String path) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Creates a path starting from the root ('/').
   *
   * @param level  the level
   * @param levels the levels
   * @return the path
   */
  public static Path createRootPath(Object level, Object... levels) {
    return new RootPath(level, levels);
  }

  /**
   * Creates the root path.
   *
   * @param path the path
   * @return the path
   */
  public static Path createRootPath(String path) {
    return new RootPath(path);
  }
}
