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

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Collection;
import java.util.List;

/**
 * The Class BufferUtils.
 */
public class BufferUtils {

  /**
   * Instantiates a new buffer utils.
   */
  private BufferUtils() {
    // Do nothing
  }

  /**
   * The Class ByteBufferWrapper.
   */
  public static class ByteBufferWrapper {

    /**
     * Wrap.
     *
     * @param array the array
     * @return the buffer wrapper
     */
    public BufferWrapper wrap(final byte[] array) {
      return new BufferWrapper(array);
    }

    /**
     * Buffer a collection of integers into a byte array buffer using 4 bytes per
     * integer.
     *
     * @param values the values
     * @return the byte buffer
     */
    public ByteBuffer allocate(final Collection<Integer> values) {
      ByteBuffer buffer = ByteBuffer.allocate(values.size() * 4);

      for (int count : values) {
        buffer.putInt(count);
      }

      return buffer;
    }

    /**
     * Allocate.
     *
     * @param values the values
     * @return the byte buffer
     */
    public ByteBuffer allocate(List<Character> values) {
      ByteBuffer buffer = ByteBuffer.allocate(values.size());

      for (char strand : values) {
        buffer.put((byte) strand);
      }

      return buffer;
    }
  }

  /**
   * The Class BufferWrapper.
   */
  public static class BufferWrapper {

    /** The m array. */
    private byte[] mArray;

    /**
     * Instantiates a new buffer wrapper.
     *
     * @param array the array
     */
    public BufferWrapper(byte[] array) {
      mArray = array;
    }

    /**
     * Interprets a byte array as integers (4 bytes per int) and creates a list of
     * integers.
     *
     * @return the list
     */
    public int[] getInts() {
      IntBuffer buffer = ByteBuffer.wrap(mArray).asIntBuffer();

      // List<Integer> ret = new ArrayList<Integer>(buffer.remaining());

      int[] ret = new int[mArray.length / 4];

      int i = 0;

      while (buffer.hasRemaining()) {
        ret[i++] = buffer.get();
      }

      return ret;
    }

    /**
     * Converts bytes to chars (ASCII).
     *
     * @return the list
     */
    public char[] byteChars() {
      ByteBuffer buffer = ByteBuffer.wrap(mArray);

      char[] ret = new char[mArray.length];

      int i = 0;

      while (buffer.hasRemaining()) {
        ret[i++] = (char) buffer.get();
      }

      return ret;
    }
  }

  /**
   * Byte buffer.
   *
   * @return the byte buffer wrapper
   */
  public static ByteBufferWrapper byteBuffer() {
    return new ByteBufferWrapper();
  }
}
