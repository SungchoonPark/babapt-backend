package com.babpat.server.domain.babpat.service.babpat;

import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.member.entity.Member;

public interface BabpatCommandService {
    void applyBabpat(BabpatApplyRequest applyRequest, String authUsername);
    void postBabpat(BabpatPostReqDto babpatPostReqDto, String authUsername);
    void delete(Long babpatId, String username);
}
