package com.babpat.server.domain.restaurant.dto.response;

import java.util.List;

public record RecommendationResponse(
        List<RestaurantInfo> recommendations
) {

    public record RestaurantInfo(
            String name,
            String mainMenus,
            List<String> categories,
            String thumbnailUrl,
            Boolean isPromotion
    ) {}
}
