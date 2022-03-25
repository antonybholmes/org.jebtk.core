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
package org.jebtk.core.json;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Iterator;

import org.jebtk.core.ColorUtils;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.text.TextUtils;

/**
 * Generic JSON object.
 * 
 * @author Antony Holmes
 *
 */
public abstract class Json implements Iterable<Json>, PrettyJson {
  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();

    try {
      toJson(buffer);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return buffer.toString();
  }

  /**
   * Returns a string representation of the object's JSON.
   *
   * @return the string
   */
  public String toJson() {
    return toString();
  }

  /**
   * Formatted txt.
   *
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public abstract void toJson(Appendable buffer) throws IOException;

  /**
   * Gets the JSON value as a double.
   *
   * @return the as double
   */
  public double getDouble() {
    return Double.NaN;
  }

  /**
   * Gets the JSON value as a int.
   *
   * @return the as int
   */
  public int getInt() {
    return Integer.MAX_VALUE;
  }

  /**
   * Gets the as long.
   *
   * @return the as long
   */
  public long getLong() {
    return Long.MAX_VALUE;
  }

  /**
   * Returns the String value of this object. This should be used in preference to
   * toString() which will return the json formatted representation of the object.
   * 
   * This method will return null if the object is not of type JsonString
   *
   * @return the as string
   */
  public String getString() {
    return toString();
  }

  /**
   * Gets the as color.
   *
   * @return the as color
   */
  public Color getColor() {
    return ColorUtils.decodeHtmlColor(toString());
  }

  /**
   * Gets the JSON value as a char.
   *
   * @return the as char
   */
  public char getChar() {
    return 0;
  }

  /**
   * Get the array item as a char. If the item is invalid, 0 is returned.
   * 
   * @param index
   * @return
   */
  public char getChar(int index) {
    Json e = get(index);

    if (e != null) {
      return e.getChar();
    } else {
      return Character.MIN_VALUE;
    }
  }

  public char getChar(String name) {
    Json e = get(name);

    if (e != null) {
      return e.getChar();
    } else {
      return Character.MIN_VALUE;
    }
  }

  /**
   * Gets the JSON value as a boolean.
   *
   * @return the as boolean
   */
  public boolean getBool() {
    return false;
  }

  /**
   * Returns the object stored in this field, if the object is a JSON object. If
   * the object is a JSON array, it will return null.
   *
   * @param name the name
   * @return the json value
   */
  public Json get(String name) {
    return this;
  }

  /**
   * Returns the object by name, creating a JsonBoolean with the default value if
   * it does not exist.
   *
   * @param name         the name
   * @param defaultValue the default value
   * @return the json
   */
  public Json get(String name, boolean defaultValue) {
    return this;
  }

  /**
   * Returns the indexed item if this object is a JSON array, otherwise return the
   * item itself.
   *
   * @param index the index
   * @return the json value
   */
  public Json get(int index) {
    return this;
  }

  /**
   * Gets the array.
   *
   * @param name the name
   * @return the array
   */
  public JsonArray getArray(String name) {
    return (JsonArray) get(name);
  }

  /**
   * Gets the array.
   *
   * @param index the index
   * @return the array
   */
  public JsonArray getArray(int index) {
    return (JsonArray) get(index);
  }

  /**
   * Gets the as string.
   *
   * @param name the name
   * @return the as string
   */
  public String getString(String name) {
    Json e = get(name);

    if (e != null) {
      return e.getString();
    } else {
      return null;
    }
  }

  /**
   * Gets the as color.
   *
   * @param name the name
   * @return the as color
   */
  public Color getColor(String name) {
    Json e = get(name);

    if (e != null) {
      return e.getColor();
    } else {
      return null;
    }
  }

  /**
   * Gets the as string.
   *
   * @param index the index
   * @return the as string
   */
  public String getString(int index) {
    Json e = get(index);

    if (e != null) {
      return e.getString();
    } else {
      return TextUtils.EMPTY_STRING;
    }
  }

