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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.jebtk.core.text.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FileUtils.
 */
public class FileUtils {

  /** The default charset. */
  public static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

  /** The Constant LOG. */
  protected final static Logger LOG = LoggerFactory.getLogger(FileUtils.class);

  public static final Path HOME = PathUtils.getPath(System.getProperty("user.home"));

  /**
   * Instantiates a new file utils.
   */
  private FileUtils() {
    // Do nothing
  }

  /**
   * Ls.
   *
   * @param dir the dir
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> ls(Path dir) throws IOException {
    return ls(dir, true);
  }

  /**
   * Ls.
   *
   * @param root        the root
   * @param includeDirs the include dirs
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> ls(Path root, boolean includeDirs) throws IOException {
    return ls(root, includeDirs, false);
  }

  /**
   * Ls.
   *
   * @param root          Path to iterate over.
   * @param includeDirs   Whether to include directories.
   * @param includeHidden Whether to include hidden files.
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> ls(Path root, boolean includeDirs, boolean includeHidden) throws IOException {
    return ls(root, includeDirs, includeHidden, false);
  }

  /**
   * List all files in a directory.
   *
   * @param root          the root
   * @param includeDirs   the include dirs
   * @param includeHidden the include hidden
   * @param recursive     the recursive
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> ls(Path root, boolean includeDirs, boolean includeHidden, boolean recursive)
      throws IOException {
    if (isFile(root)) {
      return Collections.emptyList();
    }

    List<Path> ret = new ArrayList<Path>();

    Deque<Path> dirStack = new ArrayDeque<Path>();

    dirStack.push(root);

    while (!dirStack.isEmpty()) {
      Path dir = dirStack.pop();

      for (Path file : Files.newDirectoryStream(dir)) {
        if (isDirectory(file)) {
          if (recursive) {
            dirStack.push(file);
          }

          if (includeDirs) {
            if (includeHidden || !isHidden(file)) {
              ret.add(file);
            }
          }
        } else {
          if (includeHidden || !isHidden(file)) {
            ret.add(file);
          }
        }
      }
    }

    Collections.sort(ret);

    return ret;
  }

  /**
   * Find all files with a given file extension.
   * 
   * @param root The root directory to search.
   * @param ext  The file extension to search for.
   * @return
   * @throws IOException
   */
  public static List<Path> ext(Path root, String ext) throws IOException {
    List<Path> files = FileUtils.ls(root, false, false, true);

    List<Path> ret = new ArrayList<Path>(files.size());

    for (Path file : files) {
      if (PathUtils.getFileExt(file).equals(ext)) {
        ret.add(file);
      }
    }

    return ret;
  }

  /**
   * Find all files that end with a given suffix.
   * 
   * @param root
   * @param ext
   * @return
   * @throws IOException
   */
  public static List<Path> endsWith(Path root, String ext) throws IOException {
    List<Path> files = FileUtils.ls(root, false, false, true);

    List<Path> ret = new ArrayList<Path>(files.size());

    for (Path file : files) {
      if (PathUtils.getName(file).endsWith(ext)) {
        ret.add(file);
      }
    }

    return ret;
  }

