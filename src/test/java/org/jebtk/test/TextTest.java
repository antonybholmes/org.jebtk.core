/**
toString * Copyright 2016 Antony Holmes
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
package org.jebtk.test;

import static org.junit.Assert.assertEquals;

import org.jebtk.core.text.TextUtils;
import org.junit.Test;

public class TextTest {

  @Test
  public void titleCaseTest() {
    System.err.println(TextUtils.titleCase("title case"));
    assertEquals("Title case", TextUtils.titleCase("title case"), "Title Case");
  }

  @Test
  public void formatTest() {
    System.err.println(TextUtils.format("insert {} {}", "test", 42));
    assertEquals("Format case", TextUtils.format("insert {}", "test"), "insert test");
  }

  @Test
  public void parseTest() {
    System.err.println("blocl " + TextUtils.parseDouble("200 %"));
    assertEquals("parse num", 200, TextUtils.parseDouble("200 %"), 0.0001);
  }
}
