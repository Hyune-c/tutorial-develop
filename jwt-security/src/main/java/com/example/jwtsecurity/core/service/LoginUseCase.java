package com.example.jwtsecurity.core.service;


import com.example.jwtsecurity.core.security.AuthToken;
import java.util.Optional;

public interface LoginUseCase {

  Optional<com.example.jwtsecurity.core.service.dto.MemberDTO> login(String id, String password);

  AuthToken createAuthToken(com.example.jwtsecurity.core.service.dto.MemberDTO memberDTO);
}
