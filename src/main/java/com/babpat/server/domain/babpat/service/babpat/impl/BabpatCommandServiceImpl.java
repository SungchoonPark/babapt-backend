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

    @Override
    public void applyBabpat(BabpatApplyRequest applyRequest) {
        Babpat babpat = babpatQueryService.getBabpatDetail(applyRequest.babpatId());
        participationCommandService.applyBabpat(babpat.getHeadCount(), applyRequest);
    }

    @Override
    public void postBabpat(BabpatPostReqDto babpatPostReqDto) {
        if (participationQueryService.isExistParticipationByDateTime(
                babpatPostReqDto.leader(),
                babpatPostReqDto.date(),
                babpatPostReqDto.time())
        ) {
            throw new CustomException(CustomResponseStatus.BABPAT_ALREADY_EXIST);
        }

        Babpat babpat = babpatRepository.save(babpatPostReqDto.toBabpat());
        participationCommandService.registerParticipation(babpat.getId(), babpatPostReqDto);
    }
}
