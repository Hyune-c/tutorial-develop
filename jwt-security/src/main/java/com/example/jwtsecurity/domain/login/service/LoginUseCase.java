package com.example.jwtsecurity.domain.login.service;


import com.example.jwtsecurity.common.security.AuthToken;
import com.example.jwtsecurity.domain.member.dto.MemberDTO;
import java.util.Optional;

public interface LoginUseCase {

  Optional<MemberDTO> login(String id, String password);

  AuthToken createAuthToken(MemberDTO memberDTO);
}
