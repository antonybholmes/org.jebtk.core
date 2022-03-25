package org.jebtk.core.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

import org.jebtk.core.collections.CollectionUtils;

public class StreamUtils {
  private static final int BUFFER_SIZE = 8192;

  private StreamUtils() {
    // Do nothing
  }

  /**
   * Create a new buffer for IO operations.
   * 
   * @return A new buffer.
   */
  public static byte[] createBuffer() {
    return createBuffer(BUFFER_SIZE);
  }

  public static byte[] createBuffer(int size) {
    return new byte[size];
  }

  /**
   * Copy the bytes from one stream to another.
   * 
   * @param input  An input stream.
   * @param output An output stream.
   * @return The number of bytes copied.
   * 
   * @throws IOException
   */
  public static int copy(InputStream input, OutputStream output) throws IOException {
    return copy(input, output, BUFFER_SIZE);
  }

  public static int copy(InputStream input, OutputStream output, int size) throws IOException {
    byte[] buffer = createBuffer(size);

    int c;
    int ret = 0;

    while ((c = input.read(buffer)) > 0) {
      output.write(buffer, 0, c);
      ret += c;
    }

    return ret;
  }

  public static BufferedWriter newBufferedWriter(OutputStream stream) {
    return new BufferedWriter(newWriter(stream));
  }

  public static Writer newWriter(OutputStream stream) {
    return new OutputStreamWriter(stream, StandardCharsets.UTF_8);
  }

  /**
   * New input reader.
   *
   * @param stream the stream
   * @return the reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Reader newReader(InputStream stream) {
    return new InputStreamReader(stream, StandardCharsets.UTF_8);
  }

  /**
   * New buffered reader.
   *
   * @param stream the stream
   * @return the buffered reader
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static BufferedReader newBufferedReader(InputStream stream) {
    return new BufferedReader(newReader(stream));
  }

  public static BufferedReader newBufferedReader(Reader reader) {
    return new BufferedReader(reader);
  }

  /**
   * Returns a buffered input stream.
   *
   * @param stream the stream
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static InputStream newBuffer(InputStream stream) {
    return new BufferedInputStream(stream);
  }

  /**
   * Returns a buffered input stream.
   *
   * @param stream the stream
   * @return the input stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static OutputStream newBuffer(OutputStream stream) {
    return new BufferedOutputStream(stream);
  }

  public static GZIPOutputStream gz(OutputStream output) throws IOException {
    return new GZIPOutputStream(newBuffer(output));
  }

  /**
   * Convert an output stream into one supporting zip output.
   * 
   * @param output
   * @return
   */
  public static ZipOutputStream zip(OutputStream output) {
    return new ZipOutputStream(newBuffer(output));
  }

  /**
   * Copies all bytes from the readable channel to the writable channel. Does not
   * close or flush either channel.
   *
   * @param from the readable channel to read from
   * @param to   the writable channel to write to
   * @return the number of bytes copied
   * @throws IOException if an I/O error occurs
   */
  public static long copy(ReadableByteChannel from, WritableByteChannel to) throws IOException {
    return copy(from, to, BUFFER_SIZE);
  }

  public static long copy(ReadableByteChannel from, WritableByteChannel to, int size) throws IOException {
    ByteBuffer buf = ByteBuffer.allocate(size);

    long total = 0;

    while (from.read(buf) != -1) {
      buf.flip();

      while (buf.hasRemaining()) {
        total += to.write(buf);
      }

      buf.clear();
    }

    return total;
  }

  /**
   * Reads all bytes from an input stream into a byte array. Does not close the
   * stream.
   *
   * @param in the input stream to read from
   * @return a byte array containing all the bytes from the stream
   * @throws IOException if an I/O error occurs
   */
  public static byte[] toByteArray(InputStream in) throws IOException {
    return toByteArray(in, BUFFER_SIZE);
  }

  /**
   * To byte array.
   *
   * @param in   the in
   * @param size the buf size
   * @return the byte[]
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static byte[] toByteArray(InputStream in, int size) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream(size);

    byte[] ret = null;

    try {
      copy(in, out, size);

      ret = out.toByteArray();
    } finally {
      out.close();
    }

    if (ret == null) {
      ret = CollectionUtils.EMPTY_BYTE_ARRAY;
    }

    return ret;
  }

  /**
   * To byte array.
   *
   * @param in the in
   * @return the byte[]
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static byte[] toByteArray(ReadableByteChannel in) throws IOException {
    return toByteArray(in, BUFFER_SIZE);
  }

  /**
   * To byte array.
   *
   * @param in   the in
   * @param size the buf size
   * @return the byte[]
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static byte[] toByteArray(ReadableByteChannel in, int size) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream(size);

    byte[] ret = null;

    try {
      copy(in, Channels.newChannel(out), size);

      ret = out.toByteArray();
    } finally {
      out.close();
    }

    if (ret == null) {
      ret = CollectionUtils.EMPTY_BYTE_ARRAY;
    }

    return ret;
  }
}
