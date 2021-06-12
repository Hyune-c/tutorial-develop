package com.example.jwtsecurity.config;

import com.example.jwtsecurity.core.security.Role;
import com.example.jwtsecurity.exception.JwtAccessDeniedHandler;
import com.example.jwtsecurity.exception.JwtAuthenticationEntryPoint;
import com.example.jwtsecurity.provider.security.JwtAuthTokenProvider;
import com.example.jwtsecurity.security.JWTConfigurer;
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

        .exceptionHandling()
        .authenticationEntryPoint(authenticationErrorHandler)
        .accessDeniedHandler(jwtAccessDeniedHandler)

        .and()
        .headers()
        .frameOptions()
        .sameOrigin()

        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeRequests()
        .antMatchers("/api/v1/login/**").permitAll()

        .antMatchers("/api/v1/coffees/**").hasAnyAuthority(Role.USER.getCode())
        .anyRequest().authenticated()

        .and()
        .apply(securityConfigurerAdapter());
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring()
        .antMatchers(HttpMethod.OPTIONS, "/**")

        // allow anonymous resource requests
        .antMatchers(
            "/",
            "/h2-console/**"
        );
  }

  private JWTConfigurer securityConfigurerAdapter() {
    return new JWTConfigurer(jwtAuthTokenProvider);
  }
}
