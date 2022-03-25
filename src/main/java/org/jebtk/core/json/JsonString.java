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
package org.jebtk.core.json;

import java.io.IOException;

import org.jebtk.core.text.TextUtils;

/**
 * The Class JsonString.
 *
 * @author Antony Holmes
 */
public class JsonString extends JsonRaw {
  // private static final Pattern REGEX_DOUBLE_SLASH =
  // Pattern.compile("\\\\");

  /** The Constant DOUBLE_SLASH_REP. */
  private static final String DOUBLE_SLASH_REP = "\\\\";

  // private static final Pattern REGEX_QUOTE =
  // Pattern.compile("\"");

  /** The Constant QUOTE_REP. */
  private static final String QUOTE_REP = "\\\"";

  // private static final Pattern REGEX_FORWARD =
  // Pattern.compile("/");

  /** The Constant FORWARD_REP. */
  private static final String FORWARD_REP = "\\/";

  // private static final Pattern REGEX_BACKSPACE =
  // Pattern.compile("[\b]");

  /** The Constant BACKSPACE_REP. */
  private static final String BACKSPACE_REP = "\\\\b";

  // private static final Pattern REGEX_TAB =
  // Pattern.compile("\\t");

  /** The Constant TAB_REP. */
  private static final String TAB_REP = "\\t";

  // private static final Pattern REGEX_NEWLINE =
  // Pattern.compile("\\n");

  /** The Constant NEWLINE_REP. */
  private static final String NEWLINE_REP = "\\n";

  // private static final Pattern REGEX_RETURN =
  // Pattern.compile("\\r");

  /** The Constant RETURN_REP. */
  private static final String RETURN_REP = "\\r";

  /**
   * Instantiates a new json string.
   *
   * @param value the value
   */
  public JsonString(String value) {
    super(value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.json.JsonValue#formattedTxt(java.lang.StringBuilder)
   */
  @Override
  public void toJson(Appendable buffer) throws IOException {
    buffer.append(TextUtils.quote(escape(mValue)));
  }

  /**
   * Escape characters to make the string JSON compliant.
   *
   * @param value the value
   * @return the string
   */
  public static String escape(String value) {
    /*
     * value = REGEX_DOUBLE_SLASH.matcher(value).replaceAll(DOUBLE_SLASH_REP); value
     * = REGEX_QUOTE.matcher(value).replaceAll(QUOTE_REP); value =
     * REGEX_FORWARD.matcher(value).replaceAll(FORWARD_REP); value =
     * REGEX_BACKSPACE.matcher(value).replaceAll(BACKSPACE_REP); value =
     * REGEX_TAB.matcher(value).replaceAll(TAB_REP); value =
     * REGEX_NEWLINE.matcher(value).replaceAll(NEWLINE_REP); value =
     * REGEX_RETURN.matcher(value).replaceAll(RETURN_REP);
     * 
     * return value;
     */

    return value.replace("\\", DOUBLE_SLASH_REP).replace("\"", QUOTE_REP).replace("/", FORWARD_REP)
        .replace("\b", BACKSPACE_REP).replace("\t", TAB_REP).replace("\n", NEWLINE_REP).replace("\r", RETURN_REP);

    /*
     * return value .replaceAll("\\\\", "\\\\\\\\") .replaceAll("\"", "\\\\\"")
     * .replaceAll("/", "\\\\/") .replaceAll("[\b]", "\\\\b") .replaceAll("\\n",
     * "\\\\n") .replaceAll("\\r", "\\\\r") .replaceAll("\\t", "\\\\t");
     */
  }
}