  /**
   * List just the directories in a directory.
   *
   * @param root the root
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> lsdir(Path root) throws IOException {
    return lsdir(root, false);
  }

  /**
   * Lsdir.
   *
   * @param root          the root
   * @param includeHidden the include hidden
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> lsdir(Path root, boolean includeHidden) throws IOException {
    return lsdir(root, includeHidden, false);
  }

  /**
   * List just the directories in a directory.
   *
   * @param root          the root
   * @param includeHidden the include hidden
   * @param recursive     the recursive
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> lsdir(Path root, boolean includeHidden, boolean recursive) throws IOException {
    List<Path> ret = new ArrayList<Path>();

    Deque<Path> dirStack = new ArrayDeque<Path>();

    dirStack.push(root);

    while (!dirStack.isEmpty()) {
      Path dir = dirStack.pop();

      for (Path file : Files.newDirectoryStream(dir)) {
        if (FileUtils.isDirectory(file)) {
          if (recursive) {
            dirStack.push(file);
          }

          if (includeHidden || !isHidden(file)) {
            ret.add(file);
          }
        }
      }
    }

    Collections.sort(ret);

    return ret;
  }

  /**
   * Ls.
   *
   * @param dir    the dir
   * @param filter the filter
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> ls(Path dir, FileFilter filter) throws IOException {
    List<Path> ret = new ArrayList<Path>();

    for (Path path : Files.newDirectoryStream(dir)) {
      if (filter.accept(path.toFile())) {
        ret.add(path);
      }
    }

    return ret;
  }

  /**
   * Finds the first file matching a pattern in a directory and returns it, or
   * null otherwise. This method is non-recursive.
   *
   * @param dir     the dir
   * @param pattern the pattern
   * @return the path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path find(Path dir, String pattern) throws IOException {
    for (Path path : ls(dir)) {
      if (path.toString().contains(pattern)) {
        return path;
      }
    }

    return null;
  }

  /**
   * Find the first file whose name ends with a given suffix.
   *
   * @param dir       The starting directory.
   * @param recursive Whether to search recursively.
   * @param pattern   The pattern to look for.
   * @return The first file found or null if search is empty.
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path endsWith(Path dir, boolean recursive, String pattern) throws IOException {
    return endsWith(dir, false, false, pattern);
  }

  /**
   * Find the first file whose name ends with a given suffix.
   *
   * @param dir         The starting directory.
   * @param includeDirs Whether to include directory names in search.
   * @param recursive   Whether to search recursively.
   * @param pattern     The pattern to look for.
   * @return The first file found or null if search is empty.
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Path endsWith(Path dir, boolean includeDirs, boolean recursive, String pattern) throws IOException {
    for (Path path : ls(dir, includeDirs, false, recursive)) {
      if (path.toString().endsWith(pattern)) {
        return path;
      }
    }

    return null;
  }

  /**
   * Find all.
   *
   * @param dir      the dir
   * @param patterns the patterns
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> findAll(Path dir, String... patterns) throws IOException {
    return findAll(dir, false, patterns);
  }

  /**
   * Find all.
   *
   * @param dir       the dir
   * @param recursive the recursive
   * @param patterns  the patterns
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> findAll(Path dir, boolean recursive, String... patterns) throws IOException {
    return findAll(dir, false, recursive, patterns);
  }

  /**
   * Find all.
   *
   * @param dir         the dir
   * @param includeDirs the include dirs
   * @param recursive   the recursive
   * @param patterns    the patterns
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> findAll(Path dir, boolean includeDirs, boolean recursive, String... patterns)
      throws IOException {
    List<Path> ret = new ArrayList<Path>();

    for (Path path : ls(dir, includeDirs, recursive)) {
      for (String pattern : patterns) {
        if (path.toString().contains(pattern)) {
          ret.add(path);
          break;
        }
      }
    }

    return ret;
  }

  /**
   * Find file matches that match all the patterns.
   *
   * @param dir      the dir
   * @param patterns the patterns
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static List<Path> findMatch(Path dir, String... patterns) throws IOException {
    List<Path> ret = new ArrayList<Path>();

    for (Path path : Files.newDirectoryStream(dir)) {
      boolean found = true;

      for (String pattern : patterns) {
        if (!path.toString().contains(pattern)) {
          found = false;
          break;
        }
      }

      if (found) {
        ret.add(path);
      }
    }

    return ret;
  }

  public static List<Path> match(Path dir, boolean recursive, String... patterns) throws IOException {
    return match(dir, false, recursive, patterns);
  }

  /**
   * Find files matching all parameters
   * 
   * @param dir
   * @param includeDirs
   * @param recursive
   * @param patterns
   * @return
   * @throws IOException
   */
  public static List<Path> match(Path dir, boolean includeDirs, boolean recursive, String... patterns)
      throws IOException {
    List<Path> ret = new ArrayList<Path>();

    for (Path path : ls(dir, includeDirs, recursive)) {
      boolean found = true;

      for (String pattern : patterns) {
        if (!path.toString().contains(pattern)) {
          found = false;
          break;
        }
      }

      if (found) {
        ret.add(path);
      }
    }

    return ret;
  }

