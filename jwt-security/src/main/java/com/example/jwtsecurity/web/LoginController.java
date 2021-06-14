package com.example.jwtsecurity.web;

import com.example.jwtsecurity.common.CommonResponse;
import com.example.jwtsecurity.common.exception.customexception.LoginFailedException;
import com.example.jwtsecurity.common.security.JwtAuthToken;
import com.example.jwtsecurity.domain.member.dto.MemberDTO;
import com.example.jwtsecurity.domain.login.service.LoginService;
import com.example.jwtsecurity.web.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

  private final LoginService loginService;

  @PostMapping("/api/v1/login")
  public CommonResponse login(@RequestBody LoginRequestDTO requestDto) {
    MemberDTO memberDTO = loginService.login(requestDto.getEmail(), requestDto.getPassword())
        .orElseThrow(LoginFailedException::new);
    JwtAuthToken jwtAuthToken = (JwtAuthToken) loginService.createAuthToken(memberDTO);

    return CommonResponse.builder()
        .code("LOGIN_SUCCESS")
        .status(200)
        .message(jwtAuthToken.getToken())
        .build();
  }
}
