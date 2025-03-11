package com.babpat.server.domain.babpat.entity.enums;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;

public enum MealSpeed {
    FAST,
    MIDDLE,
    SLOW,
    ;

    public static MealSpeed fromString(String mealSpeed) {
        return switch (mealSpeed.toLowerCase()) {
            case "fast" -> FAST;
            case "middle" -> MIDDLE;
            case "slow" -> SLOW;
            default -> throw new CustomException(CustomResponseStatus.INVALID_MEAL_SPEED);
        };
    }
}
