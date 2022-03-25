/**
 * Copyright (C) 2016, Antony Holmes
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. Neither the name of copyright holder nor the names of its contributors 
 *     may be used to endorse or promote products derived from this software 
 *     without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jebtk.core;

/**
 * Describes a product version.
 * 
 * @author Antony Holmes
 *
 */
public class AppVersion {

  public static final AppVersion DEFAULT_VERSION = new AppVersion(1);

  /**
   * The member version.
   */
  private String mVersion;

  /**
   * The member major.
   */
  private int mMajor = 0;

  /**
   * The member minor.
   */
  private int mMinor = 0;

  /**
   * The member revion.
   */
  private int mRevion = 0;

  /**
   * Instantiates a new application version.
   *
   * @param major the major
   */
  public AppVersion(int major) {
    this(major, 0, 0);
  }

  /**
   * Instantiates a new application version.
   *
   * @param major the major
   * @param minor the minor
   */
  public AppVersion(int major, int minor) {
    this(major, minor, 0);
  }

  /**
   * Instantiates a new application version.
   *
   * @param major    the major
   * @param minor    the minor
   * @param revision the revision
   */
  public AppVersion(int major, int minor, int revision) {
    mMajor = major;
    mMinor = minor;
    mRevion = revision;

    mVersion = major + "." + minor + "." + revision;
  }

  /**
   * Gets the major.
   *
   * @return the major
   */
  public int getMajor() {
    return mMajor;
  }

  /**
   * Gets the minor.
   *
   * @return the minor
   */
  public int getMinor() {
    return mMinor;
  }

  /**
   * Gets the revision.
   *
   * @return the revision
   */
  public int getRevision() {
    return mRevion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return getVersion();
  }

  /**
   * Gets the version.
   *
   * @return the version
   */
  public String getVersion() {
    return mVersion;
  }
}
