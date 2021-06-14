package com.example.jwtsecurity.config;

import com.example.jwtsecurity.common.exception.JwtAccessDeniedHandler;
import com.example.jwtsecurity.common.exception.JwtAuthenticationEntryPoint;
import com.example.jwtsecurity.common.security.JwtAuthTokenProvider;
import com.example.jwtsecurity.common.security.Role;
import com.example.jwtsecurity.config.security.JWTConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthTokenProvider jwtAuthTokenProvider;
  private final JwtAuthenticationEntryPoint authenticationErrorHandler;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf().disable()

        // 인증 또는 인가에 실패한 경우 Exception 처리
        .exceptionHandling()
        .authenticationEntryPoint(authenticationErrorHandler)
        .accessDeniedHandler(jwtAccessDeniedHandler)

        .and()
        .headers()
        .frameOptions()
        .sameOrigin()

        // 세션 기능을 사용하지 않는다
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // 인증 여부와 관계 없이 모두 접근 가능한 API
        .and()
        .authorizeRequests()
        .antMatchers("/api/v1/login/**").permitAll()
        .antMatchers("/api/v1/members/**").permitAll()

        // User 권한자만 접근 가능한 API
        .antMatchers("/api/v1/coffees/**").hasAnyAuthority(Role.USER.getCode())
        .anyRequest().authenticated()

        .and()
        .apply(securityConfigurerAdapter());
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .antMatchers("/", "/h2-console/**");
  }

  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(jwtAuthTokenProvider);
  }
}
