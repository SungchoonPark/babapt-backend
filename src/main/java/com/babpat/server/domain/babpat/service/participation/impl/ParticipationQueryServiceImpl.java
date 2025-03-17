package com.babpat.server.domain.babpat.service.participation.impl;

import com.babpat.server.domain.babpat.repository.ParticipationRepository;
import com.babpat.server.domain.babpat.service.participation.ParticipationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

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
