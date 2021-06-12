package com.example.jwtsecurity.web;

import com.example.jwtsecurity.common.security.Role;
import com.example.jwtsecurity.domain.member.entity.Member;
import com.example.jwtsecurity.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberRepository memberRepository;

  private final PasswordEncoder passwordEncoder;

  @PostMapping("/api/v1/members")
  public Long create() {
    String password = "password";
    Member member = new Member(null, passwordEncoder.encode(password), "hyune@gmail.com", Role.USER.getCode());
    return memberRepository.save(member).getId();
  }
}
