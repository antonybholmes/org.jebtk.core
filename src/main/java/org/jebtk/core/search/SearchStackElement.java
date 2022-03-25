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
package org.jebtk.core.search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.regex.Pattern;

import org.jebtk.core.text.TextUtils;

/**
 * Describes a search operation for determining if an experiment matches some
 * criteria or other.
 *
 * @author Antony Holmes
 * @param <T> the generic type
 */
public class SearchStackElement {

  /**
   * Valid characters in a search.
   */
  private static final Pattern Q_REGEX = Pattern.compile("[a-zA-Z0-9\\-\\_\\(\\)\\\" ]+");

  // private static final Pattern MULT_AND_REGEX =
  // Pattern.compile("AND( AND)+");

  // private static final Pattern AND_OR_AND_REGEX =
  // Pattern.compile("AND OR AND");

  /**
   * The member op.
   */
  public SearchStackOperator mOp;

  /**
   * The member text.
   */
  public String mText = null;

  /**
   * Instantiates a new search stack element.
   *
   * @param type the type
   */
  public SearchStackElement(SearchStackOperator type) {
    mOp = type;
  }

  /**
   * Creates a match element for matching a string.
   *
   * @param value the value
   */
  public SearchStackElement(String value) {
    mText = value;
    mOp = SearchStackOperator.MATCH;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  public final String toString() {
    return "[" + mOp + " " + mText + "]";
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public String getText() {
    return mText;
  }

  /**
   * Gets the operator.
   *
   * @return the operator
   */
  public SearchStackOperator getOperator() {
    return mOp;
  }

  /**
   * Returns a stack representation of a text query.
   *
   * @param the generic type
   * @param q   the q
   * @return the deque
   */
  public static List<SearchStackElement> parseQuery(String q) {
    // Deque<SearchStackElement<T>> searchStack =
    // new ArrayDeque<SearchStackElement<T>>();

    // Check that the search query seems ok
    if (!Q_REGEX.matcher(q).matches()) {
      return Collections.emptyList();
    }

    // Remove any non word chars from the search string, trim and replace
    // runs of whitespace chars with a single char to clean up the input
    // and sanitize it
    StringBuilder qs = new StringBuilder(q.length() * 2).append(q);
    // TextUtils.replace("(", " ( ", qs);
    // TextUtils.replace(")", " ) ", qs);
    TextUtils.replace(" ", " AND ", qs);
    // TextUtils.replace("AND ( AND", "(", qs);
    // TextUtils.replace("AND ) AND", ")", qs);
    TextUtils.replace("AND OR AND", "OR", qs); // AND_OR_AND_REGEX.matcher(qs).replaceAll("OR");
                                               // //qs.replace("AND OR
                                               // AND", "OR");
    TextUtils.replace("AND AND AND", "AND", qs); // MULT_AND_REGEX.matcher(qs).replaceAll("AND");
                                                 // //qs.replace("AND AND
                                                 // AND", "AND");
    TextUtils.replace("  ", " ", qs);

    // System.err.println("q:" + qs);

    // List<String> terms = Splitter
    // .onSpace()
    // .ignoreEmptyStrings()
    // .text(qs.toString());

    List<SearchStackElement> outputQueue = new ArrayList<SearchStackElement>(10);

    Deque<SearchStackOperator> opStack = new ArrayDeque<SearchStackOperator>(10);

    StringBuilder buffer = new StringBuilder(qs.length());

    char[] chars = new char[qs.length()];

    // Convert buffer to char array
    qs.getChars(0, qs.length(), chars, 0);

    for (char c : chars) {
      switch (c) {
      case '(':
        addTerm(buffer, outputQueue);
        opStack.push(SearchStackOperator.LEFT_PAREN);
        break;
      case ')':
        addTerm(buffer, outputQueue);
        rightParens(outputQueue, opStack);
        break;
      case ' ':
        addTerm(buffer, outputQueue);
        break;
      default:
        buffer.append(c);

        if (c == 'D') {
          // We must encounter a 'D' before checking if the buffer
          // contains AND
          if (buffer.toString().equals("AND")) {
            addLowerPrecedenceOps(SearchStackOperator.AND, outputQueue, opStack);
            buffer.setLength(0);
          }
        } else if (c == 'R') {
          if (buffer.toString().equals("OR")) {
            addLowerPrecedenceOps(SearchStackOperator.OR, outputQueue, opStack);
            buffer.setLength(0);
          }
        } else {
          // Do nothing
        }

        break;
      }
    }

    /*
     * // Convert to reverse polish
     * 
     * for (String term : terms) { if (term.equals("AND")) {
     * addLowerPrecedenceOps(SearchStackOperator.AND, outputQueue, opStack); } else
     * if (term.equals("OR")) { addLowerPrecedenceOps(SearchStackOperator.OR,
     * outputQueue, opStack); } else if (term.equals("(")) {
     * opStack.push(SearchStackOperator.LEFT_PAREN); } else if (term.equals(")")) {
     * rightParens(outputQueue, opStack); } else { outputQueue.add(new
     * SearchStackElement<T>(term.toLowerCase())); } }
     */

    // add any remaining operators onto the queue
    addTerm(buffer, outputQueue);

    while (opStack.size() > 0) {
      outputQueue.add(new SearchStackElement(opStack.pop()));
    }

    // Reverse the list since we want to put elements on the
    // stack in reverse order
    // Collections.reverse(outputQueue);

    // Now the stack can be evaluated correctly
    // The first elements will be terms to match to
    // for (SearchStackElement<T> item : outputQueue) {
    //// searchStack.push(item);
    // }

    return outputQueue;
  }

  /**
   * Adds the term.
   *
   * @param the         generic type
   * @param buffer      the buffer
   * @param outputQueue the output queue
   */
  private static void addTerm(StringBuilder buffer, List<SearchStackElement> outputQueue) {
    String s = buffer.toString().toLowerCase();

    if (s.length() > 0) {
      outputQueue.add(new SearchStackElement(s));
      buffer.setLength(0);
    }
  }

  //
  // Static functions
  //

  /**
   * Adds the lower precedence ops.
   *
   * @param the           generic type
   * @param op1           the operator
   * @param outputQueue   the search stack
   * @param operatorStack the operator stack
   */
  private static void addLowerPrecedenceOps(SearchStackOperator op1, List<SearchStackElement> outputQueue,
      Deque<SearchStackOperator> operatorStack) {
    int p1 = SearchStackOperator.precedence(op1);

    SearchStackOperator op2;

    // Look for ops with greater presedence since we want to evaluate
    // AND before OR

    while (operatorStack.size() > 0) {
      op2 = operatorStack.peek();

      if (p1 > SearchStackOperator.precedence(op2)) {
        break;
      }

      // We have encountered two 'and' statements for example
      outputQueue.add(new SearchStackElement(op2));

      // remove the operator as we have dealt with it
      operatorStack.pop();
    }

    // Add the new operator to the op stack
    operatorStack.push(op1);
  }

  /**
   * Pop expressions until a left parenthesis is encountered.
   *
   * @param the         generic type
   * @param outputQueue the output queue
   * @param opStack     the op stack
   */
  private static void rightParens(List<SearchStackElement> outputQueue, Deque<SearchStackOperator> opStack) {
    SearchStackOperator op2;

    // deal with existing operatorStack

    while (opStack.size() > 0) {
      op2 = opStack.pop();

      if (op2 == SearchStackOperator.LEFT_PAREN) {
        break;
      }

      outputQueue.add(new SearchStackElement(op2));
    }
  }
}
