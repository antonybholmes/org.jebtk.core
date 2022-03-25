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

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.GZIPInputStream;

import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.text.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows data from a JAR to be loaded into memory.
 *
 * @author Antony Holmes
 *
 */
public class Resources implements Iterable<String> {

  private static class ResourceLoader {

    /** The Constant INSTANCE. */
    private static final Resources INSTANCE = new Resources();
  }

  /**
   * Gets the single instance of SettingsService.
   *
   * @return single instance of SettingsService
   */
  public static Resources getInstance() {
    return ResourceLoader.INSTANCE;
  }

  /**
   * The constant RES_LOC.
   */
  private static final String RES_LOC = "res/";

  /**
   * The constant RES_DIR.
   */
  public static final Path RES_DIR = PathUtils.getPath(RES_LOC);

  /**
   * The constant FONT_RES.
   */
  private static final String FONT_RES = RES_LOC + "fonts/";

  /**
   * The log.
   */
  private final Logger LOG = LoggerFactory.getLogger(Resources.class);

  /**
   * The member resources.
   */
  private final Set<String> mResources = new HashSet<>();

  /**
   * The member loaded.
   */
  private boolean mLoaded = false;


  /**
   * Instantiates a new resources.
   */
  private Resources() {

  }

  /**
   * Load text list.
   *
   * @param name the name
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<String> loadTextList(String name) throws IOException {
    InputStream is = null;
    BufferedReader br = null;
    String line;
    List<String> list = new ArrayList<>();

    try {
      is = getResInputStream(name);

      br = new BufferedReader(new InputStreamReader(is));

      while (null != (line = br.readLine())) {
        list.add(line);
      }
    } finally {
      if (br != null) {
        br.close();
      }

      if (is != null) {
        is.close();
      }
    }

    return list;
  }

  // public static InputStream getInputStream(File file) {
  // return getInputStream(file.getPath());
  // }

  /**
   * Gets the res input stream.
   *
   * @param name the name
   * @return the res input stream
   */
  public static InputStream getResInputStream(String name) {
    return ClassLoader.getSystemResourceAsStream(name);
  }

  /**
   * Gets the res gzip input stream.
   *
   * @param name the name
   * @return the res gzip input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream getResGzipInputStream(String name) throws IOException {
    return new GZIPInputStream(getResInputStream(name));
  }

  /**
   * Gets the res gzip reader.
   *
   * @param file the file
   * @return the res gzip reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedReader getResGzipReader(File file) throws IOException {
    return getResGzipReader(file.toPath());
  }

  /**
   * Gets the res gzip reader.
   *
   * @param file the file
   * @return the res gzip reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedReader getResGzipReader(Path file) throws IOException {
    return getResGzipReader(PathUtils.toString(file));
  }

  /**
   * Gets the res gzip reader.
   *
   * @param name the name
   * @return the res gzip reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedReader getResGzipReader(String name) throws IOException {
    return new BufferedReader(new InputStreamReader(getResGzipInputStream(name)));
  }

  /**
   * Gets the input stream.
   *
   * @param file the file
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream getInputStream(File file) throws IOException {
    return getInputStream(file.toPath());
  }

  /**
   * Gets the input stream.
   *
   * @param file the file
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream getInputStream(Path file) throws IOException {
    return Files.newInputStream(file);
  }

  /**
   * Gets the gzip input stream.
   *
   * @param file the file
   * @return the gzip input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream getGzipInputStream(File file) throws IOException {
    return new GZIPInputStream(getInputStream(file));
  }

  /**
   * Gets the gzip input stream.
   *
   * @param file the file
   * @return the gzip input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream getGzipInputStream(Path file) throws IOException {
    return new GZIPInputStream(getInputStream(file));
  }

  /**
   * Gets the gzip reader.
   *
   * @param file the file
   * @return the gzip reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedReader getGzipReader(File file) throws IOException {
    return getGzipReader(file.toPath());
  }

  /**
   * Gets the gzip reader.
   *
   * @param file the file
   * @return the gzip reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedReader getGzipReader(Path file) throws IOException {
    return new BufferedReader(new InputStreamReader(getGzipInputStream(file)));
  }

  /**
   * Gets the resource.
   *
   * @param name the name
   * @return the resource
   */
  public static URL getResource(String name) {
    return ClassLoader.getSystemResource(name);
  }

