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

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.text.TextUtils;

/**
 * Provides a dictionary service so strings/words can be mapped to synonyms.
 * 
 * @author Antony Holmes
 *
 */
public class KeywordsService implements Iterable<String>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The words.
   */
  private Set<String> words = new HashSet<String>();

  /**
   * The constant INSTANCE.
   */
  private static final KeywordsService INSTANCE = new KeywordsService();

  /**
   * Gets the single instance of KeywordsService.
   *
   * @return single instance of KeywordsService
   */
  public static final KeywordsService getInstance() {
    return INSTANCE;
  }

  /**
   * Load xml.
   *
   * @param file the file
   */
  public void loadXml(File file) {
    System.err.println("Parseing " + file);

    try {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      KeywordsXmlHandler handler = new KeywordsXmlHandler(this);

      saxParser.parse(file, handler);
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<String> iterator() {
    return words.iterator();
  }

  /**
   * Returns the list of keywords found in a given sentence.
   *
   * @param sentence the sentence
   * @return the list
   */
  public List<String> searchForKeywords(String sentence) {
    Set<String> found = TextUtils.keywords(sentence);

    Set<String> found2 = new HashSet<String>();

    String s = sentence.toLowerCase();

    for (String word : words) {
      if (s.contains(word)) {
        found2.add(word);
      }
    }

    return CollectionUtils.union(found, found2);
  }
}
