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
 * Provides a unique id to a class.
 * 
 * @author Antony Holmes
 *
 */
public class FormattedTxtObject implements FormattedTxt {

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return formattedTxt();
  }

  /**
   * Formatted txt.
   *
   * @return the string
   */
  public String formattedTxt() {
    StringBuilder buffer = new StringBuilder();

    formattedTxt(buffer);

    return buffer.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.FormattedTxt#formattedTxt(java.lang.Appendable)
   */
  @Override
  public void formattedTxt(Appendable buffer) {
    // TODO Auto-generated method stub

  }
}
