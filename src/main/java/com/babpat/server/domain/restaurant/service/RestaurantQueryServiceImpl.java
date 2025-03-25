package com.babpat.server.domain.restaurant.service;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.restaurant.dto.response.RecommendationResponse;
import com.babpat.server.domain.restaurant.dto.response.RestaurantInfo;
import com.babpat.server.domain.restaurant.entity.Restaurant;
import com.babpat.server.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantQueryServiceImpl implements RestaurantQueryService {

    private final RestaurantRepository restaurantRepository;
    @Override
    public RecommendationResponse getRecommendationRestaurants() {
        List<Long> ids = List.of(92L, 130L, 20L, 12L);
        List<RecommendationResponse.RestaurantInfo> restaurantInfos = new ArrayList<>();

        int count = 0;
        for (Long id : ids) {
            count++;
            Restaurant restaurant = restaurantRepository.findById(id)
                    .orElseThrow(() -> new CustomException(CustomResponseStatus.RESTAURANT_NOT_EXIST));

            restaurantInfos.add(new RecommendationResponse.RestaurantInfo(
                    restaurant.getName(),
                    String.valueOf(restaurant.getMenus()),
                    parsingCategories(restaurant.getCategory1(), restaurant.getCategory2()),
                    restaurant.getThumbnail(),
                    count > 2
            ));

        }

        return new RecommendationResponse(restaurantInfos);
    }

    @Override
    public Page<RestaurantInfo> searchRestaurant(String keyword, Pageable pageable) {
        return restaurantRepository.findRestaurantHasName(keyword, pageable);
    }

    private List<String> parsingCategories(String category1, String category2) {
        return Arrays.stream((category1 + "," + category2).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
