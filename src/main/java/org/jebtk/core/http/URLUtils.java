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

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.util.List;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;

import org.jebtk.core.Attribute;
import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.StreamUtils;
import org.jebtk.core.text.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides methods for interacting with a network.
 * 
 * @author Antony Holmes
 *
 */
public class URLUtils {

  private static final Logger LOG = LoggerFactory.getLogger(URLUtils.class);

  /** The Constant GOOD_IRI_CHAR. */
  public static final Pattern GOOD_IRI_CHAR = Pattern.compile("a-zA-Z0-9\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF");

  /** The Constant IP_ADDRESS. */
  public static final Pattern IP_ADDRESS = Pattern
      .compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
          + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
          + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}" + "|[1-9][0-9]|[0-9]))");

  /**
   * RFC 1035 Section 2.3.4 limits the labels to a maximum 63 octets.
   */
  private static final Pattern IRI = Pattern
      .compile("[" + GOOD_IRI_CHAR + "]([" + GOOD_IRI_CHAR + "\\-]{0,61}[" + GOOD_IRI_CHAR + "]){0,1}");

  /** The Constant GOOD_GTLD_CHAR. */
  private static final Pattern GOOD_GTLD_CHAR = Pattern.compile("a-zA-Z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF");

  /** The Constant GTLD. */
  private static final Pattern GTLD = Pattern.compile("[" + GOOD_GTLD_CHAR + "]{2,63}");

  /** The Constant HOST_NAME. */
  private static final Pattern HOST_NAME = Pattern.compile("(" + IRI + "\\.)+" + GTLD);

  /** The Constant DOMAIN_NAME. */
  public static final Pattern DOMAIN_NAME = Pattern.compile("(" + HOST_NAME + "|" + IP_ADDRESS + ")");

  /**
   * Regular expression pattern to match most part of RFC 3987 Internationalized
   * URLs, aka IRIs. Commonly used Unicode characters are added.
   */
  public static final Pattern WEB_URL_PATTERN = Pattern
      .compile("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
          + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
          + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?" + "(?:" + DOMAIN_NAME + ")"
          + "(?:\\:\\d{1,5})?)" // plus option port
          // number
          + "(\\/(?:(?:[" + GOOD_IRI_CHAR + "\\;\\/\\?\\:\\@\\&\\=\\#\\~" // plus
          // option
          // query
          // props
          + "\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?" + "(?:\\b|$)");

  // and finally, a word boundary or end of
  // input. This is to stop foo.sure from
  // matching as foo.su

  /**
   * Instantiates a new network.
   */
  private URLUtils() {
    // Do nothing
  }

  public static void launch(URLPath url) throws URISyntaxException, IOException {
    launch(url.toURL());
  }

  /**
   * Launch.
   *
   * @param url the url
   * @throws URISyntaxException the URI syntax exception
   * @throws IOException        Signals that an I/O exception has occurred.
   */
  public static void launch(URL url) throws URISyntaxException, IOException {
    launch(url.toURI());
  }

  /**
   * Launch.
   *
   * @param uri the uri
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static void launch(URI uri) throws IOException {
    Desktop.getDesktop().browse(uri);
  }

  /**
   * Mailto.
   *
   * @param recipient the recipient
   * @param subject   the subject
   * @param body      the body
   * @throws IOException        Signals that an I/O exception has occurred.
   * @throws URISyntaxException the URI syntax exception
   */
  public static void mailto(String recipient, String subject, String body) throws IOException, URISyntaxException {
    mailto(CollectionUtils.asList(recipient), subject, body);
  }

  /**
   * Mailto.
   *
   * @param recipients the recipients
   * @param subject    the subject
   * @param body       the body
   * @throws IOException        Signals that an I/O exception has occurred.
   * @throws URISyntaxException the URI syntax exception
   */
  public static void mailto(List<String> recipients, String subject, String body)
      throws IOException, URISyntaxException {
    String uriStr = String.format("mailto:%s?subject=%s&body=%s", TextUtils.join(recipients, ","), // use semicolon ";"
        // for Outlook!
        urlEncode(subject), urlEncode(body));
    Desktop.getDesktop().browse(new URI(uriStr));
  }

  /**
   * Mailto.
   *
   * @param recipient the recipient
   * @throws IOException        Signals that an I/O exception has occurred.
   * @throws URISyntaxException the URI syntax exception
   */
  public static void mailto(String recipient) throws IOException, URISyntaxException {
    Desktop.getDesktop().browse(new URI("mailto", recipient, null));
  }

  /**
   * Produces a correctly encoded URL.
   *
   * @param str the str
   * @return the url
   * @throws MalformedURLException        the malformed url exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static final URL urlEncode(String str) throws MalformedURLException, UnsupportedEncodingException {
    return new URL(URLEncoder.encode(str, "UTF-8").replace("+", "%20"));
  }

  /**
   * Constructs a URL with parameters in a standardized way.
   *
   * @param baseUrl    the base url
   * @param parameters the parameters
   * @return the url
   * @throws MalformedURLException the malformed url exception
   */
  public static final URL parameterizedUrl(String baseUrl, List<Attribute> parameters) throws MalformedURLException {
    StringBuilder buffer = new StringBuilder();

    if (CollectionUtils.isNullOrEmpty(parameters)) {
      return new URL(baseUrl);
    }

    buffer.append(baseUrl).append("?");
    buffer.append(parameters.get(0).getName()).append("=").append(parameters.get(0).getValue());

    for (int i = 1; i < parameters.size(); ++i) {
      buffer.append("&").append(parameters.get(i).getName()).append("=").append(parameters.get(i).getValue());
    }

    return new URL(buffer.toString());
  }

  /**
   * Read the contents of an URL and cache it. Newlines are removed so this method
   * is best for returning XML, JSON etc where it may be subsequently reparsed and
   * newlines do not matter.
   *
   * @param url the url
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String readUrl(URL url) throws IOException {
    // LOG.info("Reading url {}...", url);

    URLConnection connection = url.openConnection();
    // URLConnection connection = url.openConnection();

    // print_https_cert(connection);

    return readUrl(connection);
  }

  /**
   * Read url.
   *
   * @param connection the connection
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String readUrl(URLConnection connection) throws IOException {
    StringBuilder response = new StringBuilder();

    String line;

    BufferedReader in = StreamUtils.newBufferedReader(connection.getInputStream());

    try {
      while ((line = in.readLine()) != null) {
        response.append(line);
      }
    } finally {
      in.close();
    }

    return response.toString();
  }

  /**
   * Prints the https cert.
   *
   * @param con the con
   */
  public static void printHttpsCert(HttpsURLConnection con) {
    if (con == null) {
      return;
    }

    try {
      System.out.println("Response Code : " + con.getResponseCode());
      System.out.println("Cipher Suite : " + con.getCipherSuite());
      System.out.println("\n");

      Certificate[] certs = con.getServerCertificates();
      for (Certificate cert : certs) {
        System.out.println("Cert Type : " + cert.getType());
        System.out.println("Cert Hash Code : " + cert.hashCode());
        System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
        System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
        System.out.println("\n");
      }

    } catch (SSLPeerUnverifiedException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Disables checking of sll certificates. This is intended for programs in a
   * debug mode using self-signed certificates to prevent the JVM from
   * complaining.
   *
   * @throws NoSuchAlgorithmException the no such algorithm exception
   * @throws KeyManagementException   the key management exception
   */
  public static void disableSLLChecks() throws NoSuchAlgorithmException, KeyManagementException {
    TrustManager[] trustAllCerts = new TrustManager[] { new TrustAllManager() };

    // Install the all-trusting trust manager
    SSLContext sc = SSLContext.getInstance("SSL");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    // Install the all-trusting host verifier
    HttpsURLConnection.setDefaultHostnameVerifier(new TrustAllHosts());
  }

  //
  // Parse Urls
  //

  /**
   * The Class URLReader.
   */
  public static class URLReader {

    /** The m url. */
    private URL mUrl;

    /** The Constant BYTE_ARRAY_SIZE. */
    private static final int BYTE_ARRAY_SIZE = 10000000;

    /**
     * Instantiates a new URL reader.
     *
     * @param url the url
     */
    public URLReader(URL url) {
      mUrl = url;
    }

    /**
     * String.
     *
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String string() throws IOException {
      return string(mUrl.openConnection());
    }

    /**
     * Read url.
     *
     * @param connection the connection
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String string(URLConnection connection) throws IOException {
      StringBuilder response = new StringBuilder();

      String line;

      BufferedReader in = StreamUtils.newBufferedReader(connection.getInputStream());

      try {
        while ((line = in.readLine()) != null) {
          response.append(line);
        }
      } finally {
        in.close();
      }

      return response.toString();
    }

    /**
     * Bytes.
     *
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public byte[] bytes() throws IOException {
      return bytes(BYTE_ARRAY_SIZE);
    }

    /**
     * Read up to maxSize in bytes from the URL.
     *
     * @param maxSize the max size
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public byte[] bytes(int maxSize) throws IOException {
      URLConnection connection = mUrl.openConnection();

      InputStream in = connection.getInputStream();

      // Now that the InputStream is open, get the content length
      int contentLength = connection.getContentLength();

      // To avoid having to resize the array over and over and over as
      // bytes are written to the array, provide a reasonable estimate of
      // the ultimate size of the byte array
      // ByteArrayOutputStream out;

      // if (contentLength != -1) {
      // out = new ByteArrayOutputStream(contentLength);
      // } else {
      // out = new ByteArrayOutputStream(maxSize); // Pick some appropriate size
      // }

      return StreamUtils.toByteArray(in, contentLength != -1 ? contentLength : maxSize);

      /*
       * try { ByteStreams.copy(in, out);
       * 
       * ret = out.toByteArray(); } finally { in.close(); out.close(); }
       */

      // return ret;

      /*
       * int b;
       * 
       * 
       * 
       * try { while ((b = in.read()) != -1) { out.write(b); } } finally { in.close();
       * out.close(); // No effect, but good to do anyway to keep the metaphor alive }
       * 
       * return out.toByteArray();
       */
    }
  }

  /**
   * Read.
   *
   * @param url the url
   * @return the URL reader
   * @throws MalformedURLException the malformed URL exception
   */
  public static URLReader read(URLPath url) throws MalformedURLException {
    return read(url.toURL());
  }

  /**
   * Read.
   *
   * @param url the url
   * @return the URL reader
   */
  public static URLReader read(URL url) {
    return new URLReader(url);
  }

  /**
   * Checks if is url.
   *
   * @param value the value
   * @return true, if is url
   */
  public static boolean isUrl(String value) {
    if (value != null) {
      return WEB_URL_PATTERN.matcher(value).find();
    } else {
      return false;
    }
  }

  public static void downloadFile(URLPath url, Path localFile) throws IOException {
    downloadFile(url.toURL(), localFile);
  }

  public static void downloadFile(URL url, Path localFile) throws IOException {
    LOG.info("Downloading {}...", url);

    InputStream input = url.openStream();

    downloadFile(input, localFile);

    input.close();

    LOG.info("Finished.");
  }

  public static void downloadFile(InputStream input, Path file) throws IOException {
    FileUtils.write(input, file);
  }

  public static BufferedReader newBufferedReader(URLPath url) throws IOException {
    return newBufferedReader(url.toURL());
  }

  public static BufferedReader newBufferedReader(URL url) throws IOException {
    return StreamUtils.newBufferedReader(url.openConnection().getInputStream());
  }
}
