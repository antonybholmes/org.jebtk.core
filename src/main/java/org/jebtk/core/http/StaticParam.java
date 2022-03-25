package org.jebtk.core.http;

public class StaticParam extends Param {
  private String mValue;
  private String mName;

  public StaticParam(String name, String value) {
    mName = name;
    mValue = value;
  }

  public StaticParam(String name, int value) {
    this(name, Integer.toString(value));
  }

  public StaticParam(String name, double value) {
    this(name, Double.toString(value));
  }

  public StaticParam(String name, Object value) {
    this(name, value.toString());
  }

  @Override
  public String getName() {
    return mName;
  }

  @Override
  public String getValue() {
    return mValue;
  }
}
