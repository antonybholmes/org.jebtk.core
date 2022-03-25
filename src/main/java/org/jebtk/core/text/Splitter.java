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
package org.jebtk.core.text;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jebtk.core.collections.IterHashMap;
import org.jebtk.core.collections.IterMap;
import org.jebtk.core.collections.MaxSizeArrayList;
import org.jebtk.core.stream.Stream;

/**
 * The Class Splitter takes a string and splits it into substrings according to
 * a delimiter or set of delimiters.
 */
public class Splitter {

  /**
   * The default size of the list of splits. This is to reduce resizing operations
   * on the list in most cases.
   */
  private static final int DEFAULT_SPLIT_SIZE = 32;

  /**
   * Split a string using a char as the delimiter.
   * 
   * @author Antony Holmes
   */
  public static class CharSplitMode implements SplitMode {

    /** The m delimiter. */
    private final char mDelimiter;

    /**
     * Instantiates a new char split mode.
     *
     * @param delimiter the delimiter
     */
    public CharSplitMode(char delimiter) {
      mDelimiter = delimiter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Splitter.SplitMode#split(java.lang.String,
     * org.abh.common.text.Trimmer, boolean, int)
     */
    @Override
    public List<String> split(final String text, boolean ignoreEmptyStrings, int maxNumItems) {

      List<String> ret = new MaxSizeArrayList<>(DEFAULT_SPLIT_SIZE, maxNumItems);

      String v;

      int i = 0;
      int j = text.indexOf(mDelimiter); // First substring

      while (j != -1 && ret.size() < maxNumItems) {
        v = text.substring(i, j);

        if (!ignoreEmptyStrings || v.length() > 0) {
          ret.add(v);
        }

        i = j + 1;
        j = text.indexOf(mDelimiter, i);
      }

      // Process the last token, if there is one

      if (i < text.length()) {
        v = text.substring(i);

        if (!ignoreEmptyStrings || v.length() > 0) {
          ret.add(v);
        }
      }

      return ret;
    }
  }

  /**
   * Split a string using any of the delimiters in the list to search for sub
   * strings.
   * 
   * @author Antony Holmes
   *
   */
  public static class CharsSplitMode implements SplitMode {

    /** The m delim chars. */
    private final char[] mDelimChars;

    /**
     * Instantiates a new char split mode.
     *
     * @param delimChars the delim chars
     */
    public CharsSplitMode(char... delimChars) {
      mDelimChars = delimChars;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Splitter.SplitMode#split(java.lang.String,
     * org.abh.common.text.Trimmer, boolean, int)
     */
    @Override
    public List<String> split(final String text, boolean ignoreEmptyStrings, int maxNumItems) {

      char[] trimmed = text.toCharArray();

      List<String> list = new MaxSizeArrayList<>(DEFAULT_SPLIT_SIZE, maxNumItems);

      String v;

      int s = 0;
      int e = 0; // First substring

      while (true) {
        e = -1;

        // Iterate over what is remaining of the string looking
        // for one of the chars we are interested in
        for (int i = s; i < trimmed.length; ++i) {
          for (int j = 0; j < mDelimChars.length; ++j) {
            if (trimmed[i] == mDelimChars[j]) {
              e = i;
              break;
            }
          }

          if (e != -1) {
            break;
          }
        }

        if (e != -1) {
          v = new String(trimmed, s, e - s);

          if (!ignoreEmptyStrings || v.length() > 0) {
            list.add(v);
          }

          s = e + 1;
        } else {
          // There is no end delimiter which means we are at the
          // end of the string
          break;
        }
      }

      // Process the last token, if there is one

      v = new String(trimmed, s, trimmed.length - s);

      if (!ignoreEmptyStrings || v.length() > 0) {
        list.add(v);
      }

      return list;
    }
  }

  /**
   * Split a string using another string as a delimiter.
   */
  public static class TextSplitMode implements SplitMode {

    /** The m delimiter. */
    private final String mDelimiter;

