package com.babpat.server.domain.babpat.service.babpat.impl;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.repository.BabpatRepository;
import com.babpat.server.domain.babpat.repository.ParticipationRepository;
import com.babpat.server.domain.babpat.service.babpat.BabpatQueryService;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.repository.MemberRepository;
import com.babpat.server.domain.restaurant.entity.Restaurant;
import com.babpat.server.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BabpatQueryServiceImpl implements BabpatQueryService {
    private final MemberRepository memberRepository;
    private final BabpatRepository babpatRepository;
    private final ParticipationRepository participationRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public BabpatInfoRespDto getBabpat() {
        List<BabpatInfoRespDto.BabpatData> babpatDatas = new ArrayList<>();
        // 1. 밥팟 테이블에서 정보 가져오기
        List<Babpat> babpats = babpatRepository.findAll();
        // 2. 해당 식당 정보 가져오기
        for (Babpat babpat : babpats) {
            Long restaurantId = babpat.getRestaurant().getId();

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new CustomException(CustomResponseStatus.RESTAURANT_NOT_EXIST));

            Member member = memberRepository.findById(babpat.getMember().getId())
                    .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST));

            babpatDatas.add(
                    new BabpatInfoRespDto.BabpatData(
                            new BabpatInfoRespDto.RestaurantInfo(
                                    restaurant.getName(),
                                    String.valueOf(restaurant.getMenus()),
                                    parsingCategories(restaurant.getCategory1(), restaurant.getCategory2()),
                                    restaurant.getThumbnail()
                            ),

                            new BabpatInfoRespDto.BabpatInfo(
                                    babpat.getId(),
                                    babpat.getComment(),
                                    new BabpatInfoRespDto.Capacity(
                                            babpat.getHeadCount(),
                                            participationRepository.countByBabpatId(babpat.getId()).intValue()
                                    ),
                                    babpat.getMealSpeed(),
                                    babpat.getPatDate(),
                                    babpat.getPatTime(),
                                    new BabpatInfoRespDto.LeaderProfile(
                                            member.getName(),
                                            member.getNickname(),
                                            member.getTrack()
                                    )
                            )
                    )
            );
        }

        // 3. dto 완성해서 내려주기
        return new BabpatInfoRespDto(babpatDatas);
    }

    @Override
    public Babpat getBabpatDetail(Long babpatId) {
        return babpatRepository.findById(babpatId)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.BABPAT_NOT_EXIST));
    }

    private List<String> parsingCategories(String category1, String category2) {
        return Arrays.stream((category1 + "," + category2).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
