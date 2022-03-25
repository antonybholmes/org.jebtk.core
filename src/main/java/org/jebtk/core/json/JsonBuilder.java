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
package org.jebtk.core.json;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;

/**
 * The Class JsonBuilder builds json strings in a similar fashion to a general
 * purpose StringBuilder. It assumes the user can correctly form json with the
 * constructs and relies upon users correctly calling {@code startArray()} and
 * {@code endArray()} and {@code startObject()} and {@code endObject()} to
 * indicate where a new JSON element should be inserted. This class is designed
 * for speed and does not offer pretty printing; JSON is written as a single
 * line string.
 */
public class JsonBuilder implements CharSequence {
  /**
   * The Constant JSON_ARRAY_START.
   */
  public static final char JSON_ARRAY_START = '[';

  /**
   * The Constant JSON_ARRAY_END.
   */
  public static final char JSON_ARRAY_END = ']';

  /**
   * The Constant JSON_OBJECT_START.
   */
  public static final char JSON_OBJECT_START = '{';

  /**
   * The Constant JSON_OBJECT_END.
   */
  public static final char JSON_OBJECT_END = '}';

  /** The Constant JSON_QUOTATION. */
  public static final char JSON_QUOTATION = '"';

  /**
   * The Constant EMPTY_OBJECT.
   */
  public static final String EMPTY_OBJECT = "{}";

  /** The Constant JSON_NULL. */
  public static final String JSON_NULL = "null";

  /** The Constant JSON_VALUE_DELIMITER. */
  public static final char JSON_VALUE_DELIMITER = ':';

  /** The Constant JSON_ARRAY_DELIMITER. */
  public static final char JSON_ARRAY_DELIMITER = ',';

  public static final String JSON_EMPTY_ARRAY = "[]";

  /** The Constant BUFFER_SIZE. */
  private static final int BUFFER_SIZE = 100000;

  /** The internal buffer. */
  private StringBuilder mBuffer = null;

  /**
   * Keep track of how many items deep we are in an array or object so that we can
   * decide whether to print a comma or not.
   */
  private int mC = 0;

  /**
   * Instantiates a new json builder with default size.
   */
  public JsonBuilder() {
    this(BUFFER_SIZE);
  }

  /**
   * Instantiates a new json builder with a given buffer size.
   *
   * @param bufferSize the buffer size
   */
  public JsonBuilder(int bufferSize) {
    mBuffer = new StringBuilder(bufferSize);
  }

  /**
   * Start a json array.
   *
   * @return the json builder
   */
  public JsonBuilder startArray() {
    _addComma();

    return _startArray();
  }

  /**
   * Start a JSON array.
   *
   * @return the json builder.
   */
  private JsonBuilder _startArray() {
    mBuffer.append(JSON_ARRAY_START);

    mC = 0;

    return this;
  }

  /**
   * Add a string to an array.
   *
   * @param s the s
   * @return the json builder
   */
  public JsonBuilder add(String s) {
    _addComma();
    quote(JsonString.escape(s));

    return this;
  }

  /**
   * Add the string representation of an object to a JSON array.
   *
   * @param s the s
   * @return the json builder
   */
  public JsonBuilder add(Object s) {
    return add(s.toString());
  }

  /**
   * Add the string representation of a file to the JSON array.
   *
   * @param file the file
   * @return the json builder
   */
  public JsonBuilder add(Path file) {
    return add(PathUtils.toString(file));
  }

  /**
   * Add the string representation of a file to a JSON object.
   *
   * @param name the name
   * @param file the file
   * @return the json builder
   */
  public JsonBuilder add(String name, Path file) {
    return add(name, PathUtils.toString(file));
  }

  /**
   * Insert JSON into builder. The inserted JSON is not verified.
   *
   * @param json the json
   */
  public void insert(String json) {
    _addComma();

    mBuffer.append(json);
  }

