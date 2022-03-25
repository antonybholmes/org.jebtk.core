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

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;
import org.jebtk.core.CSSColor;

import org.jebtk.core.Resources;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.collections.DefaultHashMap;
import org.jebtk.core.event.ChangeListeners;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.Io;
import org.jebtk.core.json.Json;
import org.jebtk.core.json.JsonArray;
import org.jebtk.core.json.JsonRepresentation;
import org.jebtk.core.path.Path;
import org.jebtk.core.path.RootPath;
import org.jebtk.core.path.StrictPath;
import org.jebtk.core.text.TextUtils;
import org.jebtk.core.xml.XmlRepresentation;
import org.jebtk.core.xml.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Settings factory for providing global settings to an application. Can load
 * settings from an XML Path or a text Path.
 * 
 * Settings will auto load setting from xml files ending settings.xml located in
 * res folders in the class path. It will then attempt to load user.settings.xml
 * from the res folder in the application directory. This allows users to alter
 * settings if they need to.
 *
 * @author Antony Holmes
 *
 */
public class Settings extends ChangeListeners implements Iterable<Path>, XmlRepresentation, JsonRepresentation {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * The log.
   */
  protected final Logger LOG = LoggerFactory.getLogger(Settings.class);

  /**
   * The member settings.
   */
  protected Map<Path, SettingsHistory> mSettings; // <Path, Setting>();

  /** The m update map. */
  // protected Map<Path, Boolean> mUpdateMap = new HashMap<Path, Boolean>();

  /**
   * The class SettingsXmlHandler.
   */
  private class SettingsXmlHandler extends DefaultHandler {

    /**
     * The member current paths.
     */
    private final Deque<Path> mCurrentPaths = new ArrayDeque<>();

    /** The m update. */
    private final boolean mUpdate;

    /**
     * Instantiates a new settings xml handler.
     *
     * @param update the update
     */
    public SettingsXmlHandler(boolean update) {
      mUpdate = update;

      mCurrentPaths.push(new SettingsRootPath());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

      if (qName.equals("setting")) {
        Path path = mCurrentPaths.peek().append(attributes.getValue("name"));

        // System.err.println("setting path " + path);

        mCurrentPaths.push(path);

        String value = attributes.getValue("value");

        if (value == null) {
          value = TextUtils.EMPTY_STRING;
        }

        String description = attributes.getValue("description");

        if (description == null) {
          description = TextUtils.EMPTY_STRING;
        }

        boolean locked = attributes.getValue("locked") != null && TextUtils.parseBool(attributes.getValue("locked"));

        Setting setting = Setting.parse(path, value, description, locked);

        update(setting, mUpdate);
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String uri, String localName, String qName) {
      if (qName.equals("setting")) {
        mCurrentPaths.pop();
      }
    }
  }

  /**
   * Instantiates a new settings.
   */
  public Settings() {
    mSettings = DefaultHashMap.create(() -> new SettingsHistory());
  }

  /**
   * Update.
   *
   * @param path  the path
   * @param color the color
   */
  public void update(String path, Color color) {
    update(path, new CSSColor(color));
  }
  
  public void update(String path, CSSColor color) {
    update(Setting.parse(path, color));
  }

  /**
   * Sets the setting.
   *
   * @param path  the path
   * @param value the value
   */
  public void update(String path, boolean value) {
    update(Setting.parse(path, value));
  }

  /**
   * Update.
   *
   * @param path  the path
   * @param value the value
   */
  public void update(Path path, boolean value) {
    update(Setting.parse(path, value));
  }

  /**
   * Set a user setting. Triggers writing of setting to res/user.settings.xml.
   *
   * @param path  the path
   * @param value the value
   */
  public void update(String path, String value) {
    update(new StrictPath(path), value);
  }

  /**
   * Sets the setting.
   *
   * @param path  the path
   * @param value the value
   */
  public void update(String path, int value) {
    update(Setting.parse(path, value));
  }

  /**
   * Update.
   *
   * @param path  the path
   * @param value the value
   */
  public void update(String path, long value) {
    update(Setting.parse(path, value));
  }

  public void update(Path path, double value) {
    update(Setting.parse(path, value));
  }

  /**
   * Sets the.
   *
   * @param path  the path
   * @param value the value
   */
  public void set(String path, double value) {
    update(path, value);

    fireChanged();
  }

  /**
   * Sets the setting.
   *
   * @param path  the path
   * @param value the value
   */
  public void update(Path path, String value) {
    update(Setting.parse(path, value));
  }

