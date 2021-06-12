package com.example.jwtsecurity.common.security;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Role {
  ADMIN("ROLE_ADMIN", "관리자 권한"),
  USER("ROLE_USER", "사용자 권한"),
  UNKNOWN("UNKNOWN", "알 수 없는 권한");

  private final String code;
  private final String description;

  Role(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static Role of(String code) {
    return Arrays.stream(Role.values())
        .filter(r -> r.getCode().equals(code))
        .findAny()
        .orElse(UNKNOWN);
  }
}
