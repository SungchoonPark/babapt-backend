package com.babpat.server.domain.babpat.service.participation.impl;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.repository.BabpatRepository;
import com.babpat.server.domain.babpat.repository.ParticipationRepository;
import com.babpat.server.domain.babpat.service.participation.ParticipationQueryService;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.member.repository.MemberRepository;
import com.babpat.server.domain.restaurant.entity.Restaurant;
import com.babpat.server.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationQueryServiceImpl implements ParticipationQueryService {
    private final ParticipationRepository participationRepository;

    @Override
    public boolean isExistParticipationByDateTime(Long memberId, LocalDate patDate, LocalTime patTime) {
        LocalTime startTime = patTime.minusHours(1);
        LocalTime endTime = patTime.plusHours(1);

        return participationRepository.existsParticipationWithinOneHour(memberId, patDate, startTime, endTime);
    }
}
