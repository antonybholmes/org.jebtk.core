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

import org.jebtk.core.PrimitiveUtils;
import org.jebtk.core.TimeUtils;

/**
 * The Class TOTP.
 */
public class TOTP {
  /**
   * Default step size of 60 secs (60000 milliseconds).
   */
  public static final long DEFAULT_TOTP_STEP_MS = 60000;

  /** The Constant DIGITS_POWER. */
  private static final int[] DIGITS_POWER = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

  /** The Constant LAST_BYTE. */
  private static final int LAST_BYTE = 31;

  /** How many digits in length should the TOTP be */
  private static final int DEFAULT_TOTP_DIGITS = 6;

  /**
   * Topt256 auth.
   *
   * @param phrase the phrase
   * @param totp   the totp
   * @return true, if successful
   */
  public static boolean totp256Auth(String phrase, int totp) {
    return totp256Auth(phrase, totp, DEFAULT_TOTP_STEP_MS);
  }

  /**
   * Topt256 auth.
   *
   * @param phrase the phrase
   * @param totp   the totp
   * @param step   the step
   * @return true, if successful
   */
  public static boolean totp256Auth(String phrase, int totp, long step) {
    return totp256Auth(phrase, totp, 0, step);
  }

  /**
   * Check that two totp phrases generated on different machines are the same.
   *
   * @param phrase the phrase
   * @param totp   the totp
   * @param epoch  the epoch
   * @param step   the step
   * @return true, if successful
   */
  public static boolean totp256Auth(String phrase, int totp, long epoch, long step) {
    return totp256Auth(phrase, totp, TimeUtils.getCurrentTimeMs(), epoch, step);
  }

  /**
   * Topt256 auth.
   *
   * @param phrase the phrase
   * @param totp   the totp
   * @param time   the time
   * @param epoch  the epoch
   * @param step   the step
   * @return true, if successful
   */
  public static boolean totp256Auth(String phrase, int totp, long time, long epoch, long step) {
    String t = totp256(phrase, time, epoch, step);

    if (t.equals(totp)) {
      return true;
    }

    // Check in case we rolled to the next step in the time taken
    // to start the check and the time we finish
    t = totp256(phrase, time - step, epoch, step);

    if (t.equals(totp)) {
      return true;
    }

    // If the clocks are seriously off, try looking one step into the
    // future
    t = totp256(phrase, time + step, epoch, step);

    if (t.equals(totp)) {
      return true;
    }

    return false;
  }

  /**
   * Generates a one time pass phrase every DEFAULT_TOPT_STEP milliseconds
   * assuming the default epoch.
   *
   * @param phrase the phrase
   * @return the string
   */
  public static String totp256(String phrase) {
    return totp256(phrase, DEFAULT_TOTP_STEP_MS);
  }

  /**
   * Topt256.
   *
   * @param phrase the phrase
   * @param step   the step
   * @return the string
   */
  public static String totp256(String phrase, long step) {
    return totp256(phrase, 0, step);
  }

  /**
   * Topt256.
   *
   * @param phrase the phrase
   * @param epoch  the epoch
   * @param step   the step
   * @return the string
   */
  public static String totp256(String phrase, long epoch, long step) {
    return totp256(phrase, TimeUtils.getCurrentTimeMs(), epoch, step);
  }

  /**
   * Topt256. Returns the first 32 chars of a 64 char encoding.
   *
   * @param key   the phrase
   * @param time  the time
   * @param epoch the epoch
   * @param step  the step
   * @return the string
   */
  public static String totp256(String key, long time, long epoch, long step) {
    long salt = getCounter(time, epoch, step);

    return totpC256(key, salt);
  }

  /**
   * Totp C 256.
   *
   * @param key     the key
   * @param counter the counter
   * @return the string
   */
  public static String totpC256(String key, long counter) {
    return Hmac.calculateSHA256HMAC(key, counter);
  }

  /**
   * Topt256 auth.
   *
   * @param phrase the phrase
   * @param totp   the totp
   * @return true, if successful
   */
  public static boolean totpAuth(String phrase, int totp) {
    return totpAuth(phrase, totp, DEFAULT_TOTP_STEP_MS);
  }

  /**
   * Topt256 auth.
   *
   * @param phrase the phrase
   * @param totp   the totp
   * @param step   the step
   * @return true, if successful
   */
  public static boolean totpAuth(String phrase, int totp, long step) {
    return totpAuth(phrase, totp, 0, step);
  }

  /**
   * Check that two totp phrases generated on different machines are the same.
   *
   * @param phrase the phrase
   * @param totp   the totp
   * @param epoch  the epoch
   * @param step   the step
   * @return true, if successful
   */
  public static boolean totpAuth(String phrase, int totp, long epoch, long step) {
    return totpAuth(phrase, totp, TimeUtils.getCurrentTimeMs(), epoch, step);
  }

  /**
   * Topt256 auth.
   *
   * @param phrase the phrase
   * @param totp   the totp
   * @param time   the time
   * @param epoch  the epoch
   * @param step   the step
   * @return true, if successful
   */
  public static boolean totpAuth(String phrase, int totp, long time, long epoch, long step) {
    int t = generateTOTP(phrase, time, epoch, step);

    // System.err.println("step " + step + " " + totp);

    if (t == totp) {
      return true;
    }

    // Check in case we rolled to the next step in the time taken
    // to start the check and the time we finish
    t = generateTOTP(phrase, time - step, epoch, step);

    if (t == totp) {
      return true;
    }

    // If the clocks are seriously off, try looking one step into the
    // future
    t = generateTOTP(phrase, time + step, epoch, step);

    return t == totp;
  }

