package com.example.jwtsecurity.security;

import com.example.jwtsecurity.provider.security.JwtAuthTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private JwtAuthTokenProvider jwtAuthTokenProvider;

  public JWTConfigurer(JwtAuthTokenProvider jwtAuthTokenProvider) {
    this.jwtAuthTokenProvider = jwtAuthTokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) {
    com.example.jwtsecurity.security.JWTFilter customFilter = new com.example.jwtsecurity.security.JWTFilter(jwtAuthTokenProvider);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
