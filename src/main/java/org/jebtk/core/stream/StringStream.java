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
package org.jebtk.core.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jebtk.core.Function;
import org.jebtk.core.text.TextUtils;

/**
 * Represents a stream of string objects and has associated reduce functions to
 * process strings.
 * 
 * @author Antony Holmes
 *
 */
public class StringStream extends ContainerStream<String> {

  /**
   * The Class JoinFunction.
   */
  private static class JoinFunction implements ReduceFunction<String, String> {

    /** The m delimiter. */
    private String mDelimiter;

    /**
     * Instantiates a new join function.
     *
     * @param delimiter the delimiter
     */
    public JoinFunction(String delimiter) {
      mDelimiter = delimiter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public String apply(Stream<String> stream) {
      StringBuilder buffer = new StringBuilder();

      // No point doing anything unless there is at least one element
      // to stream
      if (stream.hasNext()) {
        String item = stream.next();

        if (item != null) {
          buffer.append(item.toString());
        }

        while (stream.hasNext()) {
          item = stream.next();

          buffer.append(mDelimiter);

          if (item != null) {
            buffer.append(item.toString());
          }
        }
      }

      return buffer.toString();
    }
  }

  /**
   * Removes empty strings from a stream.
   */
  private static class EmptyFilter implements Filter<String> {

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.stream.Filter#keep(java.lang.Comparable)
     */
    @Override
    public boolean keep(String s) {
      return !TextUtils.isNullOrEmpty(s);
    }
  }

  /**
   * Removes empty strings from a stream.
   */
  private static class FindFilter implements Filter<String> {

    /** The m text. */
    private String mText;

    /** The m case sensitive. */
    private boolean mCaseSensitive;