  /**
   * Gets the as int.
   *
   * @param name the name
   * @return the as int
   */
  public int getInt(String name) {
    Json e = get(name);

    if (e != null) {
      return e.getInt();
    } else {
      return Integer.MIN_VALUE;
    }
  }

  /**
   * Gets the as int.
   *
   * @param index the index
   * @return the as int
   */
  public int getInt(int index) {
    Json e = get(index);

    if (e != null) {
      return e.getInt();
    } else {
      return Integer.MIN_VALUE;
    }
  }

  /**
   * Returns a named item as a double value. If the item is not a number or does
   * not exist, Double.NaN will be returned.
   *
   * @param name the name
   * @return the as double
   */
  public double getDouble(String name) {
    Json e = get(name);

    if (e != null) {
      return e.getDouble();
    } else {
      return Double.NaN;
    }
  }

  /**
   * Gets the as double.
   *
   * @param index the index
   * @return the as double
   */
  public double getDouble(int index) {
    Json e = get(index);

    if (e != null) {
      return e.getDouble();
    } else {
      return Double.NaN;
    }
  }

  /**
   * Gets the as bool.
   *
   * @param name the name
   * @return the as bool
   */
  public boolean getBool(String name) {
    Json e = get(name);

    if (e != null) {
      return e.getBool();
    } else {
      return false;
    }
  }

  /**
   * Gets the as bool.
   *
   * @param index the index
   * @return the as bool
   */
  public boolean getBool(int index) {
    Json e = get(index);

    if (e != null) {
      return e.getBool();
    } else {
      return false;
    }
  }

  /**
   * The number of items in the JSON structure.
   *
   * @return the number of items in the JSON structure.
   */
  public int size() {
    return 1;
  }

  /**
   * Adds a JSON member.
   *
   * @param name  the name
   * @param value the value
   * @return the json value
   */
  public Json add(String name, Json value) {
    return this;
  }

  /**
   * Adds the.
   *
   * @param name  the name
   * @param value the value
   * @return the json value
   */
  public Json add(String name, String value) {
    return add(name, new JsonString(value));
  }

  /**
   * If a generic object is added to a json object, it will be interpreted as a
   * string using toString() and its value added as json string.
   *
   * @param name  the name
   * @param value the value
   * @return the json
   */
  public Json add(String name, Object value) {
    return add(name, new JsonString(value.toString()));
  }

  /**
   * Adds the.
   *
   * @param name  the name
   * @param value the value
   * @return the json
   */
  public Json add(String name, Path value) {
    return add(name, PathUtils.toString(value));
  }

  /**
   * Adds the.
   *
   * @param name  the name
   * @param value the value
   * @return the json value
   */
  public Json add(String name, double value) {
    return add(name, new JsonDouble(value));
  }

  /**
   * Adds the.
   *
   * @param name  the name
   * @param value the value
   * @return the json value
   */
  public Json add(String name, int value) {
    return add(name, new JsonInteger(value));
  }

  /**
   * Adds the.
   *
   * @param name  the name
   * @param value the value
   * @return the json
   */
  public Json add(String name, long value) {
    return add(name, new JsonLong(value));
  }

  /**
   * Adds the.
   *
   * @param name  the name
   * @param value the value
   * @return the json value
   */
  public Json add(String name, boolean value) {
    return add(name, new JsonBoolean(value));
  }

  /**
   * Adds the.
   *
   * @param value the value
   * @return the json
   */
  public Json add(Path value) {
    return add(PathUtils.toString(value));
  }

  /**
   * Adds the.
   *
   * @param value the value
   * @return the json value
   */
  public Json add(String value) {
    return add(new JsonString(value));
  }

  /**
   * Adds the.
   *
   * @param value the value
   * @return the json value
   */
  public Json add(double value) {
    return add(new JsonDouble(value));
  }

  /**
   * Adds the.
   *
   * @param value the value
   * @return the json value
   */
  public Json add(int value) {
    return add(new JsonInteger(value));
  }

