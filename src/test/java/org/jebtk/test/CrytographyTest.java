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

import org.jebtk.core.cryptography.Cryptography;
import org.junit.Assert;
import org.junit.Test;

public class CrytographyTest {
  @Test
  public void hexStringsShouldBeEqual() {
    byte[] buf = new byte[64];

    Cryptography.hashSHA512("test", buf);

    String h1 = Cryptography.hexToString(buf);
    String h2 = Cryptography.hexToString2(buf);

    Assert.assertEquals("Hex strings should be equal", h1, h2);
  }

  @Test
  public void testUUID() {
    System.err.println(Cryptography.generateUUID());
  }
}
