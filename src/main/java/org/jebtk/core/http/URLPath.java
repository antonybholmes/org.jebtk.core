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
package org.jebtk.core.http;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jebtk.core.text.TextUtils;

/**
 * Easily build urls from string components.
 * 
 * @author Antony Holmes
 *
 */
public class URLPath implements Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  //
  // private static final Pattern SCHEME_REGEX =
  // Pattern.compile("^(.+):\\/\\/");
  //
  // private static final Pattern HOST_PORT_REGEX = Pattern
  // .compile("(?:^|:\\/\\/)([a-zA-Z0-9\\-\\.]+)(:\\d+)?\\/");
  //
  // private static final Pattern PATH_REGEX = Pattern.compile("\\/(.+)\\??");
  //
  // private static final Pattern QUERY_REGEX = Pattern.compile("\\?(.+)");

  /**
   * A regex for the url minus any parameters.
   */
  // private static final Pattern HOST_REGEX = Pattern
  // .compile("((https?:\\/\\/)?(localhost|([\\w-]+(\\.[\\w-]+)+))(:\\d+)?)");

  // private static final Pattern PATH_REGEX = Pattern
  // .compile("[^\\/:]\\/([\\/\\w\\.-]+[^\\/])");

  // private static final Pattern PORT_REGEX = Pattern.compile(":(\\d+)");

  private static final Pattern PART_REGEX = Pattern.compile("([\\w-]+)");

  /**
   * The constant URL_SEPARATOR.
   */
  public static final String URL_SEPARATOR = "/";

  /**
   * The constant PORT_SEPARATOR.
   */
  public static final String PORT_SEPARATOR = ":";

  /**
   * The constant PARAM_SEPARATOR.
   */
  private static final String PARAM_SEPARATOR = "&";

  /**
   * The member parts.
   */
  protected final List<String> mParts = new ArrayList<String>();

  /**
   * The member props.
   */
  private final List<Param> mParams = new ArrayList<Param>();

  private final List<Param> mHeaders = new ArrayList<Param>();

  private int mPort = -1;

  private String mScheme = "http";

  private String mHost;

  private String mUrl = null;

  private String mStr = null;

  /**
   * Instantiates a new url builder.
   *
   * @param server the server
   */
  // public UrlBuilder(URL server) {
  // this(server.toString());
  // }

  /**
   * Instantiates a new url builder.
   *
   * @param server the server
   */
  // public UrlBuilder(String server) {
  // mHost = getHost(server);
  // mPath = getPath(server);
  //
  // mParts.add(mHost);
  // mParts.add(mPath);
  // }

  /*
   * public UrlBuilder(String server, int port) { StringBuilder buffer = new
   * StringBuilder(getHost(server));
   * 
   * Matcher matcher = PORT_REGEX.matcher(buffer);
   * 
   * // If port already specified, replace it if (matcher.find()) {
   * buffer.replace(matcher.start(1), matcher.end(1), Integer.toString(port)); }
   * else { buffer.append(PORT_SEPARATOR).append(port).toString(); }
   * 
   * mServer = buffer.toString(); mPort = port; }
   */

  public URLPath() {
    // Do nothing
  }

  /**
   * Instantiates a new url builder.
   *
   * @param urlBuilder the url builder
   * @param parts      the parts
   */
  protected URLPath(URLPath urlBuilder) {
    mScheme = urlBuilder.mScheme;
    mHost = urlBuilder.mHost;
    mPort = urlBuilder.mPort;
    mParts.addAll(urlBuilder.mParts);
    mParams.addAll(urlBuilder.mParams);
    mHeaders.addAll(urlBuilder.mHeaders);
  }

  public String getScheme() {
    return mScheme;
  }

  public String getHost() {
    return mHost;
  }

  public String getPath() {
    StringBuilder buffer = new StringBuilder();

    getPath(buffer);

    return buffer.toString();
  }

  public void getPath(Appendable buffer) {
    TextUtils.join(mParts, URL_SEPARATOR, buffer);
  }

  public int getPort() {
    return mPort;
  }

  public URLPath scheme(String scheme) {
    URLPath url = new URLPath(this);

    url.mScheme = scheme;

    return url;
  }

  public URLPath host(String host) {
    URLPath url = new URLPath(this);

    url.mHost = host;

    return url;
  }

  public URLPath port(int port) {
    URLPath url = new URLPath(this);

    url.mPort = port;

    return url;
  }

  /**
   * Adds the path.
   *
   * @param path the path
   * @return the url builder
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public URLPath join(String path) {
    URLPath ret = new URLPath(this);

    for (String p : TextUtils.fastSplit(path, "/")) {
      ret.mParts.add(p);
    }

    return ret;
  }

  public URLPath join(Object path) {
    return join(path.toString());
  }

  public URLPath paths(Collection<String> paths) {
    URLPath ret = new URLPath(this);

    ret.mParts.addAll(paths);

    return ret;
  }

  /**
   * Adds the path.
   *
   * @param path the path
   * @return the url builder
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public URLPath join(int path) {
    return join(Integer.toString(path));
  }

  /**
   * Adds the path.
   *
   * @param path the path
   * @return the url builder
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public URLPath join(double path) {
    return join(Double.toString(path));
  }

  public URLPath param(String param) {
    List<String> tokens = TextUtils.fastSplit(param, TextUtils.EQUALS_DELIMITER);

    return param(tokens.get(0), tokens.get(1));
  }

  /**
   * Adds the param.
   *
   * @param name  the name
   * @param value the value
   * @return the url builder
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public URLPath param(String name, double value) {
    return param(name, Double.toString(value));
  }

  /**
   * Adds the param.
   *
   * @param name  the name
   * @param value the value
   * @return the url builder
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public URLPath param(String name, int value) {
    return param(name, Integer.toString(value));
  }

  /**
   * Adds the param.
   *
   * @param name  the name
   * @param value the value
   * @return the url builder
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public URLPath param(String name, String value) {
    return param(new StaticParam(name, value));
  }

  /**
   * Add a boolean param. True is represented as "t" and false as "f".
   * 
   * @param name  Paramter name.
   * @param value Parameter value.
   * @return New instance of UrlBuilder with parameter added.
   */
  public URLPath param(String name, boolean value) {
    return param(name, value ? "t" : "f");
  }

  public URLPath param(Param param) {
    URLPath url = new URLPath(this);

    // Don't add if param values are null
    if (!TextUtils.isNullOrEmpty(param.getName()) && !TextUtils.isNullOrEmpty(param.getValue())) {
      url.mParams.add(param);
    }

    return url;
  }

  public URLPath props(Collection<Param> props) {
    URLPath url = new URLPath(this);

    url.mParams.addAll(props);

    return url;
  }

  /**
   * Adds an API key via a URL parameter.
   * 
   * @param value key.
   * @return
   */
  public URLPath key(String value) {
    return param("key", value);
  }

  /**
   * Adds an api header request via http authorization basic header. The key will
   * be written as the user in the user:password string with an empy password so
   * to parse, split on colon and take the first token.
   * 
   * @param key
   * @return
   */
  public URLPath authKey(String key) {
    return auth(key, TextUtils.EMPTY_STRING);
  }

  public URLPath auth(String user, String password) {
    String authString = user + ":" + password;
    System.out.println("auth string: " + authString);

    String basicAuth = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));

    return header("Authorization", "Basic " + basicAuth);
  }

  public URLPath header(String name, String value) {
    return header(new StaticParam(name, value));
  }

  public URLPath header(Param param) {
    URLPath url = new URLPath(this);

    url.mHeaders.add(param);

    return url;
  }

  /**
   * Add multiple parameters with the same name.
   *
   * @param name   the name
   * @param values the values
   * @return the url builder
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public URLPath props(String name, Object... values) {
    URLPath url = new URLPath(this);

    for (Object value : values) {
      url.mParams.add(new StaticParam(name, value));
    }

    return this;
  }

  /**
   * Returns a string representation of a valid url for pasting into a brower or
   * other tool. This differs from toString() in that toString() will also output
   * header parameters so the url will be invalid.
   * 
   * @return
   */
  public String url() {
    if (mUrl == null) {
      StringBuilder buffer = new StringBuilder();

      url(buffer);

      mUrl = buffer.toString();
    }

    return mUrl;
  }

  public void url(StringBuilder buffer) {
    // if (mPath.length() > 0) {
    // buffer.append(mPath);
    // }

    // buffer.append(URL_SEPARATOR);

    buffer.append(mScheme);
    buffer.append("://");
    buffer.append(mHost);

    if (mPort > -1) {
      buffer.append(":");
      buffer.append(mPort);
    }

    buffer.append(URL_SEPARATOR);

    getPath(buffer);

    if (mParams.size() > 0) {
      buffer.append("?");

      TextUtils.join(mParams, PARAM_SEPARATOR, buffer);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    if (mStr == null) {
      StringBuilder buffer = new StringBuilder();

      url(buffer);

      if (mHeaders.size() > 0) {
        buffer.append(" ");
        TextUtils.join(mHeaders, TextUtils.COMMA_DELIMITER, buffer);
      }

      mStr = buffer.toString();
    }

    return mStr;
  }

  /**
   * Converts the URL construct into a URL for compatibility.
   *
   * @return the url
   * @throws MalformedURLException the malformed url exception
   */
  public URL toURL() throws MalformedURLException {
    return new URL(url());
  }

  /**
   * Opens a url connection build from the represented url. This method will add
   * any necessary headers to the request.
   * 
   * @return
   * @throws IOException
   */
  public URLConnection openConnection() throws IOException {
    URL url = toURL();

    URLConnection connection = url.openConnection();

    for (Param header : mHeaders) {
      connection.setRequestProperty(header.getName(), header.getValue());
    }

    return connection;
  }

  /**
   * Clean.
   *
   * @param text the text
   * @return the string
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static String clean(String text) {
    String ret = TextUtils.EMPTY_STRING;

    Matcher matcher = PART_REGEX.matcher(text);

    if (matcher.find()) {
      ret = encode(text);
    }

    return ret;
  }

  public static String encode(String text) {
    String ret = TextUtils.EMPTY_STRING;

    try {
      ret = URLEncoder.encode(text, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return ret;
  }

  // /**
  // * Sanitize.
  // *
  // * @param text the text
  // * @return the string
  // */
  // private static String getHost(String server) {
  // Matcher matcher = HOST_REGEX.matcher(server);
  //
  // if (matcher.find()) {
  // return matcher.group(1); // sanitize(server);
  // } else {
  // return TextUtils.EMPTY_STRING;
  // }
  // }
  //
  // private static String getPath(String server) {
  // Matcher matcher = PATH_REGEX.matcher(server);
  //
  // if (matcher.find()) {
  // return matcher.group(1); // sanitize(server);
  // } else {
  // return TextUtils.EMPTY_STRING;
  // }
  // }

  public static URLPath fromUrl(URL url) {
    return fromUrl(url.toString());
  }

  /**
   * Convert a string URL into a UrlBuilder.
   * 
   * @param url
   * @return
   */
  public static URLPath fromUrl(String url) {
    // System.out.println("Building url from " + url);

    int p1 = 0;
    int p2 = 0;
    int p3 = 0;
    int n = url.length();

    String scheme;

    p2 = url.indexOf("://", p1);

    if (p2 > 0) {
      scheme = url.substring(p1, p2);
      p1 = p2 + 3;
    } else {
      // Assume http if nothing specified
      scheme = "http";
    }

    // host

    String host;
    int port = -1;

    p2 = url.indexOf(":", p1);
    p3 = url.indexOf("/", p1);

    if (p2 != -1) {
      host = url.substring(p1, p2);
      port = Integer.parseInt(url.substring(p2 + 1, p3));
    } else {
      host = url.substring(p1, p3);
    }

    p1 = p3 + 1;

    // Part of path

    List<String> paths = new ArrayList<String>();

    // Path ends either when we encounter ? or end of string
    p3 = url.indexOf("?", p1);

    // If we can't find ? then process until end of string
    if (p3 == -1) {
      p3 = n;
    }

    // Keep parsing tokens until we get to either ? or end
    while (p1 < p3) {
      p2 = url.indexOf("/", p1);

      if (p2 == -1) {
        p2 = p3;
      }

      paths.add(url.substring(p1, p2));

      p1 = p2 + 1;
    }

    // props

    // Index of param start
    p1 = p3 + 1;

    List<Param> props = new ArrayList<Param>();

    // If p1 is not at the end of the string, parse url props
    while (p1 < n) {
      p2 = url.indexOf("=", p1);
      p3 = url.indexOf("&", p1);

      // If there is no & symbol, we are in the last parameter so set to the
      // end of the string
      if (p3 == -1) {
        p3 = n;
      }

      props.add(new StaticParam(url.substring(p1, p2), url.substring(p2 + 1, p3)));

      p1 = p3 + 1;
    }

    // //System.out.println("host " + host);
    //
    //
    // // Search from after the first //
    // int index = url.indexOf("//") + 2;
    //
    // List<String> parts = Collections.emptyList();
    //
    // //System.err.println("Searching for " + PATH_REGEX + " in " +
    // url.substring(index) + " " + index);
    //
    // matcher = PATH_REGEX.matcher(url.substring(index));
    //
    // if (matcher.find()) {
    // parts = TextUtils.fastSplit(matcher.group(1), "/");
    // }
    //
    // //System.out.println("parts " + parts);
    //
    // List<Param> props = Collections.emptyList();
    //
    // matcher = QUERY_REGEX.matcher(url);
    //
    // if (matcher.find()) {
    // for (String param : TextUtils.fastSplit(matcher.group(1), "&")) {
    // List<String> tokens = TextUtils.fastSplit(param, "=");
    //
    // props.add(new StaticParam(tokens.get(0), tokens.get(1)));
    // }
    // }
    //
    // return new UrlBuilder(scheme, host, port, parts, props);
    //
    //
    //
    //
    // Matcher matcher;
    //
    // String scheme = "http";
    //
    // matcher = SCHEME_REGEX.matcher(url);
    //
    // if (matcher.find()) {
    // scheme = matcher.group(1);
    // }
    //
    // //System.out.println("scheme " + scheme);
    //
    // String host = TextUtils.NA;
    // int port = -1;
    //
    // matcher = HOST_PORT_REGEX.matcher(url);
    //
    // if (matcher.find()) {
    // host = matcher.group(1);
    //
    // if (matcher.group(2) != null) {
    // // Strip off beginning ':'
    // port = Integer.parseInt(matcher.group(2).substring(1));
    // }
    // }
    //
    // //System.out.println("host " + host);
    //
    //
    // // Search from after the first //
    // int index = url.indexOf("//") + 2;
    //
    // List<String> parts = Collections.emptyList();
    //
    // //System.err.println("Searching for " + PATH_REGEX + " in " +
    // url.substring(index) + " " + index);
    //
    // matcher = PATH_REGEX.matcher(url.substring(index));
    //
    // if (matcher.find()) {
    // parts = TextUtils.fastSplit(matcher.group(1), "/");
    // }
    //
    // //System.out.println("parts " + parts);
    //
    // List<Param> props = Collections.emptyList();
    //
    // matcher = QUERY_REGEX.matcher(url);
    //
    // if (matcher.find()) {
    // for (String param : TextUtils.fastSplit(matcher.group(1), "&")) {
    // List<String> tokens = TextUtils.fastSplit(param, "=");
    //
    // props.add(new StaticParam(tokens.get(0), tokens.get(1)));
    // }
    // }

    // System.err.println(new UrlBuilder(scheme, host, port, parts, props));

    return new URLPath().scheme(scheme).host(host).port(port).paths(paths).props(props);
  }
}