    /**
     * Instantiates a new find filter.
     *
     * @param text          the text
     * @param caseSensitive the case sensitive
     */
    public FindFilter(String text, boolean caseSensitive) {
      mCaseSensitive = caseSensitive;

      if (caseSensitive) {
        mText = text;
      } else {
        mText = text.toLowerCase();
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.stream.Filter#keep(java.lang.Comparable)
     */
    @Override
    public boolean keep(String s) {
      if (mCaseSensitive) {
        return s.contains(mText);
      } else {
        return s.toLowerCase().contains(mText);
      }
    }
  }

  /**
   * The Class FindByRegexFilter.
   */
  private static class FindByRegexFilter implements Filter<String> {

    /** The m regex. */
    private Pattern mRegex;

    /**
     * Instantiates a new find by regex filter.
     *
     * @param regex the regex
     */
    public FindByRegexFilter(Pattern regex) {
      mRegex = regex;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.stream.Filter#keep(java.lang.Comparable)
     */
    @Override
    public boolean keep(String s) {
      return mRegex.matcher(s).find();
    }
  }

  /**
   * The Class FindIndicesFunction.
   */
  private static class FindIndicesFunction implements ReduceFunction<String, List<Integer>> {

    /** The m text. */
    private String mText;

    /** The m case sensitive. */
    private boolean mCaseSensitive;

    /**
     * Instantiates a new find indices function.
     *
     * @param text          the text
     * @param caseSensitive the case sensitive
     */
    public FindIndicesFunction(String text, boolean caseSensitive) {
      mCaseSensitive = caseSensitive;

      if (caseSensitive) {
        mText = text;
      } else {
        mText = text.toLowerCase();
      }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public List<Integer> apply(Stream<String> stream) {
      List<Integer> ret = new ArrayList<Integer>();

      int c = 0;

      if (mCaseSensitive) {
        while (stream.hasNext()) {
          String s = stream.next();

          if (s.contains(mText)) {
            ret.add(c);
          }

          ++c;
        }
      } else {
        while (stream.hasNext()) {
          String s = stream.next();

          if (s.toLowerCase().contains(mText)) {
            ret.add(c);
          }

          ++c;
        }
      }

      return ret;
    }
  }

  /**
   * The Class FindIndicesByRegexFunction.
   */
  private static class FindIndicesByRegexFunction implements ReduceFunction<String, List<Integer>> {

    /** The m regex. */
    private Pattern mRegex;

    /**
     * Instantiates a new find indices by regex function.
     *
     * @param regex the regex
     */
    public FindIndicesByRegexFunction(Pattern regex) {
      mRegex = regex;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public List<Integer> apply(Stream<String> stream) {
      List<Integer> ret = new ArrayList<Integer>();

      int c = 0;

      while (stream.hasNext()) {
        String s = stream.next();

        if (mRegex.matcher(s).find()) {
          ret.add(c);
        }

        ++c;
      }

      return ret;
    }
  }

  /**
   * The Class AppendFunction.
   */
  private static class AppendFunction implements Function<String, String> {

    /** The m places. */
    private String mV;

    /** The m N. */
    private int mN;

    /** The m C. */
    private int mC;

    /**
     * Instantiates a new round function.
     *
     * @param v     the v
     * @param start the start
     * @param n     the n
     */
    public AppendFunction(String v, int start, int n) {
      mV = v;
      mN = Math.max(1, n);
      mC = Math.max(0, start);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.Function#apply(java.lang.Object)
     */
    @Override
    public String apply(String v) {
      if (mC++ % mN == 0) {
        return v + mV;
      } else {
        return v;
      }
    }
  }

  /**
   * Instantiates a new string stream.
   *
   * @param stream the stream
   */
  public StringStream(Stream<String> stream) {
    super(stream);
  }

  /**
   * Removes empty strings from the stream.
   *
   * @return the string stream
   */
  public StringStream emptyFilter() {
    return new StringStream(filter(new EmptyFilter()));
  }

  /**
   * Join is a reduce function that collapses a stream to a single string
   * consisting of the string representations of each item in the stream
   * concatenated and separated by a given separator.
   *
   * @param delimiter the delimiter
   * @return the string
   */
  public String join(String delimiter) {
    return reduce(new JoinFunction(delimiter));
  }

  /**
   * Join the stream using the TAB character as the delimiter.
   *
   * @return the string
   */
  public String tabJoin() {
    return join(TextUtils.TAB_DELIMITER);
  }

  /**
   * Join the stream using the comma (',') character as the delimiter.
   *
   * @return the string
   */
  public String commaJoin() {
    return join(TextUtils.COMMA_DELIMITER);
  }

  /**
   * Find strings matching a regex.
   *
   * @param text the text
   * @return the string stream
   */
  public StringStream find(String text) {
    return find(text, false);
  }

  /**
   * Find.
   *
   * @param text          the text
   * @param caseSensitive the case sensitive
   * @return the string stream
   */
  public StringStream find(String text, boolean caseSensitive) {
    return new StringStream(filter(new FindFilter(text, caseSensitive)));
  }

  /**
   * Find strings matching a regex.
   *
   * @param regex the regex
   * @return the string stream
   */
  public StringStream find(Pattern regex) {
    return new StringStream(filter(new FindByRegexFilter(regex)));
  }

  /**
   * Find the indices of items in the stream matching a string.
   *
   * @param text the text
   * @return the list
   */
  public List<Integer> indices(String text) {
    return indices(text, false);
  }

  /**
   * Find the indices of items in the stream matching a string.
   *
   * @param text          the text
   * @param caseSensitive the case sensitive
   * @return the list
   */
  public List<Integer> indices(String text, boolean caseSensitive) {
    return reduce(new FindIndicesFunction(text, caseSensitive));
  }

  /**
   * Find the indices of items in the stream matching a regex.
   *
   * @param regex the regex
   * @return the list
   */
  public List<Integer> indices(Pattern regex) {
    return reduce(new FindIndicesByRegexFunction(regex));
  }

  /**
   * Append a string to each element.
   *
   * @param v the v
   * @return the string stream
   */
  public StringStream append(String v) {
    return append(v, 1);
  }

  /**
   * Append.
   *
   * @param v the v
   * @param n the n
   * @return the string stream
   */
  public StringStream append(String v, int n) {
    return append(v, 0, n);
  }

  /**
   * Append.
   *
   * @param v     the v
   * @param start the start
   * @param n     the n
   * @return the string stream
   */
  public StringStream append(String v, int start, int n) {
    return new StringStream(map(new AppendFunction(v, start, n)));
  }
}
