package com.babpat.server.domain.babpat.dto.response;

import com.babpat.server.domain.babpat.entity.enums.MealSpeed;
import com.babpat.server.domain.babpat.entity.enums.PatType;
import com.babpat.server.domain.member.entity.enums.Track;
import com.babpat.server.domain.restaurant.dto.response.RestaurantInfo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record BabpatInfoRespDto(
    RestaurantInfo restaurantInfo,
    BabpatInfo babpatInfo
) {


  public record BabpatInfo(
      Long id,
      String comment,
      Capacity capacity,
      MealSpeed mealSpeed,
      LocalDate date,
      LocalTime time,
      PatType patType,
      LeaderProfile leaderProfile,
      List<JoinMemberProfile> joinMemberProfiles
  ) {

  }

  public record Capacity(
      int totalSlots,
      int filledSlots
  ) {

  }

  public record LeaderProfile(
      Long leaderId,
      String name,
      String nickname,
      Track track
  ) {

  }

  public record JoinMemberProfile(
      Long id,
      String name,
      String nickname,
      Track track
  ) {

  }
}
