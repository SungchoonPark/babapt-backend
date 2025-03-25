package com.babpat.server.domain.restaurant.service;

import com.babpat.server.domain.restaurant.dto.response.RecommendationResponse;
import com.babpat.server.domain.restaurant.dto.response.RestaurantInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantQueryService {
    RecommendationResponse getRecommendationRestaurants();
    Page<RestaurantInfo> searchRestaurant(String keyword, Pageable pageable);
}
