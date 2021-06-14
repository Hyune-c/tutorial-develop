package com.example.jwtsecurity.common.security;

import com.example.jwtsecurity.common.exception.customexception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {

  private static final String AUTHORITIES_KEY = "role";
  private final Key key;

  public JwtAuthTokenProvider(String base64Secret) {
    byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public JwtAuthToken createAuthToken(String id, String role, Date expiredDate) {
    return new JwtAuthToken(id, role, expiredDate, key);
  }

  @Override
  public JwtAuthToken convertAuthToken(String token) {
    return new JwtAuthToken(token, key);
  }

  @Override
  public Authentication getAuthentication(JwtAuthToken authToken) {

    if (authToken.validate()) {
      Claims claims = authToken.getClaims();
      Collection<? extends GrantedAuthority> authorities =
          Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

      User principal = new User(claims.getSubject(), "", authorities);

      return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
    } else {
      throw new TokenValidFailedException();
    }
  }
}
