package org.jebtk.test;

import java.util.List;

import org.jebtk.core.search.SearchStackElement;
import org.junit.Test;

public class SearchTest {
  @Test
  public void ParseTest() {
    System.err.println("ParseTest");

    List<SearchStackElement> test = SearchStackElement.parseQuery("cheese AND (toast OR cake)");

    for (SearchStackElement e : test) {
      System.err.println("search: " + e.mOp + " " + e.mText);
    }
  }
}
