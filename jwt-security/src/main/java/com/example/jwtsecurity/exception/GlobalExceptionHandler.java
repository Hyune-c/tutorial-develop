package com.example.jwtsecurity.exception;

import com.example.jwtsecurity.core.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  protected ResponseEntity<CommonResponse> handleRuntimeException(RuntimeException e) {

    log.info("handleRuntimeException", e);

    CommonResponse response = CommonResponse.builder()
        .code("test")
        .message(e.getMessage())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .build();

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(com.example.jwtsecurity.exception.CustomAuthenticationException.class)
  protected ResponseEntity<CommonResponse> handleCustomAuthenticationException(com.example.jwtsecurity.exception.CustomAuthenticationException e) {

    log.info("handleCustomAuthenticationException", e);

    CommonResponse response = CommonResponse.builder()
        .code(com.example.jwtsecurity.exception.ErrorCode.AUTHENTICATION_FAILED.getCode())
        .message(e.getMessage())
        .status(com.example.jwtsecurity.exception.ErrorCode.AUTHENTICATION_FAILED.getStatus())
        .build();

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(com.example.jwtsecurity.exception.LoginFailedException.class)
  protected ResponseEntity<CommonResponse> handleLoginFailedException(com.example.jwtsecurity.exception.LoginFailedException e) {

    log.info("handleLoginFailedException", e);

    CommonResponse response = CommonResponse.builder()
        .code(com.example.jwtsecurity.exception.ErrorCode.Login_FAILED.getCode())
        .message(e.getMessage())
        .status(com.example.jwtsecurity.exception.ErrorCode.Login_FAILED.getStatus())
        .build();

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(BadCredentialsException.class)
  protected ResponseEntity<CommonResponse> handleBadCredentialsException(BadCredentialsException e) {

    log.info("handleBadCredentialsException", e);

    CommonResponse response = CommonResponse.builder()
        .code(com.example.jwtsecurity.exception.ErrorCode.Login_FAILED.getCode())
        .message(e.getMessage())
        .status(com.example.jwtsecurity.exception.ErrorCode.Login_FAILED.getStatus())
        .build();

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<CommonResponse> handleAccessDeniedException(AccessDeniedException e) {

    log.info("handleAccessDeniedException", e);

    CommonResponse response = CommonResponse.builder()
        .code(com.example.jwtsecurity.exception.ErrorCode.ACCESS_DENIED.getCode())
        .message(com.example.jwtsecurity.exception.ErrorCode.ACCESS_DENIED.getMessage())
        .status(com.example.jwtsecurity.exception.ErrorCode.ACCESS_DENIED.getStatus())
        .build();

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(InsufficientAuthenticationException.class)
  protected ResponseEntity<CommonResponse> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {

    log.info("handleInsufficientAuthenticationException", e);

    CommonResponse response = CommonResponse.builder()
        .code(com.example.jwtsecurity.exception.ErrorCode.AUTHENTICATION_FAILED.getCode())
        .message(com.example.jwtsecurity.exception.ErrorCode.AUTHENTICATION_FAILED.getMessage())
        .status(com.example.jwtsecurity.exception.ErrorCode.AUTHENTICATION_FAILED.getStatus())
        .build();

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }
}
