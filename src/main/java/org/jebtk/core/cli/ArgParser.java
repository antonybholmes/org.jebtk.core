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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.jebtk.core.collections.ArrayListCreator;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.collections.DefaultHashMap;
import org.jebtk.core.collections.IterMap;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.text.TextUtils;

/**
 * The Class CommandLineArgs.
 */
public class ArgParser implements Iterable<Entry<String, List<String>>> {

  /**
   * The member args.
   */
  private IterMap<String, List<String>> mArgMap = DefaultHashMap.create(new ArrayListCreator<String>());

  private List<String> mOthers = new ArrayList<String>();

  private Args mOptions = null;

  // private static final Pattern SHORT_ARG_REGEX =
  // Pattern.compile("^-([\\w\\-]+)");
  // private static final Pattern LONG_ARG_REGEX =
  // Pattern.compile("^--([\\w\\-]+)");

  public ArgParser() {
    this(null);
  }

  public ArgParser(Args options) {
    mOptions = options;
  }
  
  private void add(String arg) {
    add(new Arg(arg));
  }

  /**
   * Adds the arg.
   *
   * @param arg the arg
   */
  private void add(Arg arg) {
    mArgMap.get(arg.getLongName());
    
    if (!arg.getShortName().equals(arg.getLongName())) {
      mArgMap.get(arg.getShortName());
    }
  }

  private void add(Arg arg, String value) {
    mArgMap.get(arg.getLongName()).add(value);

    // Also map the short name for convenience
    if (!arg.getShortName().equals(arg.getLongName())) {
      mArgMap.get(arg.getShortName()).add(value);
    }
  }

  /**
   * Returns true if argument was matched.
   * 
   * @param name
   * @return
   */
  public boolean contains(String name) {
    return mArgMap.containsKey(name);
  }

  /**
   * Gets the arg.
   *
   * @param name the name
   * @return the arg
   */
  public String getArg(String name) {
    List<String> args = getArgs(name);

    if (args.size() > 0) {
      return args.get(0);
    } else {
      return TextUtils.EMPTY_STRING;
    }
  }

  public int getInt(String arg) {
    return Integer.parseInt(getArg(arg));
  }

  public double getDouble(String arg) {
    return Double.parseDouble(getArg(arg));
  }

  public Path getFile(String arg) {
    return PathUtils.getPath(arg);
  }

  /**
   * Returns a list of the parsed arguments with the given name
   * 
   * @param name
   * @return
   */
  public List<String> getArgs(String name) {
    name = Arg.parseArgName(name);
    
    if (contains(name)) {
      return Collections.unmodifiableList(mArgMap.get(name));
    } else {
      // See if there is a default

      if (mOptions.contains(name)) {
        String def = mOptions.get(name).getDefaultValue();

        if (def != null) {
          return CollectionUtils.asList(mOptions.get(name).getDefaultValue());
        } else {
          return Collections.emptyList();
        }
      } else {
        return Collections.emptyList();
      }
    }
  }

  /**
   * Returns the unnamed args in the order they were parsed.
   * 
   * @return
   */
  public List<String> getArgs() {
    return Collections.unmodifiableList(mOthers);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Entry<String, List<String>>> iterator() {
    return mArgMap.iterator();
  }

  /**
   * Parses a set of arguments and extracts values. If an arg is not properly
   * specified, it will be ignored, for example '--arg=' with a missing value will
   * not be available as an argument. This is to negate needing to throw
   * exceptions, but requires diligence on the user.
   *
   * @param options the options
   * @param args    the args
   * @return the command line args
   * @throws ArgException
   */
  public ArgParser parse(String... args) {
    if (mOptions == null) {
      // If no options specified, simply add them to the unprocessed others
      for (String arg : args) {
        //mOthers.add(Arg.parseArgName(arg));
        add(arg);
      }

      return this;
    }

    int index = 0;

    String arg;
    String name;
    String value;
    boolean isLong;
    //boolean isShort;

    while (index < args.length) {
      arg = args[index++];
      
      if (!arg.startsWith("-")) {
        mOthers.add(arg);
        continue;
      }
      
      isLong = arg.startsWith("--");
      
      
      name = Arg.parseArgName(arg);
      
      value = null;

      // Test the type of the argument
      //isLong = arg.startsWith("--");
      //isShort = !isLong && arg.length() == 2 && arg.startsWith("-");

      // System.err.println("is long " + isLong + " " + isShort + " " + arg + "
      // " + Arrays.toString(args));

      //
     

      if (isLong) {
        int delim = arg.indexOf("=");

        if (delim > 0) {
          value = arg.substring(delim + 1);
          //arg = arg.substring(2, delim);
        } else {
          //arg = arg.substring(2);
        }
      } else {
        //arg = arg.substring(1, 2);
      }

      Arg option = mOptions.get(name);

      if (option != null) {
        if (isLong) {
          if (option.hasValue()) {
            if (!TextUtils.isNullOrEmpty(value)) {
              add(option, value);
            }
          } else {
            add(option);
          }
        } else {
          if (option.hasValue()) {
            if (index < args.length) {
              add(option, args[index++]);
            }
          } else {
            add(option);
          }
        }
      }
    }

    return this;
  }
  
  

  public static Entry<String, String> parsePosixArg(String arg) {

    String name = Arg.parseArgName(arg);

    int index = arg.indexOf("=");

    if (index > 0) {
      return new org.jebtk.core.collections.Entry<String, String>(name, arg.substring(index + 1));
    } else {
      return new org.jebtk.core.collections.Entry<String, String>(name, TextUtils.EMPTY_STRING);
    }
  }

  public static String longArg(String name, String value) {
    return new StringBuilder().append("--").append(name).append("=").append(value).toString();
  }

  public static String longArg(String name) {
    return new StringBuilder().append("--").append(name).toString();
  }

}
