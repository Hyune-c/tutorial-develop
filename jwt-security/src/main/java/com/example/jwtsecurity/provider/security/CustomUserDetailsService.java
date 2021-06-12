package com.example.jwtsecurity.provider.security;

import com.example.jwtsecurity.core.entity.Member;
import com.example.jwtsecurity.core.repository.MemberRepository;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return memberRepository.findByEmail(email)
        .map(this::createSpringSecurityUser)
        .orElseThrow(RuntimeException::new);
  }

  private User createSpringSecurityUser(Member member) {
    List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(member.getRole()));
    //TODO: username 에 email을 넣는 방법이 적합한지?
    return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
  }
}
