package com.example.jwtsecurity.exception;

import static com.example.jwtsecurity.exception.ErrorCode.AUTHENTICATION_FAILED;

public class CustomAuthenticationException extends RuntimeException {

  public CustomAuthenticationException() {
    super(AUTHENTICATION_FAILED.getMessage());
  }

  public CustomAuthenticationException(Exception ex) {
    super(ex);
  }
}
