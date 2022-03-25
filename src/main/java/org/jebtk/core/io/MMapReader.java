package org.jebtk.core.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

/**
 * Wrapper class to make Memory mapped file appear more like a regular random
 * access file. This is to allow it to be used as more a drop in replacement for
 * RandomAccessFile without needing to change method names etc.
 * 
 * @author antony
 *
 */
public class MMapReader {
  private RandomAccessFile mReader;
  private FileChannel mFileChannel;
  private MappedByteBuffer mBuffer;

  public MMapReader(Path file) throws IOException {
    this(file, -1);
  }

  public MMapReader(Path file, long size) throws IOException {
    mReader = FileUtils.newRandomAccess(file);

    mFileChannel = mReader.getChannel();

    if (size < 1) {
      size = mReader.length();
    }

    // Get direct byte buffer access using channel.map() operation
    mBuffer = mFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
  }

  /**
   * Close the file handles for the reader. Note that as a memory mapped file the
   * resources may not be fully released until garbage collection.
   * 
   * @throws IOException
   */
  public void close() throws IOException {
    mFileChannel.close();
    mReader.close();
  }

  public MMapReader seek(long address) {
    mBuffer.position((int) address);

    return this;
  }

  /**
   * Read a 4 byte int.
   * 
   * @return
   */
  public int readInt() {
    return mBuffer.getInt();
  }

  /**
   * Read 1 byte as an int.
   * 
   * @return
   */
  public int read() {
    return mBuffer.get();
  }

  /**
   * Read 1 byte.
   * 
   * @return
   */
  public byte readByte() {
    return mBuffer.get();
  }

  /**
   * Read an 8 byte double.
   * 
   * @return
   */
  public double readDouble() {
    return mBuffer.getDouble();
  }

  /**
   * Read an 8 byte long.
   * 
   * @return
   */
  public long readLong() {
    return mBuffer.getLong();
  }

  public short readShort() {
    return mBuffer.getShort();
  }

  /**
   * Read bytes into an array.
   * 
   * @param dst
   */
  public void read(byte[] dst) {
    mBuffer.get(dst);
  }

  /**
   * Read bytes into an array.
   * 
   * @param dst
   * @param offset
   * @param length
   */
  public void read(byte[] dst, int offset, int length) {
    mBuffer.get(dst, offset, length);
  }

  public MMapReader skipBytes(int skip) {
    return skip(skip);
  }

  public MMapReader skip(int skip) {
    mBuffer.position(mBuffer.position() + skip);

    return this;
  }

  public long getFilePointer() {
    return tell();
  }

  public long tell() {
    return mBuffer.position();
  }
}
