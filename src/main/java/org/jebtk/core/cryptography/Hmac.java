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
package org.jebtk.core.cryptography;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.jebtk.core.PrimitiveUtils;
import org.jebtk.core.text.TextUtils;

import com.google.common.io.BaseEncoding;

/**
 * The Class Hmac.
 */
public class Hmac {

  /** The Constant HMAC_SHA256. */
  private static final String HMAC_SHA256 = "HmacSHA256";

  /**
   * Calculate SHA 256 HMAC.
   *
   * @param key     the key
   * @param message the message
   * @return the string
   */
  public static String calculateSHA256HMAC(String key, long message) {
    return calculateSHA256HMAC(key, PrimitiveUtils.toByteArray(message));
  }

  /**
   * Computes RFC 2104-compliant HMAC signature.
   *
   * @param key     The signing key.
   * @param message The data to be signed.
   * @return The Base64-encoded RFC 2104-compliant HMAC signature.
   */
  public static String calculateSHA256HMAC(String key, String message) {
    byte[] data = hmacSHA256(key, message);

    if (data == null) {
      return TextUtils.EMPTY_STRING;
    }

    // base64-encode the hmac. Since we are using SHA256 this will return
    // 256 bits as 32 bytes which when coded in base16 (hex) gives a
    // 64 char string
    return BaseEncoding.base16().lowerCase().encode(data);
  }

  /**
   * Calculate SHA 256 HMAC.
   *
   * @param key     the key
   * @param message the message
   * @return the string
   */
  public static String calculateSHA256HMAC(String key, byte[] message) {
    byte[] data = hmacSHA256(key, message);

    if (data == null) {
      return TextUtils.EMPTY_STRING;
    }

    // base64-encode the hmac. Since we are using SHA256 this will return
    // 256 bits as 32 bytes which when coded in base16 (hex) gives a
    // 64 char string
    return BaseEncoding.base16().lowerCase().encode(data);
  }

  /**
   * Hmac SHA 256.
   *
   * @param key     the key
   * @param message the message
   * @return the byte[]
   */
  public static byte[] hmacSHA256(String key, long message) {
    return hmacSHA256(key, PrimitiveUtils.toByteArray(message));
  }

  /**
   * Returns a 32 byte (256 bit) base.
   *
   * @param key     the key
   * @param message the message
   * @return the byte[]
   */
  public static byte[] hmacSHA256(String key, String message) {
    return hmacSHA256(key.getBytes(StandardCharsets.UTF_8), message.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Hmac SHA 256.
   *
   * @param key     the key
   * @param message the message
   * @return the byte[]
   */
  public static byte[] hmacSHA256(byte[] key, String message) {
    return hmacSHA256(key, message.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Hmac SHA 256.
   *
   * @param key     the key
   * @param message the message
   * @return the byte[]
   */
  public static byte[] hmacSHA256(String key, byte[] message) {
    return hmacSHA256(key.getBytes(StandardCharsets.UTF_8), message);
  }

  /**
   * Computes the keyed-hash message authentication code (HMAC) for the given key
   * and message. Returns null if the cryptography fails.
   *
   * @param key     the key
   * @param message the message
   * @return the byte[]
   */
  public static byte[] hmacSHA256(byte[] key, byte[] message) {
    // get an hmac_sha1 key from the raw key bytes
    SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA256);

    byte[] data = null;

    // get an hmac_sha1 Mac instance and initialize with the signing key
    try {
      Mac hmac256 = Mac.getInstance(HMAC_SHA256);
      hmac256.init(signingKey);
      data = hmac256.doFinal(message);
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
      e.printStackTrace();
    }

    // base64-encode the hmac. Since we are using SHA256 this will return
    // 256 bits as 32 bytes which when coded in base16 (hex) gives a
    // 64 char string
    return data;
  }
}