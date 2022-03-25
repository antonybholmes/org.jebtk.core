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
package org.jebtk.core.settings;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import org.jebtk.core.CSSColor;

import org.jebtk.core.ColorUtils;
import org.jebtk.core.Mathematics;
import org.jebtk.core.event.ChangeListeners;
import org.jebtk.core.http.URLPath;
import org.jebtk.core.json.Json;
import org.jebtk.core.json.JsonObject;
import org.jebtk.core.json.JsonRepresentation;
import org.jebtk.core.path.Path;
import org.jebtk.core.path.StrictPath;
import org.jebtk.core.text.TextUtils;
import org.jebtk.core.xml.XmlRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Describes a setting. The name must be of the form part1.part2.part3...name.
 * The period separated parts of the name are treated as being part of a
 * hierarchy.
 * 
 * @author Antony Holmes
 *
 */
public class Setting extends ChangeListeners implements Comparable<Setting>, XmlRepresentation, JsonRepresentation {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The member path.
   */
  protected Path mPath;

  /**
   * The member description.
   */
  private String mDescription;

  /** The m locked. */
  private boolean mLocked;

  /**
   * Instantiates a new setting.
   *
   * @param path   the path
   * @param locked the locked
   */
  public Setting(String path, boolean locked) {
    this(new StrictPath(path), locked);
  }

  /**
   * Instantiates a new setting.
   *
   * @param path   the path
   * @param locked the locked
   */
  public Setting(Path path, boolean locked) {
    this(path, null, locked);
  }

  /**
   * Instantiates a new setting.
   *
   * @param path        the path
   * @param description the description
   * @param locked      the locked
   */
  public Setting(String path, String description, boolean locked) {
    this(new StrictPath(path), description, locked);
  }

  /**
   * Instantiates a new setting.
   *
   * @param path        the path
   * @param description the description
   * @param locked      the locked
   */
  public Setting(Path path, String description, boolean locked) {

    if (path != null) {
      mPath = path;
    }

    if (description != null) {
      mDescription = description;
    }

    mLocked = locked;
  }

  /**
   * Gets the path.
   *
   * @return the path
   */
  public Path getPath() {
    return mPath;
  }
  
  public List<String> getValues() {
    return Collections.singletonList(getString());
  }

  /**
   * Gets the as string.
   *
   * @return the as string
   */
  public String getValue() {
    return mPath.toString();
  }
  
  /**
   * Gets the as string.
   *
   * @return the as string
   */
  public String getString() {
    return getValue();
  }

  /**
   * Returns the value of the setting as an integer or Integer.MIN_VALUE if the
   * setting does not parse as an int.
   *
   * @return the as int
   */
  public int getInt() {
    return Integer.MIN_VALUE;
  }

  /**
   * Returns the value of the setting as an double or Double.MIN_VALUE if the
   * setting does not parse as an double.
   *
   * @return the as double
   */
  public double getDouble() {
    return Integer.MIN_VALUE;
  }

  /**
   * Gets the as long.
   *
   * @return the as long
   */
  public long getLong() {
    return Long.MIN_VALUE;
  }

  /**
   * Gets the as bool.
   *
   * @return the as bool
   */
  public boolean getBool() {
    return false;
  }

  /**
   * Gets the as color.
   *
   * @return the as color
   */
  public CSSColor getColor() {
    return null;
  }

  /**
   * Gets the as file.
   *
   * @return the as file
   */
  public java.nio.file.Path getFile() {
    return null;
  }

  /**
   * Creates a URL from a setting or returns null if the URL is malformed.
   *
   * @return the as url
   */
  public URL getUrl() {
    URLPath builder = getUrlBuilder();

    URL url = null;

    if (builder != null) {
      try {
        url = builder.toURL();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }

    return url;
  }

  /**
   * Gets the as url builder.
   *
   * @return the as url builder
   */
  public URLPath getUrlBuilder() {
    return null;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return mDescription;
  }

  /**
   * Gets the locked.
   *
   * @return the locked
   */
  public boolean getLocked() {
    return mLocked;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mPath.toString();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.xml.XmlRepresentation#toXml()
   */
  @Override
  public Element toXml(Document doc) {
    Element element = doc.createElement("setting");
    element.setAttribute("name", mPath.toString());
    element.setAttribute("value", getString());

    if (mDescription != null) {
      element.setAttribute("description", mDescription);
    }

    return element;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Setting s) {
    return mPath.compareTo(s.mPath);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Setting)) {
      return false;
    }

    return compareTo((Setting) o) == 0;
  }