  /**
   * Sets the setting.
   *
   * @param path  the path
   * @param value the value
   */
  public void update(String path, double value) {
    update(Setting.parse(path, value));
  }

  /**
   * Sets the.
   *
   * @param path  the path
   * @param value the value
   */
  public void set(String path, boolean value) {
    update(path, value);

    fireChanged();
  }

  /**
   * Sets the.
   *
   * @param path  the path
   * @param value the value
   */
  public void set(Path path, boolean value) {
    update(path, value);

    fireChanged();
  }

  public void set(String path, String value) {
    update(path, value);

    fireChanged();
  }

  /**
   * Sets the.
   *
   * @param path  the path
   * @param value the value
   */
  public void set(Path path, String value) {
    update(path, value);

    fireChanged();
  }

  public void set(Path path, double value) {
    update(path, value);

    fireChanged();
  }

  /**
   * Add a new setting.
   *
   * @param setting the setting
   */
  protected void set(Setting setting) {
    update(setting);

    fireChanged();
  }

  /**
   * Update.
   *
   * @param setting the setting
   */
  protected synchronized void update(Setting setting) {
    update(setting, true);
  }

  /**
   * Adds a setting, optionally dictating whether the setting is flagged as
   * updated. Typically internal settings are loaded without the flag to prevent
   * all settings being written to a user's personal settings. Only subsequent
   * changes by the user get written to their personal settings file.
   *
   * @param setting the setting
   * @param updated the updated
   */
  protected synchronized void update(Setting setting, boolean updated) {
    // The first time a setting is added, is it set to not update.
    // If a further update is called (e.g. via the UI), the setting
    // is flagged so it can be written to the user settings file. In this
    // way only settings that are adjusted for the user are saved rather
    // than a copy of every setting.

    Path path = setting.getPath();

    SettingsHistory settings = getSettings(path);

    settings.add(setting, updated);

    LOG.info("Update settings {}:{}...", path, setting);

    /*
     * if (!mSettings.containsKey(path) || !mSettings.get(path).getLocked()) {
     * mSettings.put(path, setting);
     * 
     * LOG.info("Update settings {}:{}...", path, setting);
     * 
     * mUpdateMap.put(path, updated); }
     */
  }

  /**
   * If a setting exists, reset it back to its default setting.
   *
   * @param path the path
   */
  public void resetToDefault(String path) {
    resetToDefault(new StrictPath(path));
  }

