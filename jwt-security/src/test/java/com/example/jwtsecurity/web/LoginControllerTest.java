package com.example.jwtsecurity.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.jwtsecurity.common.security.Role;
import com.example.jwtsecurity.domain.member.entity.Member;
import com.example.jwtsecurity.domain.member.repository.MemberRepository;
import com.example.jwtsecurity.web.dto.LoginRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("[web] 로그인")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @DisplayName("[성공]")
  @Test
  public void success() throws Exception {
    // given
    String email = "hyune@gmail.com";
    Role role = Role.USER;
    String password = "password";
    memberRepository.save(new Member(1L, passwordEncoder.encode(password), email, role.getCode()));

    LoginRequestDTO request = new LoginRequestDTO(email, "password");

    // when
    this.mockMvc.perform(post("/api/v1/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk());

    // then

  }
}