  /**
   * Auto load.
   *
   * @throws IOException        Signals that an I/O exception has occurred.
   * @throws URISyntaxException the URI syntax exception
   */
  private synchronized void autoLoad() throws IOException, URISyntaxException {
    if (mLoaded) {
      return;
    }

    cacheResourceFiles();

    mLoaded = true;
  }

  /**
   * Cache resource files.
   *
   * @throws IOException        Signals that an I/O exception has occurred.
   * @throws URISyntaxException the URI syntax exception
   */
  private synchronized void cacheResourceFiles() throws IOException, URISyntaxException {
    LOG.info("Finding resource files...");

    List<File> files = new ArrayList<>();

    String me = Resources.class.getName().replace(".", "/") + ".class";

    URL url = Resources.class.getClassLoader().getResource(me);

    // System.exit(0);

    // need to deal with file and jar protocols

    if (url.getProtocol().equals("jar")) {
      /* A JAR path */
      String jarPath = url.getPath().substring(5, url.getPath().indexOf("!")); // strip
                                                                               // out
                                                                               // only
                                                                               // the
                                                                               // JAR
                                                                               // file
      try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"))) {
        Enumeration<JarEntry> entries = jar.entries();
        
        while (entries.hasMoreElements()) {
          String name = entries.nextElement().getName();

          name = name.replace("\\", "/");

          if (name.startsWith("res")) {
            LOG.info("Found jar resource {}", name);

            mResources.add(name);
          }
        }
      }
    } else {
      // File system

      Enumeration<URL> r = ClassLoader.getSystemResources("res");

      while (r.hasMoreElements()) {
        files.add(new File(r.nextElement().toURI()));
      }

      for (File file : files) {
        cacheResourceFiles(file);
      }
    }
  }

  /**
   * Cache resource files.
   *
   * @param file the file
   * @throws MalformedURLException the malformed url exception
   * @throws IOException           Signals that an I/O exception has occurred.
   */
  private void cacheResourceFiles(File file) throws MalformedURLException, IOException {
    LOG.info("Finding resource files in {}...", file);

    if (file.isDirectory()) {
      File[] subFiles = file.listFiles();

      for (File subFile : subFiles) {
        cacheResourceFiles(subFile);
      }
    } else {

      String res = file.getPath().replace("\\", "/");

      // LOG.info("Found resource {}.", res);

      if (res.contains("res/")) {
        res = res.replaceFirst("^.+res\\/", "res/");

        LOG.info("Found resource {}.", res);

        // Needed because calls to getSystemResources do not seem to
        // work with windows style backslashes.
        mResources.add(res);
      }
    }
  }

  /**
   * Load text.
   *
   * @param name the name
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String loadText(String name) throws IOException {
    List<String> list = loadTextList(name);

    StringBuilder builder = new StringBuilder();

    for (String line : list) {
      builder.append(line).append(TextUtils.NEW_LINE);
    }

    return builder.toString();
  }

  /**
   * Loads a TTF font from a resource and makes it available to the UI.
   *
   * @param font the font
   * @throws FontFormatException the font format exception
   * @throws IOException         Signals that an I/O exception has occurred.
   */
  public void registerFont(String font) throws FontFormatException, IOException {
    String resource = FONT_RES + font;

    LOG.info("Loading font {}...", resource);

    try (InputStream is = getResInputStream(resource)) {
      Font f = Font.createFont(Font.TRUETYPE_FONT, is);

      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

      ge.registerFont(f);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<String> iterator() {
    try {
      autoLoad();
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }

    return mResources.iterator();
  }

  /**
   * Reads a resource as a string list.
   *
   * @param res       the res
   * @param hasHeader the has header
   * @return the list from res
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<String> getListFromRes(String res, boolean hasHeader) throws IOException {
    BufferedReader reader = getResGzipReader(res);

    List<String> ret = new ArrayList<>();

    String line;

    try {
      if (hasHeader) {
        reader.readLine();
      }

      while ((line = reader.readLine()) != null) {
        ret.add(line);
      }
    } finally {
      reader.close();
    }

    return ret;
  }

  /**
   * Make the resource directory if it does not exist.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void makeResDir() throws IOException {
    FileUtils.mkdir(RES_DIR);
  }
}
