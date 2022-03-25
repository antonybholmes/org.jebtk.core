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
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;

/**
 * The Class Lines will read a file and return lines of text for processing.
 * This can be used to reduce boilerplate code that simply reads lines in a for
 * loop.
 */
public class Lines {

  /** The m tf. */
  private LineFunction mTf;

  /** The m skip. */
  private boolean mSkip = false;

  /**
   * Instantiates a new lines.
   *
   * @param tf the tf
   */
  public Lines(LineFunction tf) {
    mTf = tf;
  }

  /**
   * Instantiates a new lines.
   *
   * @param t the t
   */
  private Lines(Lines t) {
    mTf = t.mTf;
    mSkip = t.mSkip;
  }

  /**
   * Skip header.
   *
   * @param skip the skip
   * @return the lines
   */
  public Lines skipHeader(boolean skip) {
    Lines tf = new Lines(this);
    tf.mSkip = skip;

    return tf;
  }

  /**
   * Lines.
   *
   * @param file the file
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void lines(Path file) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    try {
      lines(reader);
    } finally {
      reader.close();
    }
  }

  public void lines(Reader reader) throws IOException {
    lines(StreamUtils.newBufferedReader(reader));
  }

  public void lines(InputStream s) throws IOException {
    lines(StreamUtils.newBufferedReader(s));
  }

  /**
   * Run through reader tokenizing each line for processing. Reader is closed
   * after function has been applied.
   *
   * @param reader the reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void lines(BufferedReader reader) throws IOException {
    // try {
    // Skip header
    if (mSkip) {
      reader.readLine();
    }

    String line;

    while ((line = reader.readLine()) != null) {
      mTf.parse(line);
    }
  }

  public static Lines lines(LineFunction f) {
    return new Lines(f);
  }
}
