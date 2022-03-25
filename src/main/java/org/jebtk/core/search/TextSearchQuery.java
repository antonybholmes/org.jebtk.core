package org.jebtk.core.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TextSearchQuery extends SearchQuery<String> {

  private boolean mCaseSens = false;
  private Collection<String> mSearches;

  public TextSearchQuery(Collection<String> searches) {
    mSearches = searches;
  }

  public TextSearchQuery setCaseSens(boolean sens) {
    mCaseSens = sens;

    return this;
  }

  @Override
  public Collection<String> match(String s, boolean exact, boolean include) {
    String ls;

    if (mCaseSens) {
      ls = s;
    } else {
      ls = s.toLowerCase();
    }

    List<String> ret = new ArrayList<String>(mSearches.size());

    for (String t : mSearches) {
      boolean found = false;

      if (exact) {
        if (mCaseSens) {
          found = t.equals(ls);
        } else {
          found = t.toLowerCase().equals(ls);
        }
      } else {
        if (mCaseSens) {
          found = t.contains(ls);
        } else {
          found = t.toLowerCase().contains(ls);
        }
      }

      if (!include) {
        found = !found;
      }

      if (found) {
        ret.add(t);
      }
    }

    return ret;
  }

}
