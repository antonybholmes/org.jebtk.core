package org.jebtk.test;

import java.util.Set;

import org.jebtk.core.objectdb.RadixObjectDb;
import org.jebtk.core.text.TextUtils;
import org.junit.Test;

public class RadixObjectTreeTest {
  @Test
  public void RadixTest() {
    RadixObjectDb<String> test = new RadixObjectDb<String>();

    Set<String> keywords = TextUtils.keywords("DLBCL2040");

    for (String keyword : keywords) {
      if (keyword.toLowerCase().startsWith("dl")) {
        System.err.println("indexing " + keyword);
      }

      test.addObject(keyword, "cake");
    }

    test.addObject("antleg", "xzczxc");

    System.err.println("dlbcl");
    for (String s : test.getChild("dlbcl").getObjects()) {
      System.err.println(s + ":" + test.getChild("ant").getPrefix());
    }

    System.err.println("antl");
    for (String s : test.getChild("antl").getObjects()) {
      System.err.println(s);
    }
  }
}
