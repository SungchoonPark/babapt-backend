package com.babpat.server.domain.babpat.service.participation;

import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.member.entity.Member;

public interface ParticipationCommandService {
    void registerParticipation(Babpat babpat, Member leader);
    void applyBabpat(Integer headCount, String applyUsername, BabpatApplyRequest applyRequest);
}
