package com.babpat.server.babpat.service;

import com.babpat.server.babpat.dto.request.BabpatPostReqDto;
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
        // 1. 이미 참가한 밥팟이 있는지 확인
        // babpatService.isExistBabpatByDateTime();

        // 2. 참여한 밥팟이 없다면 통과했으면 저장하기
        // participationService.isExistParticipationByDateTime();

        // 1. babpat에 저장
        Long babpatId = babpatService.registerBabpat(babpatPostReqDto);
        // 2. participation에 저장
        participationService.registerParticipation(babpatId, babpatPostReqDto);
    }
}
