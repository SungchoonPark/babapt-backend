package com.babpat.server.domain.babpat.service.participation.impl;

import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.entity.Participation;
import com.babpat.server.domain.babpat.repository.ParticipationRepository;
import com.babpat.server.domain.babpat.service.babpat.BabpatQueryService;
import com.babpat.server.domain.babpat.service.participation.ParticipationCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationCommandServiceImpl implements ParticipationCommandService {
    private final ParticipationRepository participationRepository;
    private final BabpatQueryService babpatQueryService;

    @Override
    public void registerParticipation(Long babpatId, BabpatPostReqDto babpatPostReqDto) {
        Participation participation = Participation.builder()
                .memberId(babpatPostReqDto.leader())
                .babpatId(babpatId)
                .build();

        participationRepository.save(participation);
    }

    @Override
    public void applyBabpat(Integer headCount, BabpatApplyRequest applyRequest) {
        // Todo : 코드 최적화 가능
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
