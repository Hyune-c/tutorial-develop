package com.example.handlingexception.common.exception.customexception;

import com.example.handlingexception.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getReason());
    this.errorCode = errorCode;
  }
}
