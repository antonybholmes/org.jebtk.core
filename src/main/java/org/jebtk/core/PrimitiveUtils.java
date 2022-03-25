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
package org.jebtk.core;

/**
 * The Class PrimitiveUtils.
 */
public class PrimitiveUtils {

  /** The Constant LONG_SIZE_BYTES. */
  private static final int LONG_SIZE_BYTES = Long.SIZE / Byte.SIZE;

  /** The Constant LONG_SIZE_BYTES_LAST_INDEX. */
  private static final int LONG_SIZE_BYTES_LAST_INDEX = LONG_SIZE_BYTES - 1;

  /**
   * Instantiates a new primitive utils.
   */
  private PrimitiveUtils() {
    // Do nothing
  }

  /**
   * Convert a long to a byte array.
   *
   * @param l the l
   * @return the byte[]
   */
  public static byte[] toByteArray(long l) {
    byte[] result = new byte[LONG_SIZE_BYTES];

    for (int i = LONG_SIZE_BYTES_LAST_INDEX; i >= 0; --i) {
      result[i] = (byte) (l & 0xFF);
      l >>= Byte.SIZE;
    }

    return result;
  }

  /**
   * Converts a byte array to a long value.
   *
   * @param b the b
   * @return the long
   */
  public static long toLong(final byte[] b) {
    long result = 0;

    for (int i = 0; i < LONG_SIZE_BYTES; ++i) {
      result <<= Byte.SIZE;
      result |= (b[i] & 0xFF);
    }

    return result;
  }

  public static byte[] toByteArray(int value) {
    return new byte[] { (byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value };
  }

  public static int toInt(byte[] b) {
    return b[0] << 24 | b[1] << 16 | b[2] << 8 | b[3];
  }

  public static int toInt(byte b1, byte b2, byte b3, byte b4) {
    return b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | (b4 & 0xFF);
  }

  public static byte[] toByteArray(short value) {
    return new byte[] { (byte) (value >> 8), (byte) value };
  }

  public static short toShort(byte[] b) {
    return (short) (b[2] << 8 | b[3]);
  }
}
