package com.babpat.server.domain.restaurant.repository;

import com.babpat.server.domain.restaurant.entity.Restaurant;
import com.babpat.server.domain.restaurant.repository.custom.RestaurantCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, RestaurantCustomRepository {
}
