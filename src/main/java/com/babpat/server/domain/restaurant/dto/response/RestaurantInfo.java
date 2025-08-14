package com.babpat.server.domain.restaurant.dto.response;

import java.util.List;

public record RestaurantInfo(
        String name,
        String mainMenus,
        List<String> categories,
        String thumbnailUrl
) {}