    /**
     * Instantiates a new text split mode.
     *
     * @param delimiter the delimiter
     */
    public TextSplitMode(final String delimiter) {
      mDelimiter = delimiter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Splitter.SplitMode#split(java.lang.String,
     * org.abh.common.text.Trimmer, boolean, int)
     */
    @Override
    public List<String> split(String text, boolean ignoreEmptyStrings, int maxNumItems) {
      List<String> ret = new MaxSizeArrayList<>(DEFAULT_SPLIT_SIZE, maxNumItems);

      String v;

      int i = 0;
      int j = 0;

      int n = mDelimiter.length();

      while ((j = text.indexOf(mDelimiter, i)) != -1 && ret.size() < maxNumItems) {
        // System.err.println(text + " " + trimmed + " " + i + " " + j);

        v = text.substring(i, j);

        if (!ignoreEmptyStrings || v.length() > 0) {
          ret.add(v);
        }

        // skip past the delimiter to the next symbol
        i = j + n;
      }

      // Process the last token, if there is one

      v = text.substring(i);

      if (!ignoreEmptyStrings || v.length() > 0) {
        ret.add(v);
      }

      return ret;
    }

  }

  /**
   * Split a string using a regex as the delimiter.
   */
  public static class PatternSplitMode implements SplitMode {

    /** The m delimiter. */
    private final Pattern mDelimiter;

    /**
     * Instantiates a new pattern split mode.
     *
     * @param delimiter the delimiter
     */
    public PatternSplitMode(Pattern delimiter) {
      mDelimiter = delimiter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Splitter.SplitMode#split(java.lang.String,
     * org.abh.common.text.Trimmer, boolean, int)
     */
    @Override
    public List<String> split(final String text, boolean ignoreEmptyStrings, int maxNumItems) {

      List<String> list = new MaxSizeArrayList<>(DEFAULT_SPLIT_SIZE, maxNumItems);

      String v;

      Matcher matcher = mDelimiter.matcher(text);

      int i = 0;
      int j = 0; // First substring

      while (matcher.find()) {
        j = matcher.start();

        v = text.substring(i, j);

        if (!ignoreEmptyStrings || v.length() > 0) {
          list.add(v);
        }

        i = matcher.end() + 1;
      }

      v = text.substring(i);

      if (!ignoreEmptyStrings || v.length() > 0) {
        list.add(v);
      }

      return list;
    }
  }

