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

/**
 * The class SentenceCase.
 */
public class SentenceCase {

  /**
   * The constant FONT_CASES.
   */
  public static final String[] FONT_CASES = { "Sentence case.", "UPPERCASE", "lowercase", "Capitalize Each Word" };

  /**
   * The constant types.
   */
  public static final SentenceCaseType[] types = { SentenceCaseType.SENTENCE_CASE, SentenceCaseType.UPPERCASE,
      SentenceCaseType.LOWERCASE, SentenceCaseType.CAPITALIZE_EACH_WORD };

  /**
   * Instantiates a new sentence case.
   */
  private SentenceCase() {
  }

  /**
   * Converts text to a different case.
   *
   * @param text the text
   * @param type the type
   * @return the string
   */
  public static String convert(String text, SentenceCaseType type) {
    switch (type) {
    case SENTENCE_CASE:
      return text.substring(0, 0).toUpperCase() + text.substring(1, text.length() - 1);
    case UPPERCASE:
      return text.toUpperCase();
    case LOWERCASE:
      return text.toLowerCase();
    case CAPITALIZE_EACH_WORD:
      return text;
    default:
      // do nothing
      break;
    }

    return text;
  }
}
