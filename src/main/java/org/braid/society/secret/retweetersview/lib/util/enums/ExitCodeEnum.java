package org.braid.society.secret.retweetersview.lib.util.enums;

public enum ExitCodeEnum {

  NORMAL(0),

  INTERNAL_PROCESS_FAILED(-1),

  REJECT_AUTH(-12),

  UNKNOWN(Integer.MIN_VALUE);

  final int val;

  ExitCodeEnum(int val) {
    this.val = val;
  }

  public int getCode() {
    return this.val;
  }
}
