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

import org.jebtk.core.BooleanOperator;

/**
 * Operators for search stack.
 *
 * @author Antony Holmes
 */
public enum SearchStackOperator {

  /**
   * The invalid.
   */
  INVALID,

  /**
   * The and.
   */
  AND,

  /**
   * The or.
   */
  OR,

  /**
   * The nor.
   */
  NOR,

  /**
   * The xor.
   */
  XOR,

  /**
   * The nand.
   */
  NAND,

  /**
   * The match.
   */
  MATCH,

  /**
   * The result.
   */
  RESULT,

  /**
   * The not.
   */
  NOT,

  /** The left parenthesis '('. */
  LEFT_PAREN,

  /** The right parenthesis ')'. */
  RIGHT_PAREN;

  /**
   * The constant OPERATOR_AND.
   */
  public static final String OPERATOR_AND = "AND";

  /**
   * The constant OPERATOR_OR.
   */
  public static final String OPERATOR_OR = "OR";

  /**
   * The constant OPERATOR_NAND.
   */
  public static final String OPERATOR_NAND = "NAND";

  /**
   * The constant OPERATOR_NOT.
   */
  public static final String OPERATOR_NOT = "NOT";

  /**
   * The constant OPERATOR_XOR.
   */
  public static final String OPERATOR_XOR = "XOR";

  /**
   * The constant OPERATOR_NOR.
   */
  public static final String OPERATOR_NOR = "NOR";

  /**
   * The constant OPERATOR_MATCH.
   */
  public static final String OPERATOR_MATCH = "MATCH";

  /**
   * The constant OPERATOR_RESULT.
   */
  public static final String OPERATOR_RESULT = "RESULT";

  /**
   * The constant OPERATOR_LEFT_BRACKET.
   */
  public static final String OPERATOR_LEFT_BRACKET = "(";

  /**
   * The constant OPERATOR_RIGHT_BRACKET.
   */
  public static final String OPERATOR_RIGHT_BRACKET = ")";

  /**
   * The constant OPERATOR_PARENTHESES.
   */
  public static final String OPERATOR_PARENTHESES = "\"";

  /**
   * The constant OPERATOR_INVALID.
   */
  public static final String OPERATOR_INVALID = "INVALID";

  /**
   * Instantiates a new search stack operator.
   */
  private SearchStackOperator() {
    // Do nothing
  }

  /**
   * Gets the operator.
   *
   * @param operator the operator
   * @return the operator
   */
  public static final String getOperator(SearchStackOperator operator) {
    switch (operator) {
    case AND:
      return OPERATOR_AND;
    case OR:
      return OPERATOR_OR;
    case NOR:
      return OPERATOR_NOR;
    case NAND:
      return OPERATOR_NAND;
    case XOR:
      return OPERATOR_XOR;
    case MATCH:
      return OPERATOR_MATCH;
    case RESULT:
      return OPERATOR_RESULT;
    default:
      return OPERATOR_INVALID;
    }
  }

  /**
   * Parses the operator.
   *
   * @param op the op
   * @return the search stack operator
   */
  public static final SearchStackOperator parseOperator(String op) {
    String operator = op.toUpperCase();

    if (operator.equals(OPERATOR_AND)) {
      return AND;
    } else if (operator.equals(OPERATOR_OR)) {
      return OR;
    } else if (operator.equals(OPERATOR_NOR)) {
      return NOR;
    } else if (operator.equals(OPERATOR_XOR)) {
      return XOR;
    } else if (operator.equals(OPERATOR_NAND)) {
      return NAND;
    } else if (operator.equals(OPERATOR_RESULT)) {
      return RESULT;
    } else if (operator.equals(OPERATOR_MATCH)) {
      return MATCH;
    } else if (operator.equals(OPERATOR_NOT)) {
      return NOT;
    } else {
      return INVALID;
    }
  }

  /**
   * Convert a boolean operator into the relevant MatchStack operator.
   *
   * @param operator the operator
   * @return the search stack operator
   */
  public static final SearchStackOperator convert(BooleanOperator operator) {
    switch (operator) {
    case AND:
      return SearchStackOperator.AND;
    case OR:
      return SearchStackOperator.OR;
    case NOR:
      return SearchStackOperator.NOR;
    case NAND:
      return SearchStackOperator.NAND;
    case XOR:
      return SearchStackOperator.XOR;
    case NOT:
      return SearchStackOperator.NOT;
    default:
      return SearchStackOperator.INVALID;
    }
  }

  /**
   * Precedence.
   *
   * @param operator the operator
   * @return the int
   */
  public static final int precedence(SearchStackOperator operator) {
    switch (operator) {
    case NOT:
      return 4;
    case NAND:
    case AND:
      return 3;
    case XOR:
    case OR:
      return 2;
    default:
      return -1;
    }
  }

  /**
   * Checks if is left associative.
   *
   * @param operator the operator
   * @return true, if is left associative
   */
  public static boolean isLeftAssociative(SearchStackOperator operator) {
    return true; // operator != MatchStackOperator.NOT;
  }
}
