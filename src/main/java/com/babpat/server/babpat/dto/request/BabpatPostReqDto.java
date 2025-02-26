package com.babpat.server.babpat.dto.request;

import com.babpat.server.babpat.entity.Babpat;
import com.babpat.server.babpat.entity.enums.MealSpeed;

import java.time.LocalDate;
import java.time.LocalTime;

public record BabpatPostReqDto(
        Long leader,
        String place,
        LocalDate date,
        LocalTime time,
        Integer headCount,
        String comment,
        String mealSpeed
) {

    public Babpat toBabpat() {
        return Babpat.builder()
                .leaderId(leader)
                .placeId(place)
                .comment(comment)
                .headCount(headCount)
                .patDate(date)
                .patTime(time)
                .mealSpeed(mealSpeed == null ? null : MealSpeed.fromString(mealSpeed))
                .build();
    }
}
