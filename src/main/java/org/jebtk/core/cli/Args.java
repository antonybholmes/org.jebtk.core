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
package org.jebtk.core.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jebtk.core.text.TextUtils;

/**
 * The Class Options.
 */
public class Args implements Iterable<Arg> {

  /**
   * The member options.
   */
  private Map<String, Arg> mOptionMap = new HashMap<String, Arg>();

  private List<Arg> mOptions = new ArrayList<Arg>();

  /**
   * Instantiates a new options.
   */
  public Args() {
    add('h', "help");
  }

  /**
   * Adds the.
   *
   * @param shortName the short name
   * @param longName  the long name
   * @return
   */
  public Args add(char shortName, String longName) {
    return add(shortName, longName, false);
  }

  public Args add(char shortName, boolean hasValue) {
    return add(shortName, Character.toString(shortName), hasValue);
  }

  /**
   * Adds the.
   *
   * @param shortName the short name
   * @param longName  the long name
   * @param hasValue  the has arg
   * @return
   */
  public Args add(char shortName, String longName, boolean hasValue) {
    return add(shortName, longName, hasValue, TextUtils.EMPTY_STRING);
  }

  /**
   * Adds the option.
   *
   * @param shortName   the short name
   * @param longName    the long name
   * @param hasValue    the has arg
   * @param description the description
   * @return
   */
  public Args add(char shortName, String longName, boolean hasValue, String description) {
    return add(new Arg(shortName, longName, hasValue, description));
  }

  /**
   * Adds the option.
   *
   * @param option the option
   * @return
   */
  public Args add(Arg option) {
    mOptions.add(option);
    mOptionMap.put(option.getShortName(), option);
    mOptionMap.put(option.getLongName(), option);

    return this;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Arg> iterator() {
    return mOptions.iterator();
  }

  /**
   * Prints the help.
   *
   * @param options the options
   */
  public static void printHelp(Args options) {
    System.out.println("OPTIONS");
    for (Arg option : options) {
      System.out.println("\t" + option.toString());

      if (!TextUtils.isNullOrEmpty(option.getDescription())) {
        System.out.println("\t\t" + option.getDescription());
      }

      System.out.println();
    }
  }

  /**
   * Returns true if args contains an argument with the given name. The name
   * should either the long or short variant without a '--' or '-' prefix.
   * 
   * @param name
   * @return
   */
  public boolean contains(String name) {
    return mOptionMap.containsKey(name);
  }

  /**
   * Returns true the arg with the given name. The name should either the long or
   * short variant without a '--' or '-' prefix.
   * 
   * @param name The name of the argument.
   * @return The arg or null if it does not exist.
   */
  public Arg get(String arg) {
    return mOptionMap.get(arg);
  }
}
