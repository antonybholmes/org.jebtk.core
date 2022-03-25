package org.jebtk.core.http;

import java.net.HttpURLConnection;

public class HttpPostRequest extends HttpRequest {

  public static final String CRLF = "\r\n";

  public HttpPostRequest(HttpURLConnection connection) {
    super(connection);
  }

  public HttpMultiPartContent multipart() {
    return new HttpMultiPartContent(mConnection);
  }
}
