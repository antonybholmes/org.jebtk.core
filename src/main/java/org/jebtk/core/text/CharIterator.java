package org.jebtk.core.text;

import java.util.Iterator;

/**
 * Iterate over a string one character at a time.
 * 
 * @author antony
 *
 */
public class CharIterator implements Iterator<Character> {

  private final char[] mText;
  private int mC;

  public CharIterator(String text) {
    mText = text.toCharArray();
    mC = 0;
  }

  @Override
  public boolean hasNext() {
    return mC < mText.length;
  }

  @Override
  public Character next() {
    return mText[mC++];
  }

  @Override
  public void remove() {
    // Do nothing
  }
}
