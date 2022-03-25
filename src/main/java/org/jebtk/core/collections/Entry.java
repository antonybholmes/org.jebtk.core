package org.jebtk.core.collections;

public class Entry<K, V> implements java.util.Map.Entry<K, V> {

  private K mK;
  private V mV;

  public Entry(K k, V v) {
    mK = k;
    mV = v;
  }

  @Override
  public K getKey() {
    return mK;
  }

  @Override
  public V getValue() {
    return mV;
  }

  @Override
  public V setValue(V value) {
    return null;
  }

}
