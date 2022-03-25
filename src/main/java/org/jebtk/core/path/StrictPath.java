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

import java.util.List;
import java.util.regex.Pattern;

import org.jebtk.core.text.RegexUtils;
import org.jebtk.core.text.TextUtils;

/**
 * Represents a path such as file path or tree structure.
 * 
 * @author Antony Holmes
 *
 */
public class StrictPath extends Path {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /** The Constant SLASH_REGEX. */
  private static final Pattern SLASH_REGEX = Pattern.compile("[\\/\\\\]");

  /** The Constant BRACKET_REGEX. */
  private static final Pattern BRACKET_REGEX = Pattern.compile("[\\[\\]\\(\\)\\{\\}]");

  /** The Constant ILLEGAL_REGEX. */
  private static final Pattern ILLEGAL_REGEX = Pattern.compile("[^a-zA-Z0-9\\-_\\.]");

  /**
   * Instantiates a new strict path.
   *
   * @param <T>  the generic type
   * @param path the path
   */
  public <T> StrictPath(String path) {
    super(path);
  }

  /**
   * Instantiates a new strict path.
   *
   * @param path the path
   */
  public StrictPath(Path path) {
    super(path);
  }

  /**
   * Instantiates a new strict path.
   *
   * @param path   the path
   * @param levels the levels
   */
  public StrictPath(Path path, Object... levels) {
    super(path, levels);
  }

  /**
   * Instantiates a new strict path.
   *
   * @param levels the levels
   */
  public StrictPath(List<?> levels) {
    super(levels);
  }

  /**
   * Instantiates a new strict path.
   *
   * @param levels the levels
   */
  public StrictPath(Object level, Object... levels) {
    super(false, level, levels);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.path.Path#clone()
   */
  @Override
  protected Path clone() {
    return new StrictPath(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.path.Path#sanitize(java.lang.String)
   */
  @Override
  protected String sanitize(String level) {
    String s = level; // level.toLowerCase();

    s = SLASH_REGEX.matcher(s).replaceAll(TextUtils.EMPTY_STRING);

    // Brackets become spaces
    s = BRACKET_REGEX.matcher(s).replaceAll(" ");

    // System.err.println("s1 " + s);

    // Convert spaces to underscores
    s = RegexUtils.replaceAll(s, RegexUtils.SPACES_PATTERN, "_");

    // s = RegexUtils.replaceAll(s, RegexUtils.UNDERSCORES_PATTERN, "_");

    // Strip leading and trailing underscores
    s = RegexUtils.replaceAll(s, RegexUtils.LEADING_UNDERSCORES_PATTERN, "");
    s = RegexUtils.replaceAll(s, RegexUtils.TRAILING_UNDERSCORES_PATTERN, "");

    // Remove all other illegal characters
    s = RegexUtils.replaceAll(s, ILLEGAL_REGEX, "");

    // Remove underscore repeats
    // s = s.replaceAll("_+", "_");
    s = RegexUtils.replaceAll(s, RegexUtils.UNDERSCORES_PATTERN, "_");

    return s;
  }
}
