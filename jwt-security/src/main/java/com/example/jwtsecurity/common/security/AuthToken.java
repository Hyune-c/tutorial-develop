package com.example.jwtsecurity.common.security;

public interface AuthToken<T> {

  boolean validate();

  T getClaims();
}
