package com.babpat.server.domain.member.service.member;

import com.babpat.server.domain.member.dto.request.IdCheckRequestDto;
import com.babpat.server.domain.member.dto.response.IdCheckRespDto;
import com.babpat.server.domain.member.dto.response.MemberInfoResponse;

public interface MemberQueryService {
    IdCheckRespDto isExistId(IdCheckRequestDto requestDto);

    MemberInfoResponse getMemberInfo(String authUsername);
}
