package com.example.jwtsecurity.exception;

public class TokenValidFailedException extends RuntimeException {

  public TokenValidFailedException() {
    super(com.example.jwtsecurity.exception.ErrorCode.TOKEN_GENERATION_FAILED.getMessage());
  }

  private TokenValidFailedException(String msg) {
    super(msg);
  }
}
