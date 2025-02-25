package com.babpat.server.babpat.service;

import com.babpat.server.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.common.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BabpatMainService {
    private final BabpatService babpatService;
    private final ParticipationService participationService;

    @Transactional
    public void postBabpat(BabpatPostReqDto babpatPostReqDto) {
        // 1. 일정 시간안에 참여한 밥팟이 없는지 체크하기 -> 없다면 저장하기
        if (participationService.isExistParticipationByDateTime(
                babpatPostReqDto.leader(),
                babpatPostReqDto.date(),
                babpatPostReqDto.time())
        ) {
            throw new CustomException(CustomResponseStatus.BABPAT_ALREADY_EXIST);
        }

        // 1. babpat에 저장
        Long babpatId = babpatService.registerBabpat(babpatPostReqDto);
        // 2. participation에 저장
        participationService.registerParticipation(babpatId, babpatPostReqDto);
    }
}