  /**
   * Gets the lib name.
   *
   * @param path the path
   * @return the lib name
   */
  public static String getLibName(StrictPath path) {
    return path.level(0);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(String path, String value, String description, boolean locked) {
    return parse(new StrictPath(path), value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(Path path, String value) {
    return parse(path, value, TextUtils.EMPTY_STRING, false);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(Path path, String value, String description, boolean locked) {
    if (TextUtils.isNumber(value)) {
      double v = Double.parseDouble(value); // Parser.toDouble(s);

      if (Mathematics.isInt(v)) {
        return new SettingInt(path, (int) v, description, locked);
      } else {
        return new SettingDouble(path, v, description, locked);
      }
    } else if (ColorUtils.isHtmlColor(value)) {
      return new SettingColor(path, ColorUtils.decodeHtmlColor(value), description, locked);
      // } else if (URLUtils.isUrl(value)) {
      // return new SettingUrl(path, UrlBuilder.fromUrl(value), description,
      // locked);
    } else if (value.toLowerCase().equals(TextUtils.TRUE)) {
      return new SettingBool(path, true, description, locked);
    } else if (value.toLowerCase().equals(TextUtils.FALSE)) {
      return new SettingBool(path, false, description, locked);
    } else {
      return new SettingString(path, value, description, locked);
    }
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(String path, double value) {
    return parse(new StrictPath(path), value);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(Path path, double value) {
    return parse(path, value, TextUtils.EMPTY_STRING, false);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(String path, double value, String description, boolean locked) {
    return parse(new StrictPath(path), value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(Path path, double value, String description, boolean locked) {
    return new SettingDouble(path, value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(String path, int value) {
    return parse(new StrictPath(path), value);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(Path path, int value) {
    return parse(path, value, TextUtils.EMPTY_STRING, false);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(String path, int value, String description, boolean locked) {
    return parse(new StrictPath(path), value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(Path path, int value, String description, boolean locked) {
    return new SettingInt(path, value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(String path, long value) {
    return parse(new StrictPath(path), value);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(Path path, long value) {
    return parse(path, value, TextUtils.EMPTY_STRING, false);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(String path, long value, String description, boolean locked) {
    return parse(new StrictPath(path), value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(Path path, long value, String description, boolean locked) {
    return new SettingLong(path, value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(String path, CSSColor value) {
    return parse(new StrictPath(path), value);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(Path path, CSSColor value) {
    return parse(path, value, TextUtils.EMPTY_STRING, false);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(String path, CSSColor value, String description, boolean locked) {
    return parse(new StrictPath(path), value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(Path path, CSSColor value, String description, boolean locked) {
    return new SettingColor(path, value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(String path, boolean value) {
    return parse(new StrictPath(path), value);
  }

  /**
   * Parses the.
   *
   * @param path  the path
   * @param value the value
   * @return the setting
   */
  public static Setting parse(Path path, boolean value) {
    return parse(path, value, TextUtils.EMPTY_STRING, false);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(String path, boolean value, String description, boolean locked) {
    return parse(new StrictPath(path), value, description, locked);
  }

  /**
   * Parses the.
   *
   * @param path        the path
   * @param value       the value
   * @param description the description
   * @param locked      the locked
   * @return the setting
   */
  public static Setting parse(Path path, boolean value, String description, boolean locked) {
    return new SettingBool(path, value, description, locked);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.JsonRepresentation#toJson()
   */
  @Override
  public Json toJson() {
    Json o = new JsonObject();

    o.add("name", mPath);

    formatJsonValue(o);

    if (getDescription() != null) {
      o.add("description", getDescription());
    }

    return o;
  }

  /**
   * Should add the value attribute to the json object. This is separate from
   * {@code toJson()} to allow sub-classes to override it for specialized types
   * such as bools and numbers.
   *
   * @param o the o
   */
  protected void formatJsonValue(Json o) {
    if (getString() != null) {
      o.add("value", getString());
    }
  }

}
