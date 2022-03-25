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
package org.jebtk.core.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;

import org.jebtk.core.text.TextUtils;

/**
 * The Class Tokenize.
 */
public class Tokenize {

  /** The m tf. */
  private TokenFunction mTf;

  /** The m skip. */
  private boolean mSkip = false;

  /** The m delim. */
  private String mDelim = TextUtils.TAB_DELIMITER;

  /**
   * Instantiates a new tokenize.
   *
   * @param tf the tf
   */
  public Tokenize(TokenFunction tf) {
    mTf = tf;
  }

  /**
   * Instantiates a new tokenize.
   *
   * @param t the t
   */
  private Tokenize(Tokenize t) {
    mTf = t.mTf;
    mSkip = t.mSkip;
    mDelim = t.mDelim;
  }

  /**
   * Skip header.
   *
   * @param skip the skip
   * @return the tokenize
   */
  public Tokenize skipHeader(boolean skip) {
    Tokenize tf = new Tokenize(this);
    tf.mSkip = skip;

    return tf;
  }

  /**
   * Sets the delimiter.
   *
   * @param d the d
   * @return the tokenize
   */
  public Tokenize setDelimiter(String d) {
    Tokenize tf = new Tokenize(this);
    tf.mDelim = d;

    return tf;
  }

  /**
   * Tokens.
   *
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void tokens(Path file) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    try {
      tokens(reader);
    } finally {
      reader.close();
    }
  }

  /**
   * Run through reader tokenizing each line for processing. Reader is closed
   * after function has been applied.
   *
   * @param reader the reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void tokens(BufferedReader reader) throws IOException {
    Lines lines = new Lines(new LineFunction() {

      @Override
      public void parse(String line) {
        if (!Io.isEmptyLine(line)) {
          mTf.parse(TextUtils.fastSplit(line, mDelim));
        }
      }
    }).skipHeader(mSkip);

    lines.lines(reader);
  }

  /**
   * Tokenize.
   *
   * @param tf the tf
   * @return the tokenize
   */
  public static Tokenize tokenize(TokenFunction tf) {
    return new Tokenize(tf);
  }
}
