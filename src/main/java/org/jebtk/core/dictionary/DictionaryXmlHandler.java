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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses a dictionary xml file. A dictionary file consists of the <dictionary>
 * tag within which are nested <word> tags within which are nested <synonym>
 * tags. The <word> and <synonym> tags must contain a name attribute to indicate
 * their values.
 * 
 * @author Antony Holmes
 *
 */
public class DictionaryXmlHandler extends DefaultHandler {

  /**
   * The service.
   */
  public DictionaryService service;

  /**
   * The word.
   */
  private String word;

  /**
   * The synonym.
   */
  private String synonym;

  /**
   * Instantiates a new dictionary xml handler.
   *
   * @param service the service
   */
  public DictionaryXmlHandler(DictionaryService service) {
    this.service = service;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
   * java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

    if (qName.equals("word")) {
      word = attributes.getValue("name");

      if (attributes.getValue("definition") != null) {
        service.addWord(word, attributes.getValue("definition"));
      } else {
        service.addWord(word);
      }

    } else if (qName.equals("synonym")) {
      synonym = attributes.getValue("name");

      service.addSynonym(word, synonym);
    } else {
      // do nothing
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  public void endElement(String uri, String localName, String qName) throws SAXException {
    // do nothing
  }
}
