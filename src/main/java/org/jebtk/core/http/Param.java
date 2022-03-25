package org.jebtk.core.http;

import java.io.UnsupportedEncodingException;

import org.jebtk.core.NameGetter;

public abstract class Param implements NameGetter {

  public abstract String getValue();

  @Override
  public String toString() {
    return getParamString(getName(), getValue());
  }

  /**
   * Gets the param string.
   *
   * @param name  the name
   * @param value the value
   * @return the param string
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static String getParamString(String name, int value) {
    return getParamString(name, Integer.toString(value));
  }

  /**
   * Gets the param string.
   *
   * @param name  the name
   * @param value the value
   * @return the param string
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static String getParamString(String name, double value) {
    return getParamString(name, Double.toString(value));
  }

  /**
   * Gets the param string.
   *
   * @param name  the name
   * @param value the value
   * @return the param string
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static String getParamString(String name, Object value) {
    return getParamString(name, value.toString());
  }

  /**
   * Gets the param string.
   *
   * @param name  the name
   * @param value the value
   * @return the param string
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static String getParamString(String name, String value) {
    return new StringBuilder(URLPath.encode(name)).append("=").append(URLPath.encode(value)).toString();
  }
}
