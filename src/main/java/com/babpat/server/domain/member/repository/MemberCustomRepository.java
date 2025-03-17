package com.babpat.server.domain.member.repository;

import com.babpat.server.domain.member.dto.response.MemberInfoResponse;

public interface MemberCustomRepository {
    MemberInfoResponse getMemberInfoByUsername(String username);
}