  /**
   * If a setting exists, reset it back to its default setting.
   *
   * @param path the path
   */
  public void resetToDefault(Path path) {
    SettingsHistory settings = getSettings(path);

    settings.resetToDefault();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.lib.xml.XmlRepresentation#toXml()
   */
  @Override
  public Element toXml(Document doc) {
    Element element = doc.createElement("settings");

    for (Path name : this) {
      element.appendChild(getSetting(name).toXml(doc));
    }

    return element;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.json.JsonRepresentation#toJson()
   */
  @Override
  public Json toJson() {
    Json a = new JsonArray();

    for (Path name : this) {
      a.add(getSetting(name).toJson());
    }

    return a;
  }

  /**
   * Gets the setting.
   *
   * @param name the name
   * @return the setting
   */
  public synchronized Setting getSetting(String name) {
    return getSetting(new StrictPath(name));
  }

  /**
   * Gets the setting.
   *
   * @param path the path
   * @return the setting
   */
  public synchronized Setting getSetting(Path path) {
    SettingsHistory history = getSettings(path);

    if (history.size() > 0) {
      return history.current();
    } else {
      LOG.error("Please ensure setting {} exists.", path);

      return null;
    }

  }

  /**
   * Gets the settings.
   *
   * @param path the path
   * @return the settings
   */
  public synchronized SettingsHistory getSettings(Path path) {
    return mSettings.get(path);
  }

  /**
   * Contains.
   *
   * @param path the path
   * @return true, if successful
   */
  public synchronized boolean contains(String path) {
    return contains(new StrictPath(path));
  }

  /**
   * Contains.
   *
   * @param path the path
   * @return true, if successful
   */
  public synchronized boolean contains(Path path) {
    return mSettings.containsKey(path);
  }

  /**
   * Returns true if the user has updated the setting.
   *
   * @param path the path
   * @return true, if is updated
   */
  public synchronized boolean isUpdated(Path path) {
    // if a user has updated a setting, the size will be greater than 1
    // since we log changes to settings.
    return mSettings.get(path).size() > 1;
  }

  /**
   * Gets the as int.
   *
   * @param name the name
   * @return the as int
   */
  public synchronized int getInt(String name) {
    return getSetting(name).getInt();
  }

  /**
   * Gets the as int.
   *
   * @param name the name
   * @return the as int
   */
  public synchronized int getInt(Path name) {
    return getSetting(name).getInt();
  }

  /**
   * Gets the as double.
   *
   * @param name the name
   * @return the as double
   */
  public synchronized double getDouble(String name) {
    return getSetting(name).getDouble();
  }

  /**
   * Gets the as double.
   *
   * @param name the name
   * @return the as double
   */
  public synchronized double getDouble(Path name) {
    return getSetting(name).getDouble();
  }

  public synchronized double getDouble(String name, double defaultValue) {
    return getDouble(new StrictPath(name), defaultValue);
  }

  public synchronized double getDouble(Path name, double defaultValue) {
    if (!contains(name)) {
      set(name, defaultValue);
    }

    return getSetting(name).getDouble();
  }

  /**
   * Gets the as bool.
   *
   * @param name the name
   * @return the as bool
   */
  public synchronized boolean getBool(String name) {
    Setting setting = getSetting(name);

    if (setting != null) {
      return setting.getBool();
    } else {
      return false;
    }
  }

  /**
   * Gets the as bool.
   *
   * @param name the name
   * @return the as bool
   */
  public synchronized boolean getBool(Path name) {
    Setting setting = getSetting(name);

    if (setting != null) {
      return setting.getBool();
    } else {
      return false;
    }
  }

  /**
   * Returns a setting, creating it with the default value it if does not exist.
   *
   * @param name         the name
   * @param defaultValue the default value
   * @return the as bool
   */
  public synchronized boolean getBool(String name, boolean defaultValue) {
    return getBool(new StrictPath(name), defaultValue);
  }

  /**
   * Gets the as bool.
   *
   * @param name         the name
   * @param defaultValue the default value
   * @return the as bool
   */
  public synchronized boolean getBool(Path name, boolean defaultValue) {
    if (!contains(name)) {
      set(name, defaultValue);
    }

    return getSetting(name).getBool();
  }

  /**
   * Gets the as string.
   *
   * @param name the name
   * @return the as string
   */
  public synchronized String getString(String name) {
    LOG.error("get setting s" + name);
    return getValue(name);
  }
  
  public synchronized String getString(Path name) {
    return getValue(name);
  }
  
  public synchronized String getValue(String name) {
    return getValue(new StrictPath(name));
  }
  
  /**
   * Gets the as string.
   *
   * @param path the name
   * @return the as string
   */
  public synchronized String getValue(Path path) {
    
    Setting ret = getSetting(path);
    
    LOG.error("get setting" + path + ":" + ret);
    
    if (ret != null) {
      return ret.getValue();
    } else {
      return TextUtils.EMPTY_STRING;
    }
  }

  /**
   * Gets the as color.
   *
   * @param name the name
   * @return the as color
   */
  public synchronized CSSColor getColor(String name) {
    return getSetting(name).getColor();
  }

  /**
   * Gets the as color.
   *
   * @param name the name
   * @return the as color
   */
  public synchronized CSSColor getColor(Path name) {
    return getSetting(name).getColor();
  }

  /**
   * Gets the as url.
   *
   * @param name the name
   * @return the as url
   */
  public synchronized URL getUrl(String name) {
    return getSetting(name).getUrl();
  }

  /**
   * Gets the as url.
   *
   * @param name the name
   * @return the as url
   */
  public synchronized URL getUrl(Path name) {
    return getSetting(name).getUrl();
  }

  /**
   * Gets the as file.
   *
   * @param name the name
   * @return the as file
   */
  public java.nio.file.Path getFile(String name) {
    return getSetting(name).getFile();
  }

  /**
   * Gets the as file.
   *
   * @param name the name
   * @return the as file
   */
  public java.nio.file.Path getFile(Path name) {
    return getSetting(name).getFile();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Path> iterator() {
    return CollectionUtils.sort(mSettings.keySet()).iterator();
  }

  /**
   * Load settings from a library/program. The settings Path must be called
   * libName.settings.xml and resides in the res directory accessible from the
   * programs classpath.
   *
   * @param libName the lib name
   * @return true, if successful
   * @throws SAXException                 the SAX exception
   * @throws IOException                  Signals that an I/O exception has
   *                                      occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  public boolean loadLibSettings(String libName) throws SAXException, IOException, ParserConfigurationException {
    String Path = new StringBuilder(libName.toLowerCase()).append(".settings.xml").toString();

    LOG.info("Attempting to load settings from {}...", Path);

    boolean success = loadXml(Resources.getResInputStream(Path), false);

    if (!success) {
      LOG.error("Settings are not available in {}.", Path);
    }

    return success;
  }

  /**
   * Load xml.
   *
   * @param file   the file
   * @param update the update
   * @throws SAXException                 the SAX exception
   * @throws IOException                  Signals that an I/O exception has
   *                                      occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  public void loadXml(java.nio.file.Path file, boolean update)
      throws SAXException, IOException, ParserConfigurationException {
    if (file == null || !FileUtils.exists(file)) {
      return;
    }

    LOG.info("Loading user settings from {}...", file);

    try (InputStream stream = FileUtils.newBufferedInputStream(file)) {
      loadXml(stream, update);
    }
  }

  /**
   * Load xml.
   *
   * @param is     the is
   * @param update the update
   * @return true, if successful
   * @throws SAXException                 the SAX exception
   * @throws IOException                  Signals that an I/O exception has
   *                                      occurred.
   * @throws ParserConfigurationException the parser configuration exception
   */
  protected synchronized boolean loadXml(InputStream is, boolean update)
      throws SAXException, IOException, ParserConfigurationException {
    if (is == null) {
      return false;
    }

    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();

    SettingsXmlHandler handler = new SettingsXmlHandler(update);

    saxParser.parse(is, handler);

    return true;
  }

  /**
   * Load json.
   *
   * @param file   the file
   * @param update the update
   * @throws FileNotFoundException the file not found exception
   * @throws IOException           Signals that an I/O exception has occurred.
   */
  public void loadJson(java.nio.file.Path file, boolean update) throws FileNotFoundException, IOException {
    if (file == null || !FileUtils.exists(file)) {
      return;
    }

    LOG.info("Loading user settings from {}...", file);

    try (InputStream is = FileUtils.newBufferedInputStream(file)) {
      loadJson(is, update);
    }
  }

  /**
   * Load xml.
   *
   * @param is     the is
   * @param update the update
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected synchronized boolean loadJson(InputStream is, boolean update) throws IOException {
    if (is == null) {
      return false;
    }

    Json json = Json.parse(is);

    for (int i = 0; i < json.size(); ++i) {
      Json settingJson = json.get(i);

      Setting setting = Setting.parse(settingJson.getString("name"), settingJson.getString("value"),
          settingJson.getString("description"), settingJson.getBool("locked"));

      update(setting, update);
    }

    /*
     * for (String key : json.getKeys()) { Path path = new StrictPath(key);
     * 
     * String value = json.getString(key);
     * 
     * setSetting(path, value); }
     */

    return true;
  }

  /**
   * Write xml.
   *
   * @param file the file
   * @throws IOException                  Signals that an I/O exception has
   *                                      occurred.
   * @throws TransformerException         the transformer exception
   * @throws ParserConfigurationException the parser configuration exception
   */
  public final void writeXml(java.nio.file.Path file)
      throws IOException, TransformerException, ParserConfigurationException {
    Document doc = XmlUtils.createDoc();

    doc.appendChild(toXml(doc));

    XmlUtils.writeXml(doc, file);

    // LOG.info("Wrote settings to {}", Path.getAbsoluteFile());
  }

  /**
   * Write json.
   *
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public final void writeJson(java.nio.file.Path file) throws IOException {
    Json.prettyWrite(toJson(), file);
    // toJson().prettyPrint().write(file);
  }

  /**
   * Load ini settings.
   *
   * @param file the file
   * @return
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Settings loadIniSettings(java.nio.file.Path file) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    String group = "main";

    try {
      while ((line = reader.readLine()) != null) {
        if (Io.isEmptyLine(line)) {
          continue;
        }

        if (line.charAt(0) == '#') {
          // comment
          continue;
        }

        if (line.startsWith("//")) {
          // comment
          continue;
        }

        if (line.startsWith("[")) {
          // group
          group = line.substring(1, line.length() - 1);

          continue;
        }

        List<String> tokens = TextUtils.fastSplit(line, TextUtils.EQUALS_DELIMITER, 2);

        Setting setting = Setting.parse(new RootPath(group, tokens.get(0)), tokens.get(1)); // (true, group,
                                                                                            // tokens.get(0)),

        System.err.println("ini path 2 " + (new RootPath(group, tokens.get(0)).toString()) + " "
            + setting.getPath().toString() + " " + setting.getString() + " " + group + " " + tokens.get(0));

        update(setting);
      }
    } finally {
      reader.close();
    }

    return this;
  }
}