  /**
   * Add an integer to the JSON array.
   *
   * @param v the v
   * @return the json builder
   */
  public JsonBuilder add(int v) {
    _addComma();
    mBuffer.append(Integer.toString(v));

    return this;
  }

  /**
   * Add a double to the JSON array.
   *
   * @param v the v
   * @return the json builder
   */
  public JsonBuilder add(double v) {
    _addComma();
    mBuffer.append(Double.toString(v));

    return this;
  }

  /**
   * Add a float to the JSON array.
   *
   * @param v the value.
   * @return the json builder
   */
  public JsonBuilder add(float v) {
    _addComma();

    mBuffer.append(Float.toString(v));

    return this;
  }

  /**
   * Adds the.
   *
   * @param v the value.
   * @return the json builder.
   */
  public JsonBuilder add(long v) {
    _addComma();

    mBuffer.append(Long.toString(v));

    return this;
  }

  /**
   * Adds the.
   *
   * @param v the v
   * @return the json builder
   */
  public JsonBuilder add(boolean v) {
    _addComma();

    mBuffer.append(Boolean.toString(v));

    return this;
  }

  /**
   * Adds the null.
   *
   * @return the json builder
   */
  public JsonBuilder addNull() {
    _addComma();
    mBuffer.append(JSON_NULL);

    return this;
  }

  /**
   * End array.
   *
   * @return the json builder
   */
  public JsonBuilder endArray() {
    mBuffer.append(JSON_ARRAY_END);

    // mInMode = false;

    return this;
  }

  /**
   * Adds the comma.
   */
  private void _addComma() {
    if (mC++ > 0) {
      mBuffer.append(JSON_ARRAY_DELIMITER);
    }
  }

  /**
   * Start object.
   *
   * @return the json builder
   */
  public JsonBuilder startObject() {
    _addComma();

    return _startObject();
  }

  /**
   * Start object.
   *
   * @return the json builder
   */
  private JsonBuilder _startObject() {
    mBuffer.append(JSON_OBJECT_START);

    // mInMode = true;
    mC = 0;

    return this;
  }

  /**
   * Start object.
   *
   * @param name the name
   * @return the json builder
   */
  public JsonBuilder startObject(String name) {
    addComma(name);

    return _startObject();
  }

  /**
   * Start array.
   *
   * @param name the name
   * @return the json builder
   */
  public JsonBuilder startArray(String name) {
    addComma(name);

    return _startArray();
  }

  /**
   * Adds the.
   *
   * @param name the name
   * @param s    the s
   * @return the json builder
   */
  public JsonBuilder add(String name, String s) {
    addComma(name);
    quote(JsonString.escape(s));

    return this;
  }

  /**
   * Insert JSON into builder. The inserted JSON is not verified.
   *
   * @param name the name
   * @param json the json
   */
  public void insert(String name, String json) {
    addComma(name);

    mBuffer.append(json);
  }

  /**
   * Adds the.
   *
   * @param name the name
   * @param v    the v
   * @return the json builder
   */
  public JsonBuilder add(String name, int v) {
    addComma(name);
    mBuffer.append(Integer.toString(v));

    return this;
  }

  /**
   * Adds the.
   *
   * @param name the name
   * @param v    the v
   * @return the json builder
   */
  public JsonBuilder add(String name, double v) {
    addComma(name);
    mBuffer.append(Double.toString(v));

    return this;
  }

  /**
   * Adds the.
   *
   * @param name the name
   * @param v    the v
   * @return the json builder
   */
  public JsonBuilder add(String name, float v) {
    addComma(name);

    mBuffer.append(Float.toString(v));

    return this;
  }

  /**
   * Add a named long value.
   *
   * @param name the name
   * @param v    the v
   * @return the json builder
   */
  public JsonBuilder add(String name, long v) {
    addComma(name);

    mBuffer.append(Long.toString(v));

    return this;
  }

