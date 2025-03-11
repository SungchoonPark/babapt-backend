package com.babpat.server.domain.babpat.service;

import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.babpat.entity.Participation;
import com.babpat.server.domain.babpat.repository.ParticipationRepository;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipationService {
    private final ParticipationRepository participationRepository;

    public void registerParticipation(Long babpatId, BabpatPostReqDto babpatPostReqDto) {
        Participation participation = Participation.builder()
                .memberId(babpatPostReqDto.leader())
                .babpatId(babpatId)
                .build();

        participationRepository.save(participation);
    }

    public boolean isExistParticipationByDateTime(Long memberId, LocalDate patDate, LocalTime patTime) {
        LocalTime startTime = patTime.minusHours(1);
        LocalTime endTime = patTime.plusHours(1);

        return participationRepository.existsParticipationWithinOneHour(memberId, patDate, startTime, endTime);
    }

    public void applyBabpat(int headCount, BabpatApplyRequest applyRequest) {
        if (participationRepository.existsByBabpatIdAndMemberId(applyRequest.babpatId(), applyRequest.userId())) {
            throw new CustomException(CustomResponseStatus.ALREADY_PARTICIPATION);
        }

        Long filledSlots = participationRepository.countByBabpatId(applyRequest.babpatId());

        if (headCount - filledSlots <= 0) {
            throw new CustomException(CustomResponseStatus.BABPAT_CLOSED);
        }

        participationRepository.save(applyRequest.toEntity());
    }
}
