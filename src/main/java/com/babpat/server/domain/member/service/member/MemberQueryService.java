package com.babpat.server.domain.member.service.member;

import com.babpat.server.domain.member.dto.request.IdCheckRequestDto;
import com.babpat.server.domain.member.dto.response.IdCheckRespDto;

public interface MemberQueryService {
    IdCheckRespDto isExistId(IdCheckRequestDto requestDto);
}
