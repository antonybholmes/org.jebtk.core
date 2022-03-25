/**
 * Copyright 2017 Antony Holmes
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
 * The Interface SplitMode.
 */
public interface SplitMode {

  /**
   * Split.
   *
   * @param text               the text
   * @param ignoreEmptyStrings the ignore empty strings
   * @param maxNumItems        the maxNumItems
   * @return the list
   */
  public List<String> split(final String text, boolean ignoreEmptyStrings, int maxNumItems);
}