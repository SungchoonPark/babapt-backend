package com.babpat.server.babpat.service;

import com.babpat.server.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.babpat.entity.Babpat;
import com.babpat.server.babpat.repository.BabpatRepository;
import com.babpat.server.babpat.repository.ParticipationRepository;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.member.entity.Member;
import com.babpat.server.member.repository.MemberRepository;
import com.babpat.server.restaurant.entity.Restaurant;
import com.babpat.server.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BabpatService {
    private final BabpatRepository babpatRepository;
    private final RestaurantRepository restaurantRepository;
    private final MemberRepository memberRepository;
    private final ParticipationRepository participationRepository;

    public Long registerBabpat(BabpatPostReqDto babpatPostReqDto) {
        Babpat babpat = babpatRepository.save(babpatPostReqDto.toBabpat());
        return babpat.getId();
    }

    public BabpatInfoRespDto getBabpat() {
        List<BabpatInfoRespDto.BabpatData> babpatDatas = new ArrayList<>();
        // 1. 밥팟 테이블에서 정보 가져오기
        List<Babpat> babpats = babpatRepository.findAll();
        // 2. 해당 식당 정보 가져오기
        for (Babpat babpat : babpats) {
            Long restaurantId = babpat.getRestaurantId();

            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(() -> new CustomException(CustomResponseStatus.RESTAURANT_NOT_EXIST));

            Member member = memberRepository.findById(babpat.getLeaderId())
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

    private List<String> parsingCategories(String category1, String category2) {
        return Arrays.stream((category1 + "," + category2).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public Babpat getBabpatDetail(Long babpatId) {
        return babpatRepository.findById(babpatId)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.BABPAT_NOT_EXIST));
    }
}
