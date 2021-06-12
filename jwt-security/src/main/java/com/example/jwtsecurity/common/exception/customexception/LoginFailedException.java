package com.example.jwtsecurity.common.exception.customexception;

import com.example.jwtsecurity.common.exception.ErrorCode;

public class LoginFailedException extends RuntimeException {

  public LoginFailedException() {
    super(ErrorCode.Login_FAILED.getMessage());
  }

  private LoginFailedException(String msg) {
    super(msg);
  }
}
