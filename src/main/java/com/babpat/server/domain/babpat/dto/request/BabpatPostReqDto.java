package com.babpat.server.domain.babpat.dto.request;

import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.entity.enums.BabpatStatus;
import com.babpat.server.domain.babpat.entity.enums.MealSpeed;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.restaurant.entity.Restaurant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record BabpatPostReqDto(
        @NotNull Long place,
        @NotNull LocalDate date,
        @NotNull LocalTime time,
        @NotNull Integer headCount,
        @NotBlank String comment,
        String mealSpeed
) {

    public Babpat toBabpat(Member leader, Restaurant restaurant) {
        return Babpat.builder()
                .member(leader)
                .restaurant(restaurant)
                .comment(comment)
                .headCount(headCount)
                .patDate(date)
                .patTime(time)
                .mealSpeed(mealSpeed == null ? null : MealSpeed.fromString(mealSpeed))
                .babpatStatus(BabpatStatus.ONGOING)
                .build();
    }
}
