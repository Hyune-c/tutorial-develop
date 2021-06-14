# spring security 로 JWT 인증을 구현해보자

## 환경

- spring boot 2.5.0
- jsonwebtoken

## 테스트 방법

> 테스트 케이스도 봐주세요.

- 회원 가입
    - 기본 값으로 가입됩니다.

```shell
#request 
POST http://localhost:8080/api/v1/members

#response
1
```

- 로그인

```shell
# request
POST http://localhost:8080/api/v1/login

# response
{
  "message": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoeXVuZUBnbWFpbC5jb20iLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjIzNjg4Njg5fQ.iiolEuet2QTzgu35fd8M-zVlqZ5maW1wjxAaDywUGKo",
  "status": 200,
  "code": "LOGIN_SUCCESS"
}
```

## 암호화 주요 내용

### WebSecurityConfig

- configure()
    - security 를 통해 처리되는 설정을 지정합니다.
        - authenticationEntryPoint, accessDeniedHandler
        - 인증 여부에 대한 API 를 지정합니다.

- passwordEncoder()
    - 암호화 알고리즘으로 BCryptPasswordEncoder() 를 지정합니다.

### CustomUserDetailsService

> 인증에 필요한 회원 객체를 가져오는 역할을 합니다.

- loadUserByUsername()
    - security 에서 읽을 수 있는 객체인 UserDetails 를 만듭니다.
    - 이 때 DB 에서 암호화된 비밀번호를 가져오면 됩니다.

### DaoAuthenticationProvider

> 코드에는 없지만 debug 모드로 loadUserByUsername() 이후를 step into 하여 따라가면 도달할 수 있습니다.

- additionalAuthenticationChecks()
    - 위에서 암호화된 비밀번호를 풀어주는 부분으로 `passwordEncoder.matches()` 를 호출합니다.

### LoginService

- login()
    - SecurityContextHolder.getContext().setAuthentication(authentication) 를 통해 context 에 등록하는 것이
      중요합니다.
    - 로그인 후 JWT 를 만들기 위한 memberDto 를 생성합니다.
        - 실무에서는 repository 를 거쳐 권한을 가져와야합니다.

## JWT 주요 내용

### JwtAuthTokenProvider

- JwtAuthTokenProvider()
    - JwtAuthToken 을 만들기 위한 key 를 생성합니다.
        - 본 예제에서는 salt 를 추가한 `HMAC-SHA` 알고리즘으로 생성했습니다.

### JwtAuthToken

- JwtAuthToken()
    - Jwts 를 통해 token 을 만듭니다.

### JWTFilter

- doFilter()
    - filter 를 통해 JWt 를 해석하고 context 에 등록합니다.
