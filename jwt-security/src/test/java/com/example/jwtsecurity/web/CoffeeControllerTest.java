package com.example.jwtsecurity.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jwtsecurity.core.security.Role;
import com.example.jwtsecurity.exception.ErrorCode;
import com.example.jwtsecurity.provider.security.JwtAuthTokenProvider;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CoffeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JwtAuthTokenProvider jwtAuthTokenProvider;


  @Test
  void givenValidToken_whenHttpRequest_thenSuccess() throws Exception {

    //given
    Role role = Role.USER;

    Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("x-auth-token", makeJwtAuthToken(role, expiredDate));

    //when, then
    this.mockMvc.perform(get("/api/v1/coffees").headers(httpHeaders))
        .andDo(print())
        .andExpect(status().isOk());

  }

  @Test
  void givenExpiredToken_whenHttpRequest_thenAuthenticationFailed() throws Exception {

    //given
    Role role = Role.USER;
    String expectedMessage = ErrorCode.AUTHENTICATION_FAILED.getMessage();

    Date expiredDate = Date.from(LocalDateTime.now().minusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("x-auth-token", makeJwtAuthToken(role, expiredDate));

    //when, then
    this.mockMvc.perform(get("/api/v1/coffees").headers(httpHeaders))
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(content().string(containsString(expectedMessage)));
  }


  @Test
  void givenNotRole_whenHttpRequest_thenAccessDenied() throws Exception {

    //given
    Role role = Role.UNKNOWN;
    String expectedMessage = ErrorCode.ACCESS_DENIED.getMessage();

    Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("x-auth-token", makeJwtAuthToken(role, expiredDate));

    //when, then
    this.mockMvc.perform(get("/api/v1/coffees").headers(httpHeaders))
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(content().string(containsString(expectedMessage)));

  }


  private String makeJwtAuthToken(Role role, Date expiredDate) {
    return jwtAuthTokenProvider.createAuthToken("eddy", role.getCode(), expiredDate).getToken();
  }

}
