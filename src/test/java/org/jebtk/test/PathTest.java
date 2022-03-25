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
package org.jebtk.test;

import org.jebtk.core.path.Path;
import org.junit.Test;

public class PathTest {

  @Test
  public void pathTest() {

    System.out.println("Path test");

    Path path = new Path("a/b/c.d");

    System.err.println(path.toString());
  }

  @Test
  public void rootPathTest() {

    System.out.println("root test");

    Path path = Path.createRootPath("a", "b", "c");

    System.err.println(path.toString());
  }
}