  /**
   * Generate TOTP.
   *
   * @param phrase the phrase
   * @return the int
   */
  public static int generateTOTP(String phrase) {
    return generateTOTP(phrase, DEFAULT_TOTP_STEP_MS);
  }

  /**
   * Topt256.
   *
   * @param phrase the phrase
   * @param step   the step
   * @return the string
   */
  public static int generateTOTP(String phrase, long step) {
    return generateTOTP(phrase, 0, step);
  }

  /**
   * Topt256.
   *
   * @param phrase the phrase
   * @param epoch  the epoch
   * @param step   the step
   * @return the string
   */
  public static int generateTOTP(String phrase, long epoch, long step) {
    return generateTOTP(phrase, TimeUtils.getCurrentTimeMs(), epoch, step);
  }

  /**
   * Topt256. Returns the first 32 chars of a 64 char encoding.
   *
   * @param key   the phrase
   * @param time  the time
   * @param epoch the epoch
   * @param step  the step
   * @return the string
   */
  public static int generateTOTP(String key, long time, long epoch, long step) {
    long counter = getCounter(time, epoch, step);

    return generateCTOTP(key, counter);
  }

  /**
   * Generate CTOTP.
   *
   * @param key     the key
   * @param counter the counter
   * @return the int
   */
  public static int generateCTOTP(String key, long counter) {
    return generateCTOTP(key, counter, DEFAULT_TOTP_DIGITS);
  }

  /**
   * Generates a Time base One Time Password or -1 if it fails.
   *
   * @param key     the key
   * @param counter the counter
   * @param digits  the digits
   * @return the int
   */
  public static int generateCTOTP(String key, long counter, int digits) {
    byte[] hash = Hmac.hmacSHA256(key, counter);

    // Create integer from bytes around the offset using 0x7f to mask
    // the highest order bit in case it might be misinterpreted
    return hmac256HashToTOTPInt(hash) % DIGITS_POWER[digits];
  }

  /**
   * Generates a standard 8 digit TOTP code.
   *
   * @param key     the key
   * @param counter the counter
   * @return the int
   */
  public static int generateCTOTP8(String key, long counter) {
    return generateCTOTP8(key, PrimitiveUtils.toByteArray(counter));
  }

  /**
   * Generate CTOTP 8.
   *
   * @param key     the key
   * @param message the message
   * @return the int
   */
  public static int generateCTOTP8(String key, byte[] message) {
    return generateCTOTP8(key.getBytes(StandardCharsets.UTF_8), message);
  }

  /**
   * Generates a standard 8 digit TOTP code. Returns -1 if there is a failure.
   *
   * @param key     the key
   * @param message the message
   * @return the int
   */
  public static int generateCTOTP8(byte[] key, byte[] message) {
    byte[] hash = Hmac.hmacSHA256(key, message);

    // Create integer from bytes around the offset using 0x7f to mask
    // the highest order bit in case it might be misinterpreted
    return hmac256HashToTOTPInt(hash) % 100000000;
  }

  /**
   * Generates a standard 6 digit TOTP code.
   *
   * @param key     the key
   * @param counter the counter
   * @return the int
   */
  public static int generateCTOTP6(String key, long counter) {
    return generateCTOTP6(key, PrimitiveUtils.toByteArray(counter));
  }

  /**
   * Generate CTOTP 6.
   *
   * @param key     the key
   * @param message the message
   * @return the int
   */
  public static int generateCTOTP6(String key, byte[] message) {
    return generateCTOTP6(key.getBytes(StandardCharsets.UTF_8), message);
  }

  /**
   * Generates a standard 6 digit TOTP code. Returns -1 if there is a failure.
   *
   * @param key     the key
   * @param message the message
   * @return the int
   */
  public static int generateCTOTP6(byte[] key, byte[] message) {
    byte[] hash = Hmac.hmacSHA256(key, message);

    // Create integer from bytes around the offset using 0x7f to mask
    // the highest order bit in case it might be misinterpreted
    return hmac256HashToTOTPInt(hash) % 1000000;
  }

  /**
   * Converts a hmac hash (must be 32 bytes, 256 bits) to an integer which can be
   * used to generate totp code. Returns -1 if hash is null.
   *
   * @param hash the hash
   * @return the int
   */
  private static int hmac256HashToTOTPInt(final byte[] hash) {
    if (hash == null) {
      return -1;
    }

    // Use lower 4 bits of last byte as offset
    int offset = hash[LAST_BYTE] & 0xf;

    // Create integer from bytes around the offset using 0x7f to mask
    // the highest order bit in case it might be misinterpreted
    // Use 0xff mask to convert byte to unsigned int
    return ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8)
        | (hash[offset + 3] & 0xff);
  }

  /**
   * Gets the totp salt.
   *
   * @param time the time
   * @param step the step
   * @return the totp salt
   */
  public static long getCounter(long time, long step) {
    return getCounter(time, 0, step);
  }

  /**
   * Gets the totp counter.
   *
   * @param time  The time in ms.
   * @param epoch The epoch in ms.
   * @param step  The step in ms.
   * 
   * @return The totp counter.
   */
  public static long getCounter(long time, long epoch, long step) {
    return (time - epoch) / step;
  }
}
