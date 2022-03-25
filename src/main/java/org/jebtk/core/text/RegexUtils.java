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
package org.jebtk.core.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.jebtk.core.collections.CollectionUtils;

/**
 * The Class RegexUtils.
 */
public class RegexUtils {

  /** The Constant SPACES_PATTERN. */
  public static final Pattern SPACES_PATTERN = Pattern.compile("\\s+");

  /** The Constant UNDERSCORES_PATTERN. */
  public static final Pattern UNDERSCORES_PATTERN = Pattern.compile("_+");

  /** The Constant LEADING_UNDERSCORES_PATTERN. */
  public static final Pattern LEADING_UNDERSCORES_PATTERN = Pattern.compile("^_+");

  /** The Constant TRAILING_UNDERSCORES_PATTERN. */
  public static final Pattern TRAILING_UNDERSCORES_PATTERN = Pattern.compile("_+$");

  /**
   * Instantiates a new regex utils.
   */
  private RegexUtils() {
    // Do nothing
  }

  /**
   * Converts a set of string regexes to compiled patterns.
   *
   * @param regexes the regexes
   * @return the list
   */
  public static List<Pattern> compile(List<String> regexes) {
    return compile(regexes, true);
  }

  /**
   * Compile.
   *
   * @param regexes         the regexes
   * @param caseSensitive
   * @return the list
   */
  public static List<Pattern> compile(List<String> regexes, boolean caseSensitive) {
    if (CollectionUtils.isNullOrEmpty(regexes)) {
      return Collections.emptyList();
    }

    List<Pattern> ret = new ArrayList<>(regexes.size());

    for (String regex : regexes) {
      if (caseSensitive) {
        ret.add(Pattern.compile(regex));
      } else {
        ret.add(Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
      }
    }

    return ret;
  }

  /**
   * Compile.
   *
   * @param regex the regex
   * @return the pattern
   */
  public static Pattern compile(String regex) {
    return compile(regex, false);
  }

  /**
   * Convert a string regex into a compiled pattern.
   *
   * @param regex           the regex
   * @param caseInsensitive the case insensitive
   * @return the pattern
   */
  public static Pattern compile(String regex, boolean caseInsensitive) {
    if (caseInsensitive) {
      return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    } else {
      return Pattern.compile(regex);
    }
  }

  /**
   * Literal.
   *
   * @param regex the regex
   * @return the pattern
   */
  public static Pattern literal(String regex) {
    return literal(regex, false);
  }

  /**
   * Creates a pattern from a regex assuming all special characters should be
   * interpreted as literals and not regular expressions marks.
   *
   * @param regex           the regex
   * @param caseInsensitive the case insensitive
   * @return the pattern
   */
  public static Pattern literal(String regex, boolean caseInsensitive) {
    StringBuilder buffer = new StringBuilder(regex);

    TextUtils.replace("(", "\\(", buffer);
    TextUtils.replace(")", "\\)", buffer);
    TextUtils.replace("[", "\\[", buffer);
    TextUtils.replace("]", "\\]", buffer);
    TextUtils.replace("{", "\\{", buffer);
    TextUtils.replace("}", "\\}", buffer);
    TextUtils.replace(".", "\\.", buffer);
    TextUtils.replace("*", "\\*", buffer);
    TextUtils.replace("\\\\", "\\", buffer);

    return compile(buffer.toString(), caseInsensitive);
  }

  /**
   * Replace all occurrences of a pattern in a string with a replacement.
   * 
   * @param s   A string to search for a pattern.
   * @param p   A pattern to match on.
   * @param rep The replacement.
   * @return The string s with characters replaced.
   */
  public static String replaceAll(String s, Pattern p, String rep) {
    return p.matcher(s).replaceAll(rep);
  }

  public static List<String> replaceAll(Collection<String> strings, Pattern p, String rep) {
    List<String> ret = new ArrayList<>(strings.size());

    for (String s : strings) {
      ret.add(replaceAll(s, p, rep));
    }

    return ret;
  }

  public static boolean matches(String p, Collection<String> values) {
    return matches(compile(p), values);
  }

  /**
   * Returns true if all values match the given patten.
   * 
   * @param p
   * @param values
   * @return
   */
  public static boolean matches(Pattern p, Collection<String> values) {

    for (String v : values) {
      if (!p.matcher(v).matches()) {
        return false;
      }
    }

    return true;
  }
}
