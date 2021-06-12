package com.example.jwtsecurity.web;

import com.example.jwtsecurity.core.CommonResponse;
import com.example.jwtsecurity.core.service.dto.MemberDTO;
import com.example.jwtsecurity.exception.LoginFailedException;
import com.example.jwtsecurity.provider.security.JwtAuthToken;
import com.example.jwtsecurity.provider.service.LoginService;
import com.example.jwtsecurity.web.dto.LoginRequestDTO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @PostMapping
  public CommonResponse login(@RequestBody LoginRequestDTO loginRequestDTO) {
    Optional<MemberDTO> optionalMemberDTO = loginService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());

    if (optionalMemberDTO.isPresent()) {
      JwtAuthToken jwtAuthToken = (JwtAuthToken) loginService.createAuthToken(optionalMemberDTO.get());

      return CommonResponse.builder()
          .code("LOGIN_SUCCESS")
          .status(200)
          .message(jwtAuthToken.getToken())
          .build();
    } else {
      throw new LoginFailedException();
    }
  }
}
