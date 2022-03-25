/**
 * Copyright 2018 Antony Holmes
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
package org.jebtk.core.io;

/**
 * The Class ByteStream wraps a byte array to provide helper functions for
 * reading primitives. This stream functions similarly to ByteArray and other
 * such tools, but offers the ability to read 24 bit ints and does not require
 * 3rd party libraries.
 */
public class ByteStream {

  /** The m D. */
  private byte[] mD;

  /** Pointer into array */
  private int mP = 0;

  /**
   * Instantiates a new byte stream.
   *
   * @param d the d
   */
  public ByteStream(byte[] d) {
    mD = d;
  }

  /**
   * Read short.
   *
   * @return the int
   */
  public int readShort() {
    if (mP > mD.length - 3) {
      return -1;
    }

    return (mD[mP] << 8) | (mD[mP++] & 0xFF);
  }

  /**
   * Read an unsigned 24 bit integer.
   *
   * @return the int
   */
  public int readInt24() {
    if (mP > mD.length - 4) {
      return -1;
    }

    return (mD[mP] << 16) | (mD[mP++] << 8) | (mD[mP++] & 0xFF);
  }

  /**
   * Read a 32bit int.
   *
   * @return the int
   */
  public int readInt32() {
    if (mP > mD.length - 5) {
      return -1;
    }

    return (mD[mP++] << 24) | (mD[mP++] << 16) | (mD[mP++] << 8) | (mD[mP++] & 0xFF);
  }

  /**
   * Read a 32 bit int. Equivalent to {@code readInt32()}.
   *
   * @return the int
   */
  public int readInt() {
    return readInt32();
  }

  /**
   * Read a one byte unsigned int.
   *
   * @return the int
   */
  public int read() {
    if (mP > mD.length - 2) {
      return -1;
    }

    // Promote to int as unsigned byte
    return mD[mP++] & 0xFF;
  }

  public char readChar() {
    return (char) read();
  }
}
