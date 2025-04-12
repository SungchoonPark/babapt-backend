package com.babpat.server.domain.babpat.entity.enums;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;

public enum PatType {
  BABPAT,
  DOSIRAK;

  public static PatType fromString(String patType) {
    return switch (patType.toLowerCase()) {
      case "babpat" -> BABPAT;
      case "dosirak" -> DOSIRAK;
      default -> throw new CustomException(CustomResponseStatus.INVALID_MEAL_SPEED);
    };
  }
}
