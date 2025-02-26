package com.babpat.server.babpat.dto.response;

import com.babpat.server.babpat.entity.enums.MealSpeed;
import com.babpat.server.member.entity.enums.Track;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record BabpatInfoRespDto(
        List<BabpatData> babpats
) {
    public record BabpatData(
            RestaurantInfo restaurantInfo,
            BabpatInfo babpatInfo
    ) {}

    public record RestaurantInfo(
            String name,
            String mainMenus,
            List<String> categories,
            String thumbnailUrl
    ) {}

    public record BabpatInfo(
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
