package org.braid.society.secret.retweetersview.lib.util;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for encode/decode base64.
 *
 * @author Ranfa
 * @since 1.0.0
 */
@Slf4j
public class Base64ControllUtil {

  private Base64ControllUtil() { /* do nothing */ }

  /**
   * encode the specified string.
   *
   * @param val string to encode.
   * @return encoded string.
   */
  public static String encodeBase64(String val) {
    return Base64.getEncoder().encodeToString(val.getBytes());
  }

  /**
   * decode the specified string.
   *
   * @param val string to decode.
   * @return decoded string.
   */
  public static String decodeBase64(String val) {
    return new String(Base64.getDecoder().decode(val));
  }

  /**
   * encode the specified string repeatly.
   *
   * @param val         string to encode.
   * @param repeatTimes repeat times.
   * @return encoded string.
   * @throws IllegalArgumentException if repeatTimes is less than 1.
   */
  public static String encodeBase64(String val, int repeatTimes) {
    if (repeatTimes < 1) {
      throw new IllegalArgumentException("repeatTimes must be greater than 1.");
    }
    if (repeatTimes == 1) {
      return encodeBase64(val);
    }
    return encodeBase64(encodeBase64(val), repeatTimes - 1);
  }

  /**
   * decode the specified string repeatly.
   *
   * @param val         string to decode.
   * @param repeatTimes repeat times.
   * @return decoded string.
   * @throws IllegalArgumentException if repeatTimes is less than 1.
   */
  public static String decodeBase64(String val, int repeatTimes) {
    if (repeatTimes < 1) {
      throw new IllegalArgumentException("repeatTimes must be greater than 1.");
    }
    if (repeatTimes == 1) {
      return decodeBase64(val);
    }
    return decodeBase64(decodeBase64(val), repeatTimes - 1);
  }
}