  /**
   * New buffered writer. If the file has a gz extension, the writer will
   * automatically wrap a gz compressed output.
   *
   * @param file the file
   * @return the buffered writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedWriter newBufferedWriter(Path file) throws IOException {
    if (PathUtils.getFileExt(file).equals("gz")) {
      return StreamUtils.newBufferedWriter(gz(file));
    } else {
      return Files.newBufferedWriter(file, DEFAULT_CHARSET);
    }
  }

  /**
   * New buffered table writer.
   *
   * @param file the file
   * @return the buffered table writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedTableWriter newBufferedTableWriter(Path file) throws IOException {
    return new BufferedTableWriter(newFileWriter(file));
  }

  /**
   * New file writer.
   *
   * @param file the file
   * @return the file writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static FileWriter newFileWriter(Path file) throws IOException {
    return new FileWriter(file.toFile());
  }

  /**
   * Create a buffered reader from a file. This method will cope with gzipped
   * files (by name) so can be used for compressed or uncompressed files.
   *
   * @param file a file, optionally gzipped.
   * @return the buffered reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedReader newBufferedReader(Path file) throws IOException {
    if (PathUtils.getName(file).toLowerCase().endsWith("gz")) {
      // Cope with gzipped files
      return StreamUtils.newBufferedReader(newBufferedInputStream(file));
    } else {
      return Files.newBufferedReader(file, DEFAULT_CHARSET);
    }
  }

  /**
   * Creates a new buffered input stream.
   *
   * @param file the file
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream newBufferedInputStream(Path file) throws IOException {
    return StreamUtils.newBuffer(newInputStream(file));
  }

  /**
   * Creates a new input stream. If the file name ends with the gz ext, The stream
   * will be automatically wrapped into a GZInputStream.
   *
   * @param file the file
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream newInputStream(Path file) throws IOException {
    InputStream inputStream = Files.newInputStream(file);

    if (file.getFileName().toString().toLowerCase().endsWith("gz")) {
      // Cope with gzipped files
      inputStream = new GZIPInputStream(inputStream);
    }

    return inputStream;
  }

  /**
   * New output stream for writing bytes.
   *
   * @param file the file
   * @return the output stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static OutputStream newOutputStream(Path file) throws IOException {
    return Files.newOutputStream(file);
  }

  public static OutputStream newBufferedOutputStream(Path file) throws IOException {
    return StreamUtils.newBuffer(newOutputStream(file));
  }

  /**
   * Checks if is directory.
   *
   * @param file the file
   * @return true, if is directory
   */
  public static boolean isDirectory(Path file) {
    return exists(file) && Files.isDirectory(file);
  }

  /**
   * Checks if is hidden.
   *
   * @param file the file
   * @return true, if is hidden
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static boolean isHidden(Path file) throws IOException {
    return exists(file) && Files.isHidden(file);
  }

  /**
   * Checks if is file.
   *
   * @param file the file
   * @return true, if is file
   */
  public static boolean isFile(Path file) {
    return exists(file) & !isDirectory(file);
  }

  /**
   * Returns true if the file exists.
   *
   * @param file the file
   * @return true, if successful
   */
  public static boolean exists(Path file) {
    if (file != null) {
      return Files.exists(file);
    } else {
      return false;
    }
  }

