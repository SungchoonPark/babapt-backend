package com.babpat.server.domain.babpat.service.participation;

import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;

public interface ParticipationCommandService {
    void registerParticipation(Long babpatId, BabpatPostReqDto babpatPostReqDto);
    void applyBabpat(Integer headCount, BabpatApplyRequest applyRequest);
}
