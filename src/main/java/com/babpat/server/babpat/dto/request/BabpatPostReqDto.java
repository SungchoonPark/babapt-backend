package com.babpat.server.babpat.dto.request;

import com.babpat.server.babpat.entity.Babpat;
import com.babpat.server.babpat.entity.enums.MealSpeed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record BabpatPostReqDto(
        @NotNull Long leader,
        @NotNull Long place,
        @NotNull LocalDate date,
        @NotNull LocalTime time,
        @NotNull Integer headCount,
        @NotBlank String comment,
        String mealSpeed
) {

    public Babpat toBabpat() {
        return Babpat.builder()
                .leaderId(leader)
                .restaurantId(place)
                .comment(comment)
                .headCount(headCount)
                .patDate(date)
                .patTime(time)
                .mealSpeed(mealSpeed == null ? null : MealSpeed.fromString(mealSpeed))
                .build();
    }
}
