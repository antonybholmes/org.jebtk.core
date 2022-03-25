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
package org.jebtk.core;

/**
 * Defines logical operators.
 *
 * @author Antony Holmes
 *
 */
public enum BooleanOperator {

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
   * The not.
   */
  NOT,

  /**
   * The invalid.
   */
  INVALID;

  /**
   * From string.
   *
   * @param value the value
   * @return the boolean operator
   */
  public static final BooleanOperator fromString(String value) {
    if (value.toLowerCase().equals("and")) {
      return AND;
    } else if (value.toLowerCase().equals("or")) {
      return OR;
    } else if (value.toLowerCase().equals("nor")) {
      return NOR;
    } else if (value.toLowerCase().equals("xor")) {
      return XOR;
    } else if (value.toLowerCase().equals("nand")) {
      return NAND;
    } else if (value.toLowerCase().equals("not")) {
      return NOT;
    } else {
      return INVALID;
    }
  }

  /**
   * To string.
   *
   * @param operator the operator
   * @return the string
   */
  public static String toString(BooleanOperator operator) {
    switch (operator) {
    case AND:
      return "and";
    case OR:
      return "or";
    case XOR:
      return "xor";
    case NOR:
      return "nor";
    case NOT:
      return "not";
    case NAND:
      return "nand";
    default:
      return "invalid";
    }
  }
}
