package com.babpat.server.restaurant.controller;

import com.babpat.server.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.restaurant.dto.RecommendationResponse;
import com.babpat.server.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/recommendation")
    public ResponseEntity<ApiResponse<RecommendationResponse>> getBabpat() {
        RecommendationResponse response = restaurantService.getRecommendationRestaurants();

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }
}
