/*
 * Copyright (c) 2020-present Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.goodies.mavenpath;

import javax.annotation.Nullable;

//import com.google.common.hash.HashCode;
//import com.google.common.hash.HashFunction;
//import com.google.common.hash.Hashing;
//import com.google.common.hash.HashingInputStream;
//import com.google.common.io.ByteStreams;

import static java.util.Objects.requireNonNull;

// see: https://github.com/sonatype/nexus2-internal/blob/master/private/plugins/clm/nexus-staging-plugin/src/main/java/com/sonatype/nexus/staging/internal/rules/ChecksumStagingRuleEvaluator.java

/**
 * Supported checksum types.
 *
 * @since ???
 */
public enum ChecksumType
{
  SHA_1("sha1", "SHA-1", "text/plain"),
  SHA_256("sha256", "SHA-256", "text/plain"),
  SHA_512("sha512", "SHA-512", "text/plain"),
  MD5("md5", "MD5", "text/plain");

  /**
   * File-extension suffix.
   */
  public final String extension;

  /**
   * Hash algorithm name.
   */
  public final String algorithm;

  /**
   * Content type.
   */
  public final String contentType;

  ChecksumType(final String extension, final String algorithm, final String contentType) {
    this.extension = requireNonNull(extension);
    this.algorithm = requireNonNull(algorithm);
    this.contentType = requireNonNull(contentType);
  }

  public boolean pathMatches(final String path) {
    requireNonNull(path);
    return path.endsWith("." + extension);
  }

  public String pathOf(final String path) {
    requireNonNull(path);
    return path + "." + extension;
  }

  //
  // Helpers
  //

  @Nullable
  public static ChecksumType forAlgorithm(final String algorithm) {
    for (ChecksumType type : values()) {
      if (type.algorithm.equals(algorithm)) {
        return type;
      }
    }
    return null;
  }

  @Nullable
  public static ChecksumType ofPath(final String path) {
    for (ChecksumType value : values()) {
      if (value.pathMatches(path)) {
        return value;
      }
    }
    return null;
  }
}
