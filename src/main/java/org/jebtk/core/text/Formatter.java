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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * The Class Formatter.
 */
public class Formatter {

  /**
   * Instantiates a new formatter.
   */
  private Formatter() {
    // Do nothing
  }

  /**
   * The Interface NumberFormatter.
   */
  public static interface NumberFormatter {

    /**
     * Format.
     *
     * @param v the v
     * @return the string
     */
    public String format(int v);

    /**
     * Format.
     *
     * @param v the v
     * @return the string
     */
    public String format(long v);

    /**
     * Format.
     *
     * @param v the v
     * @return the string
     */
    public String format(float v);

    /**
     * Format.
     *
     * @param v the v
     * @return the string
     */
    public String format(double v);

    /**
     * Format.
     *
     * @param <T>    the generic type
     * @param values the values
     * @return the list
     */
    public <T extends Number> List<String> format(Collection<T> values);
  }

  /**
   * The Class LocaleDpFormatter.
   */
  public static class LocaleDpFormatter implements NumberFormatter {

    /** The m nf. */
    public static NumberFormat mNf = NumberFormat.getInstance(Locale.getDefault());

    public LocaleDpFormatter() {
      this(3);
    }

    /**
     * Instantiates a new locale dp formatter.
     *
     * @param dp the dp
     */
    public LocaleDpFormatter(int dp) {
      // mNf.setMinimumFractionDigits(dp);
      mNf.setMaximumFractionDigits(dp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(int)
     */
    @Override
    public String format(int v) {
      return mNf.format(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(long)
     */
    @Override
    public String format(long v) {
      return mNf.format(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(float)
     */
    @Override
    public String format(float v) {
      return mNf.format(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(double)
     */
    @Override
    public String format(double v) {
      return mNf.format(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(java.util.
     * Collection)
     */
    @Override
    public <T extends Number> List<String> format(Collection<T> values) {
      List<String> ret = new ArrayList<>(values.size());

      for (Number value : values) {
        if (value instanceof Double) {
          ret.add(format(value.doubleValue()));
        } else if (value instanceof Float) {
          ret.add(format(value.floatValue()));
        } else if (value instanceof Long) {
          ret.add(format(value.longValue()));
        } else {
          ret.add(format(value.intValue()));
        }
      }

      return ret;
    }
  }

  /**
   * The Class DpFormatter.
   */
  public static class DpFormatter implements NumberFormatter {

    /** The m nf. */
    public static NumberFormat mNf;

    /**
     * Instantiates a new dp formatter.
     *
     * @param dp the dp
     */
    public DpFormatter(int dp) {
      mNf = new DecimalFormat("0." + TextUtils.repeat("0", dp));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(int)
     */
    @Override
    public String format(int v) {
      return mNf.format(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(long)
     */
    @Override
    public String format(long v) {
      return mNf.format(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(float)
     */
    @Override
    public String format(float v) {
      return mNf.format(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(double)
     */
    @Override
    public String format(double v) {
      return mNf.format(v);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.abh.common.text.Formatter.NumberFormatter#format(java.util.
     * Collection)
     */
    @Override
    public <T extends Number> List<String> format(Collection<T> values) {
      List<String> ret = new ArrayList<String>(values.size());

      for (Number value : values) {
        if (value instanceof Double) {
          ret.add(format(value.doubleValue()));
        } else if (value instanceof Float) {
          ret.add(format(value.floatValue()));
        } else if (value instanceof Long) {
          ret.add(format(value.longValue()));
        } else {
          ret.add(format(value.intValue()));
        }
      }

      return ret;
    }
  }

  /**
   * The Class LocaleFormatter.
   */
  public static class LocaleFormatter extends LocaleDpFormatter {

    /**
     * Instantiates a new locale formatter.
     */
    public LocaleFormatter() {
      super(2);
    }

    /**
     * Format a number with a maximum number of decimal places.
     *
     * @param dp the dp
     * @return the number formatter
     */
    public NumberFormatter dp(int dp) {
      return new LocaleDpFormatter(dp);
    }
  }

  /**
   * The Class DecimalFormatter.
   */
  public static class DecimalFormatter {

    /**
     * Format a number with a maximum number of decimal places.
     *
     * @param dp the dp
     * @return the number formatter
     */
    public NumberFormatter dp(int dp) {
      return new DpFormatter(dp);
    }
  }

  /**
   * Number.
   *
   * @return the locale formatter
   */
  public static LocaleFormatter number() {
    return new LocaleFormatter();
  }

  /**
   * Decimal.
   *
   * @return the decimal formatter
   */
  public static DecimalFormatter decimal() {
    return new DecimalFormatter();
  }
}
