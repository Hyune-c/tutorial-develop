package com.example.jwtsecurity.domain.login.service;

import com.example.jwtsecurity.common.security.AuthToken;
import com.example.jwtsecurity.common.security.JwtAuthTokenProvider;
import com.example.jwtsecurity.common.security.Role;
import com.example.jwtsecurity.domain.member.dto.MemberDTO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {

  private final static long LOGIN_RETENTION_MINUTES = 30;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final JwtAuthTokenProvider jwtAuthTokenProvider;

  public Optional<MemberDTO> login(String email, String password) {
    log.info("### LoginService.loing email={}, password={}", email, password);

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

    // 로그인 성공시 인증 객체 생성 및 스프링 시큐리티 설정
    SecurityContextHolder.getContext().setAuthentication(authentication);

    Role role = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .findFirst()
        .map(Role::of)
        .orElse(Role.UNKNOWN);

    MemberDTO memberDTO = MemberDTO.builder()
        .email(email)
        .role(role)
        .build();

    return Optional.ofNullable(memberDTO);
  }

  @Override
  public AuthToken createAuthToken(MemberDTO memberDTO) {
    Date expiredDate = Date.from(
        LocalDateTime.now().plusMinutes(LOGIN_RETENTION_MINUTES).atZone(ZoneId.systemDefault()).toInstant());
    return jwtAuthTokenProvider.createAuthToken(memberDTO.getEmail(), memberDTO.getRole().getCode(), expiredDate);
  }
}
