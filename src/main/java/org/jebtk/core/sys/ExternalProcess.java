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
package org.jebtk.core.sys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jebtk.core.text.Join;
import org.jebtk.core.text.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class ExternalProcess.
 */
public class ExternalProcess {

  /**
   * The member args.
   */
  private List<String> mArgs = new ArrayList<String>();

  /**
   * The member working directory.
   */
  private final Path mPwd;

  /**
   * The constant LOG.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ExternalProcess.class);

  /**
   * Instantiates a new external process.
   *
   * @param pwd
   */
  public ExternalProcess(Path pwd) {
    mPwd = pwd;
  }

  /**
   * Adds the arg.
   *
   * @param arg the arg
   * @param args
   */
  public final void addArg(String arg, String... args) {
    mArgs.add(arg);

    mArgs.addAll(Arrays.asList(args));
  }

  public void addParam(String key, String value) {
    addArg("--" + key + "=" + value);
  }

  public void addParam(String key, int value) {
    addParam(key, Integer.toString(value));
  }

  public void addParam(String key, boolean value) {
    addParam(key, Boolean.toString(value));
  }

  /**
   * Sets the args.
   *
   * @param args the new args
   */
  public final void setArgs(List<String> args) {
    mArgs = new ArrayList<>(args);
  }

  /**
   * Sets the args.
   *
   * @param args the new args
   */
  public final void setArgs(String[] args) {
    mArgs = Arrays.asList(args);
  }

  /**
   * Run.
   *
   * @throws IOException          Signals that an I/O exception has occurred.
   * @throws InterruptedException the interrupted exception
   */
  public final void run() throws IOException, InterruptedException {
    ProcessBuilder builder = new ProcessBuilder(mArgs);

    builder.directory(mPwd.toFile());

    builder.redirectErrorStream(true);

    LOG.info(TextUtils.join(mArgs, TextUtils.SPACE_DELIMITER));

    Process process = builder.start();

    // Runtime runtime = Runtime.getRuntime();
    // Process process = runtime.exec(args);

    BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;

    while ((line = br.readLine()) != null) {
      LOG.info(line);
    }

    process.waitFor();
  }

  @Override
  public String toString() {
    return Join.onSpace().values(mArgs).toString();
  }

  /**
   * Run.
   *
   * @param command          the command
   * @param workingDirectory the working directory
   * @throws InterruptedException the interrupted exception
   * @throws IOException          Signals that an I/O exception has occurred.
   */
  public static final void run(String command, Path pwd) throws InterruptedException, IOException {
    ExternalProcess process = new ExternalProcess(pwd);

    process.addArg(command);

    process.run();
  }

  /**
   * Run.
   *
   * @param commands         the commands
   * @param workingDirectory the working directory
   * @throws InterruptedException the interrupted exception
   * @throws IOException          Signals that an I/O exception has occurred.
   */
  public static final void run(List<String> commands, Path pwd) throws InterruptedException, IOException {
    ExternalProcess process = new ExternalProcess(pwd);

    process.setArgs(commands);

    process.run();
  }

  /**
   * Run.
   *
   * @param commands         the commands
   * @param workingDirectory the working directory
   * @throws InterruptedException the interrupted exception
   * @throws IOException          Signals that an I/O exception has occurred.
   */
  public static final void run(String[] commands, Path pwd) throws InterruptedException, IOException {
    ExternalProcess process = new ExternalProcess(pwd);

    process.setArgs(commands);

    process.run();
  }

}
