package org.jebtk.core.collections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * IntIntMap2 without states array. We introduce one extra pairs of fields - for
 * key=0, which is used as 'used' flag
 * 
 * @param <T>
 */
public abstract class IntHashMap<T> implements IterMap<Integer, T> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final int FREE_KEY = 0;

  public static final int NO_VALUE = 0;

  /** Keys */
  protected int[] mKeys;

  /** Do we have 'free' key in the map? */
  protected boolean mHasFreeKey;
  /** Value of 'free' key */
  protected T mFreeValue = null;

  /** Fill factor, must be between (0 and 1) */
  private final double mFillFactor;
  /** We will resize a map once it reaches this size */
  private int mThreshold;
  /** Current map size */
  protected int mSize;
  /** Mask to calculate the original position */
  private int mMask;

  protected class IntEntry implements Entry<Integer, T> {

    private int mKey;
    private T mValue;

    public IntEntry(int key, T value) {
      mKey = key;
      mValue = value;
    }

    @Override
    public Integer getKey() {
      return mKey;
    }

    @Override
    public T getValue() {
      return mValue;
    }

    @Override
    public T setValue(T value) {
      return null;
    }

  }

  public IntHashMap(final int size, final double fillFactor) {
    if (fillFactor <= 0 || fillFactor >= 1) {
      throw new IllegalArgumentException("FillFactor must be in (0, 1)");
    }

    if (size <= 0) {
      throw new IllegalArgumentException("Size must be positive!");
    }

    final int capacity = arraySize(size, fillFactor);

    mMask = capacity - 1;
    mFillFactor = fillFactor;

    // mKeys = new int[capacity];
    // mValues = new int[capacity];
    // mThreshold = (int)(capacity * fillFactor);

    rehash(capacity);
  }

  @Override
  public T get(Object o) {
    final int key = (int) o;

    int ptr = (phiMix(key) & mMask) << 1;

    if (key == FREE_KEY)
      return mHasFreeKey ? mFreeValue : null; // NO_VALUE;

    int k = mKeys[ptr];

    if (k == FREE_KEY)
      return null; // end of chain already
    if (k == key) // we check FREE prior to this call
      return getValue(ptr);

    while (true) {
      ptr = (ptr + 1) & mMask; // that's next index
      k = mKeys[ptr];
      if (k == FREE_KEY)
        return null;
      if (k == key)
        return getValue(ptr);
    }
  }

  protected abstract T getValue(int index);

  protected abstract void setValue(int key, T value);

  @Override
  public T put(final Integer k, final T value) {
    final int key = k;

    if (key == FREE_KEY) {
      final T ret = mFreeValue;

      if (!mHasFreeKey) {
        ++mSize;
      }

      mHasFreeKey = true;
      mFreeValue = value;
      return ret;
    }

    int ptr = (phiMix(key) & mMask) << 1;
    int kv = mKeys[ptr];

    if (kv == FREE_KEY) {
      mKeys[ptr] = key;
      setValue(ptr, value);

      if (mSize >= mThreshold) {
        rehash(mKeys.length * 2); // size is set inside
      } else {
        ++mSize;
      }

      return null;
    }

    if (kv == key) {
      final T ret = getValue(ptr);
      setValue(ptr, value);
      return ret;
    }

    while (true) {
      ptr = (ptr + 1) & mMask; // that's next index calculation
      kv = mKeys[ptr];

      if (kv == FREE_KEY) {
        mKeys[ptr] = key;
        setValue(ptr, value);

        if (mSize >= mThreshold) {
          rehash(mKeys.length * 2); // size is set inside
        } else {
          ++mSize;
        }

        return null;
      }

      if (kv == key) {
        final T ret = getValue(ptr);
        setValue(ptr, value);
        return ret;
      }
    }
  }

  @Override
  public T remove(Object o) {
    final int key = (int) o;

    if (key == FREE_KEY) {
      if (!mHasFreeKey) {
        return null;
      }

      mHasFreeKey = false;
      --mSize;
      return mFreeValue; // value is not cleaned
    }

    int ptr = (phiMix(key) & mMask) << 1;
    int k = mKeys[ptr];

    if (k == key) {
      final T res = getValue(ptr);
      shiftKeys(ptr);
      --mSize;
      return res;
    }

    if (k == FREE_KEY) {
      return null; // end of chain already
    }

    while (true) {
      ptr = (ptr + 1) & mMask; // that's next index calculation
      k = mKeys[ptr];

      if (k == key) {
        final T res = getValue(ptr);
        shiftKeys(ptr);
        --mSize;
        return res;
      }

      if (k == FREE_KEY) {
        return null;
      }
    }
  }

  private int shiftKeys(int pos) {
    // Shift entries with the same hash.
    int last;
    int slot;
    int k;
    // final int[] data = this.m_keys;

    while (true) {
      pos = ((last = pos) + 1) & mMask;

      while (true) {
        if ((k = mKeys[pos]) == FREE_KEY) {
          mKeys[last] = FREE_KEY;
          return last;
        }

        slot = (phiMix(k) & mMask) << 1; // calculate the starting slot for the
                                         // current key

        if (last <= pos ? last >= slot || slot > pos : last >= slot && slot > pos) {
          break;
        }

        pos = (pos + 1) & mMask; // go to the next entry
      }

      mKeys[last] = k;
      setValue(last, getValue(pos + 1));
    }
  }

  public int size() {
    return mSize;
  }

  private void rehash(final int newCapacity) {
    mThreshold = (int) (newCapacity / 2 * mFillFactor);
    mMask = newCapacity / 2 - 1;

    rehash(newCapacity, mKeys.length);
  }

  protected void rehashKeys(final int newCapacity) {
    mKeys = new int[newCapacity];
  }

  protected abstract void rehash(int newCapacity, int oldCapacity);

  protected abstract void resize(int newCapacity);

  public int[] keys() {
    return mKeys;
  }

  @Override
  public boolean isEmpty() {
    return mSize == 0;
  }

  @Override
  public boolean containsKey(Object k) {
    final int key = (int) k;

    if (key == FREE_KEY) {
      return mHasFreeKey;
    }

    int ptr = (phiMix(key) & mMask) << 1;
    int kv;

    while (ptr < mKeys.length) {
      kv = mKeys[ptr];

      if (kv == FREE_KEY) {
        return false;
      }

      if (kv == key) {
        return true;
      }

      ptr = (ptr + 1) & mMask; // that's next index calculation
    }

    return false;
  }

  @Override
  public void putAll(Map<? extends Integer, ? extends T> m) {
    for (Entry<? extends Integer, ? extends T> entry : m.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void clear() {
    mSize = 0;
    mHasFreeKey = false;
    Arrays.fill(mKeys, FREE_KEY);
  }

  @Override
  public Set<Integer> keySet() {
    Set<Integer> ret = new HashSet<Integer>();

    for (int key : mKeys) {
      if (key != FREE_KEY) {
        ret.add(key);
      }
    }

    return ret;
  }

  @Override
  public Set<Entry<Integer, T>> entrySet() {
    Set<Entry<Integer, T>> ret = new HashSet<Entry<Integer, T>>(mSize);

    for (int i = 0; i < mKeys.length; ++i) {
      final int key = mKeys[i];

      if (key == FREE_KEY) {
        if (mHasFreeKey) {
          ret.add(new IntEntry(key, mFreeValue));
        }
      } else {
        ret.add(new IntEntry(key, getValue(i)));
      }
    }

    return ret;
  }

  /** Taken from FastUtil implementation */

  /**
   * Return the least power of two greater than or equal to the specified value.
   *
   * <p>
   * Note that this function will return 1 when the argument is 0.
   *
   * @param x a long integer smaller than or equal to 2<sup>62</sup>.
   * @return the least power of two greater than or equal to the specified value.
   */
  public static int nextPowerOfTwo(int x) {
    if (x == 0) {
      return 1;
    }

    x--;
    x |= x >> 1;
    x |= x >> 2;
    x |= x >> 4;
    x |= x >> 8;
    x |= x >> 16;
    return (x | x >> 32) + 1;
  }

  /**
   * Returns the least power of two smaller than or equal to 2<sup>30</sup> and
   * larger than or equal to <code>Math.ceil( expected / f )</code>.
   *
   * @param expected the expected number of elements in a hash table.
   * @param f        the load factor.
   * @return the minimum possible size for a backing array.
   * @throws IllegalArgumentException if the necessary size is larger than
   *                                  2<sup>30</sup>.
   */
  public static int arraySize(final int expected, final double f) {
    final int s = Math.max(2, nextPowerOfTwo((int) Math.ceil(expected / f)));

    if (s > (1 << 30)) {
      throw new IllegalArgumentException("Too large (" + expected + " expected elements with load factor " + f + ")");
    }

    return s;
  }

  // taken from FastUtil
  private static final int INT_PHI = 0x9E3779B9;

  public static int phiMix(final int x) {
    final int h = x * INT_PHI;
    return h ^ (h >> 16);
  }

}
