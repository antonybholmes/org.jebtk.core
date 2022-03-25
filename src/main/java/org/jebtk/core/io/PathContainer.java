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
package org.jebtk.core.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;

/**
 * The Class PathContainer.
 */
public class PathContainer implements Path {

  /** The m path. */
  private Path mPath;

  /**
   * Instantiates a new path container.
   *
   * @param path the path
   */
  public PathContainer(Path path) {
    mPath = path;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#compareTo(java.nio.file.Path)
   */
  @Override
  public int compareTo(Path other) {
    return mPath.compareTo(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#endsWith(java.nio.file.Path)
   */
  @Override
  public boolean endsWith(Path other) {
    return mPath.endsWith(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#endsWith(java.lang.String)
   */
  @Override
  public boolean endsWith(String other) {
    return mPath.endsWith(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#getFileName()
   */
  @Override
  public Path getFileName() {
    return mPath.getFileName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#getFileSystem()
   */
  @Override
  public FileSystem getFileSystem() {
    return mPath.getFileSystem();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#getName(int)
   */
  @Override
  public Path getName(int index) {
    return mPath.getName(index);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#getNameCount()
   */
  @Override
  public int getNameCount() {
    return mPath.getNameCount();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#getParent()
   */
  @Override
  public Path getParent() {
    return mPath.getParent();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#getRoot()
   */
  @Override
  public Path getRoot() {
    return mPath.getRoot();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#isAbsolute()
   */
  @Override
  public boolean isAbsolute() {
    return mPath.isAbsolute();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#iterator()
   */
  @Override
  public Iterator<Path> iterator() {
    return mPath.iterator();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#normalize()
   */
  @Override
  public Path normalize() {
    return mPath.normalize();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#register(java.nio.file.WatchService,
   * java.nio.file.WatchEvent.Kind[])
   */
  @Override
  public WatchKey register(WatchService watcher, Kind<?>... events) throws IOException {
    return mPath.register(watcher, events);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#register(java.nio.file.WatchService,
   * java.nio.file.WatchEvent.Kind[], java.nio.file.WatchEvent.Modifier[])
   */
  @Override
  public WatchKey register(WatchService watcher, Kind<?>[] events, Modifier... modifiers) throws IOException {
    return mPath.register(watcher, events, modifiers);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#relativize(java.nio.file.Path)
   */
  @Override
  public Path relativize(Path other) {
    return mPath.relativize(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#resolve(java.nio.file.Path)
   */
  @Override
  public Path resolve(Path other) {
    return mPath.resolve(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#resolve(java.lang.String)
   */
  @Override
  public Path resolve(String other) {
    return mPath.resolve(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#resolveSibling(java.nio.file.Path)
   */
  @Override
  public Path resolveSibling(Path other) {
    return mPath.resolveSibling(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#resolveSibling(java.lang.String)
   */
  @Override
  public Path resolveSibling(String other) {
    return mPath.resolveSibling(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#startsWith(java.nio.file.Path)
   */
  @Override
  public boolean startsWith(Path other) {
    return mPath.startsWith(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#startsWith(java.lang.String)
   */
  @Override
  public boolean startsWith(String other) {
    return mPath.startsWith(other);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#subpath(int, int)
   */
  @Override
  public Path subpath(int beginIndex, int endIndex) {
    return mPath.subpath(beginIndex, endIndex);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#toAbsolutePath()
   */
  @Override
  public Path toAbsolutePath() {
    return mPath.toAbsolutePath();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#toFile()
   */
  @Override
  public File toFile() {
    return mPath.toFile();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#toRealPath(java.nio.file.LinkOption[])
   */
  @Override
  public Path toRealPath(LinkOption... options) throws IOException {
    return mPath.toRealPath(options);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.nio.file.Path#toUri()
   */
  @Override
  public URI toUri() {
    return mPath.toUri();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return mPath.toString();
  }
}
