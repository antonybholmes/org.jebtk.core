package org.jebtk.core.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * A response object so that response from a http connection can be read.
 * 
 * @author antony
 *
 */
public class HttpResponse {
  private HttpURLConnection mC;

  public HttpResponse(HttpURLConnection c) {
    mC = c;
  }

  /**
   * Return an input stream response to directly read from the connection. Users
   * are responsible for closing stream.
   * 
   * @return
   * @throws IOException
   */
  public InputStream getInputStream() throws IOException {
    return new BufferedInputStream(mC.getInputStream());
  }
}
