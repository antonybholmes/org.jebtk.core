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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * The class Cryptography.
 */
public class Cryptography {

  /**
   * The constant ALPHABET.
   */
  private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  /**
   * The constant HEX_ARRAY.
   */
  private static final char[] HEX_ARRAY = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
      'f' };

  /**
   * Instantiates a new cryptography.
   */
  private Cryptography() {
    // Do nothing
  }

  /**
   * Generate uuid.
   *
   * @return the string
   */
  public static String generateUUID() {
    return UUID.randomUUID().toString();
  }

  /**
   * Generates a 32 char UUID without dashes.
   *
   * @return the string
   */
  public static String generateRandomId() {
    return generateUUID().replaceAll("-", "");
  }

  /**
   * Generate a random 64 character string.
   *
   * @return the string
   */
  public static String generateRandAlphaNumId64() {
    return generateRandAlphaNumId(64);
  }

  /**
   * Generate a random 48 character string.
   * 
   * @return
   */
  public static String generateRandAlphaNumId48() {
    return generateRandAlphaNumId(48);
  }

  /**
   * Generate a random alpha numeric (case sensitive) string of n characters.
   *
   * @param length the length
   * @return the string
   */
  public static String generateRandAlphaNumId(int length) {
    SecureRandom random = new SecureRandom();

    StringBuilder buffer = new StringBuilder(length);

    int s = ALPHABET.length();

    for (int i = 0; i < length; i++) {
      buffer.append(ALPHABET.charAt(random.nextInt(s)));
    }

    return buffer.toString();
  }

  /**
   * Return a SHA-512 bit hash of a salted phrase as 128 char string.
   *
   * @param password the password
   * @param salt     the salt
   * @return the SH a512 hash
   */
  public static String getSHA512Hash(String password, String salt) {
    return getSHA512Hash(password + salt);
  }

  /**
   * Gets the SH a512 hash.
   *
   * @param password the password
   * @param salt     the salt
   * @return the SH a512 hash
   */
  public static String getSHA512Hash(String password, long salt) {
    return getSHA512Hash(password + salt);
  }

  /**
   * Return a SHA-512 bit hash of a phrase.
   *
   * @param phrase the phrase
   * @return the SH a512 hash
   */
  public static String getSHA512Hash(String phrase) {
    byte[] buf = new byte[64];

    hashSHA512(phrase, buf);

    return hexToString2(buf);
  }

  /**
   * Gets the SH a256 hash.
   *
   * @param password the password
   * @param salt     the salt
   * @return the SH a256 hash
   */
  public static String getSHA256Hash(String password, String salt) {
    return getSHA256Hash(password + salt);
  }

  /**
   * Gets the SH a256 hash.
   *
   * @param password the password
   * @param salt     the salt
   * @return the SH a256 hash
   */
  public static String getSHA256Hash(String password, long salt) {
    return getSHA256Hash(password + salt);
  }

  /**
   * Returns a SHA-512 bit hash of a phrase as hex string after truncation to 256
   * bits.
   *
   * @param phrase the phrase
   * @return the SH a256 hash
   */
  public static String getSHA256Hash(String phrase) {
    byte[] buf = new byte[32];

    hashSHA512(phrase, buf);

    return hexToString2(buf);
  }

  /**
   * Hash sh a512.
   *
   * @param phrase the phrase
   * @param buf    the buf
   */
  public static void hashSHA512(String phrase, byte buf[]) {
    hashSHA512(phrase, buf, buf.length);
  }

  /**
   * Hash sh a512.
   *
   * @param phrase the phrase
   * @param buf    the buf
   * @param len    the len
   */
  public static void hashSHA512(String phrase, byte buf[], int len) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");

      md.update(phrase.getBytes());

      // If crop bytes if necessary
      System.arraycopy(md.digest(), 0, buf, 0, len);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generate a random 512 bit (64 byte) salt (which will be 128 characters).
   *
   * @return the salt512
   */
  public static String getSalt512() {
    return getSalt(64);
  }

  /**
   * Generate a random salt.
   *
   * @param length the length
   * @return the salt
   */
  public static String getSalt(int length) {
    SecureRandom random = new SecureRandom();

    byte bytes[] = new byte[length];

    random.nextBytes(bytes);

    return hexToString2(bytes);
  }

  /**
   * Convert byte data to a string using hex notation. Each byte is represented by
   * 2 characters representing a hex number between 0-256.
   *
   * @param buf the buf
   * @return the string
   */
  public static String hexToString(byte[] buf) {
    StringBuilder hexString = new StringBuilder();

    for (int i = 0; i < buf.length; ++i) {
      String hex = Integer.toHexString(0xff & buf[i]);

      if (hex.length() == 1) {
        hexString.append('0');
      }

      hexString.append(hex);
    }

    return hexString.toString();
  }

  /**
   * Hex to string2.
   *
   * @param buf the buf
   * @return the string
   */
  public static String hexToString2(byte[] buf) {
    int i1 = 0;
    int i2 = 1;

    char[] hexChars = new char[buf.length * 2];

    for (byte v : buf) {
      // Look at the upper 4 bits
      hexChars[i1] = HEX_ARRAY[(v & 0xf0) >> 4];
      // Look at the lower 4 bits
      hexChars[i2] = HEX_ARRAY[v & 0x0f];

      // Each byte generates two chars to represent the hex value
      // in a string. We just increment two counters to set the
      // chars for each byte.
      i1 += 2;
      i2 += 2;
    }

    return new String(hexChars);
  }
}
