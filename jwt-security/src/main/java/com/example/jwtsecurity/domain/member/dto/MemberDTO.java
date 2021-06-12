package com.example.jwtsecurity.domain.member.dto;

import com.example.jwtsecurity.common.security.Role;
import com.example.jwtsecurity.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {

  private final String id;
  private final String email;
  private final Role role;

  public static MemberDTO of(Member member) {
    return MemberDTO.builder()
        .id(String.valueOf(member.getId()))
        .email(member.getEmail())
        .role(Role.of(member.getRole()))
        .build();
  }
}
