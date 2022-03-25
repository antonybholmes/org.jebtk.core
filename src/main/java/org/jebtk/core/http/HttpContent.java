package org.jebtk.core.http;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

public abstract class HttpContent {

  private HttpURLConnection mConnection;

  public HttpContent(HttpURLConnection connection) {
    mConnection = connection;
  }

  /**
   * Should modify connection as necessary and write to connection.
   * 
   * @param connection
   * @return
   * @throws IOException
   */
  public HttpResponse execute() throws IOException {
    DataOutputStream out = new DataOutputStream(new BufferedOutputStream(mConnection.getOutputStream()));

    try {
      execute(out);
    } finally {
      out.close();
    }

    return new HttpResponse(mConnection);
  }

  protected abstract void execute(DataOutputStream out) throws IOException;
}
