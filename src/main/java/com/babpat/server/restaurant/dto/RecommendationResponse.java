package com.babpat.server.restaurant.dto;

import java.util.List;

public record RecommendationResponse(
        List<RestaurantInfo> recommendations
) {

    public record RestaurantInfo(
            String name,
            String mainMenus,
            List<String> categories,
            String thumbnailUrl
    ) {}
}