  /**
   * Adds the.
   *
   * @param name the name
   * @param v    the v
   * @return the json builder
   */
  public JsonBuilder add(String name, boolean v) {
    addComma(name);

    mBuffer.append(Boolean.toString(v));

    return this;
  }

  /**
   * Create an object key consisting of an array of values. Values are converted
   * to strings using {@code toString()}.
   *
   * @param name   the name
   * @param values the values
   * @return the json builder
   */
  public JsonBuilder add(String name, final Collection<?> values) {
    addComma(name);

    _startArray();

    for (Object o : values) {
      add(o);
    }

    endArray();

    return this;
  }

  /**
   * Add a variable with the null value to a JSON object.
   *
   * @param name the name
   * @return the json builder
   */
  public JsonBuilder addNull(String name) {
    addComma(name);
    mBuffer.append(JSON_NULL);

    return this;
  }

  /**
   * End object.
   *
   * @return the json builder
   */
  public JsonBuilder endObject() {
    mBuffer.append(JSON_OBJECT_END);

    return this;
  }

  /**
   * Add a comma then a new key name in an object.
   *
   * @param name the name
   */
  private void addComma(String name) {
    _addComma();

    quote(name);
    mBuffer.append(JSON_VALUE_DELIMITER);
  }

  /**
   * Surround a name or value in parentheses.
   *
   * @param s the s
   */
  private void quote(String s) {
    quote(s, mBuffer);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mBuffer.toString();
  }

  /**
   * Insert a collection of integers into a JSON structure.
   *
   * @param values the values
   * @return the json builder
   */
  public JsonBuilder intArray(final Collection<Integer> values) {
    startArray();

    for (int v : values) {
      add(v);
    }

    endArray();

    return this;
  }

  /**
   * Insert a named array of integers into an JSON object.
   *
   * @param name   The name of the array.
   * @param values A collection of integers to write.
   * @return the json builder
   */
  public JsonBuilder intArray(String name, final Collection<Integer> values) {
    startArray(name);

    for (int v : values) {
      add(v);
    }

    endArray();

    return this;
  }

  /**
   * Reopen an array or object to add new elements. Should only be called directly
   * after {@code endArray()} or {@code endObject()}.
   */
  public void reopen() {
    // Remove the last character which should either be ']' or '}'.
    mBuffer.deleteCharAt(mBuffer.length() - 1);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.CharSequence#charAt(int)
   */
  @Override
  public char charAt(int i) {
    return mBuffer.charAt(i);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.CharSequence#length()
   */
  @Override
  public int length() {
    return mBuffer.length();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.CharSequence#subSequence(int, int)
   */
  @Override
  public CharSequence subSequence(int start, int end) {
    return mBuffer.subSequence(start, end);
  }

  //
  // Static methods
  //

  /**
   * Write a jsonBuilder to file.
   *
   * @param json the json
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void write(JsonBuilder json, Path file) throws IOException {
    BufferedWriter writer = FileUtils.newBufferedWriter(file);

    try {
      writer.write(json.toString());
      writer.newLine();
    } finally {
      writer.close();
    }
  }

  /**
   * Creates the.
   *
   * @return the json builder
   */
  public static JsonBuilder create() {
    return create(BUFFER_SIZE);
  }

  public static JsonBuilder create(int bufferSize) {
    return new JsonBuilder(bufferSize);
  }

  /**
   * Create a suitable buffer for processing JSON.
   *
   * @return the string builder
   */
  public static StringBuilder createBuffer() {
    return new StringBuilder(BUFFER_SIZE);
  }

  /**
   * Add quotations around a JSON variable name or value.
   *
   * @param text   the text
   * @param buffer the buffer
   */
  public static final void quote(String text, StringBuilder buffer) {
    buffer.append(JSON_QUOTATION).append(text).append(JSON_QUOTATION);
  }

  /**
   * Quote.
   *
   * @param text   the text
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final void quote(String text, Appendable buffer) throws IOException {
    buffer.append(JSON_QUOTATION).append(text).append(JSON_QUOTATION);
  }
}
