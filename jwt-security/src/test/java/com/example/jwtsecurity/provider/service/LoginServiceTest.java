package com.example.jwtsecurity.provider.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.jwtsecurity.core.entity.Member;
import com.example.jwtsecurity.core.repository.MemberRepository;
import com.example.jwtsecurity.core.security.Role;
import com.example.jwtsecurity.core.service.dto.MemberDTO;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@SpringBootTest
class LoginServiceTest {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private LoginService loginService;

  @Test
  void loginSuccessTest() {
    //given
    memberRepository.save(new Member(1L, "eddy", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC",
        "sieunkr@gmail.com", "ROLE_USER"));

    String expectedEmail = "sieunkr@gmail.com";
    String expectedRole = Role.USER.getCode();

    //when
    Optional<MemberDTO> m = loginService.login("sieunkr@gmail.com", "password");

    //then
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    assertEquals(expectedEmail, ((User) authentication.getPrincipal()).getUsername());
    assertEquals(expectedRole, authentication.getAuthorities().stream().findFirst().get().toString());
  }

  @Test
  void loginFailedTest() {
    //given, when, then
    memberRepository.save(new Member(1L, "eddy", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC",
        "sieunkr@gmail.com", "ROLE_USER"));

    assertThrows(BadCredentialsException.class, () -> {
      loginService.login("sieunkr@gmail.com", "invalid_password");
    });
  }
}
