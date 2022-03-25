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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayDeque;
import java.util.Deque;

import org.jebtk.core.ColorUtils;
import org.jebtk.core.Mathematics;
import org.jebtk.core.http.URLPath;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.StreamUtils;
import org.jebtk.core.text.TextUtils;

/**
 * Parses json into an object structure.
 * 
 * @author Antony Holmes
 *
 */
public class JsonParser {

  /**
   * The Constant BUFFER_SIZE.
   */
  // private static final int BUFFER_SIZE = 16777216; //65536;

  /**
   * The member buffer.
   */
  private StringBuilder mBuffer = JsonBuilder.createBuffer();

  /** The m unicode buffer. */
  private StringBuilder mUnicodeBuffer = JsonBuilder.createBuffer();

  /**
   * The member element stack.
   */
  private Deque<Json> mElementStack = new ArrayDeque<Json>();

  /**
   * The string mode.
   */
  private boolean mStringMode = false;

  /**
   * The escaped.
   */
  // private boolean mQuoteMode = false;

  /**
   * The member current name.
   */
  private CharSequence mCurrentName = null;

  /** The m escaped mode. */
  private boolean mEscapedMode = false;

  /** The m unicode mode. */
  private boolean mUnicodeMode = false;

  private byte[] mByteBuffer = StreamUtils.createBuffer();

  /**
   * Keep track of previously read character.
   */
  // private char mPc = 0;

  /**
   * Reset.
   */
  private void reset() {
    // mElementStack = new ArrayDeque<JsonValue>();

    mBuffer.setLength(0);
    mCurrentName = null;
    mStringMode = false;
    mEscapedMode = false;
    mUnicodeMode = false;
  }

  /**
   * Gets the currently parsed JSON as a JSON structure.
   *
   * @return the json
   */
  private Json getJson() {
    if (!mElementStack.isEmpty()) {
      return mElementStack.pop();
    } else {
      return null;
    }
  }

  public Json parse(URLPath url) throws IOException {
    return parse(url.openConnection());
  }

  /**
   * Reads the response from a URL and parses it as JSON.
   *
   * @param url the url
   * @return the json value
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Json parse(URL url) throws IOException {
    return parse(url.openConnection());
  }

  /**
   * Parses the.
   *
   * @param connection the connection
   * @return the json value
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private Json parse(URLConnection connection) throws IOException {
    return parse(StreamUtils.newBuffer(connection.getInputStream()));
  }

  /**
   * Parses the.
   *
   * @param file the file
   * @return the json
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Json parse(File file) throws IOException {
    return parse(file.toPath());
  }

  /**
   * Parses the.
   *
   * @param file the file
   * @return the json
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Json parse(Path file) throws IOException {
    return parse(FileUtils.newBufferedInputStream(file));
  }

  /**
   * Parses the.
   *
   * @param in the in
   * @return the json
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public Json parse(InputStream in) throws IOException {
    reset();

    int c;

    try {
      // Read blocks of data for speed
      while ((c = in.read(mByteBuffer)) != -1) {
        for (int i = 0; i < c; ++i) {
          parse((char) mByteBuffer[i]);
        }
      }
    } finally {
      in.close();
    }

    return getJson();
  }

  /**
   * Parses the.
   *
   * @param json the json
   * @return the json value
   * @throws ParseException the parse exception
   */
  public Json parse(String json) throws ParseException {
    return parse(json.toCharArray());
  }

  /**
   * Parse a JSON string of the form { ... }.
   *
   * @param json the json
   * @return the json value
   * @throws ParseException the parse exception
   */
  public Json parse(char[] json) throws ParseException {
    if (json == null) {
      return null;
    }

    if (json.length == 0) {
      return null;
    }

    char c = 0;

    reset();

    for (int i = 0; i < json.length; ++i) {
      c = json[i];

      parse(c);
    }

    return getJson();
  }