  /**
   * Copy.
   *
   * @param source the source
   * @param dest   the dest
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static boolean copy(Path source, Path dest) throws IOException {
    Files.copy(source, dest);

    return true;
  }

  /**
   * Mv.
   *
   * @param source the source
   * @param dest   the dest
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void mv(Path source, Path dest) throws IOException {
    Files.move(source, dest, StandardCopyOption.REPLACE_EXISTING);
  }

  /**
   * Makes a new directory if it does not exist. The default behavior is to create
   * all non-existant parent directories if they do not exist.
   *
   * @param dir the dir
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static boolean mkdir(Path dir) throws IOException {
    if (!exists(dir)) {
      LOG.info("Creating directory {}...", dir);

      Files.createDirectories(dir);

      return true;
    } else {
      return false;
    }
  }

  /**
   * Rm.
   *
   * @param dir the dir
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void rm(Path dir) throws IOException {
    if (!exists(dir) || !isDirectory(dir)) {
      return;
    }

    Deque<Path> stack = new ArrayDeque<Path>();

    stack.push(dir);

    rm(stack);
  }

  /**
   * Recursively empty a directory, but doesn't delete the directory itself.
   *
   * @param dir the dir
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void empty(Path dir) throws IOException {
    if (!exists(dir) || !isDirectory(dir)) {
      return;
    }

    Deque<Path> stack = new ArrayDeque<Path>();

    List<Path> files = ls(dir);

    for (Path file : files) {
      stack.push(file);
    }

    rm(stack);
  }

  /**
   * Rm.
   *
   * @param stack the stack
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void rm(Deque<Path> stack) throws IOException {
    Path path;

    while (!stack.isEmpty()) {
      path = stack.pop();

      if (isDirectory(path)) {
        List<Path> files = ls(path);

        if (files.size() > 0) {
          stack.push(path);

          for (Path file : files) {
            stack.push(file);
          }
        } else {
          Files.delete(path);
        }
      } else {
        Files.delete(path);
      }
    }
  }

  /**
   * New gzip input stream.
   *
   * @param file the file
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream newGzipInputStream(Path file) throws IOException {
    return new GZIPInputStream(Files.newInputStream(file));
  }

  /**
   * Write.
   *
   * @param path  the path
   * @param bytes the bytes
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void write(byte[] bytes, Path path) throws IOException {
    FileOutputStream stream = new FileOutputStream(path.toFile());

    try {
      stream.write(bytes);
    } finally {
      stream.close();
    }
  }

  public static void write(InputStream in, Path file) throws IOException {
    Files.copy(in, file);
  }

  /**
   * Returns a buffered data input stream on the file.
   *
   * @param file the file
   * @return the data input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataInputStream newDataInputStream(Path file) throws IOException {
    return new DataInputStream(newBufferedInputStream(file));
  }

  /**
   * New data output stream.
   *
   * @param file the file
   * @return the data output stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static DataOutputStream newDataOutputStream(Path file) throws IOException {
    return new DataOutputStream(newBufferedOutputStream(file));
  }

  /**
   * Returns a new random access file for reading.
   *
   * @param file the file
   * @return the random access file
   * @throws FileNotFoundException the file not found exception
   */
  public static RandomAccessFile newRandomAccess(Path file) throws FileNotFoundException {
    return new RandomAccessFile(file.toFile(), "r");
  }

  /**
   * New buffered reader.
   *
   * @param file  the file
   * @param entry the entry
   * @return the buffered reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedReader newBufferedReader(ZipFile file, ZipEntry entry) throws IOException {
    return StreamUtils.newBufferedReader(newBufferedInputStream(file, entry));
  }

  /**
   * New buffered input stream.
   *
   * @param file  the file
   * @param entry the entry
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream newBufferedInputStream(ZipFile file, ZipEntry entry) throws IOException {
    return StreamUtils.newBuffer(newInputStream(file, entry));
  }

  /**
   * New input stream.
   *
   * @param file  the file
   * @param entry the entry
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream newInputStream(ZipFile file, ZipEntry entry) throws IOException {
    return file.getInputStream(entry);
  }

  /**
   * Open a zip file.
   *
   * @param file the file
   * @return the zip file
   * @throws ZipException the zip exception
   * @throws IOException  Signals that an I/O exception has occurred.
   */
  public static ZipFile newZipFile(Path file) throws ZipException, IOException {
    return new ZipFile(file.toFile());
  }

