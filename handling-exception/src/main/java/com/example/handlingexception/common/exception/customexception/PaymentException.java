package com.example.handlingexception.common.exception.customexception;

import com.example.handlingexception.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

  private final ErrorCode errorCode;
  private final String orderId;

  public PaymentException(ErrorCode errorCode) {
    super(errorCode.getReason());
    this.errorCode = errorCode;
    this.orderId = "";
  }

  public PaymentException(ErrorCode errorCode, String orderId) {
    super(errorCode.getReason());
    this.errorCode = errorCode;
    this.orderId = orderId;
  }
}
