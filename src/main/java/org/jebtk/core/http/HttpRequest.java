package org.jebtk.core.http;

import java.net.HttpURLConnection;

public abstract class HttpRequest {

  protected final HttpURLConnection mConnection;

  public HttpRequest(HttpURLConnection connection) {
    mConnection = connection;
  }
}
