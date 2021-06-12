package com.example.jwtsecurity.common.exception.customexception;

import static com.example.jwtsecurity.common.exception.ErrorCode.AUTHENTICATION_FAILED;

public class CustomAuthenticationException extends RuntimeException {

  public CustomAuthenticationException() {
    super(AUTHENTICATION_FAILED.getMessage());
  }

  public CustomAuthenticationException(Exception ex) {
    super(ex);
  }
}
