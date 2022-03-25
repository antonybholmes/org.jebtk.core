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

import javax.net.ssl.X509TrustManager;

/**
 * Trust all certificates.
 * 
 * @author Antony Holmes
 *
 */
public class TrustAllManager implements X509TrustManager {

  /*
   * (non-Javadoc)
   * 
   * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
   */
  @Override
  public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.
   * X509Certificate[], java.lang.String)
   */
  @Override
  public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.
   * X509Certificate[], java.lang.String)
   */
  @Override
  public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
  }
}
