/**
toString * Copyright 2016 Antony Holmes
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
package org.jebtk.test;

import java.nio.file.Path;

import org.jebtk.core.cli.ArgParser;
import org.jebtk.core.cli.Args;
import org.jebtk.core.io.PathUtils;
import org.junit.Test;

public class ArgTest {
  @Test
  public void parseTest() {
    Args options = new Args();
    options.add('r', "region-file", true);
    options.add('b', true);
    options.add('g', "genome", true);

    String[] args = { "--region-file=test", "-b", "ha" };

    ArgParser parser = new ArgParser(options).parse(args);

    Path regionsFile = PathUtils.getPath(parser.getArg("region-file"));
    Path bam = PathUtils.getPath(parser.getArg("b"));
    String genome = parser.getArg("genome");

    System.err.println("args " + regionsFile + " " + bam);
  }
}