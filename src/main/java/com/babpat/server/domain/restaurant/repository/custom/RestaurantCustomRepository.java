package com.babpat.server.domain.restaurant.repository.custom;

import com.babpat.server.domain.restaurant.dto.response.RestaurantInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantCustomRepository {

    Page<RestaurantInfo> findRestaurantHasName(String keyword, Pageable pageable);
}
