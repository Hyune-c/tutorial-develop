package com.example.jwtsecurity.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jwtsecurity.common.exception.ErrorCode;
import com.example.jwtsecurity.common.security.Role;
import com.example.jwtsecurity.common.security.JwtAuthTokenProvider;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[web] CoffeeController 로 JWT 활용")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CoffeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JwtAuthTokenProvider jwtAuthTokenProvider;

  @DisplayName("[성공]")
  @Test
  void givenValidToken_whenHttpRequest_thenSuccess() throws Exception {
    // given
    Role role = Role.USER;

    Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("x-auth-token", makeJwtAuthToken(role, expiredDate));

    // when
    this.mockMvc.perform(get("/api/v1/coffees").headers(httpHeaders))
        .andDo(print())
        .andExpect(status().isOk());

    // then
  }

  @DisplayName("[실패] 만료된 JWT")
  @Test
  void givenExpiredToken_whenHttpRequest_thenAuthenticationFailed() throws Exception {
    // given
    Date expiredDate = Date.from(LocalDateTime.now().minusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("x-auth-token", makeJwtAuthToken(Role.USER, expiredDate));

    // when
    this.mockMvc.perform(get("/api/v1/coffees").headers(httpHeaders))
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(content().string(containsString(ErrorCode.AUTHENTICATION_FAILED.getMessage())));

    // then
  }

  @DisplayName("[실패] 적절하지 않은 Role")
  @Test
  void givenNotRole_whenHttpRequest_thenAccessDenied() throws Exception {
    // given
    Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("x-auth-token", makeJwtAuthToken(Role.UNKNOWN, expiredDate));

    // when
    this.mockMvc.perform(get("/api/v1/coffees").headers(httpHeaders))
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(content().string(containsString(ErrorCode.ACCESS_DENIED.getMessage())));

    // then
  }

  private String makeJwtAuthToken(Role role, Date expiredDate) {
    return jwtAuthTokenProvider.createAuthToken("hyune", role.getCode(), expiredDate).getToken();
  }
}
