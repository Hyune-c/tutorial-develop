package com.example.jwtsecurity.provider.security;

import com.example.jwtsecurity.core.security.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthToken implements AuthToken<Claims> {

  private static final String AUTHORITIES_KEY = "role";
  @Getter
  private final String token;
  private final Key key;

  JwtAuthToken(String token, Key key) {
    this.token = token;
    this.key = key;
  }

  JwtAuthToken(String id, String role, Date expiredDate, Key key) {
    this.key = key;
    this.token = createJwtAuthToken(id, role, expiredDate).get();
  }

  @Override
  public boolean validate() {
    return getData() != null;
  }

  @Override
  public Claims getData() {
    try {
      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    } catch (SecurityException e) {
      log.info("Invalid JWT signature.");
    } catch (MalformedJwtException e) {
      log.info("Invalid JWT token.");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token.");
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token.");
    } catch (IllegalArgumentException e) {
      log.info("JWT token compact of handler are invalid.");
    }
    return null;
  }

  private Optional<String> createJwtAuthToken(String id, String role, Date expiredDate) {

    String token = Jwts.builder()
        .setSubject(id)
        .claim(AUTHORITIES_KEY, role)
        .signWith(key, SignatureAlgorithm.HS256)
        .setExpiration(expiredDate)
        .compact();

    return Optional.ofNullable(token);
  }
}
