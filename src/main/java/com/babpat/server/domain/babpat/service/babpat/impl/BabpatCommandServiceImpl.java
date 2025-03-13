package com.babpat.server.domain.babpat.service.babpat.impl;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.repository.BabpatRepository;
import com.babpat.server.domain.babpat.service.babpat.BabpatCommandService;
import com.babpat.server.domain.babpat.service.babpat.BabpatQueryService;
import com.babpat.server.domain.babpat.service.participation.ParticipationCommandService;
import com.babpat.server.domain.babpat.service.participation.ParticipationQueryService;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.repository.MemberRepository;
import com.babpat.server.domain.restaurant.entity.Restaurant;
import com.babpat.server.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BabpatCommandServiceImpl implements BabpatCommandService {
    private final BabpatRepository babpatRepository;
    private final BabpatQueryService babpatQueryService;
    private final ParticipationQueryService participationQueryService;
    private final ParticipationCommandService participationCommandService;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public void applyBabpat(BabpatApplyRequest applyRequest, String applyUsername) {
        Babpat babpat = babpatQueryService.getBabpatDetail(applyRequest.babpatId());
        participationCommandService.applyBabpat(babpat.getHeadCount(), applyUsername, applyRequest);
    }

    @Override
    public void postBabpat(BabpatPostReqDto babpatPostReqDto, String authUsername) {
        Member leader = memberRepository.findByUsername(authUsername)
                .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_EXIST));

        Restaurant restaurant = restaurantRepository.findById(babpatPostReqDto.place())
                .orElseThrow(() -> new CustomException(CustomResponseStatus.RESTAURANT_NOT_EXIST));

        if (participationQueryService.isExistParticipationByDateTime(
                leader.getId(),
                babpatPostReqDto.date(),
                babpatPostReqDto.time())
        ) {
            throw new CustomException(CustomResponseStatus.BABPAT_ALREADY_EXIST);
        }

        Babpat babpat = babpatRepository.save(babpatPostReqDto.toBabpat(leader, restaurant));
        participationCommandService.registerParticipation(babpat, leader);
    }
}
