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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The class TableData.
 *
 * @param <T> the generic type
 */
public class TableData<T> implements Iterable<List<T>> {

  /**
   * The column headings.
   */
  public List<String> columnHeadings = null;

  /**
   * The row header.
   */
  public List<String> rowHeader = null;

  /**
   * The data.
   */
  private final List<List<T>> data = new ArrayList<>();

  /**
   * Removes the row.
   *
   * @param row the row
   */
  public final void removeRow(int row) {
    data.remove(row);

    if (rowHeader != null) {
      rowHeader.remove(row);
    }
  }

  /**
   * Removes the column.
   *
   * @param column the column
   */
  public final void removeColumn(int column) {
    for (List<T> row : data) {
      row.remove(column);
    }

    if (columnHeadings != null) {
      columnHeadings.remove(column);
    }
  }

  /**
   * Sets the column headings.
   *
   * @param headings the new column headings
   */
  public final void setColumnHeadings(List<String> headings) {
    this.columnHeadings = headings;
  }

  /**
   * Adds the column.
   *
   * @param name   the name
   * @param column the column
   */
  public final void addColumn(String name, List<T> column) {
    if (data.size() != column.size()) {
      System.out.println("wrong length:" + data.size() + " " + column.size());
      return;
    }

    if (name != null && columnHeadings != null) {
      columnHeadings.add(name);
    }

    for (int i = 0; i < data.size(); ++i) {
      data.get(i).add(column.get(i));
    }
  }

  /**
   * Adds the row.
   *
   * @param name the name
   * @param row  the row
   */
  public final void addRow(String name, List<T> row) {
    if (data.get(0).size() != row.size()) {
      return;
    }

    if (name != null && rowHeader != null) {
      rowHeader.add(name);
    }

    data.add(row);
  }

  /**
   * Adds the row.
   *
   * @param row the row
   */
  public final void addRow(List<T> row) {
    if (columnHeadings.size() != row.size()) {
      return;
    }

    data.add(row);
  }

  /**
   * Gets the column.
   *
   * @param column the column
   * @return the column
   */
  public final List<T> getColumn(int column) {
    List<T> columnValues = new ArrayList<T>();

    for (List<T> row : data) {
      columnValues.add(row.get(column));
    }

    return columnValues;
  }

  /**
   * Gets the column as double.
   *
   * @param column the column
   * @return the column as double
   */
  public final List<Double> getColumnAsDouble(int column) {
    List<Double> columnValues = new ArrayList<Double>();

    for (List<T> row : data) {
      /*
       * try { columnValues.add(Double.parseDouble(row.get(column).toString())); }
       * catch (NumberFormatException e) { columnValues.add((double) -1); }
       */

      columnValues.add(Double.parseDouble(row.get(column).toString()));
    }

    return columnValues;
  }

  /**
   * Gets the column as int.
   *
   * @param column the column
   * @return the column as int
   */
  public final List<Integer> getColumnAsInt(int column) {
    List<Integer> columnValues = new ArrayList<Integer>();

    for (List<T> row : data) {
      /*
       * try { columnValues.add(Integer.parseInt(row.get(column).toString())); } catch
       * (NumberFormatException e) { columnValues.add(-1); }
       */

      columnValues.add(Integer.parseInt(row.get(column).toString()));
    }

    return columnValues;
  }

  /**
   * Clear.
   */
  public final void clear() {
    data.clear();
  }

  /**
   * Row count.
   *
   * @return the int
   */
  public final int rowCount() {
    return size();
  }

  /**
   * Size.
   *
   * @return the int
   */
  public final int size() {
    return data.size();
  }

  /**
   * Gets the column headings.
   *
   * @return the column headings
   */
  public final List<String> getColumnHeadings() {
    return columnHeadings;
  }

  /**
   * Gets the.
   *
   * @param row the row
   * @param col the col
   * @return the t
   */
  public final T get(int row, int col) {
    return data.get(row).get(col);
  }

  /**
   * Sets the.
   *
   * @param row    the row
   * @param column the column
   * @param value  the value
   */
  public void set(int row, int column, T value) {
    data.get(row).set(column, value);
  }

  /**
   * Table to map.
   *
   * @param <T>       the generic type
   * @param table     the table
   * @param keyColumn the key column
   * @return the map
   */
  public static final <T> Map<T, List<T>> tableToMap(TableData<T> table, int keyColumn) {
    Map<T, List<T>> map = new HashMap<T, List<T>>();

    for (List<T> row : table.data) {
      if (!map.containsKey(row.get(keyColumn))) {
        map.put(row.get(keyColumn), row);
      }
    }

    return map;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  public Iterator<List<T>> iterator() {
    return data.iterator();
  }

  /**
   * Gets the row.
   *
   * @param i the i
   * @return the row
   */
  public List<T> getRow(int i) {
    return data.get(i);
  }

}
