package com.example.jwtsecurity.provider.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.jwtsecurity.common.security.Role;
import com.example.jwtsecurity.domain.login.service.LoginService;
import com.example.jwtsecurity.domain.member.entity.Member;
import com.example.jwtsecurity.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@DisplayName("[service] 로그인")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoginServiceTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private LoginService loginService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @DisplayName("[성공]")
  @Test
  void success() {
    // given
    String email = "hyune@gmail.com";
    Role role = Role.USER;
    String password = "password";
    memberRepository.save(new Member(1L, passwordEncoder.encode(password), email, role.getCode()));

    // when
    loginService.login(email, "password");

    // then
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    assertThat(email).isEqualTo(((User) authentication.getPrincipal()).getUsername());
    assertThat(role.getCode()).isEqualTo(authentication.getAuthorities().stream().findFirst().get().toString());
  }

  @DisplayName("[실패] 잘못된 비밀번호")
  @Test
  void failed_wrongPassword() {
    // given
    String email = "hyune@gmail.com";
    Role role = Role.USER;
    String password = "password";
    memberRepository.save(new Member(1L, passwordEncoder.encode(password), email, role.getCode()));
    // when
    assertThrows(BadCredentialsException.class, () -> loginService.login(email, "invalid_password"));

    // then
  }

  @DisplayName("[실패] 존재하지 않는 email")
  @Test
  void failed_notExistEmail() {
    // given
    String email = "hyune@gmail.com";
    Role role = Role.USER;

    // when
    assertThrows(BadCredentialsException.class, () -> loginService.login(email, "invalid_password"));

    // then
  }
}
