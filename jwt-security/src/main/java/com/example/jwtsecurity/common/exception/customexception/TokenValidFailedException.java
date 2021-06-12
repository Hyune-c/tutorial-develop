package com.example.jwtsecurity.common.exception.customexception;

import com.example.jwtsecurity.common.exception.ErrorCode;

public class TokenValidFailedException extends RuntimeException {

  public TokenValidFailedException() {
    super(ErrorCode.TOKEN_GENERATION_FAILED.getMessage());
  }

  private TokenValidFailedException(String msg) {
    super(msg);
  }
}
