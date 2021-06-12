package com.example.jwtsecurity.config.security;

import com.example.jwtsecurity.common.security.JwtAuthToken;
import com.example.jwtsecurity.common.security.JwtAuthTokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class JWTFilter extends GenericFilterBean {

  private static final String AUTHORIZATION_HEADER = "x-auth-token";

  private final JwtAuthTokenProvider jwtAuthTokenProvider;

  JWTFilter(JwtAuthTokenProvider jwtAuthTokenProvider) {
    this.jwtAuthTokenProvider = jwtAuthTokenProvider;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    String authToken = ((HttpServletRequest) servletRequest).getHeader(AUTHORIZATION_HEADER);

    if (StringUtils.hasText(authToken)) {
      setAuthenticationToSecurityContext(authToken);
    }

    filterChain.doFilter(servletRequest, servletResponse);
  }

  /**
   * 인증에 성공하면 Spring 이 관리하는 SecurityContext 에 인증 객체를 설정합니다.
   *
   * @param authToken
   */
  private void setAuthenticationToSecurityContext(String authToken) {
    JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(authToken);

    if (jwtAuthToken.validate()) {
      Authentication authentication = jwtAuthTokenProvider.getAuthentication(jwtAuthToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  }
}
