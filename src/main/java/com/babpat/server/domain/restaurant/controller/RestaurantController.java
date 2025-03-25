package com.babpat.server.domain.restaurant.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.domain.restaurant.dto.response.RecommendationResponse;
import com.babpat.server.domain.restaurant.dto.response.RestaurantInfo;
import com.babpat.server.domain.restaurant.service.RestaurantQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {
    private final RestaurantQueryService restaurantQueryService;

    @GetMapping("/recommendation")
    public ResponseEntity<ApiResponse<RecommendationResponse>> getBabpat() {
        RecommendationResponse response = restaurantQueryService.getRecommendationRestaurants();

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<RestaurantInfo>>> searchRestaurants(
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<RestaurantInfo> response = restaurantQueryService.searchRestaurant(keyword, pageable);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }
}
