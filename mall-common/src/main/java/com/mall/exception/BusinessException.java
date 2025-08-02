package com.mall.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author : Tomatos
 * @date : 2025/8/2
 */
@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException {
  /**
   * 异常码
   */
  private int code;

  /**
   * 具体异常信息
   */
  private String message;

  public BusinessException() {
    super();
  }

  public BusinessException(String message) {
    this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    this.message = message;
  }
}
