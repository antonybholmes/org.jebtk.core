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
package org.jebtk.core.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jebtk.core.io.Io;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.text.TextUtils;
import org.xml.sax.SAXException;

/**
 * Provides a dictionary service so strings/words can be mapped to synonyms.
 * 
 * @author Antony Holmes
 *
 */
public class DictionaryService {

  private static class DictionaryServiceLoader {

    /** The Constant INSTANCE. */
    private static final DictionaryService INSTANCE = new DictionaryService();
  }

  /**
   * Gets the single instance of SettingsService.
   *
   * @return single instance of SettingsService
   */
  public static DictionaryService getInstance() {
    return DictionaryServiceLoader.INSTANCE;
  }

  /**
   * The words.
   */
  private Set<String> words = new HashSet<String>();

  /**
   * The definitions.
   */
  private Map<String, String> definitions = new HashMap<String, String>();

  /**
   * The synonyms.
   */
  private Map<String, Set<String>> synonyms = new HashMap<String, Set<String>>();

  private boolean mAutoLoad = true;

  /**
   * The constant DEFAULT_FILE.
   */
  public static final Path DEFAULT_FILE = PathUtils.getPath("res/dictionary.xml");

  private void autoLoad() {
    if (mAutoLoad) {
      // Set this here to stop recursive infinite calling
      // of this method.
      mAutoLoad = false;

      try {
        loadXml();
      } catch (SAXException | IOException | ParserConfigurationException e) {
        e.printStackTrace();
      }
      // autoLoadJson();

    }
  }

  private void loadXml() throws ParserConfigurationException, SAXException, IOException {
    loadXml(DEFAULT_FILE);
  }

  /**
   * Load xml.
   *
   * @param file the file
   * @throws SAXException
   * @throws ParserConfigurationException
   * @throws IOException
   */
  public void loadXml(Path file) throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();

    DictionaryXmlHandler handler = new DictionaryXmlHandler(this);

    saxParser.parse(file.toFile(), handler);
  }

  /**
   * Load tsv file.
   *
   * @param file the file
   */
  public void loadTSVFile(File file) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));

      String line;

      try {
        while ((line = reader.readLine()) != null) {
          if (Io.isEmptyLine(line)) {
            continue;
          }

          List<String> tokens = TextUtils.fastSplit(line, TextUtils.TAB_DELIMITER);

          addWord(tokens.get(0), tokens.get(1));

          for (int i = 2; i < tokens.size(); ++i) {
            addSynonym(tokens.get(0), tokens.get(i));
          }
        }
      } finally {
        reader.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Exists.
   *
   * @param word the word
   * @return true, if successful
   */
  public boolean exists(String word) {
    autoLoad();

    return words.contains(word);
  }

  /**
   * Adds the word.
   *
   * @param word the word
   */
  public void addWord(String word) {
    words.add(word.toLowerCase());
  }

  /**
   * Adds the word.
   *
   * @param word       the word
   * @param definition the definition
   */
  public void addWord(String word, String definition) {
    addWord(word);

    definitions.put(word.toLowerCase(), definition);
  }

  /**
   * Gets the definition.
   *
   * @param word the word
   * @return the definition
   */
  public String getDefinition(String word) {
    autoLoad();

    return definitions.get(word.toLowerCase());
  }

  /**
   * Adds the synonym.
   *
   * @param word    the word
   * @param synonym the synonym
   */
  public void addSynonym(String word, String synonym) {
    String s = word.toLowerCase();

    if (!synonyms.containsKey(s)) {
      synonyms.put(s, new HashSet<String>());
    }

    synonyms.get(s).add(synonym);

    addWord(synonym);
  }

  /**
   * Returns the synonyms of a given word. The list will always contain the search
   * word.
   *
   * @param word the word
   * @return the synonyms
   */
  public Set<String> getSynonyms(String word) {
    autoLoad();

    Set<String> words = new HashSet<String>();

    words.add(word);

    String s = word.toLowerCase();

    if (synonyms.containsKey(s)) {
      for (String synoynm : synonyms.get(s)) {
        words.add(synoynm);
      }
    }

    return words;
  }
}