  /**
   * Parse a single char and construct the running JSON structure using it.
   *
   * @param c the c
   */
  private void parse(char c) {
    // System.err.println("buffer: " + mBuffer);

    if (mUnicodeMode) {
      // In the unicode mode, we buffer 4 characters then convert
      // that string to an int using hex decoding and finally convert
      // the unicode char array to a string and append it to the
      // running buffer
      mUnicodeBuffer.append(c);

      if (mUnicodeBuffer.length() == 4) {
        mBuffer.append(new String(Character.toChars(Integer.parseInt(mUnicodeBuffer.toString(), 16))));

        mUnicodeBuffer.setLength(0);

        mUnicodeMode = false;
      }

      // Whilst in unicode mode, skip the regular parser
      return;
    }

    switch (c) {
    case JsonBuilder.JSON_OBJECT_START:
      if (mStringMode) {
        mBuffer.append(c);
      } else {
        Json o = new JsonObject();

        if (mCurrentName != null) {
          mElementStack.peek().add(mCurrentName.toString(), o);

          mCurrentName = null;
        } else {
          // The object is being added to an array
          if (mElementStack.size() > 0) {
            mElementStack.peek().add(o);
          }
        }

        mElementStack.push(o);
      }

      break;
    case JsonBuilder.JSON_ARRAY_START:
      if (mStringMode) {
        mBuffer.append(c);
      } else {
        Json o = new JsonArray();

        if (mCurrentName != null) {
          mElementStack.peek().add(mCurrentName.toString(), o);

          mCurrentName = null;
        } else {
          // The array is being added to an array
          if (mElementStack.size() > 0) {
            mElementStack.peek().add(o);
          }
        }

        mElementStack.push(o);
      }

      break;
    case JsonBuilder.JSON_ARRAY_END:
    case JsonBuilder.JSON_OBJECT_END:
      if (mStringMode) {
        mBuffer.append(c);
      } else {
        addJsonValue();

        mCurrentName = null;

        // go back to reading variable name
        // buffer = new StringBuilder();
        mBuffer.setLength(0);

        if (mElementStack.size() > 1) {
          mElementStack.pop();
        }
      }

      break;
    case ',':
      // We will have already encountered
      // the member name since it must come
      // before the comma

      if (mStringMode) {
        mBuffer.append(c);
      } else {
        // Reading a comma implies we should add something to the
        // the json objects. We only do this if the buffer has
        // been filled. If the buffer is empty it means there is
        // nothing to do and we have just processed either an array
        // or an object so we do nothing since the comma is
        // redundant in this case
        addJsonValue();

        mCurrentName = null;

        // go back to reading variable name
        mBuffer.setLength(0);
      }

      break;
    case ':':
      if (mStringMode) {
        mBuffer.append(c);
      } else {
        // The name minus quotes
        mCurrentName = mBuffer.toString(); // .subSequence(1, mBuffer.length() -
                                           // 1);
                                           // //TextUtils.removeQuotes(String.valueOf(buffer,
                                           // 0, bc));

        mBuffer.setLength(0);
      }

      break;
    case ' ':
      if (mStringMode) {
        mBuffer.append(c);
      }

      break;
    case '\\':
      if (mStringMode) {
        if (mEscapedMode) {
          mBuffer.append(c);
        }

        // If this is first \ we encounter then put parser into
        // escaped mode. If the string is \\ then flip the
        // escaped mode to false since the first \ told us we
        // are in escape mode and the second \ is the escaped
        // character to be inserted into the JSON string and
        // should not be treated as an escape mode switch
        mEscapedMode = !mEscapedMode;
      }

      break;
    case '"':
      if (mEscapedMode) {
        mBuffer.append(c);
      } else {
        // either about to begin or
        // end a string

        mStringMode = !mStringMode;
      }

      break;
    case '\'':
      if (mStringMode) {
        mBuffer.append(c);
      }

      break;
    case '/':
      // We don't care if forward slash is escaped or not though
      // to conform to the strict spec we should
      // if (mStringMode && mEscapedMode) {
      if (mStringMode) {
        mBuffer.append(c);
      }

      break;
    case 'b':
      if (mStringMode && mEscapedMode) {
        mBuffer.append('\b');
      } else {
        mBuffer.append(c);
      }

      break;
    case 'f':
      if (mStringMode && mEscapedMode) {
        mBuffer.append('\f');
      } else {
        mBuffer.append(c);
      }

      break;
    case 'n':
      if (mStringMode && mEscapedMode) {
        mBuffer.append('\n');
      } else {
        mBuffer.append(c);
      }

      break;
    case 'r':
      if (mStringMode && mEscapedMode) {
        mBuffer.append('\r');
      } else {
        mBuffer.append(c);
      }

      break;
    case 't':
      if (mStringMode && mEscapedMode) {
        mBuffer.append('\t');
      } else {
        mBuffer.append(c);
      }

      break;
    case 'u':
      if (mStringMode && mEscapedMode) {
        mUnicodeMode = true;
      } else {
        mBuffer.append(c);
      }

      break;
    case '\b':
    case '\f':
    case '\n':
    case '\r':
    case '\t':
      // skip formatting characters
      break;
    default:
      // The default is to append to a buffer
      // which will become a member name.
      mBuffer.append(c);

      break;
    }

    if (c != '\\') {
      // Escaped is reset every time we encounter a new character.
      // This actually relaxes what can be accepted as JSON string
      // since any character can be treated as an escape character
      // so this is not a strict parser.
      mEscapedMode = false;
    }

    // mPc = c;
  }

  /**
   * Add the appropriate JSON value to the current structure.
   */
  private void addJsonValue() {
    if (mCurrentName == null && mBuffer.length() == 0) {
      return;
    }

    // System.err.println("add " + mCurrentName + " " + mBuffer);

    Json value = null;

    String s = mBuffer.toString();

    if (s.length() == 0) {
      // If the buffer is zero length, it means we have an zero
      // length string
      value = new JsonString(TextUtils.EMPTY_STRING);
    } else if (s.equals(TextUtils.NULL)) {
      value = null;
    } else if (s.toLowerCase().equals(TextUtils.TRUE)) {
      value = new JsonBoolean(true);
    } else if (s.toLowerCase().equals(TextUtils.FALSE)) {
      value = new JsonBoolean(false);
    } else if (ColorUtils.isHtmlColor(s)) {
      value = new JsonColor(ColorUtils.decodeHtmlColor(s));
    } else if (TextUtils.isNumber(s)) {
      double v = Double.parseDouble(s);

      if (Mathematics.isInt(v)) {
        value = new JsonInteger((int) v);
      } else {
        value = new JsonDouble(v);
      }
    } else {
      value = new JsonString(s);
    }

    if (mCurrentName != null) {
      // we are adding a field to an object
      mElementStack.peek().add(mCurrentName.toString(), value);
    } else {
      // we are adding to an array
      mElementStack.peek().add(value);
    }
  }

  /**
   * Json.
   *
   * @param file the file
   * @return the json
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Json json(Path file) throws IOException {
    return new JsonParser().parse(file);
  }
}