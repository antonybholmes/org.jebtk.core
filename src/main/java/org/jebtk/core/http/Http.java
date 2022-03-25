package org.jebtk.core.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Http {

  public static final String CRLF = "\r\n";

  private URL mUrl;

  private HttpURLConnection mConnection;

  public Http(URL url) throws IOException {
    mUrl = url;

    mConnection = (HttpURLConnection) mUrl.openConnection();
    mConnection.setDoInput(true);
    mConnection.setDoOutput(true);
    mConnection.setUseCaches(false);
    mConnection.setRequestMethod("POST");
  }

  public Http(URLPath url) throws IOException {
    this(url.toURL());
  }

  public HttpPostRequest post() {
    return new HttpPostRequest(mConnection);
  }

  public static Http open(URL url) throws IOException {
    return new Http(url);
  }

  public static Http open(URLPath url) throws IOException {
    return open(url.toURL());
  }
}
