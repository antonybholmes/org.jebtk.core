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

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jebtk.core.collections.DefaultHashMap;
import org.jebtk.core.collections.UniqueArrayListCreator;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads plugin class definitions from file so they can be instantiated. Plugins
 * must be in there own directory, for example annotations, within the main
 * plugin directory. Within each individual plugin folder, the classes must be
 * in the same package hierarchy as they were designed. The plugin loader will
 * scan the directories and build the packages from this.
 *
 * @author Antony Holmes
 *
 */
public class PluginService implements Iterable<Plugin> {

  private static class PluginServiceLoader {
    private static final PluginService INSTANCE = new PluginService();
  }

  /**
   * Gets the single instance of SettingsService.
   *
   * @return single instance of SettingsService
   */
  public static PluginService getInstance() {
    return PluginServiceLoader.INSTANCE;
  }

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(PluginService.class);

  /**
   * Default directory where to find plugins.
   */
  public static final Path DEFAULT_PLUGIN_DIRECTORY = PathUtils.getPath("res", "plugins");

  public static final String DEFAULT_GROUP = "default";
  /**
   * The member plugins.
   */
  // private List<Plugin> mPlugins = new UniqueArrayList<Plugin>();

  private Map<String, List<Plugin>> mPluginMap = DefaultHashMap.create(new UniqueArrayListCreator<Plugin>());

  /**
   * Instantiates a new plugin service.
   */
  private PluginService() {
    // do nothing
  }

  /**
   * Adds the plugin.
   *
   * @param c the c
   */
  public void addPlugin(Class<?> c) {
    addPlugin(DEFAULT_GROUP, c);
  }

  public void addPlugin(String group, Class<?> c) {
    addPlugin(group, new Plugin(c));
  }

  /**
   * Adds the plugin.
   *
   * @param plugin the plugin
   */
  public void addPlugin(Plugin plugin) {
    addPlugin(DEFAULT_GROUP, plugin);
  }

  public void addPlugin(String group, Plugin plugin) {
    mPluginMap.get(group).add(plugin);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Iterable#iterator()
   */
  @Override
  public Iterator<Plugin> iterator() {
    return mPluginMap.get(DEFAULT_GROUP).iterator();
  }

  public Iterable<Plugin> iterator(String group) {
    return mPluginMap.get(group);
  }

  /**
   * Scans the default plugin directory for plugins.
   *
   * @throws ClassNotFoundException the class not found exception
   * @throws IOException
   */
  public final void scan() throws ClassNotFoundException, IOException {
    scan(DEFAULT_PLUGIN_DIRECTORY);
  }

  /**
   * Scans a directory within the main plugin directory for class files.
   *
   * @param filename the filename
   * @throws ClassNotFoundException the class not found exception
   * @throws IOException
   */
  public final void scanDirectory(String filename) throws ClassNotFoundException, IOException {
    scan(PathUtils.getPath(filename));
  }

  /**
   * Scans a specific directory for plugins.
   *
   * @param pluginDir the plugin dir
   * @throws ClassNotFoundException the class not found exception
   * @throws IOException
   */
  public final void scan(Path pluginDir) throws ClassNotFoundException, IOException {
    if (!FileUtils.exists(pluginDir)) {
      return;
    }

    URLClassLoader ucl;

    List<Path> dirs = FileUtils.lsdir(pluginDir); // pluginDir.listFiles(directoryFilter);

    Class<?> pluginClass;

    Deque<Path> dirStack = new ArrayDeque<Path>();
    Deque<String> packageNameStack = new ArrayDeque<String>();

    // StringBuilder packageName = new StringBuilder();

    for (Path dir : dirs) {
      // each plugin dir represents a type of plugin

      dirStack.clear();
      packageNameStack.clear();

      dirStack.push(dir);
      packageNameStack.push("");

      // packageName = new StringBuilder();

      URL classUrl = dir.toUri().toURL();
      URL[] classUrls = { classUrl };
      ucl = new URLClassLoader(classUrls);

      Set<Path> visited = new HashSet<Path>();

      while (!dirStack.isEmpty()) {
        Path currentDirectory = dirStack.pop();

        List<Path> files = FileUtils.ls(currentDirectory); // currentDirectory.listFiles();

        String currentPackage = packageNameStack.pop();

        LOG.info("Scanning {} [{}] for plugins.", currentDirectory.toAbsolutePath(), currentPackage);

        for (Path file : files) {
          if (!FileUtils.isDirectory(file)) {
            continue;
          }

          if (visited.contains(file)) {
            continue;
          }

          dirStack.push(file);

          packageNameStack.push(currentPackage + file.getFileName().toString() + ".");
        }

        for (Path file : files) {
          if (FileUtils.isDirectory(file)) {
            continue;
          }

          if (!file.getFileName().endsWith(".class")) {
            continue;
          }

          String plugin = currentPackage + file.getFileName().toString().replaceFirst("\\.class$", "");

          LOG.info("Loading plugin {}.", plugin);

          pluginClass = ucl.loadClass(plugin);

          // System.err.println(pluginClass.getSimpleName() + " "
          // +pluginClass.getCanonicalName() + " " + pluginClass.getName());

          addPlugin(dir.getFileName().toString().toLowerCase(), new Plugin(pluginClass));
        }

        visited.add(currentDirectory);
      }
    }
  }
}