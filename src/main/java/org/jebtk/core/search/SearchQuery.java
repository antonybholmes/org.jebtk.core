/**
 * Copyright 2018 Antony Holmes
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
package org.jebtk.core.search;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.text.TextUtils;

public abstract class SearchQuery<T> {

  public Collection<T> search(String search) {
    return search(SearchStackElement.parseQuery(search));
  }

  public Collection<T> search(List<SearchStackElement> search) {

    // SearchStackElement e = null;

    Deque<Collection<T>> tempStack = new ArrayDeque<Collection<T>>();

    // while (!searchStack.isEmpty()) {
    // e = searchStack.pop();

    for (SearchStackElement e : search) {
      switch (e.mOp) {
      case MATCH:
        // First get a list of keywords matching the search

        boolean exactMatch = quoted(e.mText);

        String keyword;

        if (exactMatch) {
          keyword = TextUtils.unquote(e.mText);
        } else {
          keyword = e.mText;
        }

        boolean negated = negated(keyword);

        if (negated) {
          keyword = removeNegation(keyword);
        }

        // Get all samples matched to those keywords for the tag.
        tempStack.push(match(keyword, exactMatch, negated));

        break;
      case AND:
        tempStack.push(and(tempStack.pop(), tempStack.pop()));
        break;
      case OR:
        tempStack.push(or(tempStack.pop(), tempStack.pop()));
        break;
      default:
        break;
      }
    }

    // The result will be left on the tempStack
    Collection<T> results = tempStack.pop();

    return results;
  }

  public abstract Collection<T> match(String s, boolean exactMatch, boolean include);

  /**
   * Performs an intersection of two sets of samples. If either of the results
   * sets are non-inclusive (find samples without word) we return the compliment
   * of the inclusive set to the non-inclusive, i.e. exclude whatever is in the
   * non-inclusive.
   * 
   *
   * @param sr1 Results 1.
   * @param sr2 Results 2.
   * @return The intersection of results 1 and results 2.
   */
  public Collection<T> and(Collection<T> r1, Collection<T> r2) {
    return CollectionUtils.intersect(r1, r2);
  }

  public Collection<T> or(Collection<T> r1, Collection<T> r2) {
    return CollectionUtils.union(r1, r2);
  }

  /**
   * Returns true if the string is in quotations.
   *
   * @param keyword the keyword
   * @return true, if successful
   */
  private static boolean quoted(String keyword) {
    return keyword.charAt(0) == '"';
  }

  /**
   * Returns true if the word does not start with a dash '-'. A dash
   * representations negation of the statement.
   *
   * @param keyword the keyword
   * @return true, if successful
   */
  private static boolean negated(String keyword) {
    return keyword.charAt(0) == '-';
  }

  private static String removeNegation(String keyword) {
    return keyword.substring(1);
  }
}
