package com.example.jwtsecurity.core.security;

public interface AuthToken<T> {

  boolean validate();

  T getData();
}