  /**
   * Adds the.
   *
   * @param value the value
   * @return the json
   */
  public Json add(char value) {
    return add(new JsonChar(value));
  }

  /**
   * Adds the.
   *
   * @param value the value
   * @return the json value
   */
  public Json add(Json value) {
    return this;
  }

  /**
   * Insert raw json as a named field.
   *
   * @param name the name
   * @param json the json
   * @return the json
   */
  public Json insert(String name, String json) {
    return insert(name, new JsonRaw(json));
  }

  /**
   * Insert.
   *
   * @param name the name
   * @param json the json
   * @return the json
   */
  public Json insert(String name, JsonRaw json) {
    return this;
  }

  /**
   * Insert.
   *
   * @param json the json
   * @return the json
   */
  public Json insert(String json) {
    return insert(new JsonRaw(json));
  }

  /**
   * Insert.
   *
   * @param json the json
   * @return the json
   */
  public Json insert(JsonRaw json) {
    return this;
  }

  /**
   * Set the json of the object, ignoring the underlying data structure. The
   * string may not checked and will be assumed to be valid json.
   *
   * @param json the new json
   */
  public void setJson(String json) {

  }

  /**
   * Contains key.
   *
   * @param key the key
   * @return true, if successful
   */
  public boolean containsKey(String key) {
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Json> iterator() {
    return null;
  }

  /**
   * Gets the keys.
   *
   * @return the keys
   */
  public Collection<String> getKeys() {
    return null;
  }

  /**
   * Formatted json.
   *
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public String prettyJson() throws IOException {
    StringBuilder buffer = new StringBuilder();

    prettyJson(buffer);

    return buffer.toString();
  }

  /**
   * Formatted json.
   *
   * @param buffer the buffer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void prettyJson(Appendable buffer) throws IOException {
    prettyJson(buffer, 0);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.FormattedJsonRepresentation#formattedJson(java.lang.
   * Appendable, int)
   */
  @Override
  public void prettyJson(Appendable buffer, int level) throws IOException {
    toJson(buffer);
  }

  /**
   * Write.
   *
   * @param json the json
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void write(Json json, File file) throws IOException {
    write(json, file.toPath());
  }

  /**
   * Write.
   *
   * @param json the json
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void write(Json json, Path file) throws IOException {
    BufferedWriter writer = FileUtils.newBufferedWriter(file);

    try {
      json.toJson(writer);
      writer.newLine();
    } finally {
      writer.close();
    }
  }

  /**
   * Pretty write.
   *
   * @param json the json
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void prettyWrite(Json json, Path file) throws IOException {
    BufferedWriter writer = FileUtils.newBufferedWriter(file);

    try {
      json.prettyJson(writer);
      writer.newLine();
    } finally {
      writer.close();
    }
  }

  /**
   * Indentation.
   *
   * @param level the level
   * @return the string
   */
  public static String indentation(int level) {
    return TextUtils.repeat(TextUtils.DOUBLE_SPACE, level);
  }

  /**
   * Parses the.
   *
   * @param file the file
   * @return the json
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Json fromJson(File file) throws IOException {
    return fromJson(file.toPath());
  }

  /**
   * Parses the.
   *
   * @param file the file
   * @return the json
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Json fromJson(Path file) throws IOException {
    return new JsonParser().parse(file);
  }

  /**
   * Parses the.
   *
   * @param is the is
   * @return the json
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Json parse(InputStream is) throws IOException {
    return new JsonParser().parse(is);
  }

  /**
   * Pretty print.
   *
   * @return the object
   */
  public Object prettyPrint() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Creates the object.
   *
   * @param name the name
   * @return the json
   */
  public Json createObject(String name) {
    Json json = new JsonObject();

    add(name, json);

    return json;
  }

  /**
   * Creates the array.
   *
   * @param name the name
   * @return the json
   */
  public Json createArray(String name) {
    Json json = new JsonArray();

    add(name, json);

    return json;
  }
}