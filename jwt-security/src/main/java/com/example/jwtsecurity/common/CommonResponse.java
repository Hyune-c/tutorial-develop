package com.example.jwtsecurity.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse {

  private final String message;
  private final int status;
  private final String code;
}
