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

import java.util.Comparator;

/**
 * The class NaturalComparator performs a natural sort on strings so that
 * strings beginning with numbers or two strings with the same prefix and then a
 * number are sorted by the number and
 *
 * @param <T> the generic type
 */
public class NaturalComparator<T> implements Comparator<T> {

  /*
   * (non-Javadoc)
   * 
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  @Override
  public int compare(T o1, T o2) {
    if ((o1 == null) || (o2 == null)) {
      return 0;
    }

    char[] s1 = o1.toString().toCharArray();
    char[] s2 = o2.toString().toCharArray();

    int marker1 = 0;
    int marker2 = 0;
    int sl1 = s1.length;
    int sl2 = s2.length;

    char[] chunk1 = new char[sl1];
    char[] chunk2 = new char[sl2];

    int cl1;
    int cl2;

    while (marker1 < sl1 && marker2 < sl2) {
      cl1 = getChunk(s1, sl1, marker1, chunk1);
      marker1 += cl1;

      cl2 = getChunk(s2, sl2, marker2, chunk2);
      marker2 += cl2;

      // If both chunks contain numeric characters, sort them numerically
      int result = 0;

      if (isDigit(chunk1[0]) && isDigit(chunk2[0])) {
        // Simple chunk comparison by length.
        result = cl1 - cl2;

        // If equal, the first different number counts
        if (result == 0) {
          for (int i = 0; i < cl1; i++) {
            result = chunk1[i] - chunk2[i];

            if (result != 0) {
              return result;
            }
          }
        }
      } else {
        result = compareTo(chunk1, cl1, chunk2, cl2);
      }

      if (result != 0) {
        return result;
      }
    }

    return sl1 - sl2;
  }

  /**
   * Length of string is passed in for improved efficiency (only need to calculate
   * it once) .
   *
   * @param s       the char string
   * @param slength the slength
   * @param marker  the marker
   * @param buffer  buffer
   * @return *
   */
  private static final int getChunk(char[] s, int slength, int marker, char[] buffer) {

    // buffer counter
    int cp = 0;

    char c = s[marker++];
    cp = filter(c, cp, buffer);

    // marker++;

    if (isDigit(c)) {
      // Create the longest run of digits
      while (marker < slength) {
        c = s[marker++];

        if (!isDigit(c)) {
          break;
        }

        cp = filter(c, cp, buffer);
      }
    } else {
      // Create the longest string not including digits
      while (marker < slength) {
        c = s[marker++];

        if (isDigit(c)) {
          break;
        }

        cp = filter(c, cp, buffer);
      }
    }

    // Return buffer length
    return cp;
  }

  /**
   * Compare two char arrays based on String.compareTo().
   *
   * @param s1 char array 1.
   * @param l1 char array 1 length.
   * @param s2 char array 2.
   * @param l2 char array 2 length.
   * @return the int
   */
  private static final int compareTo(char[] s1, int l1, char[] s2, int l2) {
    int n = Math.min(l1, l2);

    for (int i = 0; i < n; ++i) {
      char c1 = s1[i];
      char c2 = s2[i];

      if (c1 != c2) {
        return c1 - c2;
      }
    }

    return l1 - l2;
  }

  /**
   * Checks if is digit.
   *
   * @param ch the ch
   * @return true, if is digit
   */
  private static final boolean isDigit(char ch) {
    return (ch >= 48) && (ch <= 57);
  }

  /**
   * Filter any characters we do not want to include.
   * 
   * @param c      The character to append.
   * @param cp     Index in buffer to add next character at.
   * @param buffer Buffer to append characters to.
   * @return
   */
  private static final int filter(char c, int cp, char[] buffer) {
    // skip spaces
    if (c != ' ') {
      buffer[cp++] = c;
    }

    return cp;
  }
}
