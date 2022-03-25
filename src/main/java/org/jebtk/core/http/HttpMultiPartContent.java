package org.jebtk.core.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.util.Map.Entry;

import org.jebtk.core.collections.IterHashMap;
import org.jebtk.core.collections.IterMap;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.io.StreamUtils;

/**
 * Creates a multipart form post connection for sending data to a server.
 * 
 * https://stackoverflow.com/questions/2646194/multipart-file-upload-post-request-from-java
 * https://stackoverflow.com/questions/19026256/how-to-upload-multipart-form-data-and-image-to-server-in-android
 * 
 * @author Antony Holmes
 *
 */
public class HttpMultiPartContent extends HttpContent {
  private static final String TWO_HYPHENS = "--";

  private static final String MIME_TEXT = "text/plain";

  private static final String TEXT_CONTENT = "Content-Type: " + MIME_TEXT + Http.CRLF;

  private static final String BINARY_TRANSFER = "Content-Transfer-Encoding: binary" + Http.CRLF;

  private IterMap<String, String> mParamMap = new IterHashMap<String, String>();
  private IterMap<String, Path> mFileMap = new IterHashMap<String, Path>();

  private final String mBoundary;

  private final String mMultipartBlock;

  private final String mMultipartEnd;

  public HttpMultiPartContent(HttpURLConnection connection) {
    super(connection);

    mBoundary = "****" + Long.toString(System.currentTimeMillis()) + "****";

    mMultipartBlock = TWO_HYPHENS + mBoundary + Http.CRLF;
    mMultipartEnd = TWO_HYPHENS + mBoundary + TWO_HYPHENS + Http.CRLF;

    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + mBoundary);
  }

  public HttpMultiPartContent addParam(String name, String value) {
    mParamMap.put(name, value);

    return this;
  }

  public HttpMultiPartContent addFile(String name, Path file) {
    mFileMap.put(name, file);

    return this;
  }

  @Override
  protected void execute(DataOutputStream out) throws IOException {
    try {
      if (mFileMap.size() > 0) {
        writeFiles(out);
      }

      if (mParamMap.size() > 0) {
        writeParams(out);
      }

      out.writeBytes(mMultipartEnd);
    } finally {
      out.close();
    }
  }

  private void writeFiles(DataOutputStream out) throws IOException {
    for (Entry<String, Path> item : mFileMap) {
      writeFile(item.getKey(), item.getValue(), out);
    }
  }

  private void writeFile(String name, Path file, DataOutputStream out) throws IOException {
    out.writeBytes(mMultipartBlock);
    out.writeBytes("Content-Disposition: form-data; name=\"");
    out.writeBytes(name);
    out.writeBytes("\"; filename=\"");
    out.writeBytes(PathUtils.getName(file));
    out.writeBytes("\"");
    out.writeBytes(Http.CRLF);
    out.writeBytes(TEXT_CONTENT);
    out.writeBytes(BINARY_TRANSFER);
    out.writeBytes(Http.CRLF);

    InputStream in = FileUtils.newInputStream(file);

    try {
      StreamUtils.copy(in, out);
    } finally {
      in.close();
    }

    // while ((bytesRead = in.read(mBuffer, 0, MAX_BUFFER_SIZE)) > 0) {
    // out.write(mBuffer, 0, bytesRead);
    // bytesAvailable = fileInputStream.available();
    // bufferSize = Math.min(bytesAvailable, MAX_BUFFER_SIZE);
    // }

    out.writeBytes(Http.CRLF);
  }

  private void writeParams(DataOutputStream out) throws IOException {
    for (Entry<String, String> item : mParamMap) {
      writeParam(item.getKey(), item.getValue(), out);
    }
  }

  private void writeParam(String key, String value, DataOutputStream out) throws IOException {
    out.writeBytes(mMultipartBlock);
    out.writeBytes("Content-Disposition: form-data; name=\"");
    out.writeBytes(key);
    out.writeBytes("\"");
    out.writeBytes(Http.CRLF);
    out.writeBytes(TEXT_CONTENT);
    out.writeBytes(Http.CRLF);
    out.writeBytes(value);
    out.writeBytes(Http.CRLF);
  }
}
