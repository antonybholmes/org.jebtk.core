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

import java.util.List;

/**
 * Trimmer removes characters such as spaces from the beginning or end of a
 * string. This is used in conjuction with {@code Splitter} to create a
 * functional way to split strings.
 */
public class Trimmer {
  /**
   * Does not trim the text.
   */
  public static final Trimmer NO_TRIM = new Trimmer();

  /**
   * The Class CharTrimMode.
   */
  public static class CharTrimMode extends Trimmer {

    /** The m delimiter. */
    private final char mDelimiter;

    /**
     * Instantiates a new char trim mode.
     *
     * @param delimiter the delimiter
     */
    public CharTrimMode(char delimiter) {
      mDelimiter = delimiter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Trimmer.TrimMode#trim(java.lang.String)
     */
    @Override
    public String trim(String text) {
      int start = 0;

      for (int i = 0; i < text.length(); ++i) {
        if (text.charAt(i) != mDelimiter) {
          break;
        }

        ++start;
      }

      int end = text.length();

      for (int i = text.length() - 1; i >= 0; --i) {
        if (text.charAt(i) != mDelimiter) {
          break;
        }

        --end;
      }

      return text.substring(start, end);
    }
  }

  /**
   * Trims a string and applies the splitter on the trimmed string.
   *
   * @param splitMode          the split mode
   * @param text               the text
   * @param ignoreEmptyStrings the ignore empty strings
   * @param maxNumItems        the max num items
   * @return the list
   */
  public List<String> trim(SplitMode splitMode, final String text, boolean ignoreEmptyStrings, int maxNumItems) {
    return splitMode.split(trim(text), ignoreEmptyStrings, maxNumItems);
  }

  /**
   * Should trim the next if necessary.
   *
   * @param text the text
   * @return the string
   */
  public String trim(String text) {
    return text;
  }

  /**
   * On.
   *
   * @param delimiter the delimiter
   * @return the trimmer
   */
  public static Trimmer on(char delimiter) {
    return new CharTrimMode(delimiter);
  }

  /**
   * On tab.
   *
   * @return the trimmer
   */
  public static Trimmer onTab() {
    return on(TextUtils.TAB_DELIMITER_CHAR);
  }

  /**
   * On space.
   *
   * @return the trimmer
   */
  public static Trimmer onSpace() {
    return on(TextUtils.SPACE_DELIMITER_CHAR);
  }
}
