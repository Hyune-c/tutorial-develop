package com.example.jwtsecurity.exception;

public class LoginFailedException extends RuntimeException {

  public LoginFailedException() {
    super(com.example.jwtsecurity.exception.ErrorCode.Login_FAILED.getMessage());
  }

  private LoginFailedException(String msg) {
    super(msg);
  }
}
