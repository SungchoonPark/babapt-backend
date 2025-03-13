package com.babpat.server.domain.babpat.service.babpat;

import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.member.entity.Member;

public interface BabpatCommandService {
    void applyBabpat(BabpatApplyRequest applyRequest);
    void postBabpat(BabpatPostReqDto babpatPostReqDto, String authUsername);
}
