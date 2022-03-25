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

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.Resources;
import org.jebtk.core.json.Json;
import org.jebtk.core.json.JsonArray;
import org.jebtk.core.path.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

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
public class SettingsService extends Settings {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * The Class SettingsServiceLoader.
   */
  private static class SettingsServiceLoader {

    /** The Constant INSTANCE. */
    private static final SettingsService INSTANCE = new SettingsService();
  }

  /**
   * Gets the single instance of SettingsService.
   *
   * @return single instance of SettingsService
   */
  public static SettingsService getInstance() {
    return SettingsServiceLoader.INSTANCE;
  }

  /**
   * The member auto loaded.
   */
  private boolean mAutoLoad = true;

  /** The m auto save. */
  private boolean mAutoSave = true;

  /** The m loaders. */
  private final SettingsReaders mLoaders = new SettingsReaders();

  /** The m savers. */
  private final SettingsWriters mSavers = new SettingsWriters();

  /**
   * Instantiates a new settings service.
   */
  private SettingsService() {
    // do nothing

    // Load settings from the packages
    mLoaders.add(new SettingsReaderPackageXml());
    mLoaders.add(new SettingsReaderPackageJson());

    // Overwrite with app defaults
    mLoaders.add(new SettingsReaderDefaultXml());
    mLoaders.add(new SettingsReaderDefaultJson());

    // Overwrite with user defaults
    mLoaders.add(new SettingsReaderUserXml());
    mLoaders.add(new SettingsReaderUserJson());
    // Ability to read settings to home directory
    // mLoaders.add(new SettingsReaderUserHomeJson());

    // mSavers.add(new PackageJsonSettingsSaver());

    // mSavers.add(new SettingsWriterUserHomeJson());

    // Save to app directory
    mSavers.add(new SettingsWriterUserJson());

  }

  /**
   * Gets the setting.
   *
   * @param path the path
   * @return the setting
   */
  @Override
  public synchronized Setting getSetting(Path path) {
    try {
      autoLoad();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return super.getSetting(path);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Path> iterator() {
    autoLoad();

    return super.iterator();
  }

  /**
   * Attempt to find all settings files and load them.
   */
  private synchronized void autoLoad() {
    if (mAutoLoad) {
      // Set this here to stop recursive infinite calling
      // of this method.
      mAutoLoad = false;

      // autoLoadXml();
      // autoLoadJson();

      load();
    }
  }

  /**
   * Load.
   */
  private synchronized void load() {
    for (ISettingsReader loader : mLoaders) {
      loader.load(this);
    }
  }

  /**
   * Auto load xml.
   *
   * @throws IOException                  Signals that an I/O exception has
   *                                      occurred.
   * @throws URISyntaxException           the URI syntax exception
   * @throws SAXException                 the SAX exception
   * @throws ParserConfigurationException the parser configuration exception
   */
  private synchronized void autoLoadXml()
      throws IOException, URISyntaxException, SAXException, ParserConfigurationException {
    LOG.info("Auto loading XML settings...");

    for (String res : Resources.getInstance()) {
      if (!res.contains("settings.xml")) {
        continue;
      }

      LOG.info("Loading settings from {}...", res);

      loadXml(Resources.getResInputStream(res), false);
    }

    // Load local settings that may overwrite internal settings.
    loadXml(SettingsReaderPackageXml.DEFAULT_XML_FILE, false);

    // Load any per user settings. We flag these as being updated so
    // that on the next write cycle, they will be written back to the
    // settings file.
    loadXml(SettingsReaderUserXml.USER_XML_FILE, true);

    LOG.info("Finished loading settings...");
  }

  /**
   * Auto load json.
   *
   * @throws ParseException the parse exception
   * @throws IOException    Signals that an I/O exception has occurred.
   */
  private synchronized void autoLoadJson() throws ParseException, IOException {
    LOG.info("Auto loading JSON settings...");

    for (String res : Resources.getInstance()) {
      if (!res.contains("settings.json")) {
        continue;
      }

      LOG.info("Loading settings from {}...", res);

      loadJson(Resources.getResInputStream(res), false);
    }

    // Load local settings that may overwrite internal settings.
    loadJson(SettingsReaderPackageJson.DEFAULT_JSON_FILE, false);

    // Load any per user settings setting them to update mode, so that
    // they will be written to file. Only settings marked as updated
    // will be saved in the user file
    loadJson(SettingsReaderUserJson.USER_JSON_FILE, true);

    LOG.info("Finished loading settings...");
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Settings#update(org.abh.common.settings.Setting)
   */
  @Override
  protected synchronized void update(Setting setting, boolean updated) {
    // Update has the same effect as add plus an autosave of the settings
    // to disk.
    super.update(setting, updated);

    // Save a local copy of the settings if the setting is flagged as
    // updated
    if (updated) {
      autoSave();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Settings#toXml(org.w3c.dom.Document)
   */
  @Override
  public Element toXml(Document doc) {
    Element element = doc.createElement("settings");

    for (Path path : this) {
      if (isUpdated(path)) {
        element.appendChild(getSetting(path).toXml(doc));
      }
    }

    return element;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.abh.common.settings.Settings#toJson()
   */
  @Override
  public Json toJson() {
    Json a = new JsonArray();

    for (Path path : this) {
      // System.err.println("js " + path);

      if (isUpdated(path)) {
        a.add(getSetting(path).toJson());
      }
    }

    return a;
  }

  /**
   * Gets the savers.
   *
   * @return the savers
   */
  public SettingsWriters getSavers() {
    return mSavers;
  }

  /**
   * Gets the loaders.
   *
   * @return the loaders
   */
  public SettingsReaders getLoaders() {
    return mLoaders;
  }

  /**
   * Auto save.
   */
  public void autoSave() {
    if (mAutoSave) {
      save();
    }
  }

  /**
   * Save the settings as user settings.
   */
  public void save() {
    // Create the res directory if it does not exist
    // Resources.makeResDir();

    // writeXml(USER_XML_FILE);

    // Make the home folder if it doesn't exist
    // FileUtils.mkhome();

    // writeJson(USER_JSON_FILE);

    for (SettingsWriter saver : mSavers) {
      saver.save(this);
    }
  }

  /**
   * Sets whether settings are automatically saved to disk when they are updated.
   *
   * @param autoSave the new auto save
   */
  public void setAutoSave(boolean autoSave) {
    mAutoSave = autoSave;

    autoSave();
  }

}
