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
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.Io;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.text.TextUtils;
import org.xml.sax.SAXException;

/**
 * Provides a substitution service so strings/words can be mapped to an
 * alternative. This is designed for cases where user data has common
 * misspellings etc.
 * 
 * @author Antony Holmes
 *
 */
public class SubstitutionService {

  /**
   * The substitutions.
   */
  private Map<String, String> substitutions = new HashMap<String, String>();

  private boolean mAutoLoad = true;

  /**
   * The constant DEFAULT_FILE.
   */
  public static final Path DEFAULT_FILE = PathUtils.getPath("res/substitutions.txt");

  /**
   * The constant instance.
   */
  private static final SubstitutionService instance = new SubstitutionService();

  /**
   * Gets the single instance of SubstitutionService.
   *
   * @return single instance of SubstitutionService
   */
  public static final SubstitutionService getInstance() {
    return instance;
  }

  private void autoLoad() {
    if (mAutoLoad) {
      // Set this here to stop recursive infinite calling
      // of this method.
      mAutoLoad = false;

      try {
        loadTSVFile(DEFAULT_FILE);
      } catch (IOException e) {
        e.printStackTrace();
      }
      // autoLoadJson();

    }
  }

  /**
   * Load xml.
   *
   * @param file the file
   * @throws IOException
   * @throws SAXException
   * @throws ParserConfigurationException
   */
  public void loadXml(Path file) throws SAXException, IOException, ParserConfigurationException {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser saxParser = factory.newSAXParser();

    SubstitutionXmlHandler handler = new SubstitutionXmlHandler(this);

    saxParser.parse(file.toFile(), handler);
  }

  /**
   * Load tsv file.
   *
   * @param file the file
   * @throws IOException
   */
  public void loadTSVFile(Path file) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    String line;

    try {
      while ((line = reader.readLine()) != null) {
        if (Io.isEmptyLine(line)) {
          continue;
        }

        List<String> tokens = TextUtils.fastSplit(line, TextUtils.TAB_DELIMITER);

        addSubstitution(tokens.get(0), tokens.get(1));
      }
    } finally {
      reader.close();
    }
  }

  /**
   * Adds the substitution.
   *
   * @param word      the word
   * @param subsitute the subsitute
   */
  public void addSubstitution(String word, String subsitute) {
    substitutions.put(word, subsitute);
  }

  /**
   * Gets the substitute.
   *
   * @param word the word
   * @return the substitute
   */
  public String getSubstitute(String word) {
    autoLoad();

    if (word == null) {
      return null;
    }

    if (!substitutions.containsKey(word)) {
      return word;
    }

    return substitutions.get(word);
  }
}
