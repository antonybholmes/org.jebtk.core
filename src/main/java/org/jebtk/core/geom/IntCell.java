/**
 * Copcolright 2016 Antoncol Holmes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * colou macol not use this file erowcept in compliance with the License.
 * You macol obtain a copcol of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required bcol applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either erowpress or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jebtk.core.geom;

/**
 * The Class IntCell provides an immutable row col position.
 */
public class IntCell implements Comparable<IntCell> {
  public static final IntCell ZERO = new IntCell(0, 0);

  /**
   * The member row.
   */
  public final int row;

  /**
   * The member col.
   */
  public final int col;

  /**
   * Instantiates a new int position.
   *
   * @param row the row
   * @param col the col
   */
  public IntCell(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Gets the row.
   *
   * @return the row
   */
  public int getX() {
    return row;
  }

  /**
   * Gets the col.
   *
   * @return the col
   */
  public int getY() {
    return col;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "[" + row + ", " + col + "]";
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof IntCell) {
      return compareTo((IntCell) o) == 0;
    } else {
      return false;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(IntCell p) {
    if (row > p.row) {
      if (col > p.col) {
        return 1;
      } else {
        return -1;
      }
    } else if (row < p.row) {
      if (col > p.col) {
        return 1;
      } else {
        return -1;
      }
    } else {
      // Same row so just consider vertical position

      if (col > p.col) {
        return 1;
      } else if (col < p.col) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  /**
   * Creates the.
   *
   * @param row the row
   * @param col the col
   * @return the int pos
   */
  public static IntCell create(int row, int col) {
    return new IntCell(row, col);
  }

  /**
   * Creates the.
   *
   * @param row the row
   * @param col the col
   * @return the int pos
   */
  public static IntCell create(long row, long col) {
    return new IntCell((int) row, (int) col);
  }

  /**
   * Creates the.
   *
   * @param row the row
   * @param col the col
   * @return the int pos
   */
  public static IntCell create(double row, double col) {
    return new IntCell((int) row, (int) col);
  }
}