  /**
   * Tokenize.
   *
   * @param file the file
   * @param tf   the tf
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void tokenize(Path file, TokenFunction tf) throws IOException {
    tokenize(file, false, tf);
  }

  /**
   * Tokenize.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @param tf         the tf
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void tokenize(Path file, boolean skipHeader, TokenFunction tf) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    try {
      tokenize(reader, skipHeader, tf);
    } finally {
      reader.close();
    }
  }

  /**
   * Tokenize.
   *
   * @param reader the reader
   * @param tf     the tf
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void tokenize(BufferedReader reader, TokenFunction tf) throws IOException {
    tokenize(reader, false, tf);
  }

  /**
   * Run through reader tokenizing each line for processing. Reader is closed
   * after function has been applied.
   *
   * @param reader     the reader
   * @param skipHeader the skip header
   * @param tf         the tf
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void tokenize(BufferedReader reader, boolean skipHeader, TokenFunction tf) throws IOException {
    // try {
    // Skip header
    if (skipHeader) {
      reader.readLine();
    }

    String line;
    List<String> tokens;

    while ((line = reader.readLine()) != null) {
      if (Io.isEmptyLine(line)) {
        continue;
      }

      tokens = TextUtils.tabSplit(line);

      tf.parse(tokens);
    }
  }

  /**
   * Lines.
   *
   * @param file the file
   * @param lf   the lf
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void lines(Path file, LineFunction lf) throws IOException {
    lines(file, false, lf);
  }

  /**
   * Lines.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @param lf         the lf
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void lines(Path file, boolean skipHeader, LineFunction lf) throws IOException {
    BufferedReader reader = FileUtils.newBufferedReader(file);

    try {
      lines(reader, skipHeader, lf);
    } finally {
      reader.close();
    }
  }

  /**
   * Lines.
   *
   * @param reader the reader
   * @param lf     the lf
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void lines(BufferedReader reader, LineFunction lf) throws IOException {
    lines(reader, false, lf);
  }

  /**
   * Run through reader tokenizing each line for processing. Reader is closed
   * after function has been applied.
   *
   * @param reader     the reader
   * @param skipHeader the skip header
   * @param lf         the lf
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void lines(BufferedReader reader, boolean skipHeader, LineFunction lf) throws IOException {
    // try {
    // Skip header
    if (skipHeader) {
      reader.readLine();
    }

    String line;

    while ((line = reader.readLine()) != null) {
      if (!Io.isEmptyLine(line)) {
        lf.parse(line);
      }
    }
  }

  /**
   * Tokenize.
   *
   * @param tf the tf
   * @return the tokenize
   */
  public static Tokenize tokenize(TokenFunction tf) {
    return Tokenize.tokenize(tf);
  }

  public static Lines lines(LineFunction f) {
    return Lines.lines(f);
  }

  /**
   * Read lines.
   *
   * @param file       the file
   * @param skipHeader the skip header
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final List<String> readLines(Path file, boolean skipHeader) throws IOException {
    LOG.info("Load list from {}, {}...", file, skipHeader);

    BufferedReader reader = newBufferedReader(file);

    String line;

    List<String> rows = new ArrayList<String>();

    try {

      if (skipHeader) {
        reader.readLine();
      }

      while ((line = reader.readLine()) != null) {
        List<String> tokens = TextUtils.tabSplit(line);

        rows.add(tokens.get(0));
      }
    } finally {
      reader.close();
    }

    return rows;
  }

  public static GZIPOutputStream gz(Path file) throws IOException {
    return StreamUtils.gz(newOutputStream(file));
  }

  /**
   * Wrap an output stream into a zip stream.
   *
   * @param output the output
   * @return the zip output stream
   * @throws IOException
   */
  public static ZipOutputStream zip(Path file) throws IOException {
    return StreamUtils.zip(newOutputStream(file));
  }

  /**
   * Zip some files.
   * 
   * @param out
   * @param files
   * @throws IOException
   */
  public static void zip(Path out, Collection<Path> files) throws IOException {
    ZipOutputStream zos = zip(out);

    for (Path f : files) {

      ZipEntry ze = new ZipEntry(PathUtils.getName(f));
      zos.putNextEntry(ze);

      InputStream in = newBufferedInputStream(f);

      StreamUtils.copy(in, zos);

      in.close();
      zos.closeEntry();
    }

    zos.close();
  }

  public static MappedByteBuffer newMemMappedFile(Path file) throws IOException {
    return newMemMappedFile(file, -1);
  }

  public static MappedByteBuffer newMemMappedFile(Path file, long size) throws IOException {
    RandomAccessFile reader = newRandomAccess(file);

    FileChannel fileChannel = reader.getChannel();

    if (size < 1) {
      size = reader.length();
    }

    // Get direct byte buffer access using channel.map() operation
    MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);

    return buffer;
  }

  public static MMapReader newMemMappedReader(Path file) throws IOException {
    return new MMapReader(file);
  }
}
