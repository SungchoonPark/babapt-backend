package com.babpat.server.domain.babpat.service.participation;

import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.babpat.entity.Babpat;

public interface ParticipationCommandService {
    void registerParticipation(Babpat babpat, BabpatPostReqDto babpatPostReqDto);
    void applyBabpat(Integer headCount, BabpatApplyRequest applyRequest);
}