  /**
   * Split a CSV string by commas (also taking into account values in quotation
   * marks.
   */
  public static class CSVSplitMode implements SplitMode {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Splitter.SplitMode#split(java.lang.String,
     * org.abh.common.text.Trimmer, boolean, int)
     */
    @Override
    public List<String> split(String text, boolean ignoreEmptyStrings, int maxNumItems) {

      List<String> ret = new ArrayList<>();

      boolean quoteMode = false;

      StringBuilder buffer = new StringBuilder();

      for (int i = 0; i < text.length(); ++i) {
        char c = text.charAt(i);

        if (c == '"') {
          quoteMode = !quoteMode;
        } else if (quoteMode) {
          buffer.append(c);
        } else if (c == ',') {
          String v = buffer.toString();

          if (!ignoreEmptyStrings || v.length() > 0) {
            ret.add(v);
          }

          if (ret.size() == maxNumItems) {
            return ret;
          }

          buffer.setLength(0);
        } else if (c == '=') {
          // Ignore
        } else {
          buffer.append(c);
        }
      }

      String v = buffer.toString();

      if (!ignoreEmptyStrings || v.length() > 0) {
        ret.add(v);
      }

      return ret;
    }
  }

  /** The m igore empty strings. */
  private final boolean mIgoreEmptyStrings;

  /** The m split mode. */
  private final SplitMode mSplitMode;

  /** The m trimmer. */
  private final Trimmer mTrimmer;

  /** The m maxNumItems. */
  private final int mLimit;

  /**
   * Instantiates a new splitter.
   *
   * @param splitMode          the split mode
   * @param trimmer            the trimmer
   * @param ignoreEmptyStrings the ignore empty strings
   * @param maxNumItems        the maxNumItems
   */
  public Splitter(SplitMode splitMode, Trimmer trimmer, boolean ignoreEmptyStrings, int maxNumItems) {
    mSplitMode = splitMode;
    mTrimmer = trimmer;
    mIgoreEmptyStrings = ignoreEmptyStrings;
    mLimit = maxNumItems;
  }

  /**
   * Splits some text into multiple sub strings, based on a delimiter or pattern.
   *
   * @param text the text
   * @return the list
   */
  public List<String> text(final String text) {
    return mTrimmer.trim(mSplitMode, text, mIgoreEmptyStrings, mLimit);
  }

  public Stream<String> stream(String text) {
    return Stream.asString(text(text));
  }

  /**
   * Ignore empty strings.
   *
   * @return the splitter
   */
  public Splitter ignoreEmptyStrings() {
    return new Splitter(mSplitMode, mTrimmer, true, mLimit);
  }

  /**
   * Returns a maximum of {@code maxNumItems} split items.
   *
   * @param maxNumItems the maxNumItems
   * @return the splitter
   */
  public Splitter limit(int maxNumItems) {
    return new Splitter(mSplitMode, mTrimmer, mIgoreEmptyStrings, maxNumItems);
  }

  /**
   * Trim.
   *
   * @return the splitter
   */
  public Splitter trim() {
    return trim(Trimmer.onSpace());
  }

  /**
   * Trim.
   *
   * @param delimiter the delimiter
   * @return the splitter
   */
  public Splitter trim(char delimiter) {
    return trim(Trimmer.on(delimiter));
  }

  /**
   * Trim.
   *
   * @param trimmer the trimmer
   * @return the splitter
   */
  public Splitter trim(Trimmer trimmer) {
    return new Splitter(mSplitMode, trimmer, mIgoreEmptyStrings, Integer.MAX_VALUE);
  }

  /**
   * On.
   *
   * @param delimiter the delimiter
   * @return the splitter
   */
  public static Splitter on(char delimiter) {
    if (delimiter == ',') {
      // Commas are treated as csv which get special handling.
      return onComma();
    } else {
      return new Splitter(new CharSplitMode(delimiter), Trimmer.NO_TRIM, false, Integer.MAX_VALUE);
    }
  }

  /**
   * On.
   *
   * @param delimiters the delimiters
   * @return the splitter
   */
  public static Splitter on(char... delimiters) {
    return new Splitter(new CharsSplitMode(delimiters), Trimmer.NO_TRIM, false, Integer.MAX_VALUE);
  }

  /**
   * On.
   *
   * @param delimiter the delimiter
   * @return the splitter
   */
  public static Splitter on(String delimiter) {
    return new Splitter(new TextSplitMode(delimiter), Trimmer.NO_TRIM, false, Integer.MAX_VALUE);
  }

  /**
   * On.
   *
   * @param regex the regex
   * @return the splitter
   */
  public static Splitter on(Pattern regex) {
    return new Splitter(new PatternSplitMode(regex), Trimmer.NO_TRIM, false, Integer.MAX_VALUE);
  }

  /**
   * On tab.
   *
   * @return the splitter
   */
  public static Splitter onTab() {
    return on(TextUtils.TAB_DELIMITER);
  }

  /**
   * On dash.
   *
   * @return the splitter
   */
  public static Splitter onDash() {
    return on(TextUtils.DASH_DELIMITER);
  }

  /**
   * On space.
   *
   * @return the splitter
   */
  public static Splitter onSpace() {
    return on(TextUtils.SPACE_DELIMITER);
  }

  /**
   * Return a splitter that splits on semi-colons.
   * 
   * @return
   */
  public static Splitter onSC() {
    return on(TextUtils.SEMI_COLON_DELIMITER);
  }

  public static Splitter onColon() {
    return on(TextUtils.COLON_DELIMITER);
  }

  /**
   * On comma.
   *
   * @return the splitter
   */
  public static Splitter onComma() {
    return new Splitter(new CSVSplitMode(), Trimmer.NO_TRIM, false, Integer.MAX_VALUE);
  }

  /**
   * Assumes each string in values is of the form key<delimiter>value and splits
   * each string and adds the key value pairs to a map. Suitable only if keys are
   * unique.
   *
   * @param values    the values
   * @param delimiter the delimiter
   * @return the map
   */
  public static IterMap<String, String> toMap(List<String> values, char delimiter) {
    IterMap<String, String> ret = new IterHashMap<>();

    for (String value : values) {
      List<String> tokens = on(delimiter).text(value);

      ret.put(tokens.get(0), tokens.get(1));
    }

    return ret;
  }

}
