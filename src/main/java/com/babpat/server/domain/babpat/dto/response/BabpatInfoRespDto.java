package com.babpat.server.domain.babpat.dto.response;

import com.babpat.server.domain.babpat.entity.enums.MealSpeed;
import com.babpat.server.domain.member.entity.enums.Track;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record BabpatInfoRespDto(
        RestaurantInfo restaurantInfo,
        BabpatInfo babpatInfo
) {
    public record RestaurantInfo(
            String name,
            String mainMenus,
            List<String> categories,
            String thumbnailUrl
    ) {}

    public record BabpatInfo(
            Long id,
            String comment,
            Capacity capacity,
            MealSpeed mealSpeed,
            LocalDate date,
            LocalTime time,
            LeaderProfile leaderProfile
    ) {}

    public record Capacity(
            int totalSlots,
            int filledSlots
    ) {}

    public record LeaderProfile(
            String name,
            String nickname,
            Track track
    ) {}
}
